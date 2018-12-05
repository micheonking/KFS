package myApp.client.vi.emp;

import java.util.Date;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import com.sencha.gxt.widget.core.client.tree.Tree;

import myApp.client.grid.GridBuilder;
import myApp.client.service.GridRetrieveData;
import myApp.client.vi.LoginUser;
import myApp.client.vi.emp.model.Emp00_InfoModel;
import myApp.client.vi.emp.model.Emp00_InfoModelProperties;
import myApp.client.vi.org.Org01_Tree_OrgCode;
import myApp.client.vi.org.model.Org01_CodeModel;

public class Emp02_Tab_OrgEmp extends BorderLayoutContainer {
	
	private DateField baseDate = new DateField();
	private Org01_Tree_OrgCode buildTree = new Org01_Tree_OrgCode();
	private Emp00_InfoModelProperties properties = GWT.create(Emp00_InfoModelProperties.class);
	private Grid<Emp00_InfoModel> grid = this.buildGrid();
	
	public interface EmpImageXTemplate extends XTemplates {
		@XTemplate("<img style=\"border: 0px solid red; height:100px;  width: 80px; margin:5px 5px; \" src=\"FileDownload?fileType=image&parentId={url}\">")
	    SafeHtml createImage(String url);
	}
	
	private class EmpImageCell extends AbstractCell<String> {
	
		private EmpImageXTemplate imageTemplate = GWT.create(EmpImageXTemplate.class);

		@Override
		public void render(Context context, String empId, SafeHtmlBuilder sb) {
			// TODO Auto-generated method stub
			if (empId != null) {
				Date tempDate = new Date();
				String tempStr = String.valueOf(tempDate.getTime());
		        sb.append(imageTemplate.createImage(empId + "&" +tempStr));
			}			
		}
   	 }
	
	public Emp02_Tab_OrgEmp() {

		ButtonBar buttonBar = new ButtonBar();
		
		baseDate.setValue(new Date());
		baseDate.setValue(LoginUser.getToday());
		
		FieldLabel dateFiledLabel = new FieldLabel(baseDate, "기준일");
		dateFiledLabel.setWidth(160);
		dateFiledLabel.setLabelWidth(40);
		buttonBar.add(dateFiledLabel);
		
		TextButton retrieveButton = new TextButton("조회");
		retrieveButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				retrieve();
			}
		});
		buttonBar.add(retrieveButton); 

		BorderLayoutData northLayoutData = new BorderLayoutData(60);
		northLayoutData.setMargins(new Margins(0, 0, 1, 0));
		this.setNorthWidget(buttonBar, northLayoutData);// 왼쪽 컨테이너 표시

		BorderLayoutData westLayoutData = new BorderLayoutData(0.2);
		westLayoutData.setSplit(true);
		westLayoutData.setMaxSize(1000);
		westLayoutData.setMargins(new Margins(1, 4, 0, 0));

		
		Tree<Org01_CodeModel, String> orgTree = buildTree.getTree();  
		orgTree.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Org01_CodeModel>() {

			@Override
			public void onSelectionChanged(SelectionChangedEvent<Org01_CodeModel> event) {

				Org01_CodeModel data = event.getSource().getSelectedItem(); //event.getSelection().get(0); 
				
				if(data != null ) {
						
					GridRetrieveData<Emp00_InfoModel> service = new GridRetrieveData<Emp00_InfoModel>(grid.getStore());
					service.addParam("orgCodeId", data.getCodeId());
					service.addParam("baseDate", baseDate.getValue());
					service.retrieve("emp.Emp00_Info.selectByOrgCodeId");
				}
			}
		}) ; 
		
		ContentPanel cp = new ContentPanel();
		cp.setHeaderVisible(false);
		cp.add(orgTree);
		this.setWestWidget(cp, westLayoutData);// 왼쪽 컨테이너 표시
		
		retrieve();

		BorderLayoutData centerLayoutData = new BorderLayoutData();//중앙 컨테이너 레이아웃 데이터
		centerLayoutData.setSplit(true);
		this.setCenterWidget(grid, centerLayoutData);//중앙 컨테이너 표시
	}

	public Grid<Emp00_InfoModel> buildGrid(){

		
		GridBuilder<Emp00_InfoModel> gridBuilder = new GridBuilder<Emp00_InfoModel>(properties.keyId());
		gridBuilder.setChecked(SelectionMode.SINGLE);
		
		gridBuilder.addCell(properties.personId_String(), 110, "사원이미지",	new EmpImageCell());
		gridBuilder.addText(properties.titleName(), 60, "직책");
		gridBuilder.addText(properties.empNo(), 80, "사번");
		gridBuilder.addText(properties.korName(), 100, "성명");
		gridBuilder.addDate(properties.birthday(), 100, "생일");
		gridBuilder.addText(properties.genderName(), 40, "성별");
		gridBuilder.addText(properties.mobileTelNo(), 120, "핸드폰번호");
		gridBuilder.addText(properties.emergencyTelNo(), 120, "회사번호");
		gridBuilder.addText(properties.emailAddress(), 170, "이메일주소");
		
		return gridBuilder.getGrid();
	}	


	private void retrieve() {
		buildTree.retrieve(baseDate.getValue());
	}
	
//	private void retrievePage() {
//		Dcr01_ClassTreeModel classTreeModel = this.dcrTree.getSelectionModel().getSelectedItem();
//		gridDoc.retrieve(classTreeModel);
//	}
}