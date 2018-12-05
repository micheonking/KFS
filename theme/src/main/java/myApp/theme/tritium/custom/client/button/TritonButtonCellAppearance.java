package myApp.theme.tritium.custom.client.button;


import com.google.gwt.core.client.GWT;
import myApp.theme.tritium.client.base.button.Css3ButtonCellAppearance;


public class TritonButtonCellAppearance<M> extends Css3ButtonCellAppearance<M> {

  public interface TritonButtonStyle extends Css3ButtonStyle {
  }

  public TritonButtonCellAppearance() {
    this(GWT.<Css3ButtonCellAppearance.Css3ButtonResources> create(Css3ButtonResources.class));
  }

  public TritonButtonCellAppearance(Css3ButtonResources resources) {
    super(resources);
  }

  @Override
  protected boolean applyMenuArrowClass() {
    return true;
  }
}
