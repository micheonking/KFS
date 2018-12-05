package myApp.client.vi;

import com.gargoylesoftware.htmlunit.javascript.host.Window;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.PlainTabPanel;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer.AccordionLayoutAppearance;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer.ExpandMode;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.FocusEvent;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.kfsEntryPoint;
import myApp.client.resource.ResourceIcon;

public class MainFrame extends BorderLayoutContainer {

	private PlainTabPanel tabPanel = new PlainTabPanel();
	private MenuTree treeMenu = new MenuTree(tabPanel); 
	
	public MainFrame getMainWindow() {
		
		// 상단 Bar 등록  
		BorderLayoutData northLayoutData = new BorderLayoutData(60);
		northLayoutData.setMargins(new Margins(0,0,3,0));
		this.setNorthWidget(this.getNorthLayout(), northLayoutData); 
		
		// West Layout setting 
		BorderLayoutData westLayoutData = new BorderLayoutData(250);
		westLayoutData.setMargins(new Margins(0, 1, 0, 0)); // 앞쪽에 보이는 가로 줄을 없애준다
		westLayoutData.setCollapsible(true);
		westLayoutData.setCollapseHeaderVisible(true);
		westLayoutData.setSplit(true);
		
		//westLayoutData.setCollapseMini(true);
		this.setWestWidget(this.getWestLayout2(), westLayoutData);
		//this.setWestWidget(treeMenu.getMenuTree()); 
				
		tabPanel.setTabScroll(true);
		tabPanel.add(new TabBorder(), "My Page"); // my page setting

		VerticalLayoutContainer vlc = new VerticalLayoutContainer(); 
		vlc.add(tabPanel, new VerticalLayoutData(1, 1, new Margins(2))); //, 2, 2, 2)));
		
		this.setCenterWidget(vlc);
		return this; 
	}
	
	private ContentPanel getNorthLayout(){
		BoxLayoutData flex = new BoxLayoutData(new Margins(0, 5, 0, 0));
		flex.setFlex(1);
		    
		HBoxLayoutContainer header = new HBoxLayoutContainer();
		header.setPadding(new Padding(5));
		header.setHBoxLayoutAlign(HBoxLayoutAlign.TOP);
//		header.setHeight(60);

		// HIIS
		Image image = new Image();
		image.setResource(ResourceIcon.INSTANCE.getLogo());
		image.setPixelSize(280, 26);
		header.add(image, new BoxLayoutData(new Margins(15, 0, 0, 20)));

		BoxLayoutData boxLayoutData = new BoxLayoutData(new Margins(0, 0, 0, 0)); 
		boxLayoutData.setFlex(1);
		
		header.add(new Label(), boxLayoutData);
		 
		String userInfo = LoginUser.getOrgKorName() + " " + LoginUser.getUserName() + " 님" ; ;
		userInfo = "<p style='color:#666666; font-size:14px; font-weight:bold'>" +  userInfo + "</p>" ;  
		
		SafeHtml safeEscapedHtml = SafeHtmlUtils.fromTrustedString(userInfo);
		
		LabelToolItem label = new LabelToolItem(safeEscapedHtml); 
		
//		Label label = new Label(headerMessage);  GWT Label 설정. 
//		label.getElement().getStyle().setProperty("color", "#666666"); // font color 변경
//		label.getElement().getStyle().setProperty("fontWeight", "bold"); // font color 변경
//		label.getElement().getStyle().setProperty("fontSize", "14px"); // font color 변경

		header.add(label, new BoxLayoutData(new Margins(25, 0, 0, 0)));
		
		Image logoutImage = new Image(ResourceIcon.INSTANCE.getLogout()); 
		logoutImage.setPixelSize(25,  25);
		 
		
		
//		ToolButton close = new ToolButton(ToolButton.DOUBLERIGHT); 
//		header.add(logoutImage, new BoxLayoutData(new Margins(22, 0, 0, 10))); 

		
		String logoutString = "<button style='cursor:pointer;' onclick=\"location.reload();\">Logout</button>" ; 
		SafeHtml logoutHtml = SafeHtmlUtils.fromTrustedString(logoutString);
		
		LabelToolItem logoutLabel = new LabelToolItem(logoutHtml);
//		logoutLabel.add		
		header.add(logoutLabel, new BoxLayoutData(new Margins(22, 40, 0, 5)));
		
		ContentPanel cp = new ContentPanel();
		cp.setBodyStyle("backgroundColor:#FFFFFF"); // http://www.w3schools.com/colors/colors_names.asp 페이지 참조 
		
		cp.add(header);
//		cp.forceLayout();
		cp.setHeaderVisible(false);
//		cp.setBorders(true);
//		cp.setHeight(60);
//		cp.getButtonBar().setHeight(0);
		
		return cp ; 
	}
	
	private VerticalLayoutContainer getWestLayout(){
		
		AccordionLayoutAppearance accordianLayout = GWT.create(AccordionLayoutAppearance.class); 
		ContentPanel treeAccordianPanel = new ContentPanel(accordianLayout); 
		treeAccordianPanel.setHeading("Menu Navigation");	
		treeAccordianPanel.add(this.treeMenu.getMenuTree()); // tree menu setting 

		AccordionLayoutContainer accordianContainer = new AccordionLayoutContainer();
		
		accordianContainer.setExpandMode(ExpandMode.SINGLE_FILL);
		accordianContainer.setHideCollapseTool(true); // 감추기 버튼 감추기
		accordianContainer.setTitleCollapse(false); // 감추기 버튼 작동안하기 
		
		accordianContainer.add(treeAccordianPanel);
		accordianContainer.setActiveWidget(treeAccordianPanel);
		
		VerticalLayoutContainer vlc = new VerticalLayoutContainer(); 
		vlc.add(accordianContainer, new VerticalLayoutData(1, 1, new Margins(3, 2, 2, 2)));
		
		return vlc ; 
		
	}
	
	private ContentPanel getWestLayout2(){
		
		AccordionLayoutAppearance accordianLayout = GWT.create(AccordionLayoutAppearance.class); 
		ContentPanel treeAccordianPanel = new ContentPanel(accordianLayout); 
		treeAccordianPanel.setHeading("Menu Navigation");	
		treeAccordianPanel.add(this.treeMenu.getMenuTree()); // tree menu setting 
		treeAccordianPanel.getHeader().setHeight(18);
		AccordionLayoutContainer accordianContainer = new AccordionLayoutContainer();
		
		accordianContainer.setExpandMode(ExpandMode.SINGLE_FILL);
		accordianContainer.setHideCollapseTool(true); // 감추기 버튼 감추기
		accordianContainer.setTitleCollapse(false); // 감추기 버튼 작동안하기 
		
		accordianContainer.add(treeAccordianPanel);
		accordianContainer.setActiveWidget(treeAccordianPanel);
		
		VerticalLayoutContainer vlc = new VerticalLayoutContainer(); 
		vlc.add(accordianContainer, new VerticalLayoutData(1, 1, new Margins(3, 2, 2, 2)));
		
		return treeAccordianPanel; 
		
	}
}
