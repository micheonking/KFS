package myApp.client.vi.fnd;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent.TriggerClickHandler;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;

import myApp.client.field.LookupTriggerField;
import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.service.GridRetrieveData;
import myApp.client.vi.fnd.model.Fnd01_FundCodeModel;
import myApp.client.vi.fnd.model.Fnd01_FundCodeModel;
import myApp.client.vi.fnd.model.Fnd01_FundCodeModelProperties;

public class Fnd01_TabPage_FundCode extends BorderLayoutContainer implements InterfaceGridOperate{

    private Fnd01_FundCodeModelProperties properties = GWT.create(Fnd01_FundCodeModelProperties.class);
	private Grid<Fnd01_FundCodeModel> grid = this.buildGrid();

	private CheckBox closeYnCheckBox = new CheckBox();
	private Long fundCodeIdField;

	public void retrieve(Long fundCodeId){
		GridRetrieveData<Fnd01_FundCodeModel> service = new GridRetrieveData<Fnd01_FundCodeModel>(grid.getStore());

		fundCodeIdField = fundCodeId;

		service.addParam("fundId", fundCodeId);
		service.addParam("closeYn", closeYnCheckBox.getValue().toString());
		service.retrieve("pln.Fnd01_FundPlan.selectByPlanFundId");
	}

	public Grid<Fnd01_FundCodeModel> buildGrid(){
		
		GridBuilder<Fnd01_FundCodeModel> gridBuilder = new GridBuilder<Fnd01_FundCodeModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);

		//문서분류선택
		LookupTriggerField lookupClassTree = new LookupTriggerField();
		lookupClassTree.addTriggerClickHandler(new TriggerClickHandler(){
			@Override
			public void onTriggerClick(TriggerClickEvent event) {
//				openlookUpClassTree();
			}
		}); 
		
//		gridBuilder.addText	(properties.classTreeName(),	250,	"문서분류",		lookupClassTree);
//		gridBuilder.addText	(properties.approvalTypeName(),	100,	"최종결재"		);
//		gridBuilder.addText	(properties.orgName(),			100,	"부서"			);
//		gridBuilder.addText	(properties.titleName(),		100,	"직위"			);
//		//담당자선택
//		LookupTriggerField lookupEmpInfo = new LookupTriggerField();
//		lookupEmpInfo.addTriggerClickHandler(new TriggerClickHandler(){
//			@Override
//			public void onTriggerClick(TriggerClickEvent event) {
//				openLookupEmpInfo();
//			}
//		});
//		
//		gridBuilder.addText	(properties.korName(),			90,		"담당자"		,	lookupEmpInfo);
		gridBuilder.addDate	(properties.startDate(),		100,	"시작일"		,	new DateField());
//		gridBuilder.addDate	(properties.dueDate(),			100,	"완료예정일"	,	new DateField());
		gridBuilder.addText	(properties.closeYn(),			100,	"진행상태"		);	//	"[완료여부]"
//		gridBuilder.addText	(properties.note(),				400,	"기타사항"		,	new TextField());

//		gridBuilder.addLong	(properties.planId(),			100,	"*상품기획ID");
//		gridBuilder.addLong	(properties.classTreeId(),		100,	"*문서분류ID");
//		gridBuilder.addLong	(properties.apprId(),			80,		"*상신문서ID"	,	new LongField()	);
//		gridBuilder.addLong	(properties.docId(),			100,	"*문서ID"		,	new LongField()	);
//		gridBuilder.addLong	(properties.fundId(),			100,	"*펀드ID"		,	new LongField()	);
//		gridBuilder.addText	(properties.fundName(),			200,	"*상품명"		);

		return gridBuilder.getGrid(); 
	}

	@Override
	public void retrieve() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertRow() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteRow() {
		// TODO Auto-generated method stub
		
	}

}
