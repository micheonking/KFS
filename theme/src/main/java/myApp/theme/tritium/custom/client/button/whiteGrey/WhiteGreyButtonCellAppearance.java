package myApp.theme.tritium.custom.client.button.whiteGrey;

import com.google.gwt.core.client.GWT;

import myApp.theme.tritium.client.base.button.Css3ButtonCellAppearance;

public class WhiteGreyButtonCellAppearance<M> extends Css3ButtonCellAppearance<M> {

	public interface WhiteGrayCss3ButtonStyle extends Css3ButtonStyle {
	}

	public interface WhiteGrayCss3ButtonResources extends Css3ButtonResources {
		@Override
		@Source({ "myApp/theme/tritium/client/base/button/Css3ButtonCell.gss",
				"myApp/theme/tritium/client/base/button/Css3ButtonCellToolBar.gss", "WhiteGreyButton.gss" })
		WhiteGrayCss3ButtonStyle style();
	}

	public WhiteGreyButtonCellAppearance() {
		super(GWT.<Css3ButtonResources>create(WhiteGrayCss3ButtonResources.class));
	}

}