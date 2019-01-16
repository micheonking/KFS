package myApp.theme.tritium.custom.client.button.blue;

import com.google.gwt.core.client.GWT;

import myApp.theme.tritium.client.base.button.Css3ButtonCellAppearance;

public class BlueButtonCellAppearance<M> extends Css3ButtonCellAppearance<M> {

	public interface BlueCss3ButtonStyle extends Css3ButtonStyle {
	}

	public interface BlueCss3ButtonResources extends Css3ButtonResources {
		@Override
		@Source({ "myApp/theme/tritium/client/base/button/Css3ButtonCell.gss",
				"myApp/theme/tritium/client/base/button/Css3ButtonCellToolBar.gss", "BlueButton.gss" })
		BlueCss3ButtonStyle style();
	}

	public BlueButtonCellAppearance() {
		super(GWT.<Css3ButtonResources>create(BlueCss3ButtonResources.class));
	}

}