package myApp.client.vi.home.company;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer.VBoxLayoutAlign;

import myApp.client.resource.ResourceIcon;
import myApp.client.vi.home.MainFramePage;

public class TabAboutUsCeo extends ContentPanel {
	
	public TabAboutUsCeo() {

		this.setHeaderVisible(false);

		VBoxLayoutContainer gridVBox = new VBoxLayoutContainer();
		gridVBox.setVBoxLayoutAlign(VBoxLayoutAlign.LEFT);

		Image lineBar0 = new Image(ResourceIcon.INSTANCE.lineBar());

		HBoxLayoutContainer gridHBox = new HBoxLayoutContainer();
		gridHBox.setHBoxLayoutAlign(HBoxLayoutAlign.TOP);
		
		HTML ceoimage = new HTML("<div><img src='img/ceoWBHT.jpg' width='190' height='266'></div>"); //인물사진

		VBoxLayoutContainer leftVBox = new VBoxLayoutContainer();
		leftVBox.setVBoxLayoutAlign(VBoxLayoutAlign.LEFT);

		VBoxLayoutContainer rightVBox = new VBoxLayoutContainer();
		rightVBox.setVBoxLayoutAlign(VBoxLayoutAlign.LEFT);

		HorizontalLayoutContainer totalHBar = new HorizontalLayoutContainer();
		HorizontalLayoutData hld = new HorizontalLayoutData();
		Margins margins = new Margins();
		margins.setTop(20);
		margins.setLeft(45);
		margins.setRight(0);
		margins.setBottom(5);
		hld.setMargins(margins);
		hld.setWidth(600);
		totalHBar.setWidth(800);

		Label content = new HTML("<span style=\"font-weight:bold;font-size:1.2em;\">안녕하세요!</span><br>"
					+	"<br><span style=\"font-size:0.9em;\">"
					+	"한국펀드서비스(주)는 금융위원회에 일반사무관리회사 등록을 마치고 최고의  <br>"
					+	" 펀드서비스회사가 되도록 최선을 다하고 있습니다. <br>"
					+	"<br>"
					+	"2000년에 설립되어 금융서비스전문회사로서 일반사무관리 업무솔루션(ICAM), <br>"
					+	"경영지원시스템, 자산운용 정보통합관리시스템, 기금관리시스템, 신탁 및 <br>"
					+	"수탁시스템 등 금융업무 서비스, 컨설팅 및 개발을 위해 고객과 함께해 왔습니다. <br>"
					+	"<br>"
					+	"특히, 다양화 및 전문화되고 있는 투자일임자문사와 자산운용회사를 위해  <br>"
					+	"고객자산평가의 신뢰성 확보와 자산운용성과 지원을 위한 맞춤형 <br>"
					+	"일반사무관리업무시스템(ICAM)을 구축하여 운영 중입니다. <br>"
					+	"<br>"
					+	"한국펀드서비스(주)는 고객과 함께 동반성장하는 파트너가 되어 고객의 선택이  <br>"
					+	"자랑이 되는 회사가 되겠습니다! <br>"
					+	"<br>"
					+	"그 동안 보여주신 관심과 배례에 감사드리며, 앞으로도 많은 성원을 부탁드립니다. <br>"
					+	" <br>"
					+	" <br></span>"
					+	"<span style=\"font-weight:bold;font-size:1.2em;\">한국펀드서비스㈜ 대표이사 박동진</span>"
					);
		
		content.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		Label education = new HTML(
				 		"<br>"
				 	+	"<img src='img/smallTitle.png'>"
				 	+	"<span style=\"font-weight:bold;\"> 학력 및 자격사항</span><br>"
				 	+	"<span style=\"font-size:0.8em;\">ㆍCISA, ISO9001 책임심사원</span><br>"
				 	+	"<span style=\"font-size:0.8em;\">ㆍ전문투자형 운용전문인력</span><br>"
				 	+	"<span style=\"font-size:0.8em;\">ㆍ홍익대학교 이학석사</span><br>"
				 	);
		
		Label career = new HTML(
						"<br>"
					+	"<img src='img/smallTitle.png'>"
					+	"<span style=\"font-weight:bold;\"> 주요이력 </span><br>"
					+	"<span style=\"font-size:0.8em;\">現) 한국펀드서비스(주) 대표이사</span><br>"
					+	"<span style=\"font-size:0.8em;\">前) 한국채권투자자문 전략본부장</span><br>"
					+	"<span style=\"font-size:0.8em;\">前) 신한BNP자산운용 운용지원본부장</span><br>"
					+	"<span style=\"font-size:0.8em;\">前) 펀드넷 및 정보화 허브 자문위원</span><br>"
					+	"<span style=\"font-size:0.8em;\">前) 대한투자신탁(주)</span><br>"
					);
		
		gridVBox.add(MainFramePage.FuncTextContents("CEO 인사말"));
		gridVBox.add(lineBar0,new BoxLayoutData(new Margins(0, 0, 0, 40)));

		rightVBox.add(ceoimage, new BoxLayoutData(new Margins(5, 0, 0, 0)));
		rightVBox.add(education,new BoxLayoutData(new Margins(0, 0, 0, 0)));
		rightVBox.add(career,   new BoxLayoutData(new Margins(0, 0, 0, 0)));
		
		totalHBar.add(content,hld);
		totalHBar.add(rightVBox);
//		totalHBar.add(content, new BoxLayoutData(new Margins(20, 0, 5, 45)));
//		totalHBar.add(rightVBox), new BoxLayoutData(new Margins(20, 5, 5, 20)));

		gridVBox.add(totalHBar);

		this.add(gridVBox);

	}
}
