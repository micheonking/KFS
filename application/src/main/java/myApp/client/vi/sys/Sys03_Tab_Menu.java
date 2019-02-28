package myApp.client.vi.sys;

import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;

public class Sys03_Tab_Menu extends BorderLayoutContainer {

	private Sys03_Tree_Menu allMenuTree = new Sys03_Tree_Menu();
	private Sys04_Tree_CmpMenu cmpMenuTree = new Sys04_Tree_CmpMenu();
	
	public Sys03_Tab_Menu(){
		BorderLayoutData westLayoutData = new BorderLayoutData(0.5);
		westLayoutData.setSplit(true);
		westLayoutData.setMargins(new Margins(0,4,0,0));
		westLayoutData.setMaxSize(1000);
		this.setWestWidget(allMenuTree, westLayoutData);
		this.setCenterWidget(cmpMenuTree);
	}
}
