package myApp.theme.tritium.custom.client.button.purple;

import com.google.gwt.core.client.GWT;

import myApp.theme.tritium.client.base.button.Css3ButtonCellAppearance;

public class PurpleButtonCellAppearance<M> extends Css3ButtonCellAppearance<M> {

	public interface PurpleCss3ButtonStyle extends Css3ButtonStyle {
	}

	public interface PurpleCss3ButtonResources extends Css3ButtonResources {
		@Override
		@Source({ "myApp/theme/tritium/client/base/button/Css3ButtonCell.gss",
				"myApp/theme/tritium/client/base/button/Css3ButtonCellToolBar.gss", "PurpleButton.gss" })
		PurpleCss3ButtonStyle style();
	}

	public PurpleButtonCellAppearance() {
		super(GWT.<Css3ButtonResources>create(PurpleCss3ButtonResources.class));
	}

}