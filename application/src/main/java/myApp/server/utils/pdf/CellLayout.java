package myApp.server.utils.pdf;

import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;

import myApp.server.utils.file.MyAppProperties;

public class CellLayout {
	BaseColor whiteBack = new BaseColor(255,255,255);
	BaseColor whiteOrange = new BaseColor(255,204,153);
	BaseColor whiteCyan = new BaseColor(204,255,255);
	
	BaseFont baseCellFont;
	Font cellFont;

	public CellLayout() throws DocumentException, IOException {
		//cellFont09Font = BaseFont.createFont("font/malgun.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
	}

	public CellLayout(float fontSize) throws DocumentException, IOException {
		// font 위치 확인 필요. 
		baseCellFont = BaseFont.createFont("E://WebFiles//font//D2Coding.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		cellFont = new Font(baseCellFont, fontSize);
	}

	public CellLayout(String textFont, float fontSize) throws DocumentException, IOException {
		// font 위치 확인 필요.
		if (textFont == "malgun") {				// 맑은 고딕
			baseCellFont = BaseFont.createFont(getFolderPath() + "malgun.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		} else if (textFont == "HIGothicM") {	// 현대해상고딕 미디움
			baseCellFont = BaseFont.createFont(getFolderPath() + "HIGothicM.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		} else if (textFont == "HIGothicL") {	// 현대해상고딕 라이트
			baseCellFont = BaseFont.createFont(getFolderPath() + "HIGothicL.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		} else if (textFont == "HIMaumL") {		// 현대해상마음 라이트
			baseCellFont = BaseFont.createFont(getFolderPath() + "HIMaumL.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		} else if (textFont == "HIMaumR") {		// 현대해상마음 레귤러
			baseCellFont = BaseFont.createFont(getFolderPath() + "HIMaumR.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		} else if (textFont == "D2Coding") {
			baseCellFont = BaseFont.createFont(getFolderPath() + "D2Coding.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		} else {
			baseCellFont = BaseFont.createFont(getFolderPath() + "malgun.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		}
		cellFont = new Font(baseCellFont, fontSize);
	}

	public PdfPCell getCell(String text){
		PdfPCell cell = new PdfPCell(new Phrase(text, cellFont));

		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);

		return cell; 
	}
	
	public PdfPCell getCell(String text, int textAlignment){
		PdfPCell cell = new PdfPCell(new Phrase(text, cellFont));
		
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(textAlignment);
		return cell;
	}

	public PdfPCell getCell(String text, float height, int vAlign, int hAlign){
		PdfPCell cell = new PdfPCell(new Phrase(text, cellFont));
		
		cell.setFixedHeight(height);
		cell.setVerticalAlignment(vAlign);
		cell.setHorizontalAlignment(hAlign);
		return cell;
	}

	public PdfPCell getCell(String text, int textSize, int textType, BaseColor baseColor) {
		PdfPCell cell = new PdfPCell(new Phrase(text, cellFont));

		cell.setFixedHeight(16f);
		cellFont.setSize(textSize);
		cellFont.setStyle(textType);
		cellFont.setColor(baseColor);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		return cell; 
	}
	private String getFolderPath() {
		MyAppProperties a = new MyAppProperties("fontPath");
		return a.getProperty();
	}
}
