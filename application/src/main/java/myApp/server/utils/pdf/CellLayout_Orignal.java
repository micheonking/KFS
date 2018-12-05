package myApp.server.utils.pdf;

import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;

public class CellLayout_Orignal {
	BaseColor whiteBack = new BaseColor(255,255,255);
	BaseColor whiteOrange = new BaseColor(255,204,153);
	BaseColor whiteCyan = new BaseColor(204,255,255);
	
	// font 위치 확인 필요. 
	BaseFont fontMalgunFont = BaseFont.createFont("E://WebFiles//font//malgun.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
	Font fontTitle = new Font(fontMalgunFont, 12);
	Font fontHeader = new Font(fontMalgunFont, 10);
	Font fontBase = new Font(fontMalgunFont, 9);
	Font fontFooter = new Font(fontMalgunFont, 7);
	
	public CellLayout_Orignal() throws DocumentException, IOException {
		//fontBaseFont = BaseFont.createFont("font/malgun.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
	}

	public CellLayout_Orignal(float fontSize) throws DocumentException, IOException {
		fontBase.setSize(fontSize);
	}

	public PdfPCell getTitle(String text){
//		fontTitle.setSize(10);
//		return this.getCell(text, Element.ALIGN_MIDDLE);
//		Phrase p = new Phrase(text, fontFont); 
		Paragraph p = new Paragraph(text, fontTitle);
//		p.setAlignment(textAlignment);
		p.setFont(fontTitle);
		
		PdfPCell cell = new PdfPCell(p);  
//		cell.setFixedHeight(20f);
//		cell.setBackgroundColor(whiteOrange);
//		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

		return cell; 
	}
	
	public PdfPCell getTitle(String text, int textSize){
		
		fontTitle.setSize(textSize);
		PdfPCell cell = new PdfPCell(new Phrase(text, fontTitle));
		
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		cell.setFixedHeight(16f);
//		cell.setBackgroundColor(whiteOrange);
//		//Phrase p = new Phrase(text, fontFont); 
//		Paragraph p = new Paragraph(text, fontTitle);
//		p.setAlignment(Element.ALIGN_MIDDLE);
//		p.setFont(fontTitle);
//		
//		PdfPCell cell = new PdfPCell(p);  
//		cell.setFixedHeight(16f);
		
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

		return cell; 
	}

	public PdfPCell getTitle(String text, float height, int vAlign, int hAlign){
		
		PdfPCell cell = new PdfPCell(new Phrase(text, fontTitle));
		
		cell.setFixedHeight(height);
		cell.setVerticalAlignment(vAlign);
		cell.setHorizontalAlignment(hAlign);
		return cell;
	}

	public PdfPCell getTitle(String text, String textFont, int textSize, int textType, BaseColor baseColor) {
		PdfPCell cell = new PdfPCell(new Phrase(text, fontTitle));
		fontTitle.setSize(textSize);
		fontTitle.setStyle(textType);
		fontTitle.setColor(baseColor);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

		return cell; 
	}

	public PdfPCell getHeader(String text){
		PdfPCell cell = new PdfPCell(new Phrase(text, fontHeader));
		cell.setFixedHeight(12f);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

		return cell; 
	}
	
	public PdfPCell getHeader(String text, int textSize){
		
		PdfPCell cell = new PdfPCell(new Phrase(text, fontHeader));
		
		fontHeader.setSize(textSize);
		cell.setFixedHeight(16f);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		return cell; 
	}

	public PdfPCell getHeader(String text, float height, int vAlign, int hAlign){
		
		PdfPCell cell = new PdfPCell(new Phrase(text, fontHeader));
		
		cell.setFixedHeight(height);
		cell.setVerticalAlignment(vAlign);
		cell.setHorizontalAlignment(hAlign);
		return cell;
	}

	public PdfPCell getHeader(String text, String textFont, int textSize, int textType, BaseColor baseColor) {
		PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(textFont, textSize, textType, baseColor)));
//		PdfPCell cell = new PdfPCell(new Phrase(text, fontHeader));


		Paragraph p = new Paragraph(text, fontTitle);
//		p.setAlignment(textAlignment);
		p.setFont(fontTitle);
		cell = new PdfPCell(new Paragraph(text, fontTitle));  


		fontHeader.setSize(textSize);
		fontHeader.setStyle(textType);
		fontHeader.setColor(baseColor);
		cell.setBackgroundColor(whiteCyan);
		return cell; 
	}

	public PdfPCell getCell(String text){
		return this.getCell(text, Element.ALIGN_LEFT); 
	}
	
	public PdfPCell getCell(String text, int textAlignment){
		
		//Phrase p = new Phrase(text, fontBase); 
		Paragraph p = new Paragraph(text, fontBase);
		p.setAlignment(textAlignment);
		p.setFont(fontBase);
		
		PdfPCell cell = new PdfPCell(p);  
		cell.setFixedHeight(16f);
		
		cell.setVerticalAlignment(textAlignment);

		return cell; 
	}

	public PdfPCell getCell(String text, float height, int vAlign, int hAlign){
		
		PdfPCell cell = new PdfPCell(new Phrase(text, fontBase));
		
		cell.setFixedHeight(height);
		cell.setVerticalAlignment(vAlign);
		cell.setHorizontalAlignment(hAlign);
		return cell;
	}

	public PdfPCell getCell(String text, String textFont, int textSize, int textType, BaseColor baseColor) {
//		PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(textFont, textSize, textType, baseColor)));
		PdfPCell cell = new PdfPCell(new Phrase(text, fontBase));
		fontBase.setSize(textSize);
		fontBase.setStyle(textType);
		fontBase.setColor(baseColor);
//		cell.setBackgroundColor(whiteCyan);
		return cell; 
	}

	public PdfPCell getCell(String text, int textSize, int hAlign) {
//		PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont("malgun", textSize, Font.NORMAL, BaseColor.BLACK)));
		PdfPCell cell = new PdfPCell(new Phrase(text, fontBase));
		cell.setHorizontalAlignment(hAlign);
		cell.setFixedHeight(16f);
//		cell.setFixedHeight(16f);
		return cell; 
	}

}
