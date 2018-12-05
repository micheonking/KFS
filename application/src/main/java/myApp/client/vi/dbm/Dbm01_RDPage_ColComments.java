package myApp.client.vi.dbm;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.TextField;
import myApp.client.grid.InterfaceGridOperate;

public class Dbm01_RDPage_ColComments extends BorderLayoutContainer implements InterfaceGridOperate {

	TextField tableNameField = new TextField();
	VerticalLayoutContainer rdLayoutContainer = new VerticalLayoutContainer();

	public interface RDTemplate extends XTemplates {
	    @XTemplate("<iframe id='reportDesinger' border=0 src='{pageName}' width='100%' height='99%'/> ")
	    SafeHtml getTemplate(String pageName);
	}

	public Dbm01_RDPage_ColComments() {
		this.setBorders(true); 
		RDTemplate rdTemplate = GWT.create(RDTemplate.class);
		String pageName = "";

		HtmlLayoutContainer htmlLayoutContainer = new HtmlLayoutContainer(rdTemplate.getTemplate(pageName));

		rdLayoutContainer.clear();
		rdLayoutContainer.add(htmlLayoutContainer, new VerticalLayoutData(1, 1));
		this.setCenterWidget(rdLayoutContainer);
		
	}

	public void retrieve(String tableName) {
		TextField text = new TextField();
		text.setValue(tableName);
		tableName = text.getValue().toString().toUpperCase();

		RDTemplate rdTemplate = GWT.create(RDTemplate.class);
		String pageName = "http://172.20.200.252:8283/ReportingServer/html5/RDhtml/rd_Table_Define.html?table_in="+tableName;

		HtmlLayoutContainer htmlLayoutContainer = new HtmlLayoutContainer(rdTemplate.getTemplate(pageName));

		rdLayoutContainer.clear();
		rdLayoutContainer.add(htmlLayoutContainer, new VerticalLayoutData(1, 1));
		this.setCenterWidget(rdLayoutContainer);
	}

	@Override
	public void retrieve() {
	}

	@Override
	public void update() {
	}

	@Override
	public void insertRow() {
	}

	@Override
	public void deleteRow() {
	}

}
