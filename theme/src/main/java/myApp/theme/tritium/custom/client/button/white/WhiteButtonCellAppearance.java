package myApp.theme.tritium.custom.client.button.white;

import com.google.gwt.core.client.GWT;

import myApp.theme.tritium.client.base.button.Css3ButtonCellAppearance;

public class WhiteButtonCellAppearance<M> extends Css3ButtonCellAppearance<M> {

	public interface WhiteCss3ButtonStyle extends Css3ButtonStyle {
	}

	public interface WhiteCss3ButtonResources extends Css3ButtonResources {
		@Override
		@Source({ "myApp/theme/tritium/client/base/button/Css3ButtonCell.gss",
				"myApp/theme/tritium/client/base/button/Css3ButtonCellToolBar.gss", "WhiteButton.gss" })
		WhiteCss3ButtonStyle style();
	}

	public WhiteButtonCellAppearance() {
		super(GWT.<Css3ButtonResources>create(WhiteCss3ButtonResources.class));
	}

}