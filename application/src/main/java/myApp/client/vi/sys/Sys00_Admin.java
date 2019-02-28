package myApp.client.vi.sys;

import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.PlainTabPanel;
import com.sencha.gxt.widget.core.client.container.MarginData;

public class Sys00_Admin extends ContentPanel {
	
	public Sys00_Admin() {

		this.setHeading("시스템 관리자");
		this.setBorders(true);
		
		PlainTabPanel tabPanel = new PlainTabPanel();
		
		Sys01_Tab_CmpInfo tabCmpInfo = new Sys01_Tab_CmpInfo();
		tabPanel.add(tabCmpInfo, "회사정보");

		Sys03_Tab_Menu tabMenu = new Sys03_Tab_Menu();
		tabPanel.add(tabMenu, "메뉴구성");

		Sys50_Tab_TermsDict tabCode = new Sys50_Tab_TermsDict();
		tabPanel.add(tabCode, "용어사전");
		
		this.add(tabPanel, new MarginData(3));
	}
}