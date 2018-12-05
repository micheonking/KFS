package myApp.client.vi.home.company;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer.VBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.resource.ResourceIcon;
import myApp.client.vi.home.MainFramePage;

public class TabAboutUsCompany extends ContentPanel {
	
	public TabAboutUsCompany() {

		this.setHeaderVisible(false);

		VBoxLayoutContainer gridVBox = new VBoxLayoutContainer();
		gridVBox.setVBoxLayoutAlign(VBoxLayoutAlign.LEFT);

		Image lineBar0 = new Image(ResourceIcon.INSTANCE.lineBar());

		VBoxLayoutContainer rightVBox = new VBoxLayoutContainer();
		rightVBox.setVBoxLayoutAlign(VBoxLayoutAlign.LEFT);

		HBoxLayoutContainer totalHBar = new HBoxLayoutContainer();
		totalHBar.setHBoxLayoutAlign(HBoxLayoutAlign.TOP);
		
		Label content = new HTML("<div id=\"contents\">												"
				+	"		<table class=\"tbl1\">													"
				+	"			<colgroup>															"
				+	"				<col style=\"width:26%;\">										"
				+	"				<col style=\"\">												"
				+	"			</colgroup>															"
				+	"			<tbody><tr>															"
				+	"				<th>회사명</th>													"
				+	"				<td><strong>한국펀드서비스(주)</strong></td>					"
				+	"			</tr>																"
				+	"			<tr class=\"bg\">													"
				+	"				<th>설립일</th>													"
				+	"				<td>2000년 03월 20일</td>										"
				+	"			</tr>																"
				+	"			<tr>																"
				+	"				<th>납입자본금</th>												"
				+	"				<td>30억원</td>													"
				+	"			</tr>																"
				+	"			<tr>																"
				+	"				<th>주요업무</th>												"
				+	"				<td>일반사무관리업무(금융위등록 제2018-112호)</td>				"
				+	"			</tr>																"
				+	"			<tr class=\"bg\">													"
				+	"				<th>대표이사</th>												"
				+	"				<td>박동진</td>													"
				+	"			</tr>																"
				+	"			<tr>																"
				+	"				<th>주주구성</th>												"
				+	"				<td>금융전문가(50%), IT전문가(40%), 일반사무전문가(10%)</td>	"
				+	"			</tr>																"
				+	"			<tr class=\"bg\">													"
				+	"				<th>주소</th>													"
				+	"				<td>서울시 영등포구 의사당대로 143 금융투자협회빌딩 5층</td>	"
				+	"			</tr>																"
				+	"			</tbody>															"
				+	"		</table>																"
				+	"</div>"
					);
		content.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		totalHBar.add(content, new BoxLayoutData(new Margins(20, 0, 5, 45)));

		gridVBox.add(MainFramePage.FuncTextContents("회사개요"));
		gridVBox.add(lineBar0,new BoxLayoutData(new Margins(0, 0, 0, 40)));
		gridVBox.add(totalHBar);

		this.add(gridVBox);

	}
}
