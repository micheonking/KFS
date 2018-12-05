package myApp.client.vi;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.grid.CellSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;

public class GridWithCheckboxCell implements EntryPoint {

	  // ~~~ Custom checkbox cell
	  // ~~~ This lacks the style from teh appearance, so I'd suggest replacing that if needed
	  public class MyCheckboxCell extends AbstractCell<Plant> {
	    public MyCheckboxCell() {
	      super(BrowserEvents.CLICK);
	    }

	    @Override
	    public void render(com.google.gwt.cell.client.Cell.Context context, Plant value, SafeHtmlBuilder sb) {
	      boolean disabled = true; // TODO enable/disable depending on your criteria
	      String disabledSetting = "";
	      if (disabled) {
	        disabledSetting = "disabled='disabled'";
	      }

	      String checked = "";
	      if (value != null && value.getIndoor() == true) {
	        checked = "checked";
	      }
	      SafeHtml safeHtml = SafeHtmlUtils.fromSafeConstant("<input type=\"checkbox\" " + checked + " " + disabledSetting + "/>");
	      sb.append(safeHtml);
	    }

	    @Override
	    public void onBrowserEvent(com.google.gwt.cell.client.Cell.Context context, Element parent, Plant value,
	        NativeEvent event, ValueUpdater<Plant> valueUpdater) {

	      // Plan B, ignore the event
	      boolean disabled = true; // TODO enable/disable depending on your criteria
	      if (disabled) {
	        //return;
	      }

	      // Optional: ignore an inline editor
	      event.preventDefault();
	      event.stopPropagation();

	      // Optional: update the value
	      if (event.getType().contains("click")) {
	        int row = context.getIndex();
	        int column = context.getColumn();

	        // Optional: updating the value change in store
	        // value.setIndoor(!value.getIndoor());
	        // valueUpdater.update(value);
	      }
	    }
	  }

	  @Override
	  public void onModuleLoad() {
	    VerticalLayoutContainer vlc = new VerticalLayoutContainer();
	    vlc.add(createGrid(), new VerticalLayoutData(1, 1));

	    Viewport vp = new Viewport();
	    vp.add(vlc);
	    RootPanel.get().add(vp);
	  }

	  private static final PlantProperties properties = GWT.create(PlantProperties.class);

	  protected Grid<Plant> grid;
	  private FramedPanel panel;
	  private ListStore<Plant> store;
	  private ColumnModel<Plant> columnModel;

	  public Widget createGrid() {
	    if (panel == null) {
	      ColumnConfig<Plant, String> cc1 = new ColumnConfig<Plant, String>(properties.name(), 220, "Name");
	      ColumnConfig<Plant, String> cc2 = new ColumnConfig<Plant, String>(properties.light(), 130, "Light");
	      ColumnConfig<Plant, Date> cc3 = new ColumnConfig<Plant, Date>(properties.available(), 95, "Date");
	      // ~~~ provide the model to the cell
	      ColumnConfig<Plant, Plant> cc4 = new ColumnConfig<Plant, Plant>(new IdentityValueProvider<Plant>("indoor"), 150,
	          "Indoor");
	      ColumnConfig<Plant, Double> cc5 = new ColumnConfig<Plant, Double>(properties.price(), 100, "Price");

	      // ~~~ Custom cell uses model value
	      cc4.setCell(new MyCheckboxCell());

	      // grid cell options
	      cc4.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	      cc4.setCellPadding(false);
	      cc4.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

	      List<ColumnConfig<Plant, ?>> columns = new ArrayList<ColumnConfig<Plant, ?>>();
	      columns.add(cc1);
	      columns.add(cc2);
	      columns.add(cc5);
	      columns.add(cc3);
	      columns.add(cc4);

	      columnModel = new ColumnModel<Plant>(columns);

	      store = new ListStore<Plant>(properties.key());
	      store.setAutoCommit(false);
	      store.addAll(getPlants());

	      grid = new Grid<Plant>(store, columnModel);
	      grid.getView().setAutoExpandColumn(cc1);

	      grid.setSelectionModel(new CellSelectionModel<Plant>());
	      grid.getColumnModel().getColumn(0).setHideable(false);

	      panel = new FramedPanel();
	      panel.setHeading("Grid");
	      panel.setPixelSize(600, 400);
	      panel.setWidget(grid);
	    }

	    return panel;
	  }

	  private static int AUTO_ID = 0;

	  public class Plant {
	    private DateTimeFormat df = DateTimeFormat.getFormat("MM/dd/y");

	    private int id;
	    private String name;
	    private String light;
	    private double price;
	    private Date available;
	    private boolean indoor;
	    private String color;
	    private int difficulty;
	    private double progress;

	    public Plant() {
	      id = AUTO_ID++;

	      difficulty = (int) (Math.random() * 100);
	      progress = Math.random();

	    }

	    public Plant(String name, String light, double price, String available, boolean indoor) {
	      this();
	      setName(name);
	      setLight(light);
	      setPrice(price);
	      setAvailable(df.parse(available));
	      setIndoor(indoor);
	    }

	    public double getProgress() {
	      return progress;
	    }

	    public void setProgress(double progress) {
	      this.progress = progress;
	    }

	    public String getColor() {
	      return color;
	    }

	    public int getDifficulty() {
	      return difficulty;
	    }

	    public void setDifficulty(int difficulty) {
	      this.difficulty = difficulty;
	    }

	    public void setColor(String color) {
	      this.color = color;
	    }

	    public Date getAvailable() {
	      return available;
	    }

	    public int getId() {
	      return id;
	    }

	    public String getLight() {
	      return light;
	    }

	    public String getName() {
	      return name;
	    }

	    public double getPrice() {
	      return price;
	    }

	    public boolean isIndoor() {
	      return indoor;
	    }

	    public void setAvailable(Date available) {
	      this.available = available;
	    }

	    public void setId(int id) {
	      this.id = id;
	    }

	    public void setIndoor(boolean indoor) {
	      this.indoor = indoor;
	    }

	    public boolean getIndoor() {
	      return indoor;
	    }

	    public void setLight(String light) {
	      this.light = light;
	    }

	    public void setName(String name) {
	      this.name = name;
	    }

	    public void setPrice(double price) {
	      this.price = price;
	    }

	    @Override
	    public String toString() {
	      return name != null ? name : super.toString();
	    }
	  }

	  public List<Plant> getPlants() {
	    List<Plant> plants = new ArrayList<Plant>();
	    plants.add(new Plant("Bloodroot", "Mostly Shady", 2.44, "03/15/2006", true));
	    plants.add(new Plant("Columbine", "Shade", 9.37, "03/15/2006", true));
	    plants.add(new Plant("Marsh Marigold", "Mostly Sunny", 6.81, "05/17/2006", false));
	    plants.add(new Plant("Cowslip", "Mostly Shady", 9.90, "03/06/2006", true));
	    plants.add(new Plant("Dutchman's-Breeches", "Mostly Shady", 6.44, "01/20/2006", true));
	    plants.add(new Plant("Ginger, Wild", "Mostly Shady", 9.03, "04/18/2006", true));
	    return plants;
	  }

	  // just to show the converter feature
	  public enum Light {
	    MOSTLYSHADY("Mostly Shady"), MOSTLYSUNNY("Mostly Sunny"), SHADE("Shade"), SUNNY("Sunny"), SUNORSHADE(
	        "Sun or Shade");
	    static Light parseString(String object) throws ParseException {
	      if (Light.MOSTLYSUNNY.toString().equals(object)) {
	        return Light.MOSTLYSUNNY;
	      } else if (Light.SUNORSHADE.toString().equals(object)) {
	        return Light.SUNORSHADE;
	      } else if (Light.MOSTLYSHADY.toString().equals(object)) {
	        return Light.MOSTLYSHADY;
	      } else if (Light.SHADE.toString().equals(object)) {
	        return Light.SHADE;
	      } else if (Light.SUNNY.toString().equals(object)) {
	        return Light.SUNNY;
	      } else {
	        throw new ParseException(object.toString() + " could not be parsed", 0);
	      }
	    }

	    private String text;

	    Light(String text) {
	      this.text = text;
	    }

	    @Override
	    public String toString() {
	      return text;
	    }
	  }

	  interface PlantProperties extends PropertyAccess<Plant> {
	    ValueProvider<Plant, Date> available();

	    @Path("id")
	    ModelKeyProvider<Plant> key();

	    ValueProvider<Plant, String> light();

	    ValueProvider<Plant, String> name();

	    ValueProvider<Plant, Boolean> indoor();

	    ValueProvider<Plant, Double> price();
	  }


}
