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
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;
import com.sencha.gxt.widget.core.client.event.BeforeStartEditEvent.BeforeStartEditHandler;
import com.sencha.gxt.widget.core.client.form.IsField;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GroupingView;
import com.sencha.gxt.widget.core.client.grid.RowNumberer;
import com.sencha.gxt.widget.core.client.grid.editing.GridEditing;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

import myApp.client.resource.ResourceIcon;

public class GridBuilder_new<T> {

	private RowNumberer<T> rowNum = new RowNumberer<T>();
	private boolean menuDiable = true;  // default true, 메뉴를 사용하지 않는다. 
	private Grid<T> grid;
	private ModelKeyProvider<T> keyProvider;
	private List<ColumnConfig<T, ?>> columnList =  new ArrayList<ColumnConfig<T, ?>>();
	private Map<ColumnConfig<T, ?>, IsField<?>> editorMap = new HashMap<ColumnConfig<T, ?>, IsField<?>>();
	private CheckBoxSelectionModel<T> selectionModel ; 

	private GroupingView<T> groupingView = new GroupingView<T>();
	private BeforeStartEditHandler<T> beforeStartEditHandler;   
	private List<HeaderGroupMap> headerGroupList = new ArrayList<HeaderGroupMap>(); 
	
	private StoreSortInfo<T> storeSortInfo ; 
	
	public GridBuilder_new(ModelKeyProvider<T> keyProvider){
		this.keyProvider = keyProvider;
		this.columnList.add(rowNum); // 첫번째 칼럼에 행번호를 등록한다. 
	}

	public void setMenuDisable(Boolean disable) { // 컬럼헤더의 메뉴를 사용하지 않는다.
		this.menuDiable = disable; 
	}

	public void setChecked(SelectionMode selectionMode){
		IdentityValueProvider<T> identity = new IdentityValueProvider<T>();
		selectionModel = new CheckBoxSelectionModel<T>(identity);
		selectionModel.setSelectionMode(selectionMode); // parameter
		columnList.add(selectionModel.getColumn()); 
	}
	
	/*
	 *  Grid Header Group Setting
	 */
	public void addHeaderGroupMap(HeaderGroupMap headerGroupMap){
		this.headerGroupList.add(headerGroupMap); 
	}
	
	public void setHeaderGroupMap() {
	}

	
	public void build() {
		ListStore<T> listStore = new ListStore<T>(this.keyProvider);
		ColumnModel<T> columnModel = new ColumnModel<T>(this.columnList);
		
		// grid의 컬럼 빼대를 넣는다. 
		this.grid = new Grid<T>(listStore, columnModel);

		// grid display setting 
		grid.clearSizeCache();
		grid.setColumnReordering(true);
		grid.getView().setStripeRows(true);
		grid.getView().setColumnLines(true); 

		if(this.selectionModel != null ){ // row별 Check Box 설정 
			grid.setSelectionModel(this.selectionModel);
		}
		
		// 컬럼별 메뉴사용여부 설정 
		for(ColumnConfig<T, ?> columnConfig : grid.getColumnModel().getColumns()){
			columnConfig.setMenuDisabled(menuDiable);
		}
		
		// grid header group setting 
		for(HeaderGroupMap config: headerGroupList){
			grid.getColumnModel().addHeaderGroup(config.getRow(), config.getCol(), config.getConfig());
		}

		
		rowNum.initPlugin(grid);
		rowNum.setWidth(40);
	}
	
	 
	public void addBeforeStartEditHandler(BeforeStartEditHandler<T> handler){

		// handler 등록한다. 맞는지 모르겠다. 이후 이거 확인하고 찾아가서 코딩하기 어렵다.
		GridEditing<T> editing = new GridInlineEditing<T>(grid);
		editing.addBeforeStartEditHandler(beforeStartEditHandler); 
	}

	
	public Grid<T> getGrid(){

		/*
		 * 넘어오는 필드가 어떤 형태인지 모르므로 일단 무시하고 에디터로 넣는다. - @SuppressWarnings
		 */
		GridEditing<T> editing = new GridInlineEditing<T>(grid);
		
		for(ColumnConfig columnConfig : editorMap.keySet() ){
			editing.addEditor(columnConfig, editorMap.get(columnConfig));
		}
		
		if(this.storeSortInfo != null) {
			grid.getStore().addSortInfo(this.storeSortInfo);	
		}

		return grid; 
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TreeGrid<T> getTreeGrid(int treeColumn){
		
		// treeColumn은 몇번째 트리 화살표가 보일지를 선택한다. 
		TreeStore<T> treeStore = new TreeStore<T>(this.keyProvider); 
		ColumnModel<T> columnModel = new ColumnModel<T>(columnList);
		for(ColumnConfig<T, ?> columnConfig : columnModel.getColumns()){
			// 컬럼헤더의 메뉴를 사용하지 않는다. 
			columnConfig.setMenuDisabled(this.menuDiable);
		}
		
		// 첫번째 칼럼으로 트리를 구성한다. 
		TreeGrid<T> treeGrid = new TreeGrid<T>(treeStore, columnModel, columnModel.getColumns().get(treeColumn));
		treeGrid.getStyle().setLeafIcon(ResourceIcon.INSTANCE.textButton());
		
		if(this.selectionModel != null ){
			// check box setting 여부 
			treeGrid.setSelectionModel(this.selectionModel);
		}
		GridEditing<T> editing = new GridInlineEditing<T>(treeGrid);
		for(ColumnConfig columnConfig : editorMap.keySet() ){
			editing.addEditor(columnConfig, editorMap.get(columnConfig));
		}
		
		if(this.storeSortInfo != null) {
			treeGrid.getTreeStore().addSortInfo(this.storeSortInfo);	
		}
		
		return treeGrid; 
	}

	public void setSortInfo(ValueProvider<T, String> valueProvider, SortDir sortDir) {
		storeSortInfo = new StoreSortInfo<T>(valueProvider, sortDir); 
	}

	public void addGrouping(ColumnConfig<T, ?> groupingColumn){
	      groupingView.setShowGroupedColumn(false);
	      groupingView.groupBy(groupingColumn);
	      groupingView.setAutoExpandColumn(groupingColumn);
	      this.setMenuDisable(false);
	}
	public void setGrouping() {
		this.grid.setView(groupingView);
	}
}
