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

public class TabAboutUsHistory extends ContentPanel {
	
	public TabAboutUsHistory() {

		this.setHeaderVisible(false);

		VBoxLayoutContainer gridVBox = new VBoxLayoutContainer();
		gridVBox.setVBoxLayoutAlign(VBoxLayoutAlign.LEFT);

		Image lineBar0 = new Image(ResourceIcon.INSTANCE.lineBar());

		VBoxLayoutContainer rightVBox = new VBoxLayoutContainer();
		rightVBox.setVBoxLayoutAlign(VBoxLayoutAlign.LEFT);

		HBoxLayoutContainer totalHBar = new HBoxLayoutContainer();
		totalHBar.setHBoxLayoutAlign(HBoxLayoutAlign.TOP);
		
		Label content = new HTML("<div id=\"contents\" style=\"height: auto; width: 100%; border:1px solid gold;\">																		" + 
				"		<h3>회사연혁</h3>																					" + 
				"		<div>																								" + 
				"			<table class=\"tbl_history\">																	" + 
				"                <colgroup>																					" + 
				"                    <col width=\"170\">																	" + 
				"                    <col width=\"60\">																		" + 
				"                    <col width=\"*\">																		" + 
				"                </colgroup><colgroup>																		" + 
				"																											" + 
				"				</colgroup><tbody>																			" + 
				"							<tr>																			" + 
				"		                            <td class=\"tbl_year\" rowspan=\"3\"><span>2000</span></td>				" + 
				"		                            <td class=\"tbl_month\"><span>03</span></td>							" + 
				"		                            <td><span>(주)펀드소프트설립</span></td>								" + 
				"							</tr>																			" + 
				"	                        <tr>																			" + 
				"	                            <td class=\"tbl_month\"><span>07</span></td>								" + 
				"	                            <td><span>FAS (Fund Accounting System)개발완료								" + 
				"<br>PMS (Portfolio Management System)개발완료 </span></td>													" + 
				"	                        </tr>																			" + 
				"	                        <tr>																			" + 
				"	                            <td class=\"tbl_month\"><span>10</span></td>								" + 
				"	                            <td><span>프로그램 등록(FAS, PMS) - 한국소프트웨어진흥원 </span></td>		" + 
				"	                        </tr>																			" + 
				"							<tr>																			" + 
				"		                            <td class=\"tbl_year\" rowspan=\"3\"><span>2002</span></td>				" + 
				"		                            <td class=\"tbl_month\"><span>04</span></td>							" + 
				"		                            <td><span>기술연구소 설립 </span></td>									" + 
				"							</tr>																			" + 
				"	                        <tr>																			" + 
				"	                            <td class=\"tbl_month\"><span>05</span></td>								" + 
				"	                            <td><span>자산운용시스템 업그레이드											" + 
				"<br>FAS → FAMS (Fund Asset Management System)</span></td>													" + 
				"	                        </tr>																			" + 
				"	                        <tr>																			" + 
				"	                            <td class=\"tbl_month\"><span>08</span></td>								" + 
				"	                            <td><span>프로그램 등록(FAMS) – 한국소프트웨어진흥원 </span></td>			" + 
				"	                        </tr>																			" + 
				"							<tr>																			" + 
				"		                            <td class=\"tbl_year\" rowspan=\"1\"><span>2009</span></td>				" + 
				"		                            <td class=\"tbl_month\"><span>08</span></td>							" + 
				"		                            <td><span>경영혁신 중소기업(Main Biz.) 인증</span></td>					" + 
				"							</tr>																			" + 
				"							<tr>																			" + 
				"		                            <td class=\"tbl_year\" rowspan=\"3\"><span>2017</span></td>				" + 
				"		                            <td class=\"tbl_month\"><span>03</span></td>							" + 
				"		                            <td><span>ICAM 솔루션 구축 완료 										" + 
				"<br>정보보호시스템 누리아이티 파트너사 계약																" + 
				"<br></span></td>																							" + 
				"							</tr>																			" + 
				"	                        <tr>																			" + 
				"	                            <td class=\"tbl_month\"><span>07</span></td>								" + 
				"	                            <td><span>투자일임 / 자문업무 금융서비스 시행</span></td>					" + 
				"	                        </tr>																			" + 
				"	                        <tr>																			" + 
				"	                            <td class=\"tbl_month\"><span>09</span></td>								" + 
				"	                            <td><span>자본금 28억 원 증액 (총 30억 원) </span></td>						" + 
				"	                        </tr>																			" + 
				"							<tr>																			" + 
				"		                            <td class=\"tbl_year\" rowspan=\"1\"><span>2018</span></td>				" + 
				"		                            <td class=\"tbl_month\"><span>04</span></td>							" + 
				"		                            <td><span>티맥스 티베로DB 파트너사 계약 								" + 
				"<br>일반사무관리업무 등록(제2018-112호) 																	" + 
				"<br>전문사모자산운용 업무 개시 / 상호변경																	" + 
				"<br></span></td>																							" + 
				"							</tr>																			" + 
				"                        <!--  tr>																			" + 
				"                            <td class=\"tbl_year\" rowspan=\"3\"><span>2017</span></td>					" + 
				"                            <td class=\"tbl_month\"><span>3</span></td>									" + 
				"                            <td><span>ICAM 솔루션 구축 완료 <br>											" + 
				"                              	 정보보호시스템 누리아이티 파트너사 계약</span></td>						" + 
				"                        </tr>																				" + 
				"                        <tr>																				" + 
				"                            <td class=\"tbl_month\"><span>7</span></td>									" + 
				"                            <td><span>투자일임 / 자문업무 금융서비스 시행</span></td>						" + 
				"                        </tr-->																			" + 
				"				</tbody>																					" + 
				"																											" + 
				"			</table>																						" + 
				"																								</div>		" + 
				"	</div>																									"	);
		content.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		totalHBar.add(content, new BoxLayoutData(new Margins(20, 0, 5, 45)));

		gridVBox.add(MainFramePage.FuncTextContents("회사연혁"));
//		gridVBox.add(lableToolItem1,new BoxLayoutData(new Margins(100, 600, 0, 40)));
		gridVBox.add(lineBar0,new BoxLayoutData(new Margins(0, 0, 0, 40)));
		gridVBox.add(totalHBar);

		this.add(gridVBox);

	}
}
