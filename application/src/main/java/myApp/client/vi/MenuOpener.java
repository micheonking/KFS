package myApp.client.vi;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.TabItemConfig;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.info.Info;

public class MenuOpener {

	private Widget createTab(String className) {

		if ("Sys04_Tab_Role.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.sys.Sys04_Tab_Role.class);
		}

		if ("Sys06_Tab_Menu.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.sys.Sys06_Tab_Menu.class);
		}

		if ("Sys07_Tab_RoleMenu.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.sys.Sys07_Tab_RoleMenu.class);
		}

		if ("Sys08_Tab_CodeKind".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.sys.Sys08_CodeKind.class);
		}
		
		return null;
	}

	public void openTab(TabPanel tabPanel, String className, String pageName) {

		TabItemConfig tabItemConfig = new TabItemConfig(pageName, true);
		Widget tabPage = tabPanel.findItem(pageName, true);

		if (tabPage != null) {
			tabPanel.setActiveWidget(tabPage);
			return;
		}

		// not found tab page
		tabPage = createTab(className);

		if (tabPage != null) {
			tabPanel.add(tabPage, tabItemConfig);
			tabPanel.setActiveWidget(tabPage);
		} else {
			Info.display(pageName, "해당 오브젝트(" + className + ")가 시스템에 등록되어 있지 않습니다.");
		}
	}
}