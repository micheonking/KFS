package myApp.client.vi.hom.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer.VBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.RowExpander;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.grid.InterfaceGridOperate;
import myApp.client.resource.ResourceIcon;
import myApp.client.service.GridRetrieveData;
import myApp.client.vi.hom.model.Hom02_BoardModel;
import myApp.client.vi.hom.model.Hom02_BoardModelProperties;
import myApp.client.vi.hom.StartPage;

public class ReportNews extends ContentPanel implements InterfaceGridOperate{
	
	private Grid<Hom02_BoardModel> grid;
	Hom02_BoardModelProperties properties = GWT.create(Hom02_BoardModelProperties.class);
	Hom02_BoardModel bbsModel = new Hom02_BoardModel();
	
	TextField searchText = new TextField();
	TextButton searchButton = new TextButton();

	public ReportNews() {

		this.setHeaderVisible(false);
//		searchButton.setWidth(50);
		searchButton.setText("검색");
		searchButton.setIcon(ResourceIcon.INSTANCE.search16Button());
		searchButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				retrieve();
			}
		});
		searchText.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode() == 13) { // enter key event
                    retrieve();
                }				
			}
        });
		searchText.setWidth(400);

		HBoxLayoutContainer hblc = new HBoxLayoutContainer();
		hblc.add(searchText, new BoxLayoutData(new Margins(0,5,0,0)));
		hblc.add(searchButton, new BoxLayoutData(new Margins(0,0,0,0)));
		
		VBoxLayoutContainer gridBox = new VBoxLayoutContainer();
		gridBox.setVBoxLayoutAlign(VBoxLayoutAlign.LEFT);

		Margins getTextMargins = new Margins(0, 0, 15, 0);
//		Margins totalHBarMargins = new Margins(20, 0, 5, 30);
		Margins lineBar0Margins = new Margins(10, 0, 20, 30);

		Image lineBar0 = new Image(ResourceIcon.INSTANCE.verticalBar());

		gridBox.add(StartPage.getTextContents("보도자료"),new BoxLayoutData(getTextMargins));
		gridBox.add(lineBar0, new BoxLayoutData(new Margins(10,0,20,30)));
		gridBox.add(hblc, new BoxLayoutData(lineBar0Margins));
		gridBox.add(rowExpander(),new BoxLayoutData(new Margins(20,0,0,30)));

		this.add(gridBox);
		retrieve();

	}

	private Widget rowExpander() {

		RowExpander<Hom02_BoardModel> rowExpander = new RowExpander<>(new AbstractCell<Hom02_BoardModel>() {
			@Override
			public void render(Context context, Hom02_BoardModel value, SafeHtmlBuilder sb) {
				sb.appendHtmlConstant("<p style='margin:5px 5px 10px;font-size: 15px; line-height:150%'><b>"+value.getTitleName()+"</b></p>");
				sb.appendHtmlConstant("<p style='margin:5px 5px 10px;font-size: 15px; line-height:150%'><br>"+value.getContents()+"</p>");
				sb.appendHtmlConstant("<p style='margin:5px 5px 10px;font-size: 15px; line-height:150%'><br>"+value.getFileName()+"</p>");
			}
		});

		ColumnConfig<Hom02_BoardModel, String> titleName = new ColumnConfig<Hom02_BoardModel,String>(properties.titleName(),40,"제목");
		ColumnConfig<Hom02_BoardModel, Date> setDate = new ColumnConfig<Hom02_BoardModel, Date>(properties.settleDate(), 100, "작성일");
		ColumnConfig<Hom02_BoardModel, Long> cnt = new ColumnConfig<Hom02_BoardModel, Long>(properties.cnt(), 100, "조회수");
		
		List<ColumnConfig<Hom02_BoardModel, ?>> columns = new ArrayList<ColumnConfig<Hom02_BoardModel, ?>>();
		columns.add(rowExpander);
		columns.add(titleName);
		
		setDate.setCell(new DateCell(DateTimeFormat.getFormat("yyyy-MM-dd")));
		setDate.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		columns.add(setDate);
		
		cnt.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER );
		columns.add(cnt);
		
		ColumnModel<Hom02_BoardModel> cm = new ColumnModel<Hom02_BoardModel>(columns);
		ListStore<Hom02_BoardModel> store = new ListStore<Hom02_BoardModel>(properties.keyId());
		
		grid = new Grid<Hom02_BoardModel>(store,cm);
		grid.getView().setAutoExpandColumn(titleName);
		grid.setBorders(false);
		grid.getView().setStripeRows(true);
		grid.getView().setColumnLines(true);
		
		rowExpander.initPlugin(grid);
		ContentPanel panel = new ContentPanel();
	    panel.add(grid);
	    grid.setHeight(600);
	    panel.setHeaderVisible(false);
	    return panel;
		}

	@Override
	public void retrieve() {
		GridRetrieveData<Hom02_BoardModel> service = new GridRetrieveData<Hom02_BoardModel>(grid.getStore());
		Hom02_BoardModel boardModel = new Hom02_BoardModel();
		service.addParam("boardId",boardModel.getBoardId());
		service.addParam("typeCode", "release");
		service.addParam("setCount", (long)1000);
		service.addParam("titleName",searchText.getText());
		service.retrieve("hom.Hom02_Board.selectByTypeCode");
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertRow() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteRow() {
		// TODO Auto-generated method stub
		
	}
	}
