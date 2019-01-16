package myApp.client.resource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/** 사용예제 : ImageResource.INSTANCE.addButton();  
 * 	이미지를 처리하는 새로운 방법이다. 
 *  이미지를 자바 패키지 안에 속하게 하고 이 안에서 이미지 명으로 이미지를 설정한다. 
 *  외부에서는 logo()라는 함수명으로 이미지를 호출할 수 있으며
 *  이미지를 모아서 처리할 수 있다. - 나름 좋다. 
 *
 */
public interface ResourceIcon extends ClientBundle {

	public static final ResourceIcon INSTANCE = GWT.create(ResourceIcon.class);
	  
	  @Source("files/KFIALogo.png")
	  ImageResource getLogo();

	  @Source("files/KFSLogo.png")
	  ImageResource getKFSLogo();

	  @Source("files/WBHTLogo.png")
	  ImageResource getWBHTLogo();

	  @Source("files/logout.png")
	  ImageResource getLogout();

	  @Source("files/gearIcon.png")
	  ImageResource gearIcon();

	  @Source("files/add.png")
	  ImageResource addButton();

	  @Source("files/db.png")
	  ImageResource dBButton();

	  @Source("files/next.png")
	  ImageResource itemButton();

	  @Source("files/text.png")
	  ImageResource textButton();

	  @Source("files/update.png")
	  ImageResource updateIcon();
	  
	  @Source("files/search_button_green.png")
	  ImageResource searchButton();

	  @Source("files/1462692039_stock_person.png")
	  ImageResource searchPerson();

	  @Source("files/1462692039_stock_person.png")
	  ImageResource searchImage();

	  @Source("files/closeFolder.png")
	  ImageResource closeFolder();
	  
	  @Source("files/closeButton.png")
	  ImageResource closeButton();
	  
	  @Source("files/icon_left.png")
	  ImageResource iconLeft();
	  
	  @Source("files/icon_middle.png")
	  ImageResource iconMiddle();
	  
	  @Source("files/icon_right.png")
	  ImageResource iconRight();
	  
	  @Source("files/horizontalBar.png")
	  ImageResource horizontalBar();
	  
	  @Source("files/verticalBar.png")
	  ImageResource verticalBar();
	  
	  @Source("files/verticalTitle.png")
	  ImageResource verticalTitle();
	  
	  @Source("files/horizontalTitle.png")
	  ImageResource horizontalTitle();
	  
	  @Source("files/verticalLine.png")
	  ImageResource verticalLine();
	  
	  @Source("files/lineBar.png")
	  ImageResource lineBar();
	  
	  @Source("files/blank.png")
	  ImageResource blank();
	  
	  @Source("files/borderBox.png")
	  ImageResource borderBox();
	  
	  @Source("files/close.png")
	  ImageResource close();
	  
	  @Source("files/xmlButton.png")
	  ImageResource xmlButton();
	  
	  @Source("files/xmlButton16.png")
	  ImageResource xml16Button();
	  
	  @Source("files/search16Btn.png")
	  ImageResource search16Button();

	  @Source("files/delete16Btn.png")
	  ImageResource delete16Button();

	  @Source("files/save16Btn.png")
	  ImageResource save16Button();

	  @Source("files/insert16Btn.png")
	  ImageResource insert16Button();

	  @Source("files/update16Btn.png")
	  ImageResource update16Button();

	  @Source("files/trash16Btn.png")
	  ImageResource trash16Button();

	  @Source("files/eraser16Btn.png")
	  ImageResource eraser16Button();

	  @Source("files/close16Btn.png")
	  ImageResource close16Button();

	  @Source("files/close24Btn.png")
	  ImageResource close24Button();

	  @Source("files/tabs24Btn.png")
	  ImageResource tabs24Button();

	  @Source("files/select16Btn.png")
	  ImageResource select16Button();

	  @Source("files/cancel16Btn.png")
	  ImageResource cancel16Button();

}
