package myApp.client.vi.hom.beginning;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.cell.core.client.ButtonCell.IconAlign;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.CellButtonBase;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer.VBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

import myApp.client.resource.ResourceIcon;
import myApp.client.vi.hom.StartPage;

public class CoverPageBodyLeft extends ContentPanel {

	public CoverPageBodyLeft() {

		HorizontalLayoutData rowLayout = new HorizontalLayoutData(300, 125, new Margins(0, 0, 0, 0));
		
		SafeHtml label1Html = SafeHtmlUtils.fromTrustedString(	"<center>"
				+	"<div style='background-color: #1d7bbb; line-height:130%; '>"
				+	"<span style='font-size:0.1em;'><br></span>"
				+	"<div><img src='img/icon_left.png' width='32' height='40'></div>"
				+	"<span style='font-size:0.1em;'><br></span>"
				+	"<span style='font-weight:normal; font-size:1.2em;'>"
				+	"<font color='#eeeeee'>총 운용규모<br>약 3조 1,426억</font></span>"
				+	"<span style='font-size:0.1em;'><br><br></span>"
				+	"</div>"
				);
		LabelToolItem labelToolItem1 = new LabelToolItem(label1Html);
		labelToolItem1.setWidth(300);
		labelToolItem1.setHeight(130);
		labelToolItem1.setBorders(true);

		this.add(labelToolItem1);
	}
}
