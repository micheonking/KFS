package myApp.client.vi.opr;

import java.util.List;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.data.shared.Store.Change;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.grid.GridBuilder;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;
import myApp.client.service.GridDeleteData;
import myApp.client.service.GridInsertRow;
import myApp.client.service.GridRetrieveData;
import myApp.client.service.GridUpdate;
import myApp.client.service.InterfaceCallback;
import myApp.client.service.InterfaceServiceCall;
import myApp.client.service.ServiceCall;
import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.FileUpdownForm;
import myApp.client.utils.SimpleMessage;
import myApp.client.vi.LoginUser;
import myApp.client.vi.opr.model.Opr03_RegModel;
import myApp.client.vi.opr.model.Opr03_RegModelProperties;

public class Opr03_Tabpage_Reg extends VerticalLayoutContainer implements InterfaceGridOperate, InterfaceServiceCall {
	
	Opr03_RegModelProperties properties = GWT.create(Opr03_RegModelProperties.class);
	Opr03_RegModel chkRegModel;

	private Grid<Opr03_RegModel> grid = this.buildGrid();
	private Long createId;
	private Long loginEmpId = LoginUser.getUserId();
	private String actionName;
	
	private FileUpdownForm fileUpdownForm = new FileUpdownForm();
	
	public Opr03_Tabpage_Reg() {

		LabelToolItem title = new LabelToolItem("▶ 보고서 작성 순서");
		title.setWidth(125);
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.getSearchBar().add(title);
		searchBarBuilder.addUpdateButton();
		
		TextButton okeyButton = new TextButton("작성완료 확정");
		okeyButton.setWidth(100);
		okeyButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				okey();
			}

		});
		searchBarBuilder.getSearchBar().add(okeyButton);

		TextButton cancelButton = new TextButton("작성완료 취소");
		cancelButton.setWidth(100);
		cancelButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				cancel();
			}

		});
		searchBarBuilder.getSearchBar().add(cancelButton);

		this.add(searchBarBuilder.getSearchBar(), new VerticalLayoutData(-1, 50, new Margins(0,0,0,0)));
		this.add(setGrid(), new VerticalLayoutData(1, 1, new Margins(0,0,0,0)));
	}

	private VerticalLayoutContainer setGrid() {

		VerticalLayoutContainer vlc = new VerticalLayoutContainer();

		vlc.add(grid, new VerticalLayoutData(1,1, new Margins(0,0,0,0)));
		vlc.add(fileUpdownForm.getForm(), new VerticalLayoutData(1,100));

		fileUpdownForm.getForm().setVisible(false);
		fileUpdownForm.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {

				if (("".equals(fileUpdownForm.getFileName())) || (fileUpdownForm.getFileName() == null)) {
					return;
				}

				Opr03_RegModel regModel = grid.getSelectionModel().getSelectedItem();
				grid.getStore().getRecord(regModel).addChange(properties.docNm(), fileUpdownForm.getFileName());
				grid.getStore().getRecord(regModel).addChange(properties.regDate(), LoginUser.getToday());

				String actionUrl = "FileUpload?fileType=file&parentId=" + regModel.getRegId();
				fileUpdownForm.submit(actionUrl, new InterfaceCallback() {
					@Override
					public void execute() {
						update();
					}
				});
			}
		});
		
		return vlc;
	}

	private Grid<Opr03_RegModel> buildGrid() {

		GridBuilder<Opr03_RegModel> gridBuilder = new GridBuilder<Opr03_RegModel>(properties.keyId());
		
		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText	  (properties.seq()		 	,  50, "순서"		);
		gridBuilder.addText	  (properties.korName()	 	,  70, "담당자"	);
//		gridBuilder.addText	  (properties.positionName(),  50, "직위"		);
		gridBuilder.addText	  (properties.orgName()	 	, 130, "부서"		);
		gridBuilder.addText	  (properties.docNm()		, 250, "운용보고서 첨부파일");
		
		ActionCell<String> fileUpButton = new ActionCell<String>("up", new ActionCell.Delegate<String>() {
			@Override
			public void execute(String object) {
				upLoad();
			}
		});
		gridBuilder.addCell	  (properties.actionCellUp()	,  60, "파일등록", fileUpButton);

		ActionCell<String> fileDownButton = new ActionCell<String>("down", new ActionCell.Delegate<String>() {
			@Override
			public void execute(String object) {
				downLoad();
			}
		});
		gridBuilder.addCell	  (properties.actionCellDown()	,  70, "파일받기", fileDownButton);
		
		gridBuilder.addDate	  (properties.regDate()	 	,  80, "등록일", new DateField());
		gridBuilder.addText   (properties.closeYnNm() 	,  70, "완료여부");
		gridBuilder.addText	  (properties.note()		, 300, "비고", new TextField());
		
		return gridBuilder.getGrid();
	}

	private void okey() {
		
		for (int i=0; i < grid.getStore().size(); i++) {

			chkRegModel = grid.getStore().get(i);
			Long regEmpId = chkRegModel.getRegEmpId();

			if (loginEmpId.equals(regEmpId)) {
				//완료유무체크
				if (chkRegModel.getCloseYnFlag()) {
					new SimpleMessage("확인", "이미 작성완료 되었습니다.");
					return;
				}
				//보고서 존재유무체크
				if (chkRegModel.getDocNm() == null) {
					new SimpleMessage("확인", "보고서 등록 완료후 확정처리가 가능합니다.");
					return;
				} else {
					grid.getStore().getRecord(chkRegModel).addChange(properties.closeYn(), "true");
					update();
					break;
				}
			}
		}
	}
	
	private void cancel() {
		
		for (int i=0; i < grid.getStore().size(); i++) {

			chkRegModel   = grid.getStore().get(i);
			Long regEmpId =  chkRegModel.getRegEmpId();

			if (loginEmpId.equals(regEmpId)) {
				//완료유무체크
				if (!chkRegModel.getCloseYnFlag()) {
					new SimpleMessage("확인", "아직 완료확정 전입니다.");
					return;
				}
				
				//다음작성자 보고서 등록여부 체크
				actionName = "selectOnePreCancelCheck";
				String seq = chkRegModel.getSeq();

				ServiceRequest request = new ServiceRequest("opr.Opr03_Reg.selectOnePreCancelCheck");
				request.putLongParam  ("createId", createId	);
				request.putStringParam("seq"     , seq);

				ServiceCall service = new ServiceCall();
				service.execute(request, this);
				break;
			}
		}
	}
	
	private void upLoad() {

		Opr03_RegModel regModel = grid.getSelectionModel().getSelectedItem();

		Long 	regEmpId = regModel.getRegEmpId();
		String	closeYn	 = regModel.getCloseYn();
		String	uploadYn = regModel.getUploadYn();
		if (uploadYn == null) {
			uploadYn = "true";
		}

		if (loginEmpId.equals(regEmpId)) {
			if ((closeYn == null)||(closeYn.equals("false"))) {
				if (uploadYn.equals("true")) {
					fileUpdownForm.click();
				} else {
					new SimpleMessage("확인", "전 작성자 작성완료 후 보고서 업로드가 가능합니다.");
					return;	
				}
			} else {
				new SimpleMessage("확인", "등록완료된 보고서는 재업로드 불가합니다.");
				return;	
			}
		} else {
			new SimpleMessage("확인", "담당자가 아닙니다.");
			return;	
		}
	}

	private void downLoad() {

		Opr03_RegModel regModel = grid.getSelectionModel().getSelectedItem();
		String	closeYn = regModel.getCloseYn();

		if ((closeYn == null)||(closeYn.equals("false"))) {
			new SimpleMessage("확인", "작성완료된 보고서만 확인 가능합니다.");
			return;	
		} else {
			String actionUrl = "FileDownload?fileType=file&fileId=" + regModel.getFileId();
			fileUpdownForm.submit(actionUrl, new InterfaceCallback() {
				@Override
				public void execute() {
				}
			});
		}
	}

	public void retrieve(long createId) {

		this.createId = createId;
		
		GridRetrieveData<Opr03_RegModel> service = new GridRetrieveData<Opr03_RegModel>(grid.getStore());
		service.addParam("companyId", LoginUser.getCompanyId());
		service.addParam("createId" , this.createId);
		service.addParam("empId"	, LoginUser.getUserId());
		service.retrieve("opr.Opr03_Reg.selectByCreateId");
	}

	@Override
	public void retrieve() {
		// TODO Auto-generated method stub
	}

	@Override
	public void update() {
		
		if (chkPreUpdate() < 0) {
			return;
		}
		
		GridUpdate<Opr03_RegModel> service = new GridUpdate<Opr03_RegModel>(); 
		service.update(grid.getStore(), "opr.Opr03_Reg.update"); 
		
	}

	private int chkPreUpdate() {

		for(Store<Opr03_RegModel>.Record modified : grid.getStore().getModifiedRecords()) {

			Opr03_RegModel chkModel = modified.getModel(); 

			for (Change<Opr03_RegModel, ?> changes : modified.getChanges()) {
				// 컬럼별로 변경된 자료를 적용한다. 
				changes.modify(chkModel);
			}

			if ((chkModel.getSeq() == null) || (chkModel.getSeq().equals(""))) {
				new SimpleMessage("입력확인","순서를 입력하여 주십시오.");
				return -1;
			}
			if (chkModel.getRegEmpId() == null) {
				new SimpleMessage("입력확인","담당자를 입력하여 주십시오.");
				return -1;
			}
		}
		return 1;
	}

	@Override
	public void insertRow() {
		
		if (this.createId == null) {
			new SimpleMessage("확인","운용보고서 선택 후 입력가능합니다.");
			return;
		}
		
		Opr03_RegModel insertModel = new Opr03_RegModel();
		insertModel.setCreateId(this.createId);
		
		GridInsertRow<Opr03_RegModel> service = new GridInsertRow<Opr03_RegModel>(); 
		service.insertRow(grid, insertModel);
	}

	@Override
	public void deleteRow() {

		List<Opr03_RegModel> delList = grid.getSelectionModel().getSelectedItems();

		GridDeleteData<Opr03_RegModel> service = new GridDeleteData<Opr03_RegModel>();
		service.delete(grid.getStore(), delList, "opr.Opr03_Reg.delete");
	}

	@Override
	public void getServiceResult(ServiceResult result) {
		if (actionName.equals("selectOnePreCancelCheck")) {
			if (result.getStatus() == 1) {
				grid.getStore().getRecord(chkRegModel).addChange(properties.closeYn(), "false");
				update();
			} else {
				new SimpleMessage("확인", "상신진행 혹은 다음 작성자의 보고서가 등록되어 취소가 불가합니다.");
			}
		}
	}

}
