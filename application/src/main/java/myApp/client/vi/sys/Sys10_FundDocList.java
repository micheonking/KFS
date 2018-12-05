package myApp.client.vi.sys;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.grid.GridBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.InterfaceCallback;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.vi.LoginUser;
import myApp.client.vi.apr.Apr01_Edit_ApprDoc;
import myApp.client.vi.apr.model.Apr01_ApprModel;
import myApp.client.vi.apr.model.Apr01_ApprModelProperties;
import myApp.client.vi.sys.model.Sys10_FileModel;
import myApp.client.vi.sys.model.Sys10_FileModelProperties;

public class Sys10_FundDocList extends ContentPanel {
	
//	private Grid<Sys10_FileModel> grid = this.buildGrid();
	private Grid<Apr01_ApprModel> grid = this.buildGrid();

	public Sys10_FundDocList(){
		this.setHeaderVisible(false);
		this.add(this.grid);
	}

     public Grid<Apr01_ApprModel> buildGrid(){
		
//    	 Sys10_FileModelProperties properties = GWT.create(Sys10_FileModelProperties.class);
    	 Apr01_ApprModelProperties properties = GWT.create(Apr01_ApprModelProperties.class);
    	 
    	 GridBuilder<Apr01_ApprModel> gridBuilder = new GridBuilder<Apr01_ApprModel>(properties.keyId());
		
    	 gridBuilder.setChecked(SelectionMode.SINGLE);
    	 
    	 gridBuilder.addDate(properties.regDate(), 80, "상신일");
    	 gridBuilder.addDate(properties.effectDate(), 80, "시행일");
    	 gridBuilder.addText(properties.title(), 400, "상신제목");
    	 
    	 ActionCell<String> detailCell = new ActionCell<String>("Detail", new ActionCell.Delegate<String>(){
    		 @Override
    		 public void execute(String arg0) {
    			 Apr01_ApprModel apprModel = grid.getSelectionModel().getSelectedItem();
    			 Apr01_Edit_ApprDoc apprEditor = new Apr01_Edit_ApprDoc();   
    			 apprEditor.retrieveApproval(apprModel, false, new InterfaceCallback() {
    					@Override
    					public void execute() {
//    						retrieve();
    					}
    				});
    		 }
    	 });
    	 gridBuilder.addCell(properties.actionCell(), 70, "상세보기", detailCell); //, new TextField()) ;
    	 gridBuilder.addText(properties.fileName(), 400, "파일명");
//    	 gridBuilder.addDate(properties.regDate(), 100, "등록일");
//    	 gridBuilder.addText(properties.empName(), 80, "등록자");
//    	 gridBuilder.addText(properties.orgName(), 200, "등록부서");

    	 return gridBuilder.getGrid(); 
     }
	
	public void retrieve(Long classTreeId, Long fundCodeId)  {
		grid.mask("Loading");
		GridRetrieveData<Apr01_ApprModel> service = new GridRetrieveData<Apr01_ApprModel>(this.grid.getStore());
		service.addParam("classTreeId", classTreeId);
		service.addParam("fundCodeId", fundCodeId);
		service.addParam("orgId", LoginUser.getOrgCodeId());
		service.retrieve("apr.Apr01_Appr.selectByFundCodeId", new InterfaceCallback() {
			@Override
			public void execute() {
				grid.unmask();
			}
		}); 
	}
	
//	private void sendMail() {
//		Sys10_FileModel relateFundModel = grid.getSelectionModel().getSelectedItem();
//
//		//Dcr10_Lookup_MailSender mailSender = new Dcr10_Lookup_MailSender(dcrModel.getDocId(), "");
//		Dcr10_Lookup_MailSender mailSender = new Dcr10_Lookup_MailSender(relateFundModel.getApprModel().getApprId(), "");
//		mailSender.show();
//	}
}
