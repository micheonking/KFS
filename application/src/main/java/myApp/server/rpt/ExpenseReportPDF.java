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

public class ExpenseReportPDF extends PdfPageEventHelper {

	public void getDocument(BufferedOutputStream bufferedOutputStream, HttpServletRequest request) throws DocumentException, IOException {

        String filePath = "E://WebFiles//pdf//" ;
        String imageFile = filePath + "펀드회계팀.png" ;
         
        Image img = Image.getInstance(imageFile);

        Document document = new Document(PageSize.A4, 0, 0, 50, 0);
		PdfWriter.getInstance(document, bufferedOutputStream);
		document.open();

		CellLayout titleLayout = new CellLayout("malgun", 16);
		CellLayout headerLayout = new CellLayout("malgun", 10);
		CellLayout cellLayout = new CellLayout("malgun", 9);
		CellLayout cellLayoutRed = new CellLayout("malgun", 9);

		PdfPTable table = new PdfPTable(50); // column count s
		table.setWidthPercentage(85);
        table.setWidths(new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1});

		PdfPCell cell;

		cell = titleLayout.getCell("..");
		cell.setFixedHeight(78f);
		cell.setColspan(50);
		cell.setRowspan(2);
		cell.setBorder(0);
		table.addCell(cell);

		cell = titleLayout.getCell("지출결의서");
		cell.setFixedHeight(60f);
		cell.setColspan(50);
		cell.setRowspan(2);
		table.addCell(cell);

		cell = headerLayout.getCell("계");
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setFixedHeight(24f);
		cell.setColspan(6);
		table.addCell(cell);

//		Paragraph p = new Paragraph("원감");
//		// p.setIndentationLeft(10);
//		p.setAlignment(Element.ALIGN_RIGHT);
//		cell.addElement(p);

		cell = cellLayout.getCell("원감");
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setColspan(6);
		table.addCell(cell);

		cell = headerLayout.getCell("원장");
		cell.setColspan(6);
		table.addCell(cell);

//		PdfPCell cell2 = new PdfPCell();
//		cell2 = headerLayout.getCell("원장", Element.ALIGN_RIGHT);
//		cell2.setColspan(6);
//		table.addCell(cell2);

        cell = headerLayout.getCell("2018 학년도 세출\n선호유치원 회계"); 
        cell.setColspan(14);;
        cell.setRowspan(2);
        table.addCell(cell);
        cell = headerLayout.getCell("계"); 
        cell.setColspan(6);
        table.addCell(cell);
        cell = headerLayout.getCell("원감"); 
        cell.setColspan(6);
        table.addCell(cell);
        cell = headerLayout.getCell("원장"); 
        cell.setColspan(6);
        table.addCell(cell);
 
        cell = headerLayout.getCell(""); 
        cell.setImage(Image.getInstance("E://WebFiles//pdf//펀드회계팀.png"));
        cell.setFixedHeight(56f);
        cell.setColspan(6);
        table.addCell(cell);
        cell = headerLayout.getCell(""); 
        cell.setImage(Image.getInstance("E://WebFiles//pdf//준법감시인.png"));
        cell.setColspan(6);
        table.addCell(cell);
        cell = headerLayout.getCell(""); 
        cell.setImage(Image.getInstance("E://WebFiles//pdf//사내복지근로기금.png"));
        cell.setColspan(6);
        table.addCell(cell);
//      cell = headerLayout.getCell(""); 
//      cell.setColspan(15);;
//      table.addCell(cell);
        cell = headerLayout.getCell(""); 
        cell.setImage(Image.getInstance("E://WebFiles//pdf//법인마케팅본부.png"));
        cell.setColspan(6);
        table.addCell(cell);
        cell = headerLayout.getCell(""); 
        cell.setImage(Image.getInstance("E://WebFiles//pdf//리테일마케팅본부.png"));
        cell.setColspan(6);
        table.addCell(cell);
        cell = headerLayout.getCell(""); 
        cell.setImage(Image.getInstance("E://WebFiles//pdf//대체투자본부.png"));
        cell.setColspan(6);
        table.addCell(cell);
 
//      table.addCell(headerLayout.getCell(""));
//      table.addCell(headerLayout.getCell(""));
//      table.addCell(headerLayout.getCell(""));
//      table.addCell(headerLayout.getCell(""));
//      table.addCell(headerLayout.getCell(""));
//      table.addCell(headerLayout.getCell(""));
//      table.addCell(headerLayout.getCell(""));

		cell = headerLayout.getCell("발의");
		cell.setFixedHeight(36f);
		cell.setColspan(6);
		table.addCell(cell);
		cell = headerLayout.getCell("2018년01월01일");
		cell.setColspan(9);
		table.addCell(cell);
		cell = headerLayout.getCell("인");
		cell.setColspan(3);
		table.addCell(cell);
		cell = headerLayout.getCell("관");
		cell.setColspan(3);
		table.addCell(cell);
		cell = headerLayout.getCell("관리운영비");
		cell.setColspan(11);
		table.addCell(cell);
		cell = headerLayout.getCell("발의");
		cell.setColspan(6);
		table.addCell(cell);
		cell = headerLayout.getCell("2018년01월01일");
		cell.setColspan(9);
		table.addCell(cell);
		cell = headerLayout.getCell("인");
		cell.setColspan(3);
		table.addCell(cell);

		cell = headerLayout.getCell("지출원인\n행위부등기");
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setFixedHeight(36f);
		cell.setColspan(6);
		table.addCell(cell);
		cell = headerLayout.getCell("2018년01월01일");
		cell.setColspan(9);
		table.addCell(cell);
		cell = headerLayout.getCell("인");
		cell.setColspan(3);
		table.addCell(cell);
		cell = headerLayout.getCell("항"); 
		cell.setRowspan(2);
		cell.setColspan(3);
        table.addCell(cell);
        cell = headerLayout.getCell("학교운영비"); 
		cell.setRowspan(2);
        cell.setColspan(11);
        table.addCell(cell);
        cell = headerLayout.getCell("지급명령\n발행부등기"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setColspan(6);
        table.addCell(cell);
        cell = headerLayout.getCell("2018년01월01일"); 
        cell.setColspan(9);
        table.addCell(cell);
        cell = headerLayout.getCell("인"); 
		cell.setColspan(3);
        table.addCell(cell);
 
        cell = headerLayout.getCell("계약"); 
        cell.setFixedHeight(36f);
        cell.setColspan(6);
        table.addCell(cell);
        cell = headerLayout.getCell("2018년01월01일"); 
        cell.setColspan(9);
        table.addCell(cell);
        cell = headerLayout.getCell("인"); 
		cell.setColspan(3);
        table.addCell(cell);
//      cell = headerLayout.getCell("항"); 
//		cell.setColspan(3);
//      table.addCell(cell);
//      cell = headerLayout.getCell("학교운영비"); 
//      cell.setColspan(11);
//      table.addCell(cell);
        cell = headerLayout.getCell("지출부등기"); 
        cell.setColspan(6);
        table.addCell(cell);
        cell = headerLayout.getCell("2018년01월01일"); 
        cell.setColspan(9);
        table.addCell(cell);
        cell = headerLayout.getCell("인"); 
		cell.setColspan(3);
        table.addCell(cell);
 
        cell = headerLayout.getCell("검사"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(36f);
        cell.setColspan(6);
        table.addCell(cell);
        cell = headerLayout.getCell("2018년01월01일"); 
        cell.setColspan(9);
        table.addCell(cell);
        cell = headerLayout.getCell("인"); 
		cell.setColspan(3);
        table.addCell(cell);
        cell = headerLayout.getCell("목"); 
        cell.setRowspan(2);
		cell.setColspan(3);
        table.addCell(cell);
        cell = headerLayout.getCell("공통운영비"); 
        cell.setRowspan(2);
        cell.setColspan(11);
        table.addCell(cell);
        cell = headerLayout.getCell("지급명령\n법    호"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setColspan(6);
        table.addCell(cell);
        cell = headerLayout.getCell("제        호"); 
        cell.setColspan(12);
        table.addCell(cell);
 
        cell = headerLayout.getCell("출납부등기"); 
        cell.setFixedHeight(36f);
        cell.setColspan(6);
        table.addCell(cell);
        cell = headerLayout.getCell("2018년01월01일"); 
        cell.setColspan(9);
        table.addCell(cell);
        cell = headerLayout.getCell("인"); 
		cell.setColspan(3);
        table.addCell(cell);
//      cell = headerLayout.getCell("항"); 
//		cell.setColspan(3);
//      table.addCell(cell);
//      cell = headerLayout.getCell("학교운영비"); 
//      cell.setColspan(14);
//      table.addCell(cell);
        cell = headerLayout.getCell("부가가치세"); 
        cell.setColspan(6);
        table.addCell(cell);
        cell = headerLayout.getCell("0"); 
        cell.setColspan(12);
        table.addCell(cell);
 
        cell = headerLayout.getCell("적    요"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(36f);
        cell.setColspan(18);;
        table.addCell(cell);
        cell = headerLayout.getCell("채    주"); 
        cell.setColspan(14);
        table.addCell(cell);
        cell = headerLayout.getCell(""); 
        cell.setColspan(6);
        table.addCell(cell);
        cell = headerLayout.getCell(""); 
        cell.setColspan(12);
        table.addCell(cell);
 
        cell = headerLayout.getCell("차량연료"); 
        cell.setVerticalAlignment(Element.ALIGN_LEFT);
        cell.setRowspan(6);
        cell.setColspan(18);;
        table.addCell(cell);
        cell = headerLayout.getCell("상    호"); 
        cell.setFixedHeight(36f);
        cell.setColspan(6);
        table.addCell(cell);
        cell = headerLayout.getCell("본동문화주유소"); 
        cell.setColspan(12);
        table.addCell(cell);
        cell = headerLayout.getCell(""); 
        cell.setColspan(6);
        table.addCell(cell);
        cell = headerLayout.getCell(""); 
        cell.setColspan(12);
        table.addCell(cell);
 
//      cell = headerLayout.getCell("차량연료"); 
//      cell.setVerticalAlignment(Element.ALIGN_LEFT);
//      cell.setFixedHeight(28f);
//      cell.setColspan(15);;
//      table.addCell(cell);
        cell = headerLayout.getCell("주    소"); 
        cell.setFixedHeight(72f);
        cell.setColspan(6);
        table.addCell(cell);
        cell = headerLayout.getCell("대구시 달서구 본동 1113-3외 5필"); 
        cell.setColspan(12);
        table.addCell(cell);
        cell = headerLayout.getCell(""); 
        cell.setColspan(6);
        table.addCell(cell);
        cell = headerLayout.getCell(""); 
        cell.setColspan(12);
        table.addCell(cell);
 
//      cell = headerLayout.getCell("차량연료"); 
//      cell.setVerticalAlignment(Element.ALIGN_LEFT);
//      cell.setFixedHeight(36f);
//      cell.setColspan(15);;
//      table.addCell(cell);
        cell = headerLayout.getCell("법인(주민)\n번호"); 
        cell.setFixedHeight(36f);
        cell.setColspan(6);
        table.addCell(cell);
        cell = headerLayout.getCell("514-13-90896"); 
        cell.setColspan(12);
        table.addCell(cell);
        cell = headerLayout.getCell(""); 
        cell.setColspan(6);
        table.addCell(cell);
        cell = headerLayout.getCell(""); 
        cell.setColspan(12);
        table.addCell(cell);
 
//      cell = headerLayout.getCell("차량연료"); 
//      cell.setVerticalAlignment(Element.ALIGN_LEFT);
//      cell.setFixedHeight(36f);
//      cell.setColspan(15);;
//      table.addCell(cell);
        cell = headerLayout.getCell("계좌번호"); 
        cell.setFixedHeight(36f);
        cell.setColspan(6);
        table.addCell(cell);
        cell = headerLayout.getCell("11111-314-01-92516"); 
        cell.setColspan(12);
        table.addCell(cell);
        cell = headerLayout.getCell(""); 
        cell.setColspan(6);
        table.addCell(cell);
        cell = headerLayout.getCell(""); 
        cell.setColspan(12);
        table.addCell(cell);
 
//      cell = headerLayout.getCell("차량연료"); 
//      cell.setVerticalAlignment(Element.ALIGN_LEFT);
//      cell.setFixedHeight(36f);
//      cell.setColspan(15);;
//      table.addCell(cell);
        cell = headerLayout.getCell("성    명"); 
        cell.setFixedHeight(36f);
        cell.setColspan(6);
        table.addCell(cell);
        cell = headerLayout.getCell("황순이"); 
        cell.setColspan(12);
        table.addCell(cell);
        cell = headerLayout.getCell("공급가액"); 
        cell.setColspan(6);
        table.addCell(cell);
        cell = headerLayout.getCell("91,000"); 
        cell.setColspan(12);
        table.addCell(cell);
 
//      cell = headerLayout.getCell("차량연료"); 
//      cell.setVerticalAlignment(Element.ALIGN_LEFT);
//      cell.setFixedHeight(36f);
//      cell.setColspan(15);;
//      table.addCell(cell);
        cell = headerLayout.getCell("오른쪽금액을 영수함.\n2018년 03월 18일"); 
        cell.setFixedHeight(76f);
        cell.setColspan(18);
        table.addCell(cell);
        cell = headerLayout.getCell("합    계");	//	new PdfPCell(img, true);	//	
         cell.setColspan(6);
        table.addCell(cell);
        cell = headerLayout.getCell("91,000"); 
        cell.setColspan(12);
        table.addCell(cell);

        cell = cellLayout.getCell("");
//        cell.setColspan(6);
        table.addCell(cell);
         
        document.add(table);
//
//        
//        Paragraph p = new Paragraph(" ");
//        document.add(p); 
//          
//      PdfPTable table2 = new PdfPTable(8);
//      table2.setWidthPercentage(90);
//
//      table2.setWidths(new int[]{10, 15, 10, 15, 10, 15, 10, 15});
//      
//      table2.addCell(cellLayout.getCell("헨드폰")); 
//      table2.addCell(cellLayout.getCell(""));
//
//      table2.addCell(cellLayout.getCell("집전화")); 
//      table2.addCell(cellLayout.getCell(""));
//
//      table2.addCell(cellLayout.getCell("이메일")); 
//      table2.addCell(cellLayout.getCell(""));
//      
//      table2.addCell(cellLayout.getCell("이메일"));
//      table2.addCell(cellLayout.getCell(""));
         
         
//      document.add(table2);
         
        document.close();


//        try {
            PdfReader pdfReader = new PdfReader("Printing.pdf");

            PdfStamper pdfStamper = new PdfStamper(pdfReader,
                    new FileOutputStream("Student.pdf"));

            Image image = Image.getInstance("E://WebFiles//pdf//펀드회계팀.png");

            for(int i=1; i<= pdfReader.getNumberOfPages(); i++){

                //put content under
                PdfContentByte content = pdfStamper.getUnderContent(i);
                image.setAbsolutePosition(100f, 150f);
                content.addImage(image);

                //put content over
                content = pdfStamper.getOverContent(i);
                image.setAbsolutePosition(300f, 150f);
                content.addImage(image);

                //Text over the existing page
                BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
                content.beginText();
                content.setFontAndSize(bf, 18);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Page No: " + i,430,15,0);
                content.endText();

            }

            pdfStamper.close();

//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }

//      for(AbstractDataModel dataModel:list){
//          StudentModel studentModel = (StudentModel)dataModel; 
//          
//          PdfPCell cell;
//          
//          cell = createImageCell(studentModel.getStudentId().toString()); 
//          cell.setRowspan(7);
//          cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//          table.addCell(cell);
//          
//          table.addCell(cellLayout.getCell("원생번호"));
//          table.addCell(cellLayout.getCell(studentModel.getStudentNo()));
//  
//          table.addCell(cellLayout.getCell("한글이름")); 
//          table.addCell(cellLayout.getCell(studentModel.getKorName()));
//          
//          table.addCell(cellLayout.getCell("주민번호")); 
//          table.addCell(cellLayout.getCell(""));
//  
//          table.addCell(cellLayout.getCell("반이름")); 
//          table.addCell(cellLayout.getCell(""));
//          
//          table.addCell(cellLayout.getCell("배정일"));
//          table.addCell(cellLayout.getCell(""));
//          
//          table.addCell(cellLayout.getCell("남녀구분")); 
//          table.addCell(cellLayout.getCell(""));
//          
//          table.addCell(cellLayout.getCell("생일")); 
//          table.addCell(cellLayout.getCell(""));
//  
//          table.addCell(cellLayout.getCell("영문명")); 
//          table.addCell(cellLayout.getCell(""));
//  
//          table.addCell(cellLayout.getCell("한자명")); 
//          table.addCell(cellLayout.getCell(""));
//  
//          table.addCell(cellLayout.getCell("헨드폰")); 
//          table.addCell(cellLayout.getCell(""));
//  
//          table.addCell(cellLayout.getCell("집전화")); 
//          table.addCell(cellLayout.getCell(""));
//  
//          table.addCell(cellLayout.getCell("이메일")); 
//          table.addCell(cellLayout.getCell(""));
//  
//          table.addCell(cellLayout.getCell("성격")); 
//  
//          cell = cellLayout.getCell("아주좋음"); 
//          cell.setColspan(3);
//  //      cell.setFixedHeight(20f);
//  //      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//  //      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//          
//          table.addCell(cell);
//          
//          table.addCell(cellLayout.getCell("특기")); 
//          table.addCell(cellLayout.getCell(""));
//          
//          table.addCell(cellLayout.getCell("버릇")); 
//          table.addCell(cellLayout.getCell(""));
//          
//          cell = cellLayout.getCell("좋아하는\r음식"); 
//          cell.setFixedHeight(28f);
//          table.addCell(cell); 
//          table.addCell(cellLayout.getCell(""));
//  
//          table.addCell(cellLayout.getCell("싫어하는\r음식"));
//          table.addCell(cellLayout.getCell(""));
//  
//          table.addCell(cellLayout.getCell("희망학교"));
//          table.addCell(cellLayout.getCell(""));
//  
//          table.addCell(cellLayout.getCell("이전학력")); 
//          cell = cellLayout.getCell(""); 
//          cell.setColspan(3);
//          table.addCell(cell);
//          
//          cell = cellLayout.getCell("특기사항"); 
//          cell.setFixedHeight(42f);
//          table.addCell(cell);
//          
//          
//          cell = cellLayout.getCell("");
//          cell.setColspan(6);
//          table.addCell(cell);
//      }
//      document.add(table);
//
//        
//        Paragraph p = new Paragraph(" ");
//        document.add(p); 
//          
//      PdfPTable table2 = new PdfPTable(8);
//      table2.setWidthPercentage(90);
//
//      table2.setWidths(new int[]{10, 15, 10, 15, 10, 15, 10, 15});
//      
//      table2.addCell(cellLayout.getCell("헨드폰")); 
//      table2.addCell(cellLayout.getCell(""));
//
//      table2.addCell(cellLayout.getCell("집전화")); 
//      table2.addCell(cellLayout.getCell(""));
//
//      table2.addCell(cellLayout.getCell("이메일")); 
//      table2.addCell(cellLayout.getCell(""));
//      
//      table2.addCell(cellLayout.getCell("이메일"));
//      table2.addCell(cellLayout.getCell(""));
//      
//      
//      document.add(table2);
//      
//      document.close();
    }

//	@Override
//	public void onEndPage(PdfWriter writer, Document document) {
//		try {
//			Image background = Image.getInstance("E://WebFiles//pdf//펀드회계팀.png");
//			    float width = background.getWidth();
//			    float height = background.getHeight();
//			    writer.getDirectContentUnder().addImage(background, width, 0, 0, height, 0, 0);
//			}
//		catch (BadElementException e) {
//		    e.printStackTrace();
//		}
//		catch (MalformedURLException e) {
//		    e.printStackTrace();
//		}
//		catch (IOException e) {
//		    e.printStackTrace();
//		}
//		catch (DocumentException e)	{
//		    e.printStackTrace();
//	    }
//	}

     
//    private PdfPCell createImageCell(String studentId) throws DocumentException, IOException {
//        String filePath = "E://WebFiles//pdf//" ;
//        String imageFile = filePath + "펀드회계팀.png" ;
//         
//        Image img = Image.getInstance(imageFile);
//        PdfPCell cell = new PdfPCell(img, true);
//        cell.setPadding(3);
//        return cell;
//    }

//    public Image getWatermarkedImage(PdfContentByte cb, Image img, String watermark) throws DocumentException {
//		float width = img.getScaledWidth();
//		float height = img.getScaledHeight();
//		PdfTemplate template = cb.createTemplate(width, height);
//		template.addImage(img, width, 0, 0, height, 0, 0);
//		ColumnText.showTextAligned(template, Element.ALIGN_CENTER, new Phrase(watermark, new Font()), width / 2, height / 2, 30);
//		return Image.getInstance(template);
//	}

}