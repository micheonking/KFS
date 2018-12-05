package myApp.server.rpt;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
 
import javax.servlet.http.HttpServletRequest;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.psc.model.StudentModel;
import myApp.server.utils.pdf.CellLayout;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class TestReportPDF extends PdfPageEventHelper {

	public void getDocument(BufferedOutputStream bufferedOutputStream, HttpServletRequest request) throws DocumentException, IOException {

        Document document = new Document(PageSize.A4, 0, 0, 50, 0);
		PdfWriter.getInstance(document, bufferedOutputStream);
		document.open();

		// 현대해상고딕체M
        CellLayout largelLayout = new CellLayout("HIGothicM",16);
        CellLayout cellLayout = new CellLayout("HIGothicL",12);
        CellLayout rowCellLayout = new CellLayout("HIGothicL",10);
        CellLayout rowCellItalicLayout = new CellLayout("HIGothicL",10);
        
        PdfPTable table = new PdfPTable(50); // column count s
		table.setWidthPercentage(100);
        table.setWidths(new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1});

		PdfPCell cell;

		//여백 및 명함이미지
		cell = cellLayout.getCell("[명함이미지]", Element.ALIGN_RIGHT);
		cell.setImage(Image.getInstance("E://WebFiles//pdf//sendLogo.jpg"));
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setFixedHeight(100f);
		cell.setColspan(50);
		cell.setBorder(0);//2
		table.addCell(cell);
		//
		cell = cellLayout.getCell("");
		cell.setFixedHeight(30f);
		cell.setColspan(50);
		cell.setBorder(0);
		table.addCell(cell);
		//
		
		cell = cellLayout.getCell("");
		cell.setFixedHeight(30f);
		cell.setColspan(3);
		cell.setBorder(0);
		table.addCell(cell);
		//문서번호
		cell = cellLayout.getCell("문서번호 :", Element.ALIGN_LEFT);
		cell.setColspan(5);
		cell.setBorder(0);
		table.addCell(cell);
		
		cell = cellLayout.getCell("HIS - [인감대장 中 순번]", Element.ALIGN_LEFT);
		cell.setColspan(42);
		cell.setBorder(0);
		table.addCell(cell);
	
		//수신
		cell = cellLayout.getCell("");
		cell.setFixedHeight(30f);
		cell.setColspan(3);
		cell.setBorder(0);
		table.addCell(cell);

		cell = cellLayout.getCell("수      신 :", Element.ALIGN_LEFT);
		cell.setColspan(5);
		cell.setBorder(0);
		table.addCell(cell);
		
		cell = cellLayout.getCell("");
		cell.setColspan(42);
		cell.setBorder(0);
		table.addCell(cell);
		
		//참조
		cell = cellLayout.getCell("");
		cell.setFixedHeight(30f);
		cell.setColspan(3);
		cell.setBorder(0);
		table.addCell(cell);

		cell = cellLayout.getCell("참      조 :", Element.ALIGN_LEFT);
		cell.setColspan(5);
		cell.setBorder(0);
		table.addCell(cell);
		
		cell = cellLayout.getCell(""); 
		cell.setColspan(42);
		cell.setBorder(0);
		table.addCell(cell);
		
		//제목
		cell = cellLayout.getCell("");
		cell.setFixedHeight(30f);
		cell.setColspan(3);
		cell.setBorder(0);
		table.addCell(cell);

		cell = cellLayout.getCell("제      목 :", Element.ALIGN_LEFT);
		cell.setColspan(5);
		cell.setBorder(0);
		table.addCell(cell);
		
		cell = cellLayout.getCell("[문서상신 中 제목]", Element.ALIGN_LEFT);
		cell.setColspan(42);
		cell.setBorder(0);
		table.addCell(cell);
		
		//여백
		cell = cellLayout.getCell("");
		cell.setFixedHeight(110f);
		cell.setColspan(3);
		cell.setBorder(0);
		table.addCell(cell);

		cell = cellLayout.getCell("[문서상신 中 상신내용 ]", Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setColspan(47);
		cell.setBorder(0);
		table.addCell(cell);	
		
		//붙임
		cell = cellLayout.getCell("");
		cell.setFixedHeight(30f);
		cell.setColspan(3);
		cell.setBorder(0);
		table.addCell(cell);

		cell = cellLayout.getCell("붙      임 :", Element.ALIGN_LEFT);
		cell.setColspan(5);
		cell.setBorder(0);
		table.addCell(cell);

		cell = cellLayout.getCell(""); 
		cell.setColspan(42);
		cell.setBorder(0);
		table.addCell(cell);
		
		//여백
		cell = cellLayout.getCell("");
		cell.setFixedHeight(30f);
		cell.setColspan(3);
		cell.setRowspan(2);
		cell.setBorder(0);
		table.addCell(cell);

		cell = cellLayout.getCell("[신규문서등록 中 문서명 목록]", Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setColspan(48);
		cell.setBorder(0);
		table.addCell(cell);

		cell = cellLayout.getCell("[참조문서등록 中 문서명 목록]", Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setColspan(48);
		cell.setBorder(0);
		table.addCell(cell);

		//여백
		cell = cellLayout.getCell(" ");
		cell.setFixedHeight(110f);
		cell.setColspan(50);
		cell.setBorder(0);
		table.addCell(cell);	
		
		//인감날인
		cell = largelLayout.getCell("현대인베스트먼트자산운용주식회사 신탁회계팀", Element.ALIGN_RIGHT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setFixedHeight(65f);
		cell.setColspan(37);
		cell.setBorder(0);
		table.addCell(cell);

//		//인감구분
//		cell = largelLayout.getCell("신탁회계팀"); 
//		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//		cell.setFixedHeight(45f);
//		cell.setColspan(10);
//		cell.setBorder(0);
//		table.addCell(cell);

		cell = cellLayout.getCell("[인감이미지]");
		cell.setImage(Image.getInstance("E://WebFiles//pdf//준법감시인.png"));
		cell.setFixedHeight(65f);
		cell.setColspan(13);
		cell.setBorder(0);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);
		
		//여백
		cell = cellLayout.getCell("");
		cell.setFixedHeight(45f);
		cell.setColspan(50);
		cell.setBorder(0);
		table.addCell(cell);

	//row 1 
		//여백
		cell = cellLayout.getCell("");
		cell.setFixedHeight(24f);
		cell.setColspan(3);
		cell.setBorder(0);
		table.addCell(cell);
		//
		cell = cellLayout.getCell("");
		cell.setBorder(5);
		table.addCell(cell);
		
		//상신일
		cell = rowCellLayout.getCell("상신일 :", Element.ALIGN_LEFT);
		cell.setColspan(4);
		cell.setBorder(1);//1
		table.addCell(cell);
		
		cell = rowCellLayout.getCell("2018-09-01", Element.ALIGN_LEFT);
		cell.setColspan(16);
		cell.setBorder(1);//1
		table.addCell(cell);
			
		//시행일
		cell = rowCellLayout.getCell("시행일 :", Element.ALIGN_LEFT);
		cell.setColspan(5);
		cell.setBorder(1);//1
		table.addCell(cell);
			
		cell = rowCellLayout.getCell("2018-09-01", Element.ALIGN_LEFT);
		cell.setColspan(18);
		cell.setBorder(9);
		table.addCell(cell);
			
		cell = cellLayout.getCell("");
		cell.setColspan(3);
		cell.setBorder(0);
		table.addCell(cell);
			
		
	//row 2 
		cell = cellLayout.getCell("");
		cell.setFixedHeight(24f);
		cell.setColspan(3);
		cell.setBorder(0);
		table.addCell(cell);
		//여백
		cell = cellLayout.getCell("");
		cell.setBorder(4);
		table.addCell(cell);
		
		//담당자
		cell = rowCellLayout.getCell("담당자 :", Element.ALIGN_LEFT);
		cell.setColspan(4);
		cell.setBorder(0);
		table.addCell(cell);
		
		cell = rowCellLayout.getCell("이동필", Element.ALIGN_LEFT);
		cell.setColspan(16);
		cell.setBorder(0);
		table.addCell(cell);
			
		//부서장
		cell = rowCellLayout.getCell("부서장 :", Element.ALIGN_LEFT);
		cell.setColspan(5);
		cell.setBorder(0);
		table.addCell(cell);
		
		cell = rowCellLayout.getCell("이동필", Element.ALIGN_LEFT);
		cell.setColspan(18);
		cell.setBorder(8);
		table.addCell(cell);

		cell = cellLayout.getCell("");
		cell.setColspan(3);
		cell.setBorder(0);
		table.addCell(cell);
			
	//row 3
		cell = cellLayout.getCell("");
		cell.setFixedHeight(24f);
		cell.setColspan(3);
		cell.setBorder(0);
		table.addCell(cell);
		//여백
		cell = cellLayout.getCell("");
		cell.setBorder(4);
		table.addCell(cell);
		
		//이메일
		cell = rowCellLayout.getCell("이메일 :", Element.ALIGN_LEFT);
		cell.setColspan(4);
		cell.setBorder(0);
		table.addCell(cell);
		
		cell = rowCellLayout.getCell("dpLee@hdfund.co.kr", Element.ALIGN_LEFT);
		cell.setColspan(16);
		cell.setBorder(0);
		table.addCell(cell);
			
		//전화번호
		cell = rowCellLayout.getCell("전화번호 :", Element.ALIGN_LEFT);
		cell.setColspan(5);
		cell.setBorder(0);
		table.addCell(cell);
		
		cell = rowCellLayout.getCell("02-785-8100", Element.ALIGN_LEFT);
		cell.setColspan(18);
		cell.setBorder(8);
		table.addCell(cell);

		cell = cellLayout.getCell("");
		cell.setColspan(3);
		cell.setBorder(0);
		table.addCell(cell);
			
	//row4 
		cell = cellLayout.getCell("");
		cell.setFixedHeight(24f);
		cell.setColspan(3);
		cell.setBorder(0);
		table.addCell(cell);
		//여백
		cell = cellLayout.getCell("");
		cell.setBorder(6);
		table.addCell(cell);
			
		//주소
		cell = rowCellLayout.getCell("주소 :", Element.ALIGN_LEFT);
		cell.setColspan(4);
		cell.setBorder(2);
		table.addCell(cell);

		cell = rowCellItalicLayout.getCell("(07330)   서울특별시 영등포구 국제금융로 8길 16 신영증권 B/D 10F", 10, Font.ITALIC, BaseColor.BLACK);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setColspan(39);
		cell.setBorder(10);
		table.addCell(cell);

		cell = cellLayout.getCell("");
		cell.setColspan(3);
		cell.setBorder(0);
		table.addCell(cell);
			
		//여백
		cell = cellLayout.getCell("");
		cell.setFixedHeight(24f);
		cell.setColspan(50);
		cell.setBorder(0);
		table.addCell(cell);	

        cell = cellLayout.getCell("");
        table.addCell(cell);
         
        document.add(table);
        document.close();
    }
}