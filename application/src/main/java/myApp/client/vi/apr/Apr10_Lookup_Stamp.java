package myApp.client.vi.apr;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;

import myApp.client.grid.GridBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.utils.FileUpdownForm;
import myApp.client.utils.InterfaceCallbackResult;
import myApp.client.vi.LoginUser;
import myApp.client.vi.apr.model.Apr10_StampModel;
import myApp.client.vi.apr.model.Apr10_StampModelProperties;



public class Apr10_Lookup_Stamp extends Window{
	Date baseDate ;
	private final Logger   logger = Logger.getLogger(this.getClass().getName());
	
	private Apr10_StampModelProperties properties = GWT.create(Apr10_StampModelProperties.class);
	private Grid<Apr10_StampModel> grid = this.buildGrid(); 
	private InterfaceCallbackResult callbackResult;
	
	private TextField searchText = new TextField();
	private FileUpdownForm fileUpdownForm = new FileUpdownForm();
	
	private Boolean flag = false;
	
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
	 
	 
	public Apr10_Lookup_Stamp() {
		
	}
	
	public void open(InterfaceCallbackResult callbackResult) {
		this.callbackResult = callbackResult;
		makeLayout();
		
		retrieve();
	}
	
	public void open(InterfaceCallbackResult callbackResult, Boolean flag) {
		this.flag = flag;
		open(callbackResult);
	}
	
	private void makeLayout() {
		this.setModal(true);
		this.setHeading("인감을 선택하세요.");
		this.setResizable(false);
		this.setPixelSize(580, 550);
		
		this.add(setGrid(), new VerticalLayoutData(1, 1, new Margins(0,0,0,0)));
		
		TextButton confirmButton = new TextButton("확인");
		confirmButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				confirm();
			}
		});
		TextButton deleteButton = new TextButton("해당사항 없음");
		deleteButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				delete();
			}
		});
		TextButton closeButton = new TextButton("닫기");
		closeButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				hide();
			}
		});
		this.getButtonBar().add(confirmButton);
		this.getButtonBar().add(deleteButton);
		this.getButtonBar().add(closeButton);
		this.setButtonAlign(BoxLayoutPack.CENTER);
		this.show();
	}
	
	private VerticalLayoutContainer setGrid() {
		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		
		HorizontalLayoutContainer row01 = new HorizontalLayoutContainer();
		FieldLabel searchField = new FieldLabel(searchText, "검색어");
		searchField.setLabelWidth(50);
		row01.add(searchField, new HorizontalLayoutData(200, -1, new Margins(0)));
		
		TextButton searchButton = new TextButton("검색") ;
		searchButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				retrieve();
			}
		});
		row01.add(searchButton, new HorizontalLayoutData(60, -1, new Margins(0,0,0,6)));
		
		vlc.add(row01, new VerticalLayoutData(1, 38, new Margins(5)));
		vlc.add(grid, new VerticalLayoutData(1,1 , new Margins(0,0,0,0)));
		
		return vlc;

	}
	private Grid<Apr10_StampModel> buildGrid() {

		GridBuilder<Apr10_StampModel> gridBuilder = new GridBuilder<Apr10_StampModel>(properties.keyId());

		gridBuilder.setChecked(SelectionMode.SINGLE);
		//gridBuilder.addLong(properties.stampId(), 20, "ID");
		gridBuilder.addCell(properties.stampId_string(), 80, "인감", new CustomImageCell()); 
		gridBuilder.addText(properties.stampName(),      120, "인감이름");
		gridBuilder.addText(properties.useName(),        200, "사용용도");
		gridBuilder.addText(properties.orgName(),	     100, "부서");
		
		return gridBuilder.getGrid();
	}
	
	private void retrieve() {
		GridRetrieveData<Apr10_StampModel> service = new GridRetrieveData<Apr10_StampModel>(grid.getStore());
		service.addParam("companyId", LoginUser.getCompanyId());
		service.addParam("orgId", LoginUser.getOrgCodeId());
		service.addParam("searchText", searchText.getText());
		if(flag) {
			service.retrieve("apr.Apr10_Stamp.selectBySearchText");
		} else {
			service.retrieve("apr.Apr10_Stamp.selectByOrgId");
		}
	}
	
	private void confirm() {
		Apr10_StampModel stampModel = grid.getSelectionModel().getSelectedItem();
		callbackResult.execute(stampModel);
		hide();
		
		/*
		//회사조직이면서 펀드회계팀 인장이 아닐경우 그냥 통과
		if("1000000".equals(stampModel.getOrgId().toString()) && !"2018126".equals(stampModel.getStampId().toString())) {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("stampModel", stampModel);
			callbackResult.execute(result);
			hide();
		} else {
			selectTeamLeader(stampModel);//합의자 강제추가
		}
		*/
	}
	
	private void delete() {
		Apr10_StampModel stampModel = new Apr10_StampModel();
		callbackResult.execute(stampModel);
		
		hide();
	}
	
	//합의자 강제추가(부서장)
	private void selectTeamLeader(Apr10_StampModel stampModel) {
		
		GridRetrieveData<Apr10_StampModel> service = new GridRetrieveData<Apr10_StampModel>(grid.getStore());
		service.addParam("companyId", LoginUser.getCompanyId());
		service.addParam("orgId", stampModel.getOrgId());
		
		service.retrieve("emp.Emp00_Info.selectTeamLeaderByOrgId", new InterfaceCallbackResult() {
			
			@Override
			public void execute(Object result) {
				logger.info(result.toString());
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("stampModel", stampModel);
				//callbackResult.execute(resultMap);
				//hide();
			}
		});
	}
}

