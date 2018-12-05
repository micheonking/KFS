package myApp.client.vi;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.TabItemConfig;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.info.Info;

public class MenuOpener {

	private Widget createTab(String className) {

		// 시스템 관리 - 시스템관리자용으로 변경하였음.
		// if("Sys01_Tab_Company.class".equals(className)) {
		// return (Widget) GWT.create(myApp.client.sys.Sys01_Tab_Company.class) ;
		// }

		if ("Sys02_Tab_User.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.sys.Sys02_Page_User.class);
		}

		if ("Sys04_Tab_Role.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.sys.Sys04_Tab_Role.class);
		}

		if ("Sys05_Tab_UserRole.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.sys.Sys05_Tab_EmpRole.class);
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

		if ("Sys12_Tab_Calendar.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.sys.Sys12_Tab_Calendar.class);
		}
		//개선사항 및 오류점검 처리내역
		if ("Sys90_Tab_AfterService.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.sys.Sys90_Tab_AfterService.class);
		}

		if ("Dbm01_Tab_TabComments".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.dbm.Dbm01_Tab_TabComments.class);
		}
		if ("Emp10_Tab_AddrBook.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.emp.Emp10_Tab_AddrBook.class);
		}
		if ("Emp10_Tab_ManagerContact.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.emp.Emp10_Tab_ManagerContact.class);
		}

		// 상품기획
		if ("Pln02_Tab_Plan.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.pln.Pln02_Tab_Plan.class);
		}
		if("Pln02_Tab_EmpPlan.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.pln.Pln02_Tab_PlanEmp.class) ;
		}
		if("Pln03_Tab_Resrch.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.pln.Pln03_Tab_Resrch.class) ;
		}
		
		if("Tab_LicenseCode".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.sys.Sys21_Tab_LicenseCode.class) ;
		}
		if ("Pln03_Tab_Resrch.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.pln.Pln03_Tab_Resrch.class);
		}

		if ("Tab_LicenseCode".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.sys.Sys21_Tab_LicenseCode.class);
		}

		// 조직 및 인사정보
		if ("Org01_Tab_OrgCode".equals(className)) { // 입금항목등록
			return (Widget) GWT.create(myApp.client.vi.org.Org01_Tab_OrgCode.class);
		}

		if ("Emp01_Tab_Person".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.emp.Emp02_Tab_Employee.class);
		}

		if ("Emp02_Tab_OrgEmp".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.emp.Emp02_Tab_OrgEmp.class);
		}

		if ("Emp09_Tab_MgrRlue".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.emp.Emp09_Tab_MgrRlue.class);
		}

		// 문서관리
		if ("Cmp01_Tab_Advertise.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.cmp.Cmp01_Tab_Advertise.class);
		}

		if ("Dcr00_Tab_DocList.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.dcr.Dcr00_Tab_DocList.class);
		}
		if ("Dcr01_Tab_Classification.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.dcr.Dcr01_Tab_ClassTree.class);
		}

		if ("Dcr03_Tab_OrgAuth.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.dcr.Dcr03_Tab_OrgAuth.class);
		}
		if ("Dcr02_Tab_DcrManage.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.apr.Apr01_Tab_DocManager.class);
		}
		if ("Dcr02_Tab_DcrUser.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.apr.Apr01_Tab_DocUser.class);
		}
		if ("Dcr04_Tab_SearchDoc.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.dcr.Dcr04_Tab_SearchDoc.class);
		}
		if ("Dcr10_Tab_MailHistory.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.dcr.Dcr10_Tab_MailHistory.class);
		}
		if ("Dcr10_Tab_MailHistory_Manager.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.dcr.Dcr10_Tab_MailHistory_Manager.class);
		}
		if ("Apr01_Tab_AplyHistory.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.apr.Apr01_Tab_MyRequestList.class);
		}
		if ("Apr01_Tab_AprHistory.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.apr.Apr01_Tab_MyApproveList.class);
		}
		if ("Apr07_Tab_AltAppr.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.apr.Apr07_Tab_AltAppr.class);
		}
		if ("Apr10_Tab_Stamp.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.apr.Apr10_Tab_Stamp.class);
		}
		if ("Apr07_Tab_AltApprMgr.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.apr.Apr07_Tab_AltApprMgr.class);
		}
		if ("Apr01_Tab_FundDocument.class".equals(className)) { // 펀드별 문서검색 
			return (Widget) GWT.create(myApp.client.vi.apr.Apr01_Tab_FundDocument.class);
		}

		if ("Apr00_Tab_StampSeq.class".equals(className)) { // 인감대장 조회 
			return (Widget) GWT.create(myApp.client.vi.apr.Apr00_Tab_StampSeq.class);
		}
		if ("Apr11_Tab_AdvertBook.class".equals(className)) { // 광고심의대장 조회 
			return (Widget) GWT.create(myApp.client.vi.apr.Apr11_Tab_AdvertBook.class);
		}
		if ("Opr01_Tab_Create.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.opr.Opr01_Tab_Create.class);
		}
		if ("Opr01_Tab_Create_Manager.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.opr.Opr01_Tab_Create_Manager.class);
		}
		if ("Tab_DailyAccount".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.rpt.Tab_DailyAccount.class);
		}
		if ("Tab_CashBook".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.rpt.Tab_CashBook.class);
		}
		if ("Tab_TrialBalance".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.rpt.Tab_TrialBalance.class);
		}
		if ("Tab_GeneralLedger".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.rpt.Tab_GeneralLedger.class);
		}
		// 기준정보관련 -펀드,발행기관,판매사,수탁사,사무수탁사
		if ("Fnd01_Tab_FundCode.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.fnd.Fnd01_Tab_FundCode.class);
		}
		if ("Fnd01_Tab_FundInfoLoad.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.fnd.Fnd01_Tab_FundInfoLoad.class);
		}
		if ("Fnd01_Tab_FundPlan.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.fnd.Fnd01_Tab_FundPlan.class);
		}
		if ("Fnd07_Tab_IssuingEntity.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.fnd.Fnd07_Tab_IssuingEntity.class);
		}
		if ("Fnd03_Tab_Consignment.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.fnd.Fnd03_Tab_Consignment.class);
		}
		
		if ("Fnd06_Tab_OfficeWork.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.fnd.Fnd06_Tab_OfficeWork.class);
		}
		if ("Fnd08_Tab_OrgAuth.class".equals(className)) { // 펀드별 권한등록
			return (Widget) GWT.create(myApp.client.vi.fnd.Fnd08_Tab_OrgAuth.class);
		}

		if ("Tab_Memo".equals(className)) {
			// return (Widget) GWT.create(myApp.client.acc.Tab_Memo.class) ;
		}
		//게시판
		
		if ("Bbs01_Tab_BoardManager.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.bbs.Bbs01_Tab_BoardManager.class);
		}
		if ("Bbs01_Tab_Board.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.bbs.Bbs01_Tab_Board.class);
		}

		// RD 테스트
		if ("Dbm01_RD_TabComments.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.dbm.Dbm01_RD_TabComments.class);
		}
		if ("Dbm99_Tab_RDTest.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.vi.dbm.Dbm99_Tab_RDTest.class);
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