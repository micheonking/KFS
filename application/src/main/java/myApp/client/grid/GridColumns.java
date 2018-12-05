package myApp.client.grid;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.sencha.gxt.cell.core.client.NumberCell;
import com.sencha.gxt.cell.core.client.form.CheckBoxCell;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.widget.core.client.form.IsField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;

public class GridColumns<T> {

	private List<ColumnConfig<T, ?>> columnList =  new ArrayList<ColumnConfig<T, ?>>();
	private Map<ColumnConfig<T, ?>, IsField<?>> editorMap = new HashMap<ColumnConfig<T, ?>, IsField<?>>();

	public List<ColumnConfig<T, ?>> getColumnList(){
		return this.columnList; 
	}
	
	public Map<ColumnConfig<T, ?>, IsField<?>> getEditorMap(){
		return this.editorMap; 
	}
	
	public ColumnConfig<T, String> addText(ValueProvider<T, String> valueProvider, int width, String header){
		ColumnConfig<T, String> column = new ColumnConfig<T, String>(valueProvider, width, header) ;
		column.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		columnList.add(column);
		return column;  
	}
	public ColumnConfig<T, String> addText(ValueProvider<T, String> valueProvider, int width, String header, IsField<?> field){
		ColumnConfig<T, String> column = this.addText(valueProvider, width, header); 
		editorMap.put(column, field);
		return column;
	}

	public ColumnConfig<T, Double> addDouble(ValueProvider<T, Double> valueProvider, int width, String header){
		ColumnConfig<T, Double> column = new ColumnConfig<T, Double>(valueProvider, width, header) ;
		column.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT); // 숫자는 우측정렬 
		column.setCell(new NumberCell<Double>()); // number format setting
		columnList.add(column);
		return column;  
	}
	public ColumnConfig<T, Double> addDouble(ValueProvider<T, Double> valueProvider, int width, String header, IsField<?> field){
		ColumnConfig<T, Double> column =  addDouble(valueProvider, width, header) ;
		editorMap.put(column, field);
		return column; 
	}
	
	
	public ColumnConfig<T, Long> addLong(ValueProvider<T, Long> valueProvider, int width, String header){
		ColumnConfig<T, Long> column = new ColumnConfig<T, Long>(valueProvider, width, header) ;
		column.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT); // 숫자는 우측정렬 
		column.setCell(new NumberCell<Long>()); // number format setting
		columnList.add(column);
		return  column; 
	}
	
	public ColumnConfig<T, Long> addLong(ValueProvider<T, Long> valueProvider, int width, String header, IsField<?> field){
		ColumnConfig<T, Long> column = this.addLong(valueProvider, width, header);
		editorMap.put(column, field);
		return column; 
	}
	
	public ColumnConfig<T, Date> addDate(ValueProvider<T, Date> valueProvider, int width, String header){
		ColumnConfig<T, Date> column = new ColumnConfig<T, Date>(valueProvider, width, header) ;
		column.setCell(new DateCell(DateTimeFormat.getFormat("yyyy-MM-dd")));
		column.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		columnList.add(column);
		return  column; 
	}
	public ColumnConfig<T, Date> addDate(ValueProvider<T, Date> valueProvider, int width, String header, IsField<?> field){
		ColumnConfig<T, Date> column = this.addDate(valueProvider, width, header);
		editorMap.put(column, field);
		return column; 
	}
	
	public ColumnConfig<T, Date> addDateTime(ValueProvider<T, Date> valueProvider, int width, String header){
		ColumnConfig<T, Date> column = new ColumnConfig<T, Date>(valueProvider, width, header) ;
		column.setCell(new DateCell(DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss")));
		column.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		columnList.add(column);
		return column; 
	}
	public ColumnConfig<T, Date> addDateTime(ValueProvider<T, Date> valueProvider, int width, String header, IsField<?> field){
		ColumnConfig<T, Date> column = this.addDateTime(valueProvider, width, header); 
		editorMap.put(column, field);
		return column; 
	}
	
	public ColumnConfig<T, Boolean> addBoolean(ValueProvider<T, Boolean> valueProvider, int width, String header){
		ColumnConfig<T, Boolean> column = new ColumnConfig<T, Boolean>(valueProvider, width, header) ;
		CheckBoxCell cell = new CheckBoxCell();
		column.setCell(cell);
		columnList.add(column);
		return column ; 
	}
	
	public ColumnConfig<T, String> addCell(ValueProvider<T, String> valueProvider, int width, String header, Cell<String> cell){
		// user type cell 
		ColumnConfig<T, String> column = new ColumnConfig<T, String>(valueProvider, width, header) ;
		column.setCell(cell);
		column.setCellPadding(false);
		column.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		columnList.add(column);
		return column; 
	}
}
