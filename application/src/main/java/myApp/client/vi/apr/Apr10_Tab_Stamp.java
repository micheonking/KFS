package myApp.client.vi.apr;


import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent.TriggerClickHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.field.LookupTriggerField;
import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.service.InterfaceCallback;
import myApp.client.utils.FileUpdownForm;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.vi.LoginUser;
import myApp.client.vi.apr.model.Apr10_StampModel;
import myApp.client.vi.apr.model.Apr10_StampModelProperties;
import myApp.client.vi.emp.Emp00_Lookup_EmpInfo;
import myApp.client.vi.emp.model.Emp00_InfoModel;
import myApp.client.vi.opr.model.Opr03_RegModel;
import myApp.client.vi.org.model.Org01_CodeModel;



public class Apr10_Tab_Stamp extends VerticalLayoutContainer implements InterfaceGridOperate{
	Date baseDate ;
	private final Logger   logger = Logger.getLogger(this.getClass().getName());
	
	private Apr10_StampModelProperties properties = GWT.create(Apr10_StampModelProperties.class);
	private Grid<Apr10_StampModel> grid = this.buildGrid(); 
	
	private FileUpdownForm fileUpdownForm = new FileUpdownForm();
	
	
	public interface ImageXTemplate extends XTemplates {
		@XTemplate("<img style=\"border: 0px solid red; height:50px;  width: 50px; margin:5px 5px; \" src=\"FileDownload?fileType=image&parentId={url}\">")
//		@XTemplate("<img style=\"border: 0px solid red; height:50px;  width: 50px; margin:5px 5px; \" src=\"img/Login.jpg\">")
	    SafeHtml createImage(String url);
	}
	
	 public class CustomImageCell extends AbstractCell<String> {
		    private ImageXTemplate imageTemplate = GWT.create(ImageXTemplate.class);

			@Override
			public void render(Context context, String stampId, SafeHtmlBuilder sb) {
				if (stampId != null) {
			        SafeUri url = UriUtils.fromString(stampId);
			        Date tempDate = new Date();
			        String tempStr = String.valueOf(tempDate.getTime());
			        sb.append(imageTemplate.createImage(stampId + "&" +tempStr));
			  }				
			}
		
		  }
	 
	 
	public Apr10_Tab_Stamp() {
		
		this.setBorders(false);
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		
		searchBarBuilder.addRetrieveButton();
		searchBarBuilder.addInsertButton();
		searchBarBuilder.addUpdateButton();
		searchBarBuilder.addDeleteButton();
		
		this.add(searchBarBuilder.getSearchBar(), new VerticalLayoutData(-1, -1, new Margins(0,0,0,0)));
		this.add(setGrid(), new VerticalLayoutData(1, 1, new Margins(0,0,0,0)));
		
		retrieve();
		
	}
	
	private VerticalLayoutContainer setGrid() {
		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		vlc.add(grid, new VerticalLayoutData(1,1 , new Margins(0,0,0,0)));
		vlc.add(fileUpdownForm.getForm(), new VerticalLayoutData(1,1));
		
		fileUpdownForm.getForm().setVisible(false); //숨어있는 파일선택 flase
		fileUpdownForm.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				Apr10_StampModel stampModel = grid.getSelectionModel().getSelectedItem();
				
				
//				grid.getStore().getRecord(stampModel).addChange(properties.stampId_string().fileUpdownForm.getFileName());
//				Info.display("stamp id 파일체인지 ", stampModel.getStampId() + ""); 
				String actionUrl = "FileUpload?fileType=image&parentId=" + stampModel.getStampId();
				fileUpdownForm.submit(actionUrl, new InterfaceCallback() {
					@Override
					public void execute() {
						update();
//						Info.display("","파일업로드입니다.");
					} 
				});
			}
		});
		
		return vlc;

	}
	private Grid<Apr10_StampModel> buildGrid() {

		GridBuilder<Apr10_StampModel> gridBuilder = new GridBuilder<Apr10_StampModel>(properties.keyId());
		gridBuilder.setChecked(SelectionMode.SINGLE	);
		
		LookupTriggerField lookupOrgInfo = new LookupTriggerField();
		lookupOrgInfo.addTriggerClickHandler(new TriggerClickHandler(){
			@Override
			public void onTriggerClick(TriggerClickEvent event) {
				findOrg();
			}
		}); 
		
		gridBuilder.addText(properties.orgName(),	250,	"부서",		lookupOrgInfo);
		
		LookupTriggerField lookupEmpInfo = new LookupTriggerField();
		lookupEmpInfo.addTriggerClickHandler(new TriggerClickHandler(){
			@Override
			public void onTriggerClick(TriggerClickEvent event) {
				findEmp();
			}
		}); 

		
		gridBuilder.addText(properties.aprEmpName(), 100,	"책임자", lookupEmpInfo );
		gridBuilder.addText(properties.stampName(), 200,	"인감이름", new TextField() );
		gridBuilder.addDate(properties.startDate(), 90,		"시작날짜", new DateField() );
		gridBuilder.addDate(properties.closeDate(), 90,		"종료날짜", new DateField() );
		gridBuilder.addText(properties.useName(),	300,	"사용용도", new TextField() );
		gridBuilder.addCell(properties.stampId_string(), 130, "인감",	new CustomImageCell()); 
		
		ActionCell<String> fileUpButton = new ActionCell<String>("인감등록", new ActionCell.Delegate<String>() {
			@Override
			public void execute(String object) {
				fileUpdownForm.click();
			}
		});
		gridBuilder.addCell	  (properties.actionCellUp(),  90, 				" ", fileUpButton);
		gridBuilder.addText(properties.note(),		300, 			"비고", new TextField() );
		return gridBuilder.getGrid();
	}
	
	protected void findEmp() {
			Emp00_Lookup_EmpInfo lookupEmp = new Emp00_Lookup_EmpInfo(); 
			lookupEmp.open(LoginUser.getToday(), new InterfaceCallbackResult() {
				@Override
				public void execute(Object result) {

					Emp00_InfoModel empInfoModel = (Emp00_InfoModel)result;
					Apr10_StampModel  stampModel     = grid.getSelectionModel().getSelectedItem();

					grid.getStore().getRecord(stampModel).addChange(properties.aprEmpId()		, empInfoModel.getEmpId());
					grid.getStore().getRecord(stampModel).addChange(properties.aprEmpName()		, empInfoModel.getKorName());
				}
			});
		}
		
	protected void findOrg() {
		Apr10_Move_OrgCode lookupOrg = new Apr10_Move_OrgCode();
		lookupOrg.open(LoginUser.getToday(), new InterfaceCallbackResult() {

			@Override
			public void execute(Object result) {
				Org01_CodeModel orgModel = (Org01_CodeModel)result;
				Apr10_StampModel stampModel = grid.getSelectionModel().getSelectedItem();
				
				grid.getStore().getRecord(stampModel).addChange(properties.orgName(),orgModel.getOrgInfoModel().getKorName());
				
				grid.getStore().getRecord(stampModel).addChange(properties.orgId(),orgModel.getCodeId());
				
				return ; 
					
				}
				
		});
				
			}

	@Override
	public void retrieve() {
		grid.mask("Loading");
		Apr10_StampModel stampModel = new Apr10_StampModel();
//		Info.display("", " 인감이미지: " +stampModel.getStampId_string());
		GridRetrieveData<Apr10_StampModel> service = new GridRetrieveData<Apr10_StampModel>(grid.getStore());
		service.addParam("orgId",LoginUser.getOrgCodeId());
		service.retrieve("apr.Apr10_Stamp.selectByCodeName");
		grid.unmask();

	}


	@Override
	public void update() {
		
		Apr10_StampModel stampModel = new Apr10_StampModel();
//		Info.display("", " 인감이미지update: " +grid.getSelectionModel().getSelectedItem().getStampId());
//		Info.display("", " STAMPID update: " +stampModel.getStampId());
		
		GridUpdate<Apr10_StampModel> service = new GridUpdate<Apr10_StampModel>();
		service.update(grid.getStore(), "apr.Apr10_Stamp.update");
		grid.getView().refresh(true);
	}

	@Override
	public void insertRow() {
//		Info.display("입력","getOrgCodeId."+LoginUser.getOrgCodeId());
		GridInsertRow<Apr10_StampModel> service = new GridInsertRow<Apr10_StampModel>(); 
		Apr10_StampModel stamModel = new Apr10_StampModel();
		stamModel.setOrgId(LoginUser.getOrgCodeId());
		stamModel.setStartDate(LoginUser.getToday());
//		stamModel.setAprEmpId(Emp00_InfoModel.getEmpId());
		service.insertRow(grid, stamModel);			
	}

	@Override
	public void deleteRow() {
		GridDeleteData<Apr10_StampModel> service = new GridDeleteData<Apr10_StampModel>();
		List<Apr10_StampModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.delete(grid.getStore(), checkedList, "apr.Apr10_Stamp.delete");		
	}

}

