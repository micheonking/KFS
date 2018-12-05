package myApp.server.apr;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.html.simpleparser.StyleSheet;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

import myApp.client.service.ServiceRequest;
import myApp.client.vi.apr.model.Apr01_ApprModel;
import myApp.client.vi.apr.model.Apr10_StampModel;
import myApp.client.vi.emp.model.Emp00_InfoModel;
import myApp.client.vi.sys.model.Sys01_CompanyModel;
import myApp.client.vi.sys.model.Sys10_FileModel;
import myApp.server.utils.db.DatabaseFactory;
import myApp.server.utils.file.MyAppProperties;
import myApp.server.utils.pdf.CellLayout;

public class Apr10_StampPDF extends PdfPageEventHelper {

	//결재완료시
	public void getDocument(ServiceRequest request) throws NumberFormatException, DocumentException, IOException {
		Long keyId = Long.parseLong(request.getStringParam("keyId"));
		Long stampId = 0L;
		if(request.getStringParam("stampId") != null) {
			stampId = Long.parseLong(request.getStringParam("stampId"));
		}
		
		SqlSession sqlSession = DatabaseFactory.openSession();
		Apr10_StampModel stampModel = sqlSession.selectOne("apr10_stamp.selectOneStampFileId", stampId);
		Long fileId = sqlSession.selectOne("dbConfig.getSeq");

		Map<String, Object> empParam = new HashMap<String, Object>();
		empParam.put("empId", request.getLongParam("regEmpId"));
		empParam.put("orgId", request.getLongParam("regOrgId"));
		
		System.out.println("(ServiceRequest) orgID ===============> "+request.getLongParam("regOrgId"));
		
		Emp00_InfoModel empModel = sqlSession.selectOne("emp00_info.selectByEmpId", empParam);
		String regEmp = "";
		if(empModel.getKorName() != null) {
			regEmp = empModel.getTitleName() + " " + empModel.getKorName();
		}
		String regEmail = "";
		if(empModel.getEmailAddress() != null) {
			regEmail = empModel.getEmailAddress();
		}
		String regTel = "";
		if(empModel.getEmergencyTelNo() != null) {
			regTel = empModel.getEmergencyTelNo();
		}
		
		//팀장(SQL 한번 더 돌려야함)
		String teamEmp = "";
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("empId", request.getLongParam("regEmpId"));
//		param.put("regDate", request.getStringParam("regDate"));
//		Emp00_InfoModel teamLeaderModel = sqlSession.selectOne("emp00_info.selectTeamLeader", param);
//		if(teamLeaderModel != null) {
//			teamEmp = teamLeaderModel.getTitleName() + " " + teamLeaderModel.getKorName();
//		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("empId", request.getLongParam("regEmpId"));
		param.put("orgId", request.getLongParam("regOrgId"));
		param.put("baseDate", request.getStringParam("regDate"));
		
		System.out.println("emp id is " + request.getLongParam("regEmpId")); 
		System.out.println("org id is " + request.getLongParam("regOrgId"));
		System.out.println("base date is " + request.getStringParam("regDate"));
		
		Emp00_InfoModel teamLeaderModel = sqlSession.selectOne("emp00_info.selectOrgLeaderByEmpId", param);

		if(teamLeaderModel != null) {
			teamEmp = teamLeaderModel.getTitleName() + " " + teamLeaderModel.getKorName();
		}
		
		Sys01_CompanyModel companyModel = sqlSession.selectOne("sys01_company.selectById", empModel.getCompanyId());
		String companyAdd = "";
		if(companyModel.getZipAddress() != null) {
			companyAdd += companyModel.getZipAddress() + " ";
		}
		if(companyModel.getZipDetail() != null) {
			companyAdd += companyModel.getZipDetail();
		}
		Apr01_ApprModel apprModel = sqlSession.selectOne("apr01_appr.selectById", request.getLongParam("keyId"));
		String stampSeq = "";
		if(apprModel != null) {
			stampSeq = checkNull(String.valueOf(apprModel.getStampSeq()));
		}
		
		
		sqlSession.close();
		
		Long stampFileId = 0L;
		if(stampModel.getStampFileId() != null) {
			stampFileId = stampModel.getStampFileId();
		}
		Map<String, Object> map = request.getParam();
		map.put("regEmp", regEmp);
		map.put("regEmail", regEmail);
		map.put("regTel", regTel);
		map.put("teamEmp", teamEmp);
		map.put("companyAdd", companyAdd);
		map.put("stampSeq", stampSeq);
		
		Document document = new Document(PageSize.A4, 0, 0, 0, 0);
		//원본 PDF 생성
		FileOutputStream originPDF = new FileOutputStream(getPdfPath());
		PdfWriter writer = PdfWriter.getInstance(document, originPDF);
		try {
			document.open();
			makeLayout(document, map);
		} catch (Exception e) {
			if(document != null) {document.close();}
			if(originPDF != null) {originPDF.close();}
			if(writer != null) {writer.close();}
		} finally {
			if(document != null) {document.close();}
			if(originPDF != null) {originPDF.close();}
		}
		
		File subDir  = new File(this.getUploadPath(fileId));
        if(!subDir.exists()) {
        	subDir.mkdir(); // 해당 폴더가 없으면 신규로 만든다.   
        }

		PdfReader pdfReader2 = getOriginFile();
	    FileOutputStream copyPDF = new FileOutputStream(this.getUploadPath(fileId)+ getFolderPath() + fileId);
	    PdfStamper pdfStamper2 = new PdfStamper(pdfReader2, copyPDF);
		try {
			//실제 파일 만들기
			writeFile(pdfReader2, pdfStamper2, stampFileId);
		} catch (Exception e) {
		} finally {
			if(pdfStamper2 != null) {pdfStamper2.close();}
			if(pdfReader2 != null) {pdfReader2.close();}
			if(copyPDF != null) {copyPDF.close();}
			if(writer != null) {writer.close();}
		}
		
		insertFileModel(keyId, fileId, request.getStringParam("title"), stampSeq);
	}
	
	public void aaa(BufferedOutputStream bufferedOutputStream, String str) throws DocumentException, IOException {
		try {
			str = str.replace("<br>", "<br/>");//iText 에서는br태그 인식못함
			str = str.replace("%26nbsp;", " ");//iText 에서는&nbsp;태그 인식못함
			// Document 생성
			Document document = new Document(PageSize.A4, 0, 0, 50, 0);
			
			// PdfWriter 생성
			//원본 PDF 생성
			FileOutputStream originPDF = new FileOutputStream(getPdfPath());
			PdfWriter writer = PdfWriter.getInstance(document, bufferedOutputStream);
			//PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("d:/test.pdf")); // 바로 다운로드.
			writer.setInitialLeading(12.5f);
			
			// 파일 다운로드 설정
			String fileName = URLEncoder.encode("한글파일명", "UTF-8"); // 파일명이 한글일 땐 인코딩 필요
			
			// Document 오픈
			document.open();
			XMLWorkerHelper helper = XMLWorkerHelper.getInstance();
			
			// CSS
			CSSResolver cssResolver = new StyleAttrCSSResolver();
			CssFile cssFile = helper.getCSS(new FileInputStream("E:\\WebFiles\\font\\pdf.css"));
			cssResolver.addCss(cssFile);
			
			// HTML, 폰트 설정
			XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
			fontProvider.register("E:\\WebFiles\\font\\HIGothicM.ttf", "MalgunGothic"); // MalgunGothic은 alias,
			CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
			
			HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
			htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
			
			// Pipelines
			PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
			HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
			CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);
			
			XMLWorker worker = new XMLWorker(css, true);
			XMLParser xmlParser = new XMLParser(worker, Charset.forName("UTF-8"));
			
			// 폰트 설정에서 별칭으로 줬던 "MalgunGothic"을 html 안에 폰트로 지정한다.
			String htmlStr = "<html><head><body style='font-family:MalgunGothic;'>"
					+ str
					+ "</body></head></html>";
			
			StringReader strReader = new StringReader(htmlStr);
			xmlParser.parse(strReader);
			
			document.close();
			writer.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
	}
	
	//미리보기 실행시
	public void getDocument(BufferedOutputStream bufferedOutputStream, HttpServletRequest request) throws DocumentException, IOException {
		//aaa(bufferedOutputStream, request.getParameter("content"));
		
		if(true) {
			//return;
		}
		
		Long keyId = Long.parseLong(request.getParameter("keyId"));
		Long stampId = 0L;
		if(request.getParameter("stampId") != null) {
			if(!"undefined".equals(request.getParameter("stampId"))) {
				stampId = Long.parseLong(request.getParameter("stampId"));
			}
		}
		
		SqlSession sqlSession = DatabaseFactory.openSession();
		Apr10_StampModel stampModel = sqlSession.selectOne("apr10_stamp.selectOneStampFileId", stampId);

		Map<String, Object> empParam = new HashMap<String, Object>();
		empParam.put("empId", Long.parseLong(request.getParameter("regEmpId")));
		empParam.put("orgId", Long.parseLong(request.getParameter("regOrgId")));
		
		System.out.println("(BufferedOutputStream) orgID ===============> "+Long.parseLong(request.getParameter("regOrgId")));
		
		Emp00_InfoModel empModel = sqlSession.selectOne("emp00_info.selectByEmpId", empParam);
		String regEmp = "";
		if(empModel.getKorName() != null) {
			regEmp = empModel.getTitleName() + " " + empModel.getKorName();
		}
		String regEmail = "";
		if(empModel.getEmailAddress() != null) {
			regEmail = empModel.getEmailAddress();
		}
		String regTel = "";
		if(empModel.getEmergencyTelNo() != null) {
			regTel = empModel.getEmergencyTelNo();
		}
		
		//팀장(SQL 한번 더 돌려야함)
		String teamEmp = "";
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("empId", Long.parseLong(request.getParameter("regEmpId")));
		param.put("orgId", Long.parseLong(request.getParameter("regOrgId")));
		param.put("baseDate", request.getParameter("regDate"));
		
		System.out.println("200 emp id is " + Long.parseLong(request.getParameter("regEmpId"))); 
		System.out.println("200 org id is " + Long.parseLong(request.getParameter("regOrgId")));
		System.out.println("200 base date is " + request.getParameter("regDate"));
		
		Emp00_InfoModel teamLeaderModel = sqlSession.selectOne("emp00_info.selectOrgLeaderByEmpId", param);

		if(teamLeaderModel != null) {
			teamEmp = teamLeaderModel.getTitleName() + " " + teamLeaderModel.getKorName();
		}
		
		Sys01_CompanyModel companyModel = sqlSession.selectOne("sys01_company.selectById", empModel.getCompanyId());
		String companyAdd = "";
		if(companyModel.getZipAddress() != null) {
			companyAdd += companyModel.getZipAddress() + " ";
		}
		if(companyModel.getZipDetail() != null) {
			companyAdd += companyModel.getZipDetail();
		}
		
		sqlSession.close();
		
		Long stampFileId = 0L;
		if(stampModel.getStampFileId() != null) {
			stampFileId = stampModel.getStampFileId();
		}
		Map<String, Object> map = new HashMap();
		Enumeration<String> enuma = request.getParameterNames();
		try {
			while(enuma.hasMoreElements()) {
				String name = (String)enuma.nextElement();
				String value[] = request.getParameterValues(name);
				for(int i = 0 ; i < value.length ; i++) {
					map.put(name, value[i]);
				}
			}
			map.put("regEmp", regEmp);
			map.put("regEmail", regEmail);
			map.put("regTel", regTel);
			map.put("teamEmp", teamEmp);
			map.put("companyAdd", companyAdd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Document document = new Document(PageSize.A4, 0, 0, 0, 0);
		//원본 PDF 생성
		FileOutputStream originPDF = new FileOutputStream(getPdfPath());
		PdfWriter writer = PdfWriter.getInstance(document, originPDF);
		try {
			document.open();
			makeLayout(document, map);
			//makeLayout2(document, map);
			//makeLayout3();
		} catch (Exception e) {
			if(document != null) {document.close();}
			if(originPDF != null) {originPDF.close();}
			if(writer != null) {writer.close();}
		} finally {
			if(document != null) {document.close();}
			if(originPDF != null) {originPDF.close();}
		}
		
		File subDir  = new File(this.getUploadPath(keyId));
        if(!subDir.exists()) {
        	subDir.mkdir(); // 해당 폴더가 없으면 신규로 만든다.   
        }

        PdfReader pdfReader = getOriginFile();
		PdfStamper pdfStamper = new PdfStamper(pdfReader, bufferedOutputStream);
		try {
			//미리보기용 파일 만들기
			writeFile(pdfReader, pdfStamper, stampFileId);
		} catch (Exception e) {
		} finally {
			if(pdfStamper != null) {pdfStamper.close();}
			if(pdfReader != null) {pdfReader.close();}
			if(writer != null) {writer.close();}
		}
	}
	
	//미리보기 실행시(결재완료문서)
	public void getDocumentFromFile(BufferedOutputStream bufferedOutputStream, HttpServletRequest request) throws DocumentException, IOException {
		Long keyId = Long.parseLong(request.getParameter("keyId"));
		SqlSession sqlSession = DatabaseFactory.openSession();
		Apr10_StampModel stampModel = sqlSession.selectOne("apr10_stamp.getPDFId", keyId);
		keyId = stampModel.getStampId();
		sqlSession.close();
		
		PdfReader pdfReader = new PdfReader(getUploadPath(keyId) + getFolderPath() + keyId);
		PdfStamper pdfStamper = new PdfStamper(pdfReader, bufferedOutputStream);
		try {
			//미리보기용 파일 만들기
			//writeFile(pdfReader, pdfStamper, keyId);
		} catch (Exception e) {
		} finally {
			if(pdfStamper != null) {pdfStamper.close();}
			if(pdfReader != null) {pdfReader.close();}
		}
	}

	//PDF 레이아웃 생성
	private void makeLayout(Document document, Map map) throws DocumentException, IOException {
		try {
			String tempStr = "";
			
			// 현대해상고딕체M
	        CellLayout blankLayout = new CellLayout("",0);
	        CellLayout smallLayout = new CellLayout("HIGothicL",6);
	        CellLayout largelLayout = new CellLayout("HIGothicL",20);
	        CellLayout titleLayout = new CellLayout("HIGothicL",11);
	        CellLayout headerLayout = new CellLayout("HIGothicL",10); 
	        CellLayout cellLayout = new CellLayout("HIGothicL",9); 
	        
	        PdfPTable table = new PdfPTable(50); // column count s
	        table.setWidths(new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1});

			PdfPCell cell;

			//여백 및 명함이미지
			cell = titleLayout.getCell("");
			//cell.setImage(Image.getInstance(getUploadPath() + "pdf" + getFolderPath()  + "sendLogo.jpg"));
			cell.setFixedHeight(130f);
			cell.setColspan(50);
			cell.setBorder(0);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			
			//문서번호
			cell = titleLayout.getCell("문서번호 :"); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(20f);
			cell.setColspan(6);
			cell.setBorder(0);
			table.addCell(cell);

			tempStr = checkNull((String) map.get("stampSeq"));
			cell = titleLayout.getCell("HIIS-" + tempStr); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(44);
			cell.setBorder(0);
			table.addCell(cell);

			//수신
			cell = titleLayout.getCell("수      신 :"); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(20f);
			cell.setColspan(6);
			cell.setBorder(0);
			table.addCell(cell);
			
			tempStr = checkNull((String) map.get("receiveName"));
			cell = titleLayout.getCell(tempStr); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(20f);
			cell.setColspan(44);
			cell.setBorder(0);
			cell.setPaddingLeft(4);
			table.addCell(cell);

			//참조
			cell = titleLayout.getCell("참      조 :"); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(20f);
			cell.setColspan(6);
			cell.setBorder(0);
			table.addCell(cell);
			
			tempStr = checkNull((String) map.get("referenceName"));
			cell = titleLayout.getCell(tempStr); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(20f);
			cell.setColspan(44);
			cell.setBorder(0);
			cell.setPaddingLeft(4);
			table.addCell(cell);

			//제목
			tempStr = checkNull((String) map.get("title"));
			cell = titleLayout.getCell("제      목 :"); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(20f);
			cell.setColspan(6);
			cell.setBorder(0);
			table.addCell(cell);
			
			tempStr = checkNull((String) map.get("title"));
			cell = titleLayout.getCell(tempStr); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(20f);
			cell.setColspan(44);
			cell.setBorder(0);
			cell.setPaddingLeft(4);
			table.addCell(cell);

			//여백
			BaseFont bf = BaseFont.createFont(getFontPath() + "HIGothicL.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf);
			tempStr = URLDecoder.decode(checkNull((String) map.get("content")), "UTF-8");
			tempStr = tempStr.replace("<br>", "<br/>");//iText 에서는br태그 인식못함
			tempStr = tempStr.replace("%26nbsp;", " ");//iText 에서는&nbsp;태그 인식못함
			
			tempStr = tempStr.replace("<div>", "<p>");//iText 에서는 div태그 인식못함
			tempStr = tempStr.replace("</div>", "</p>");//iText 에서는 div태그 인식못함
			//tempStr = "<html><h1>Header</h1><p>A paragraph</p><p>Another Paragraph</p></html>";
			
//			String CSS = "p{line-height:200%;font-size:15px;}";
			String CSS = "p{line-height:170%;font-size:14px;}";
			for(Element e : XMLWorkerHelper.parseToElementList(tempStr, CSS)) {
				for (Chunk c:e.getChunks()){ 
			        c.setFont(new Font(bf)); 
			        
			    } 
				cell.addElement(e);
			}
			//cell = cellLayout.getCell(tempStr);
			cell.setFixedHeight(420f);
			cell.setColspan(50);
			cell.setBorder(0);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			
			//붙임
			tempStr = URLDecoder.decode(checkNull((String) map.get("fileName")), "UTF-8");
			if("".equals(tempStr)) {
				cell = titleLayout.getCell("");
			} else {
				cell = titleLayout.getCell("붙      임 :"); 
			}
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(35f);
	        cell.setRowspan(2);
			cell.setColspan(6);
			cell.setBorder(0);
			table.addCell(cell);
			
			if("".equals(tempStr)) {
				cell = titleLayout.getCell("");
			} else {
				cell = titleLayout.getCell(tempStr);
			}
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        cell.setRowspan(2);
			cell.setColspan(46);
			cell.setBorder(0);
			cell.setPaddingLeft(4);
			table.addCell(cell);

			//인감날인
			cell = largelLayout.getCell("현대인베스트먼트자산운용주식회사"); 
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setFixedHeight(30f);
			cell.setColspan(50);
			cell.setBorder(0);
			table.addCell(cell);
			
//			//인감구분
//			cell = smallLayout.getCell(""); 
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell.setFixedHeight(30f);
//			cell.setColspan(5);
//			cell.setBorder(0);
//			table.addCell(cell);
//			
//			//인감날인
//			cell = smallLayout.getCell(""); 
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			//cell.setImage(Image.getInstance("E://WebFiles//pdf//fund.png"));
//			cell.setFixedHeight(30f);
//			cell.setColspan(5);
//			cell.setBorder(0);
//			table.addCell(cell);
			
			//여백
			cell = blankLayout.getCell("");
			cell.setFixedHeight(30f);
			cell.setColspan(50);
			cell.setBorder(0);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			//row 1 
			//여백
			cell = blankLayout.getCell("", 12, Font.NORMAL, BaseColor.BLACK);
			cell.setFixedHeight(20f);

			cell.setColspan(2);
			cell.setBorder(5);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			//상신일
			tempStr = checkNull((String) map.get("regDate"));
			cell = headerLayout.getCell("상신일 : " + tempStr); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(20f);
			cell.setColspan(11);
			cell.setBorder(1);

			table.addCell(cell);
			
			cell = cellLayout.getCell(""); 
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setFixedHeight(20f);
			cell.setColspan(13);
			cell.setBorder(1);

			table.addCell(cell);
				
			//시행일	
			tempStr = checkNull((String) map.get("effectDate"));
			cell = headerLayout.getCell("시행일 : " + tempStr,  10, Font.NORMAL, BaseColor.BLACK); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(20f);
			cell.setColspan(11);
			cell.setBorder(1);

			table.addCell(cell);
				
			cell = cellLayout.getCell(""); 
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setFixedHeight(20f);
			cell.setColspan(13);
			cell.setBorder(9);
			table.addCell(cell);
			
			//row 2 
			//여백
			cell = blankLayout.getCell("", 12, Font.NORMAL, BaseColor.BLACK);
			cell.setFixedHeight(20f);
			cell.setColspan(2);
			cell.setBorder(4);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			//담당자
			tempStr = checkNull((String) map.get("regEmp"));
			cell = headerLayout.getCell("담당자 : " + tempStr,  10, Font.NORMAL, BaseColor.BLACK); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(20f);
			cell.setColspan(24);
			cell.setBorder(0);
			table.addCell(cell);
			
//			cell = cellLayout.getCell(""); 
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell.setFixedHeight(20f);
//			cell.setColspan(13);
//			cell.setBorder(0);
//			table.addCell(cell);
				
			//부서장
			tempStr = checkNull((String) map.get("teamEmp"));
			cell = headerLayout.getCell("부서장 : " + tempStr,  10, Font.NORMAL, BaseColor.BLACK); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(20f);
			cell.setColspan(24);
			cell.setBorder(8);
			table.addCell(cell);
//			
//			cell = cellLayout.getCell(""); 
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell.setFixedHeight(20f);
//			cell.setColspan(13);
//			cell.setBorder(8);
//			table.addCell(cell);

			//row 3
			//여백
			cell = blankLayout.getCell("", 12, Font.NORMAL, BaseColor.BLACK);
			cell.setFixedHeight(20f);
			cell.setColspan(2);
			cell.setBorder(4);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			//이메일
			tempStr = checkNull((String) map.get("regEmail"));
			cell = headerLayout.getCell("이메일 : " + tempStr,  10, Font.NORMAL, BaseColor.BLACK); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(20f);
			cell.setColspan(19);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = cellLayout.getCell(""); 
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setFixedHeight(20f);
			cell.setColspan(5);
			cell.setBorder(0);
			table.addCell(cell);
				
			//전화번호
			tempStr = checkNull((String) map.get("regTel"));
			cell = headerLayout.getCell("전화번호 : " + tempStr,  10, Font.NORMAL, BaseColor.BLACK); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(20f);
			cell.setColspan(19);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = cellLayout.getCell(""); 
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setFixedHeight(20f);
			cell.setColspan(5);
			cell.setBorder(8);
			table.addCell(cell);

			//row4 
			//여백
			cell = blankLayout.getCell("", 12, Font.NORMAL, BaseColor.BLACK);
			cell.setFixedHeight(20f);
			cell.setColspan(2);
			cell.setBorder(6);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
				
			//주소
			tempStr = checkNull((String) map.get("companyAdd"));
			cell = headerLayout.getCell("주   소 : " + tempStr,  10, Font.NORMAL, BaseColor.BLACK); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(20f);
			cell.setColspan(48);
			cell.setBorder(10);
			table.addCell(cell);
				
			//여백
			cell = blankLayout.getCell("", 12, Font.NORMAL, BaseColor.BLACK);
			cell.setFixedHeight(30f);
			cell.setColspan(50);
			cell.setBorder(0);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);	

			cell = cellLayout.getCell("");

			table.addCell(cell);
			document.add(table);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void makeLayout2(Document document, Map map) throws DocumentException, IOException {
		try {
			FontFactory.register(("E:\\WebFiles\\font\\HIGothicM.ttf"));
			//FontFactory.register(("C:\\Windows\\Fonts\\gulim.ttc"));
			
			StringBuffer sBuff = new StringBuffer("<html>");
			sBuff.append("<head></head>");
			sBuff.append("<body>");
			sBuff.append(checkNull((String) map.get("content")));
			sBuff.append("</body>");
			sBuff.append("</html>");
	
			StringReader stringReader = new StringReader(sBuff.toString());
	
			document = new Document();
			StyleSheet st = new StyleSheet();
			st.loadTagStyle("body", "face", "굴림체"); 
			st.loadTagStyle("body", "encoding", "Identity-H"); 
			st.loadTagStyle("body", "leading", "12,0"); 
			
			HTMLWorker worker = new HTMLWorker(document);
			PdfWriter.getInstance(document, new FileOutputStream("E:\\worker.pdf"));
			
			document.open();
			List<Element> p = HTMLWorker.parseToList(stringReader, st);
			for (int k = 0; k < p.size(); ++k)
				document.add((Element)p.get(k));
			document.close();
		} catch (Exception e) {
			// 	TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private void makeLayout3() throws IOException {
		// Document 생성
		try {
			Document document = new Document(PageSize.A4, 50, 50, 50, 50); // 용지 및 여백 설정
		
			FileOutputStream originPDF;
			originPDF = new FileOutputStream("E:\\copy.pdf");
		
		// PdfWriter 생성
		//PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("d:/test.pdf")); // 바로 다운로드.
			PdfWriter writer;
			writer = PdfWriter.getInstance(document, originPDF);
			writer.setInitialLeading(12.5f);
		
		 
		// 파일 다운로드 설정
		//response.setContentType("application/pdf");
		String fileName = URLEncoder.encode("한글파일명", "UTF-8"); // 파일명이 한글일 땐 인코딩 필요
		//response.setHeader("Content-Transper-Encoding", "binary");
		//response.setHeader("Content-Disposition", "inline; filename=" + fileName + ".pdf");
		 
		// Document 오픈
		document.open();
		XMLWorkerHelper helper = XMLWorkerHelper.getInstance();
		     
		// CSS
		CSSResolver cssResolver = new StyleAttrCSSResolver();
		//CssFile cssFile = helper.getCSS(new FileInputStream("C:/eGovFrame/workspace/projectName/src/main/webapp/css/pdf.css"));
		//cssResolver.addCss(cssFile);
		     
		// HTML, 폰트 설정
		XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
		fontProvider.register(getFolderPath() + "HIGothicM.ttf", "HIGothicM"); // MalgunGothic은 alias,
		CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
		 
		HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
		htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
		 
		// Pipelines
		PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
		HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
		CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);
		 
		XMLWorker worker = new XMLWorker(css, true);
		XMLParser xmlParser = new XMLParser(worker, Charset.forName("UTF-8"));
		 
		// 폰트 설정에서 별칭으로 줬던 "MalgunGothic"을 html 안에 폰트로 지정한다.
		String htmlStr = "<html><head><body style='font-family: HIGothicM;'>"
		            + "<p>PDF 안에 들어갈 내용입니다.</p>"
		            + "<h3>한글, English, 漢字.</h3>"
		        + "</body></head></html>";
		 
		StringReader strReader = new StringReader(htmlStr);
		xmlParser.parse(strReader);
		 
		document.close();
		writer.close();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String checkNull(String paramStr) {
		//System.out.println(paramStr);
		String returnStr = "";
		if(paramStr == null) {
			returnStr = "";
		} else if("undefined".equals(paramStr)) {
			returnStr = "";
		} else if("null".equals(paramStr)){
			returnStr = "";
		} else {
			returnStr = paramStr;
		}
		return returnStr;
	}
	
	//미리보기용 임시파일 위치
	private PdfReader getOriginFile() throws IOException {
		PdfReader pdfReader = new PdfReader(getPdfPath());
		return pdfReader;
	}
	
	private void addContent(PdfReader pdfReader, PdfStamper pdfStamper, String htmlStr) {
		try {
			for(int i=1; i<= pdfReader.getNumberOfPages(); i++){
				PdfContentByte content = pdfStamper.getUnderContent(i);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void htmlContent() {
		
	}
	
	//인감 적용
	private void writeFile(PdfReader pdfReader, PdfStamper pdfStamper, Long stampFileId) throws MalformedURLException, IOException, DocumentException {
		//Image image = Image.getInstance("E://WebFiles//pdf//logo.png");
		try {
			Image image = Image.getInstance(getUploadPath(stampFileId) + getFolderPath() + stampFileId);//직인
			Image image2 = Image.getInstance(getUploadPath() + "pdf" + getFolderPath()  + "sendLogo.jpg");//회사로고
			//cell.setImage(Image.getInstance(getUploadPath() + "pdf" + getFolderPath()  + "sendLogo.jpg"));
			
			for(int i=1; i<= pdfReader.getNumberOfPages(); i++){
				PdfContentByte content = pdfStamper.getUnderContent(i);
				image.setAbsolutePosition(410f, 195f);
				image2.setAbsolutePosition(0f, 0f);
				//content.addImage(image);
				//content.addImage(image, 70f, 0, 0, 70f, 410f, 210f);
				content.addImage(image, 64f, 0, 0, 64f, 410f, 120f);
				content.addImage(image2, 280f, 0, 0, 100f, 320f, 710f);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//첨부파일 로그입력
	public void insertFileModel(Long parentId, Long fileId, String title, String stampSeq) {
        SqlSession sqlSession = DatabaseFactory.openSession();
        Sys10_FileModel  fileModel = new Sys10_FileModel();
		
		fileModel.setFileId(fileId);
		fileModel.setParentId(parentId);
		fileModel.setServerPath(this.getUploadPath(fileModel.getFileId())); //하나의 폴더안에 100개씩 잘라 보관한다.
		
		String fileName = "HIIS-" + stampSeq + "-" + title + ".pdf"; 
		fileModel.setFileName(fileName);

		String fileExtName = "pdf";
		fileModel.setExt(fileExtName);
		
		fileModel.setRegDate(new Date()); // 시스템 시간. 
		fileModel.setDelDate(null); 
		fileModel.setNote("");
		fileModel.setTitleYn("true");
		
		sqlSession.update("sys10_file.insertUpdate", fileModel);
		
        sqlSession.commit();
		sqlSession.close();
		
	}
	
	private String getUploadPath(){
		MyAppProperties a = new MyAppProperties("filePath");
		return a.getProperty(); 
	}
	private String getUploadPath(Long fileId){
		MyAppProperties a = new MyAppProperties("filePath");
		return a.getProperty() + (fileId/100); 
	}
	private String getPdfPath() {
		MyAppProperties a = new MyAppProperties("tempPDF");
		return a.getProperty();
	}
	private String getFolderPath() {
		MyAppProperties a = new MyAppProperties("folderPath");
		return a.getProperty();
	}
	
	private String getFontPath() {
		MyAppProperties a = new MyAppProperties("fontPath");
		return a.getProperty();
	}
	
	
}