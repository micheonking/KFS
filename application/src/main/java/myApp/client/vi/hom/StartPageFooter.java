package myApp.client.vi.hom;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;

public class StartPageFooter extends ContentPanel {

	public StartPageFooter() {

		this.setHeaderVisible(false);
		this.setBodyStyle("backgroundColor:rgb(51, 51, 51); color:#aaaaaa"); // http://www.w3schools.com/colors/colors_names.asp 페이지 참조

		// 홈페이지 하단 회사 주소 페이지
		VerticalLayoutContainer vlc = new VerticalLayoutContainer();

		Label textHTML = new HTML(
				"<center><font size='2' color=#aaaaaa><p style='background-color: rgb(51, 51, 51);'>※ 서울시 영등포구 의사당대로 143 금융투자협회빌딩 5층  대표자 : 김형호 사업자번호 : 107-87-45430 Fax : 02-6959-3598"
						+ "<br>Copyright ⓒ Korea Fixed-Income Investment Advisory. All Rights Reserved. e-mail : master@kfia.co.kr | 개인정보취급방침</p></font></center>");

		vlc.add(textHTML, new VerticalLayoutData(1, -1, new Margins(5, 0, 5, 0)));

		this.add(vlc);
	}
}