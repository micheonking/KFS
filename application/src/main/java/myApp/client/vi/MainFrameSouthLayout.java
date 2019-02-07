package myApp.client.vi;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;

public class MainFrameSouthLayout extends ContentPanel {

	public MainFrameSouthLayout() {

		this.setHeaderVisible(false);
		this.setBodyStyle("backgroundColor:#2b579a;"); // http://www.w3schools.com/colors/colors_names.asp 페이지 참조 005fa7

		// 홈페이지 하단 회사 주소 페이지
		VerticalLayoutContainer vlc = new VerticalLayoutContainer();

		Label textHTML = new HTML(
				"<center><font size='1' color=#fdfdfd><p style='background-color:#2b579a;'>Copyright ⓒ Korea Fund Service. All Rights Reserved. E-Mail : admin@k-fs.co.kr</p></font></center>");

		vlc.add(textHTML, new VerticalLayoutData(1, -1, new Margins(5, 0, 5, 0)));

		this.add(vlc);
	}
}