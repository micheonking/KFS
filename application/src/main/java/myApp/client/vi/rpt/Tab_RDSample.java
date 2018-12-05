package myApp.client.vi.rpt;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.TextField;

import myApp.client.grid.ComboBoxField;
import myApp.client.grid.InterfaceGridOperate;
import myApp.client.grid.SearchBarBuilder;

public class Tab_RDSample extends VerticalLayoutContainer implements InterfaceGridOperate {
	
	private TextField companyName = new TextField(); 
	private ComboBoxField companyTypeName = new ComboBoxField("CompanyTypeCode");
	
	public interface RDTemplate extends XTemplates {
	    @XTemplate("<iframe id='reportDesinger' border=0 src='{pageName}' width='100%' height='99%'/> ")
	    SafeHtml getTemplate(String pageName);
	}

	
	public Tab_RDSample() {
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addComboBox(companyTypeName, "기관구분", 200, 60); 

		companyTypeName.add("전체");
		companyTypeName.setText("전체");
		
		searchBarBuilder.addTextField(companyName, "기관명", 150, 50, true); 

		searchBarBuilder.addRetrieveButton(); 
//		searchBarBuilder.addUpdateButton();
//		searchBarBuilder.addInsertButton();
//		searchBarBuilder.addDeleteButton();

		this.add(searchBarBuilder.getSearchBar(), new VerticalLayoutData(1, 40));
		RDTemplate rdTemplate = GWT.create(RDTemplate.class);
		String pageName = "http://172.20.200.252:8283/ReportingServer/html5/sample/sample.html";   
		
		HtmlLayoutContainer htmlLayoutContainer = new HtmlLayoutContainer(rdTemplate.getTemplate(pageName));
		
		htmlLayoutContainer.setBorders(true);
		
		ContentPanel panel = new ContentPanel();
		panel.setBorders(false);
		panel.add(htmlLayoutContainer, new MarginData(0));
		panel.setHeight(1);
		
		this.add(htmlLayoutContainer, new VerticalLayoutData(1, 1));
		
	}
	
	

	@Override
	public void retrieve() {
		
		String companyTypeCode = companyTypeName.getCode();
		if(companyTypeCode == null){
			companyTypeCode = "%"; 
		}
//		String name = companyName.getText();
		
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