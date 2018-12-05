package myApp.server.dbm;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;

import myApp.client.utils.GridDataModel;
import myApp.client.vi.dbm.model.Dbm02_ColCommentsModel;
import myApp.client.vi.dbm.model.Dbm01_TabCommentsModel;
import myApp.server.utils.db.DatabaseFactory;
import myApp.server.utils.pdf.CellLayout;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class Dbm02_ColCommentsPDF {
 
	public void getDocument(BufferedOutputStream bufferedOutputStream, HttpServletRequest request) throws DocumentException, IOException {

		String tableName = request.getParameter("tableName");

		BaseColor whiteOrange = new BaseColor(255, 204, 153);
		BaseColor whiteCyan = new BaseColor(204, 255, 255);

		SqlSession sqlSession = DatabaseFactory.openSession();

		List<GridDataModel> list = sqlSession.selectList("dbm02_col_comments.selectByTableName", tableName);

		Dbm01_TabCommentsModel tabComments = sqlSession.selectOne("dbm01_tab_comments.selectByTableName", tableName);

		Document document = new Document(PageSize.A4, 0, 0, 50, 0);
		PdfWriter.getInstance(document, bufferedOutputStream);

		document.open();

		CellLayout titleLayout = new CellLayout("D2Coding", 12);
		CellLayout headerLayout = new CellLayout("D2Coding", 10);
		CellLayout cellLayout = new CellLayout("D2Coding", 9);
		CellLayout cellLayoutRed = new CellLayout("D2Coding", 9);

		PdfPTable table = new PdfPTable(50); // column count s
		table.setWidthPercentage(95);
        table.setWidths(new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1});
 
        PdfPCell cell;
        // Title 
		cell = headerLayout.getCell("Table Name", 12, Font.NORMAL, BaseColor.BLACK);
		cell.setFixedHeight(32f);
		cell.setColspan(6);
		cell.setBackgroundColor(whiteOrange);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
	
		cell = titleLayout.getCell(tabComments.getTableName()+" ( "+tabComments.getTableComments()+" )");
		cell.setBackgroundColor(whiteOrange);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(44);
		table.addCell(cell);

		// Header
		cell = headerLayout.getCell("No", 10, Font.NORMAL, BaseColor.RED); 
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setFixedHeight(18f);
		cell.setColspan(2);
		cell.setBackgroundColor(whiteCyan);
		table.addCell(cell);
		
		cell = headerLayout.getCell("Column ID", 10, Font.NORMAL, BaseColor.BLACK); 
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(15);
		cell.setBackgroundColor(whiteCyan);
		table.addCell(cell);
		
		cell = headerLayout.getCell("Type", Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(4);
		cell.setBackgroundColor(whiteCyan);
		table.addCell(cell);
		
		cell = headerLayout.getCell("Size", Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(3);
		cell.setBackgroundColor(whiteCyan);
		table.addCell(cell);
		
		cell = headerLayout.getCell("Nullable", Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(4);
		cell.setBackgroundColor(whiteCyan);
		table.addCell(cell);

		cell = headerLayout.getCell("Column Comment", Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(22);
		cell.setBackgroundColor(whiteCyan);
		table.addCell(cell);

		// Cell
	    for(GridDataModel data : list){
	    	
	    	Dbm02_ColCommentsModel columnModel = (Dbm02_ColCommentsModel)data;
	    	
			cell = cellLayoutRed.getCell(columnModel.getColumnId()+"", 9, Font.NORMAL, BaseColor.RED); 
			cell.setColspan(2);
			table.addCell(cell);
			
			cell = cellLayout.getCell(columnModel.getColumnName(), Element.ALIGN_LEFT); 
			cell.setColspan(15);
			table.addCell(cell);
			
			cell = cellLayout.getCell(columnModel.getDataType(), Element.ALIGN_CENTER); 
			cell.setColspan(4);
			table.addCell(cell);
			
			cell = cellLayout.getCell(columnModel.getColumnLength(), Element.ALIGN_CENTER); 
			cell.setColspan(3);
			table.addCell(cell);
			
			cell = cellLayout.getCell(columnModel.getNullAble(), Element.ALIGN_CENTER); 
			cell.setColspan(4);
			table.addCell(cell);

			cell = cellLayout.getCell(columnModel.getColumnComment(), Element.ALIGN_LEFT);
			cell.setColspan(22);
			table.addCell(cell);
			
	    }

		cell = cellLayout.getCell(""); 
		cell.setFixedHeight(150f);
		cell.setColspan(22);
		table.addCell(cell);

		document.add(table);
		document.close();
    }

}