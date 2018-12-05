package myApp.server.apr;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import myApp.client.service.ServiceRequest;
import myApp.client.vi.sys.model.Sys10_FileModel;
import myApp.server.utils.db.DatabaseFactory;
import myApp.server.utils.file.MyAppProperties;
import myApp.server.utils.pdf.CellLayout;

public class Apr10_PreviewStampPDF extends PdfPageEventHelper {

	//메일보내기 화면 open시
	public void getDocument(ServiceRequest request) throws NumberFormatException, DocumentException, IOException {
		Long keyId = Long.parseLong(request.getStringParam("keyId"));
		Document document = new Document(PageSize.A4, 0, 0, 50, 0);
		//원본 PDF 생성
		FileOutputStream originPDF = new FileOutputStream(getPdfPath());
		PdfWriter writer = PdfWriter.getInstance(document, originPDF);
		try {
			document.open();
			makeLayout(document);
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

		PdfReader pdfReader2 = getOriginFile();
	    FileOutputStream copyPDF = new FileOutputStream(this.getUploadPath(keyId)+ "\\" + keyId);
	    PdfStamper pdfStamper2 = new PdfStamper(pdfReader2, copyPDF);
		try {
			//실제 파일 만들기
			writeFile(pdfReader2, pdfStamper2);
		} catch (Exception e) {
		} finally {
			if(pdfStamper2 != null) {pdfStamper2.close();}
			if(pdfReader2 != null) {pdfReader2.close();}
			if(copyPDF != null) {copyPDF.close();}
			if(writer != null) {writer.close();}
		}
		
		insertFileModel(keyId);
	}
	
	//미리보기 실행시
	public void getDocument(BufferedOutputStream bufferedOutputStream, HttpServletRequest request) throws DocumentException, IOException {
		Long keyId = Long.parseLong(request.getParameter("keyId"));
		Document document = new Document(PageSize.A4, 0, 0, 50, 0);
		//원본 PDF 생성
		FileOutputStream originPDF = new FileOutputStream(getPdfPath());
		PdfWriter writer = PdfWriter.getInstance(document, originPDF);
		try {
			document.open();
			makeLayout(document);
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
		PdfReader pdfReader2 = getOriginFile();
	    FileOutputStream copyPDF = new FileOutputStream(this.getUploadPath(keyId)+ "\\" + keyId);
	    PdfStamper pdfStamper2 = new PdfStamper(pdfReader2, copyPDF);
		try {
			//미리보기용 파일 만들기
			writeFile(pdfReader, pdfStamper);
			//실제 파일 만들기
			writeFile(pdfReader2, pdfStamper2);
		} catch (Exception e) {
		} finally {
			if(pdfStamper != null) {pdfStamper.close();}
			if(pdfReader != null) {pdfReader.close();}
			if(pdfStamper2 != null) {pdfStamper2.close();}
			if(pdfReader2 != null) {pdfReader2.close();}
			if(copyPDF != null) {copyPDF.close();}
			if(writer != null) {writer.close();}
		}
		insertFileModel(keyId);
	}

	//PDF 레이아웃 생성
	private void makeLayout(Document document) throws DocumentException, IOException {
		try {
			// 현대해상고딕체M
	        CellLayout blankLayout = new CellLayout("",0);
	        CellLayout smallLayout = new CellLayout("HIGothicM",6);
	        CellLayout largelLayout = new CellLayout("HIGothicM",20);
	        CellLayout titleLayout = new CellLayout("HIGothicM",12);
	        CellLayout headerLayout = new CellLayout("HIGothicM",10); 
	        CellLayout cellLayout = new CellLayout("HIGothicM",9); 
	        
	        PdfPTable table = new PdfPTable(50); // column count s
	        table.setWidths(new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1});

			PdfPCell cell;

			//여백 및 명함이미지
			cell = titleLayout.getCell("[명함이미지]");
			cell.setImage(Image.getInstance("E://WebFiles//pdf//sendLogo.jpg"));
			cell.setFixedHeight(100f);
			cell.setColspan(50);
			cell.setBorder(2);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			
			//문서번호
			cell = titleLayout.getCell("문서번호 :"); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(30f);
			cell.setColspan(10);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = titleLayout.getCell(""); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(30f);
			cell.setColspan(40);
			cell.setBorder(0);
			table.addCell(cell);
		
			//수신
			cell = titleLayout.getCell("수     신 :"); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(30f);
			cell.setColspan(10);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = titleLayout.getCell(""); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(30f);
			cell.setColspan(40);
			cell.setBorder(0);
			table.addCell(cell);
			
			//참조
			cell = titleLayout.getCell("참     조 :"); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(30f);
			cell.setColspan(10);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = titleLayout.getCell(""); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(30f);
			cell.setColspan(40);
			cell.setBorder(0);
			table.addCell(cell);
			
			//제목
			cell = titleLayout.getCell("제     목 :"); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(30f);
			cell.setColspan(10);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = titleLayout.getCell(""); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(30f);
			cell.setColspan(40);
			cell.setBorder(0);
			table.addCell(cell);
			
			//여백
			cell = cellLayout.getCell("[문서상신 중 상신내용 ]");
			cell.setFixedHeight(110f);
			cell.setColspan(50);
			cell.setBorder(0);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);	
			
			//붙임
			cell = titleLayout.getCell("붙     임 :"); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(30f);
			cell.setColspan(10);
			cell.setBorder(0);
			table.addCell(cell);

			cell = titleLayout.getCell(""); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(30f);
			cell.setColspan(40);
			cell.setBorder(0);
			table.addCell(cell);
			
			//여백
			cell = cellLayout.getCell("[신규문서 등록 중 문서명 목록] ");
			cell.setFixedHeight(150f);
			cell.setColspan(50);
			cell.setBorder(0);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);	
			
			//여백
			cell = blankLayout.getCell(" ");
			cell.setFixedHeight(30f);
			cell.setColspan(50);
			cell.setBorder(0);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);	
			
			//인감날인
			cell = largelLayout.getCell("현대인베스트먼트자산운용주식회사"); 
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setFixedHeight(30f);
			cell.setColspan(40);
			cell.setBorder(0);
			table.addCell(cell);
			
			//인감구분
			cell = smallLayout.getCell("[인감 구분]"); 
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setFixedHeight(30f);
			cell.setColspan(5);
			cell.setBorder(0);
			table.addCell(cell);
			
			//인감날인
			cell = smallLayout.getCell("[인감 날인]"); 
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setImage(Image.getInstance("E://WebFiles//pdf//fund.png"));
			cell.setFixedHeight(30f);
			cell.setColspan(5);
			cell.setBorder(0);
			table.addCell(cell);
			
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
			cell.setFixedHeight(24f);

			cell.setColspan(2);
			cell.setBorder(5);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			//상신일
			cell = headerLayout.getCell("상신일"); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(24f);
			cell.setColspan(11);
			cell.setBorder(1);

			table.addCell(cell);
			
			cell = cellLayout.getCell(""); 
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setFixedHeight(24f);
			cell.setColspan(13);
			cell.setBorder(1);

			table.addCell(cell);
				
			//시행일	
			cell = headerLayout.getCell("시행일",  10, Font.NORMAL, BaseColor.BLACK); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(24f);
			cell.setColspan(11);
			cell.setBorder(1);

			table.addCell(cell);
				
			cell = cellLayout.getCell(""); 
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setFixedHeight(24f);
			cell.setColspan(13);
			cell.setBorder(9);
			table.addCell(cell);
			
			//row 2 
			//여백
			cell = blankLayout.getCell("", 12, Font.NORMAL, BaseColor.BLACK);
			cell.setFixedHeight(24f);
			cell.setColspan(2);
			cell.setBorder(4);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			//담당자
			cell = headerLayout.getCell("담당자",  10, Font.NORMAL, BaseColor.BLACK); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(24f);
			cell.setColspan(11);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = cellLayout.getCell(""); 
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setFixedHeight(24f);
			cell.setColspan(13);
			cell.setBorder(0);
			table.addCell(cell);
				
			//부서장
			cell = headerLayout.getCell("부서장",  10, Font.NORMAL, BaseColor.BLACK); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(24f);
			cell.setColspan(11);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = cellLayout.getCell(""); 
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setFixedHeight(24f);
			cell.setColspan(13);
			cell.setBorder(8);
			table.addCell(cell);

			//row 3
			//여백
			cell = blankLayout.getCell("", 12, Font.NORMAL, BaseColor.BLACK);
			cell.setFixedHeight(24f);
			cell.setColspan(2);
			cell.setBorder(4);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			
			//이메일
			cell = headerLayout.getCell("이메일",  10, Font.NORMAL, BaseColor.BLACK); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(24f);
			cell.setColspan(11);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = cellLayout.getCell(""); 
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setFixedHeight(24f);
			cell.setColspan(13);
			cell.setBorder(0);
			table.addCell(cell);
				
			//전화번호
			cell = headerLayout.getCell("전화번호",  10, Font.NORMAL, BaseColor.BLACK); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(24f);
			cell.setColspan(11);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = cellLayout.getCell(""); 
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setFixedHeight(24f);
			cell.setColspan(13);
			cell.setBorder(8);
			table.addCell(cell);

			//row4 
			//여백
			cell = blankLayout.getCell("", 12, Font.NORMAL, BaseColor.BLACK);
			cell.setFixedHeight(24f);
			cell.setColspan(2);
			cell.setBorder(6);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
				
			//주소
			cell = headerLayout.getCell("주소",  10, Font.NORMAL, BaseColor.BLACK); 
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(24f);
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

	//미리보기용 임시파일 위치
	private PdfReader getOriginFile() throws IOException {
		PdfReader pdfReader = new PdfReader(getPdfPath());
		return pdfReader;
	}
	
	//인감 적용
	private void writeFile(PdfReader pdfReader, PdfStamper pdfStamper) throws MalformedURLException, IOException, DocumentException {
		//Image image = Image.getInstance("E://WebFiles//pdf//logo.png");
		try {
			Long tempKey = 2018108L;
			Image image = Image.getInstance(getUploadPath(tempKey)+"\\" + tempKey);
			
			
			for(int i=1; i<= pdfReader.getNumberOfPages(); i++){
				PdfContentByte content = pdfStamper.getUnderContent(i);
				image.setAbsolutePosition(380f, 195f);
				//content.addImage(image);
				content.addImage(image, 94f, 0, 0, 94f, 380f, 195f);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//첨부파일 로그입력
	public void insertFileModel(Long parentId) {
        SqlSession sqlSession = DatabaseFactory.openSession();
        Sys10_FileModel  fileModel = new Sys10_FileModel();
		Long fileId = parentId;
		
		fileModel.setFileId(fileId);
		fileModel.setParentId(parentId);
		fileModel.setServerPath(this.getUploadPath(fileModel.getFileId())); //하나의 폴더안에 100개씩 잘라 보관한다.
		
		String fileName = "메일송신.pdf"; 
		fileModel.setFileName(fileName);

		String fileExtName = "pdf";
		fileModel.setExt(fileExtName);
		
		fileModel.setRegDate(new Date()); // 시스템 시간. 
		fileModel.setDelDate(null); 
		fileModel.setNote("");

		sqlSession.update("sys10_file.insertUpdate", fileModel);
		
        sqlSession.commit();
		sqlSession.close();
		
	}
	
	private String getUploadPath(Long fileId){
		MyAppProperties a = new MyAppProperties("filePath");
		return a.getProperty() + (fileId/100); 
	}
	private String getPdfPath() {
		MyAppProperties a = new MyAppProperties("tempPDF");
		return a.getProperty();
	}
	
}