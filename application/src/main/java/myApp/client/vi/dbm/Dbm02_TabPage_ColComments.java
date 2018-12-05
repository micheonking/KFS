package myApp.client.vi.dbm;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;

import myApp.client.grid.GridBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.vi.PDFViewer;
import myApp.client.vi.RDViewer;
import myApp.client.vi.dbm.model.Dbm02_ColCommentsModel;
import myApp.client.vi.dbm.model.Dbm02_ColCommentsModelProperties;

public class Dbm02_TabPage_ColComments<Stock> extends ContentPanel implements InterfaceServiceCall {

	private Grid<Dbm02_ColCommentsModel> grid = this.buildGrid();
	private String tableName;

	public Dbm02_TabPage_ColComments() {
		this.setHeaderVisible(false); 
		this.add(this.grid);

		TextButton xmlGeneratorButton = new TextButton("XML");
		xmlGeneratorButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
//				Info.display("XML Generator", "This is Model XML" );
				popupXML();
			}
		});
		this.getButtonBar().add(xmlGeneratorButton); 

		TextButton modelGeneratorButton = new TextButton("Model");
		modelGeneratorButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
//				Info.display("Model Generator", "This is Model Generator");
				popupModel();
			}
		});
		this.getButtonBar().add(modelGeneratorButton); 

		TextButton modelPropertiesButton = new TextButton("Properties");
		modelPropertiesButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
//				Info.display("Properties Generator", "This is Properties Generator");
				popupProperties();
			}
		});
		this.getButtonBar().add(modelPropertiesButton); 

		TextButton gridBuilderButton = new TextButton("GridBuilder");
		gridBuilderButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
//				Info.display("Properties Generator", "This is GridBuilder Generator");
				popupGridBuilder();
			}
		});
		this.getButtonBar().add(gridBuilderButton); 

		TextButton retrievePDFButton = new TextButton("Table Print");
	    retrievePDFButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {

				PDFViewer viewer = new PDFViewer(); 
				// 호출하려면 className과 기타 Parameter를 String으로 붙여서 넘겨주어야 한다. 
				viewer.open("className=dbm.Dbm02_ColCommentsPDF&tableName=" + tableName);
			}
		});
		this.getButtonBar().add(retrievePDFButton); 

		TextButton retrievePDF2Button = new TextButton("Report Print");
	    retrievePDF2Button.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {

				PDFViewer viewer = new PDFViewer(); 
				// 호출하려면 className과 기타 Parameter를 String으로 붙여서 넘겨주어야 한다. keyId는 파일생성용키
//				viewer.open("className=rpt.Apr10_StampPDF&keyId=10001");
//				viewer.open("className=rpt.TestReportPDF");
				viewer.open("className=dcr.Dcr10_MailSenderPDF");
			}
		});
		this.getButtonBar().add(retrievePDF2Button); 

		TextButton retrieveRDButton = new TextButton("RD Print");
		retrieveRDButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {

				Info.display("tableName", tableName);
//				PDFViewer viewer = new PDFViewer(); 
//				// 호출하려면 className과 기타 Parameter를 String으로 붙여서 넘겨주어야 한다. 
//				viewer.open("className=dbm.Dbm02_ColCommentsPDF&tableName=" + tableName);

				TextField text = new TextField();
				text.setValue(tableName);
				tableName = text.getValue().toString().toUpperCase();

				RDViewer viewer = new RDViewer(); 
				// 호출하려면 className과 기타 Parameter를 String으로 붙여서 넘겨주어야 한다. 
				viewer.open("rd_Table_Define.html?table_in=" + tableName);
			}
		});
		this.getButtonBar().add(retrieveRDButton); 

//		TextButton sendMailSMTPButton = new TextButton("send Mail");
//		sendMailSMTPButton.addSelectHandler(new SelectHandler() {
//			@Override
//			public void onSelect(SelectEvent event) {
//
////				sendEMailOffice365();
//				sendMailSMTP();
//
//			}
//		});
//		this.getButtonBar().add(sendMailSMTPButton); 

		TextButton updateButton = new TextButton("Comment Save"); 
	    updateButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				update(); 
			}
		}); 
	    
	    this.getButtonBar().add(updateButton); 
		this.setButtonAlign(BoxLayoutPack.CENTER);
	}

	public Grid<Dbm02_ColCommentsModel> buildGrid(){

		Dbm02_ColCommentsModelProperties properties = GWT.create(Dbm02_ColCommentsModelProperties.class);
		GridBuilder<Dbm02_ColCommentsModel> gridBuilder = new GridBuilder<Dbm02_ColCommentsModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);

//		gridBuilder.addLong(properties.columnId()		, 	30	,	"No");
		gridBuilder.addText(properties.columnName()		,	250	,	"Column ID");
		
//		TextField textField = new TextField(); 
//		textField.addDomHandler(new KeyDownHandler() {
//		    @Override 
//		    public void onKeyDown(KeyDownEvent event) {
//		        if (KeyCodes.KEY_TAB == event.getNativeEvent().getKeyCode()) {
//
//		        	execute(textField.getText());
//		        }
//		    }
//
//		}, KeyDownEvent.getType() );
		
		gridBuilder.addText(properties.columnComment()	,	300	,	"Comment",	new TextField());
		gridBuilder.addText(properties.dataType()		,	80	,	"Type");
		gridBuilder.addText(properties.nullAble()		,	60	,	"Null");
		gridBuilder.addText(properties.columnLength()	,	60	,	"Length");

		return gridBuilder.getGrid(); 
	}

//	public void execute(String comments){
//
//		Dbm02_ColCommentsModel data = grid.getSelectionModel().getSelectedItem(); 
//		
//		if(data != null) {
//			String columnName = data.getColumnName();
//		
////			colGrid.retrieve(columnName);
//			
//			ServiceRequest request = new ServiceRequest("dbm.Dbm00_DDLRun.callByDDLRun");
//			request.putStringParam("para", "comment on column " + data.getTableName() + "." + columnName + " is '" + comments + "'");
//			
//			Info.display("execute", "comment on column " + data.getTableName() + "." + columnName + " is '" + comments + "'");
//			ServiceCall service = new ServiceCall();
//			service.execute(request, this);
//		}
////		retrieve(tableName); 
//	}

	@Override
	public void getServiceResult(ServiceResult result) {

		
		String	msgString = result.getMessage();
		if (result.getStatus() < 0) {
			Info.display("메뉴조회 오류 : ",msgString);
		}
		
		Dbm02_PopupSourceGenerator popupXmlGenerator = new Dbm02_PopupSourceGenerator(msgString);
		popupXmlGenerator.setHeading("Generating");
		popupXmlGenerator.show();

//		if (result.getStatus() < 0) {
//			Info.display("메뉴조회 오류", result.getMessage());
//		} 
//		else {
////			grid.getStore().clear(); // 깨끗이 비운다.
////			
////			Info.display("tableName 오류 지울것", tableName);
////			retrieve(tableName);
//
//			grid.getStore().commitChanges();
//
////			for (GridDataModel model : result.getResult()) {
////				// 서버에서 전체 트리를 한번에 가져온 후 트리를 구성한다.
////				Dbm02_ColCommentsModel colModel = (Dbm02_ColCommentsModel) model;
//
////				dbmModel.getStore().add(dbmModel);
////				this.addChild(dbmModel); // child menu & object setting
////				this.orgTree.setExpanded(dbmModel, true);//첫번째 레벨만 펼치기
////			}
//		}
	}

	public void retrieve(String tableName) {
		this.tableName = tableName; 
		GridRetrieveData<Dbm02_ColCommentsModel> service = new GridRetrieveData<Dbm02_ColCommentsModel>(this.grid.getStore());
		service.addParam("tableName", tableName);
		service.retrieve("dbm.Dbm02_ColComments.selectByTableName");
	}

	public void update() {
		GridUpdate<Dbm02_ColCommentsModel> service = new GridUpdate<Dbm02_ColCommentsModel>(); 
		service.update(this.grid.getStore(), "dbm.Dbm02_ColComments.update"); 
	}

	private void popupXML() {
		
		ServiceRequest request = new ServiceRequest("dbm.Dbm03_TabColumns.selectByXML");

		request.putStringParam("tableName", this.tableName);
		
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}
	
	private void popupModel() {
		
		ServiceRequest request = new ServiceRequest("dbm.Dbm03_TabColumns.selectByModel");

		request.putStringParam("tableName", this.tableName);
		
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}
	
	private void popupProperties() {
		
		ServiceRequest request = new ServiceRequest("dbm.Dbm03_TabColumns.selectByProperties");

		request.putStringParam("tableName", this.tableName);
		
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}
	
	private void popupGridBuilder() {
		
		ServiceRequest request = new ServiceRequest("dbm.Dbm03_TabColumns.selectByGridBuilder");

		request.putStringParam("tableName", this.tableName);
		request.putLongParam("inOut", (long) 1);
		
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}

//	private void sendMailSMTP() {
//		
//		ServiceRequest request = new ServiceRequest("utils.mail.SendMailSMTP.sendMailSession");
//
//		List<String> fileList = new ArrayList<String>(); 
//		fileList.add("E:\\WebFiles\\20139\\111.pdf"); 
//		fileList.add("E:\\WebFiles\\20139\\222.jpg");
//		
//		request.putStringParam("receptor", "test@hdfund.co.kr");
//		request.putStringParam("header", "이러한 제목으로 보내드립니다.");
//		request.putStringParam("content", "첨부파일을 확인하시고 회신메일 부탁드립니다." + LoginUser.getTodayString());
//		request.addParm("fileList", fileList);
//		
//		ServiceCall service = new ServiceCall();
//		service.execute(request, new InterfaceServiceCall() {
//			@Override
//			public void getServiceResult(ServiceResult result) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//	}

}