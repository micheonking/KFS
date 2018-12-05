package myApp.client.vi.apr;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.FormPanel.Encoding;
import com.sencha.gxt.widget.core.client.form.FormPanel.Method;
import com.sencha.gxt.widget.core.client.grid.Grid;

import myApp.client.grid.GridBuilder;
import myApp.client.service.DBUtil;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.InterfaceCallback;
import myApp.client.utils.GridDataModel;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.vi.LoginUser;
import myApp.client.vi.apr.model.Apr01_ApprModel;
import myApp.client.vi.apr.model.Apr03_RelateFundModel;
import myApp.client.vi.apr.model.Apr03_RelateFundModelProperties;
import myApp.client.vi.apr.model.Apr04_ApprStepModel;
import myApp.client.vi.apr.model.Apr04_ApprStepModelProperties;
import myApp.client.vi.apr.model.Apr05_RelateDocModel;
import myApp.client.vi.apr.model.Apr05_RelateDocModelProperties;
import myApp.client.vi.dcr.model.Dcr01_ClassTreeModel;
import myApp.client.vi.fnd.Fnd01_Lookup_MultiFund;
import myApp.client.vi.fnd.model.Fnd00_FundModel;

public class Apr03_Page_RelateFund extends ContentPanel {

	private Apr01_ApprModel apprModel ; 

	private TextButton relateFundInsertButton = new TextButton("연관펀드 등록");
	private Grid<Apr03_RelateFundModel> relateFundGrid = this.buildRelateFundGrid();
	
	private TextButton relateDocInsertButton = new TextButton("참조문서 등록");
	private Grid<Apr05_RelateDocModel> relateDocGrid = this.buildRelateDocGrid();
	
	private TextButton apprStepLineButton = new TextButton("결재선 설정");	
	private Grid<Apr04_ApprStepModel> apprStepGrid = this.buildApprStepGrid();
	
	private FormPanel fileDownloadForm = new FormPanel(); // file download form
	
	private Long stampEmpId = null;
	private Long stampId = null;
	private Long complianceOfficer = null;
	private InterfaceCallbackResult callBackResult = null;
	
	public Grid<Apr05_RelateDocModel> getRelateDocGrid(){
		return this.relateDocGrid; 
	}
	
	public Apr03_Page_RelateFund() {
		this.setHeaderVisible(false);
		// file download form 
		
		VerticalLayoutContainer vlc = new VerticalLayoutContainer(); 
		Margins gridMargins = new Margins(0, 10, 0, 10);
		Margins buttonMargins = new Margins(16, 0, 0, 510);
		
		this.fileDownloadForm.setVisible(false);
		vlc.add(this.fileDownloadForm); // form은 Layout에 붙어 있어야 한다.
		
		// 연관펀드 등록 
		this.relateFundInsertButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				insertRelateFundList();
			}
		});
		
		vlc.add(relateFundInsertButton, new VerticalLayoutData(100, -1, buttonMargins));
		vlc.add(relateFundGrid, new VerticalLayoutData(1, 0.3, gridMargins));
		
		// 참조문서 
		this.relateDocInsertButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				Long apprId = apprModel.getApprId(); 
				Apr05_Lookup_RelateDoc editor = new Apr05_Lookup_RelateDoc(relateDocGrid, apprId, new InterfaceCallbackResult() {
					@Override
					public void execute(Object result) {
						// TODO Auto-generated method stub
						//logger.info("콜백 받았습니다." + result.toString());
						retrieveRelateDocGrid();
					}
				});
				editor.show();
			}
		});
		vlc.add(relateDocInsertButton, new VerticalLayoutData(100, -1, buttonMargins));
		vlc.add(relateDocGrid, new VerticalLayoutData(1, 0.3, gridMargins));
		
		this.apprStepLineButton.setWidth(90);
		this.apprStepLineButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				Apr04_Edit_ApprStep editor = new Apr04_Edit_ApprStep(apprStepGrid, apprModel.getApprId());
				editor.setStampEmpId(stampEmpId);
				editor.setStampId(stampId);
				editor.show();
			}
		});

		vlc.add(this.apprStepLineButton, new VerticalLayoutData(100, -1, buttonMargins));
		vlc.add(this.apprStepGrid, new VerticalLayoutData(1, 0.36, gridMargins));
		
		this.add(vlc); 
	}

	public void insertRelateFundList() {
		
		List<Apr03_RelateFundModel> relateFundList = relateFundGrid.getStore().getAll(); 
		List<Long> relateFundIdList = new ArrayList<Long>(); 
		
		if(relateFundList.size() > 0) {
			for(Apr03_RelateFundModel relateFundModel : relateFundList) {
				relateFundIdList.add(relateFundModel.getFundId());
			}			
		}
		
		Fnd01_Lookup_MultiFund lookupFundInfo = new Fnd01_Lookup_MultiFund(); 
		lookupFundInfo.open(relateFundIdList, new InterfaceCallbackResult() {
			@Override
			public void execute(Object result) {
				@SuppressWarnings("unchecked")
				List<Fnd00_FundModel> fundList = (List<Fnd00_FundModel>) result;  
				if(fundList != null) {
					for(Fnd00_FundModel fundModel : fundList) {
						Apr03_RelateFundModel relateFundModel = new Apr03_RelateFundModel();  
						relateFundModel.setApprId(apprModel.getApprId());
						relateFundModel.setFundId(fundModel.getFundId());
						relateFundModel.setFundModel(fundModel);
						DBUtil dbUtil = new DBUtil();
						dbUtil.setSeq(relateFundModel, new InterfaceCallback() {
							@Override
							public void execute() {
								relateFundGrid.getStore().add(relateFundModel);
							}
						}); 
					}
				}
			}
		}); 
	}
	
    public void retrieve(Apr01_ApprModel apprModel, Boolean editable, InterfaceCallback interfaceCallback) {
    	this.apprModel = apprModel;
    	relateFundInsertButton.setEnabled(editable);
		
    	// 연관펀드의 삭제컬럼을 감추고 파일명을 넓혀준다.  
		this.relateFundGrid.getColumnModel().setHidden(5, true);
		this.relateFundGrid.getColumnModel().setColumnWidth(4, 380);
		
		//참조문서 삭제 컬럼 감추기
		this.relateDocGrid.getColumnModel().setHidden(4, true);
		this.relateDocGrid.getColumnModel().setColumnWidth(2, 430);
		
    	relateDocInsertButton.setEnabled(editable);
    	apprStepLineButton.setEnabled(editable);
    	this.retrieveRelateFundGrid(); 
    	this.retrieveRelateDocGrid(); 
    	this.retrieveApprStepGrid(interfaceCallback); 
    }

    // 연관펀드 조회
	private void retrieveRelateFundGrid() {  
    	GridRetrieveData<Apr03_RelateFundModel> service = new GridRetrieveData<Apr03_RelateFundModel>(relateFundGrid.getStore());
		service.addParam("apprId", this.apprModel.getApprId());
		service.retrieve("apr.Apr03_RelateFund.selectByApprId");
    }

	// 참조문서 조회
	private void retrieveRelateDocGrid() {  
    	GridRetrieveData<Apr05_RelateDocModel> service = new GridRetrieveData<Apr05_RelateDocModel>(relateDocGrid.getStore());
		service.addParam("companyId", LoginUser.getCompanyId());
		service.addParam("apprId" , this.apprModel.getApprId());
		service.retrieve("apr.Apr05_RelateDoc.selectByApprId");
    }
	
	
	// 결재선 조회
	private void retrieveApprStepGrid(InterfaceCallback callback) {  
   		GridRetrieveData<Apr04_ApprStepModel> service = new GridRetrieveData<Apr04_ApprStepModel>(this.apprStepGrid.getStore());
   		service.addParam("companyId", LoginUser.getCompanyId());
   		service.addParam("empId", LoginUser.getUserId());
   	    service.addParam("apprId", this.apprModel.getApprId());
   	    service.addParam("baseDate", this.apprModel.getRegDate());
   	    service.retrieve("apr.Apr04_ApprStep.selectByApprId", callback);
	}

	// 결재선 생성
   	public void createApprStepLine(Dcr01_ClassTreeModel classTreeModel, Apr01_ApprModel apprModel, Boolean isAdmin, InterfaceCallbackResult callBackResult) { 
   		this.callBackResult = callBackResult;
   		//결재 또는 조회 화면일 경우 설정되어 있는 결재선 생성
   		this.apprModel = apprModel; 
   		
   		GridRetrieveData<Apr04_ApprStepModel> service = new GridRetrieveData<Apr04_ApprStepModel>(this.apprStepGrid.getStore());
   		service.addParam("companyId", LoginUser.getCompanyId());
 	    service.addParam("empId", LoginUser.getUserId());
 	    service.addParam("approvalTypeCode", classTreeModel.getApprovalTypeCode());

 	    service.addParam("apprId", this.apprModel.getApprId()); 
 	    service.addParam("orgId", LoginUser.getOrgCodeId());
 	    service.retrieve("apr.Apr04_ApprStep.selectByApprovalTypeCd", new InterfaceCallback() {
			@Override
			public void execute() {
				if(isAdmin) {
					clearApprStep();
				} else {
					if(callBackResult != null) {
						callBackResult.execute(null);
					}
				}
			}
		});
   	}
	
    // 연관펀드 Grid Build
    private Grid<Apr03_RelateFundModel> buildRelateFundGrid() {
    	Apr03_RelateFundModelProperties properties = GWT.create(Apr03_RelateFundModelProperties.class);		
		GridBuilder<Apr03_RelateFundModel> gridBuilder = new GridBuilder<Apr03_RelateFundModel>(properties.keyId());  

		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText(properties.fundTypeName(), 80, "펀드유형");
		gridBuilder.addText(properties.fundCode(), 50, "코드");
		gridBuilder.addText(properties.fundName(), 320, "연관펀드명");
		
		ActionCell<String> editDocCell = new ActionCell<String>("Delete", new ActionCell.Delegate<String>(){
			@Override
			public void execute(String arg0) {
				GridDeleteData<Apr03_RelateFundModel> service = new GridDeleteData<Apr03_RelateFundModel>();
				List<Apr03_RelateFundModel> checkedList = relateFundGrid.getSelectionModel().getSelectedItems() ; 
				service.delete(relateFundGrid.getStore(), checkedList, "apr.Apr03_RelateFund.delete");
			}
		});
		gridBuilder.addCell(properties.deleteCell(), 60, "삭제", editDocCell); //, new TextField()) ;
		return gridBuilder.getGrid(); 
    }
    
    // 참조문서 Grid Build
    private Grid<Apr05_RelateDocModel> buildRelateDocGrid() {
    	Apr05_RelateDocModelProperties properties = GWT.create(Apr05_RelateDocModelProperties.class);		
		GridBuilder<Apr05_RelateDocModel> gridBuilder = new GridBuilder<Apr05_RelateDocModel>(properties.keyId());  

		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText(properties.fileName(), 350, "문서명");
		
		ActionCell<String> downCell = new ActionCell<String>("Down", new ActionCell.Delegate<String>(){
			@Override
			public void execute(String arg0) {
//				viewRelateDoc();
				fileDownloadForm.setEncoding(Encoding.MULTIPART);
				fileDownloadForm.setMethod(Method.POST);
				String actionUrl = "FileDownload?fileType=file&fileId=" + relateDocGrid.getSelectionModel().getSelectedItem().getFileModel().getFileId(); 
				fileDownloadForm.setAction(actionUrl);
				fileDownloadForm.submit(); // form을 submit하려면 Layout에 붙어 있어야 한다.
			}
		});
		gridBuilder.addCell(properties.actionCell(), 80, "파일받기", downCell); //, new TextField()) ;
		
		ActionCell<String> deleteCell = new ActionCell<String>("Delete", new ActionCell.Delegate<String>(){
			@Override
			public void execute(String arg0) {
				relateDocGrid.getStore().remove(relateDocGrid.getSelectionModel().getSelectedItem());
			}
		});
		gridBuilder.addCell(properties.actionCell(), 80, "삭제", deleteCell); //, new TextField()) ;
		
		return gridBuilder.getGrid();
    }
    
    // 결재선 Grid Build
    private Grid<Apr04_ApprStepModel> buildApprStepGrid(){
    	Apr04_ApprStepModelProperties properties = GWT.create(Apr04_ApprStepModelProperties.class);		
		GridBuilder<Apr04_ApprStepModel> gridBuilder = new GridBuilder<Apr04_ApprStepModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		
		gridBuilder.addText(properties.stepTypeName(), 80, "구분");
		gridBuilder.addText(properties.stepStatusName(), 80, "결재상태");
		//gridBuilder.addText(properties.seq(), 40, "seq");
		gridBuilder.addDate(properties.apprDate(), 85, "결재일");
		gridBuilder.addText(properties.stepApprName(), 200, "결재자");
		//gridBuilder.addLong(properties.stepEmpId(), 200, "원결재자");
		//gridBuilder.addLong(properties.apprEmpId(), 200, "실결재자");

		ActionCell<String> detailCell = new ActionCell<String>("Detail", new ActionCell.Delegate<String>(){
			@Override
			public void execute(String arg0) {
				String msg = apprStepGrid.getSelectionModel().getSelectedItem().getRejectReason(); 
				Apr01_Edit_RejectMessage editor = new Apr01_Edit_RejectMessage();
				editor.setMessage(msg);
			}
		});
		
		gridBuilder.addCell(properties.actionCell(), 60, "상세보기", detailCell); //, new TextField()) ;
		
		return gridBuilder.getGrid(); 
	}

   	public Grid<Apr04_ApprStepModel> getApprStepGrid() {
   		return this.apprStepGrid; 
   	}
   	
   	public List<GridDataModel> getRelateFundList() {
   		// 연관펀드 List 가져오기.   
   		List<GridDataModel> updateList = new ArrayList<GridDataModel>(); 
   		for(Apr03_RelateFundModel data :this.relateFundGrid.getStore().getAll()) {
   			updateList.add((GridDataModel)data); 
   		}
   		return updateList; 
   	}

   	public List<GridDataModel> getRelateDocList() {
   		// 연관문서 List 가져오기.   
   		List<GridDataModel> updateList = new ArrayList<GridDataModel>(); 
   		for(Apr05_RelateDocModel data :this.relateDocGrid.getStore().getAll()) {
   			updateList.add((GridDataModel)data); 
   		}
   		return updateList; 
   	}

   	public void openButton() {
   		relateFundInsertButton.setEnabled(true);
    	relateDocInsertButton.setEnabled(true);
    	apprStepLineButton.setEnabled(true);
    	
    	this.relateFundGrid.getColumnModel().setHidden(5, false);
		this.relateFundGrid.getColumnModel().setColumnWidth(4, 320);
		
		this.relateDocGrid.getColumnModel().setHidden(4, false);
		this.relateDocGrid.getColumnModel().setHidden(3, false);
		this.relateDocGrid.getColumnModel().setColumnWidth(2, 350);
		
   	}
   	
   	public void hideDown() {
   		this.relateDocGrid.getColumnModel().setHidden(4, true);
		this.relateDocGrid.getColumnModel().setHidden(3, true);
		this.relateDocGrid.getColumnModel().setColumnWidth(2, 500);
   	}
   	
   	public void clearApprStep() {
		Apr04_ApprStepModel tempModel = apprStepGrid.getStore().get(0);
		apprStepGrid.getStore().clear();
		apprStepGrid.getStore().add(tempModel);
		//this.apprFundList.getApprStepGrid().getView().refresh(true);
	}
   	
   	public void setStampEmpId(Long stampEmpId) {
   		this.stampEmpId = stampEmpId;
   	}
   	
   	public void setStampId(Long stampId) {
   		this.stampId = stampId;
   	}
}
