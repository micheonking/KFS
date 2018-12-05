package myApp.client.vi.home;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;

public class MainSouthArea extends BorderLayoutContainer {

	public MainSouthArea() {

		// 홈페이지 하단 회사 주소 페이지
		VerticalLayoutContainer vlc = new VerticalLayoutContainer();

		Label textHTML = new HTML(
				"<center><font size='2'><p style=\"background-color: rgba(209, 210, 212, 0.5);\"><br> ※ 서울시 영등포구 의사당대로 143 금융투자협회빌딩 5층  대표자 : 박동진   사업자번호 : 107-81-79471   Tel: 02-785-8100  Fax: 02-785-8145\r\n<br>"
						+ "Copyright ⓒ Korea Fund Service Co., Ltd All Rights Reserved. | 개인정보취급방침<br><br><br></p></font></center>");

		vlc.add(textHTML, new VerticalLayoutData(1, -1, new Margins(0, 0, 0, 0)));

		this.add(vlc);

	}
}
