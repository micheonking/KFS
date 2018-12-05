package myApp.client.vi.dcr;

import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent.CollapseHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent.TriggerClickHandler;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;

import myApp.client.field.LookupTriggerField;
import myApp.client.grid.ComboBoxField;
import myApp.client.grid.GridBuilder;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.service.InterfaceCallback;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.vi.LoginUser;
import myApp.client.vi.apr.Apr10_Lookup_Stamp;
import myApp.client.vi.apr.model.Apr10_StampModel;
import myApp.client.vi.dcr.model.Dcr01_ClassTreeModel;
import myApp.client.vi.dcr.model.Dcr01_ClassTreeModelProperties;

public class Dcr01_Grid_DocType extends ContentPanel {
	private final Logger   logger = Logger.getLogger(this.getClass().getName());
	private Dcr01_ClassTreeModelProperties properties = GWT.create(Dcr01_ClassTreeModelProperties.class);
	public Grid<Dcr01_ClassTreeModel> grid = this.buildGrid();
	private Long classTreeId;

	private TextButton deleteButton = new TextButton("삭제");
	private TextButton insertButton = new TextButton("입력");
	private TextButton updateButton = new TextButton("저장");
	
	public Dcr01_Grid_DocType(){
		this.setHeaderVisible(false);
		
		deleteButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				deleteRow(); 
			}
		});
		insertButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				insertRow(); 
			}
		});
		updateButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				update(); 
			}
		});

		this.deleteButton.setEnabled(false); // 초기값은 사용할 수 없음으로 설정.  
		this.insertButton.setEnabled(false);
		this.updateButton.setEnabled(false);
		this.addButton(deleteButton); //삭제버튼
		this.addButton(insertButton); //입력버튼
		this.addButton(updateButton); //수정버튼
		this.setButtonAlign(BoxLayoutPack.CENTER);

		this.add(this.grid, new VerticalLayoutData(1, 1));//그리드 표현
	}
	
	public Grid<Dcr01_ClassTreeModel> buildGrid(){
		
		GridBuilder<Dcr01_ClassTreeModel> gridBuilder = new GridBuilder<Dcr01_ClassTreeModel>(properties.keyId());  
		
		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText(properties.classTreeCode(), 80, "구분코드", new TextField());
		gridBuilder.addText(properties.classTreeName(), 250, "문서구분명", new TextField());

		final ComboBoxField docItemComboBox = new ComboBoxField("DocItemTypeCode");
		docItemComboBox.addCollapseHandler(new CollapseHandler(){
			@Override
			public void onCollapse(CollapseEvent event) {
				Dcr01_ClassTreeModel data = grid.getSelectionModel().getSelectedItem();
				grid.getStore().getRecord(data).addChange(properties.itemTypeCode(), docItemComboBox.getCode());
			}
		});
		gridBuilder.addText(properties.itemTypeName(), 100, "상품분류", docItemComboBox);
		
		final ComboBoxField approvalComboBox = new ComboBoxField("ApprovalTypeCode");
		approvalComboBox.addCollapseHandler(new CollapseHandler(){
			@Override
			public void onCollapse(CollapseEvent event) {
				Dcr01_ClassTreeModel data = grid.getSelectionModel().getSelectedItem(); 
				grid.getStore().getRecord(data).addChange(properties.approvalTypeCode(), approvalComboBox.getCode());
			}
		}); 	
		gridBuilder.addText(properties.approvalTypeName(), 100, "결재구분", approvalComboBox);
		/*
		final ComboBoxField sealComboBox = new ComboBoxField("SealCode");
		sealComboBox.addCollapseHandler(new CollapseHandler(){
			@Override
			public void onCollapse(CollapseEvent event) {
				Dcr01_ClassTreeModel data = grid.getSelectionModel().getSelectedItem(); 
				grid.getStore().getRecord(data).addChange(properties.sealCode(), sealComboBox.getCode());
			}
		});
		gridBuilder.addText(properties.sealName(), 100, "인감종류", sealComboBox);
		*/
		LookupTriggerField sealName = new LookupTriggerField();//인감명		
		sealName.addTriggerClickHandler(new TriggerClickHandler(){
			@Override
			public void onTriggerClick(TriggerClickEvent event) {
				findStamp();
			}
		});
		sealName.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				event.preventDefault();
			}
		});
		gridBuilder.addText(properties.sealName(), 100, "인감종류", sealName);
		gridBuilder.addBoolean(properties.researchYnCheck(), 60, "리서치");
		gridBuilder.addBoolean(properties.advertYnCheck(), 60, "광고심사");
		gridBuilder.addText(properties.seq(), 60, "정렬순서", new TextField());
		gridBuilder.addText(properties.note(), 240, "비고", new TextField());

		return gridBuilder.getGrid(); 
	}

	public void retrieve(Dcr01_ClassTreeModel classTreeModel) {
		
		this.grid.getStore().clear();
		this.grid.mask("Loading");
		if(classTreeModel.getChildList().size() > 0) {
			// 하위 분류가 더 있다.
			this.deleteButton.setEnabled(false); 
			this.insertButton.setEnabled(false);
			this.updateButton.setEnabled(false);
			this.grid.unmask();
		}
		else {
			this.deleteButton.setEnabled(true); 
			this.insertButton.setEnabled(true);
			this.updateButton.setEnabled(true);
			this.classTreeId = classTreeModel.getClassTreeId();
			
			GridRetrieveData<Dcr01_ClassTreeModel> service = new GridRetrieveData<Dcr01_ClassTreeModel>(this.grid.getStore());
			service.addParam("companyId", LoginUser.getCompanyId());
			service.addParam("parentId", classTreeModel.getClassTreeId());
			service.retrieve("dcr.Dcr01_ClassTree.selectByClassTreeId", new InterfaceCallback() {
				
				@Override
				public void execute() {
					grid.unmask();
				}
			});
		}
	}
	
	public void findStamp() {
		Apr10_Lookup_Stamp lookupStamp = new Apr10_Lookup_Stamp();
		lookupStamp.open(new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {
				Apr10_StampModel stampModel = (Apr10_StampModel) result;
				
				Dcr01_ClassTreeModel classTreeModel = grid.getSelectionModel().getSelectedItem(); 
				grid.getStore().getRecord(classTreeModel).addChange(properties.sealCode(), stampModel.getStampId());
				grid.getStore().getRecord(classTreeModel).addChange(properties.sealName(), stampModel.getStampName());
				//stampId.setValue(stampModel.getStampId());
				//stampName.setValue(stampModel.getStampName());
				//stampFileId.setValue(stampModel.getStampFileId());
			}
		}, true);
	}
	
	public void update() {
		GridUpdate<Dcr01_ClassTreeModel> service = new GridUpdate<Dcr01_ClassTreeModel>(); 
		service.update(this.grid.getStore(), "dcr.Dcr01_ClassTree.update");
	}
	
	public void insertRow() {
		GridInsertRow<Dcr01_ClassTreeModel> service = new GridInsertRow<Dcr01_ClassTreeModel>();
		Dcr01_ClassTreeModel classTreeModel = new Dcr01_ClassTreeModel();
		classTreeModel.setCompanyId(LoginUser.getCompanyId());
		classTreeModel.setParentTreeId(this.classTreeId);
		classTreeModel.setTypeCode("T"); // 문서구분은 'T' 이다.
		
		service.insertRow(grid, classTreeModel);
	}
	
	public void deleteRow() {
		GridDeleteData<Dcr01_ClassTreeModel> service = new GridDeleteData<Dcr01_ClassTreeModel>();
		List<Dcr01_ClassTreeModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.delete(this.grid.getStore(), checkedList, "dcr.Dcr01_ClassTree.delete");
	}
}
