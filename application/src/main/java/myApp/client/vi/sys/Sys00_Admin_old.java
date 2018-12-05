package myApp.client.vi.sys;

import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.PlainTabPanel;
import com.sencha.gxt.widget.core.client.container.MarginData;

public class Sys00_Admin_old extends ContentPanel {
	
	public Sys00_Admin_old() {

		this.setHeading("시스템 관리자");
		this.setBorders(true);
		
		
		PlainTabPanel tabPanel = new PlainTabPanel();
		
		Sys01_Tab_Company tabCompany = new Sys01_Tab_Company();
		tabPanel.add(tabCompany, "고객정보");

		Sys06_Tab_Menu tabMenu = new Sys06_Tab_Menu();
		tabPanel.add(tabMenu, "메뉴구성");
		
		Sys08_CodeKind tabCode = new Sys08_CodeKind();
		tabPanel.add(tabCode, "공통코드");
		
		this.add(tabPanel, new MarginData(3));
		
	}
}