package myApp.client.vi.home;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Image;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.CellButtonBase;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer.VBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.resource.ResourceIcon;
import myApp.client.vi.home.business.TabBusinessTerritory;
import myApp.client.vi.home.business.TabClientCompany;
import myApp.client.vi.home.business.TabLeadingRecode;
import myApp.client.vi.home.solution.TabSolutionICAM;

public class TabOurSolution extends BorderLayoutContainer {

	private TabSolutionICAM tabSolutionICAM  = new TabSolutionICAM();
	
	ContentPanel contentPanel  = new ContentPanel();

	public TabOurSolution() {

		this.setBorders(false);

		VBoxLayoutContainer centerVBox = new VBoxLayoutContainer();
		centerVBox.setVBoxLayoutAlign(VBoxLayoutAlign.CENTER);

		HBoxLayoutContainer totalHBar = new HBoxLayoutContainer();
		totalHBar.setHBoxLayoutAlign(HBoxLayoutAlign.TOP);

		HBoxLayoutContainer menuHBar = new HBoxLayoutContainer();
		menuHBar.setHBoxLayoutAlign(HBoxLayoutAlign.TOP);

		BoxLayoutData boxLayoutData = new BoxLayoutData();

		VBoxLayoutContainer menuVBox = new VBoxLayoutContainer();
		menuVBox.setVBoxLayoutAlign(VBoxLayoutAlign.CENTER);

		Image lineImage0 = new Image(ResourceIcon.INSTANCE.verticalBar());
		Image lineImage1 = new Image(ResourceIcon.INSTANCE.verticalBar());

		menuVBox.add(MainFramePage.FuncLabelToolItem("솔루션"));
		menuVBox.add(lineImage0, new BoxLayoutData());

		SafeHtml button1Html = SafeHtmlUtils.fromTrustedString("<font color='#606060' style='font-size:16px;'>ㆍ업무시스템　　　　　</font> ");
		CellButtonBase mainButton1 = new CellButtonBase<>();
		mainButton1.setSize("200", "40");
		mainButton1.setHTML(button1Html);
//		mainButton1.setBorders(true);
		mainButton1.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				getSolutionICAM();
			}
		});
		menuVBox.add(mainButton1, new BoxLayoutData(new Margins(1, 3, 1, 3)));
		menuVBox.add(lineImage1, new BoxLayoutData(new Margins(0, 0, 0, 0)));

		menuVBox.setWidth(210);
		menuVBox.setHeight(1000);
		menuHBar.add(menuVBox, boxLayoutData);

		totalHBar.add(menuHBar);
		totalHBar.add(getSolutionICAM());
		centerVBox.add(totalHBar);
		this.setCenterWidget(centerVBox);

	}

	private ContentPanel getSolutionICAM() {
		
		contentPanel.setHeaderVisible(false);
		contentPanel.setBorders(false);
		contentPanel.setWidth(800);
		contentPanel.setHeight(1000);
		contentPanel.setWidget(tabSolutionICAM);

		return contentPanel;
	}

}
