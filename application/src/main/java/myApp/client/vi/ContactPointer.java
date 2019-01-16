package myApp.client.vi;

import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.Viewport;

import myApp.client.vi.hom.StartPage;

public class ContactPointer {

	private CenterLayoutContainer container = new CenterLayoutContainer();
	Viewport viewport = new Viewport();

	public void open() {

		this.viewport.remove(container);
		viewport.add(new StartPage(), new MarginData(0, 0, 0, 0));
		RootPanel.get().add(viewport);

	}
}