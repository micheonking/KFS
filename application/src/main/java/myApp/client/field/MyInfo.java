package myApp.client.field;

import com.sencha.gxt.widget.core.client.info.DefaultInfoConfig;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.info.InfoConfig.InfoPosition;

public class MyInfo extends Info {

	public MyInfo() {

	}

	public static void showInfo(String str1, String str2) {
        DefaultInfoConfig config = new DefaultInfoConfig(str1, str2);
        config.setPosition(InfoPosition.BOTTOM_RIGHT);
        
		Info.display(config);
	}
	 
	public static void showInfo(String str1, String str2, String str3) {
        DefaultInfoConfig config = new DefaultInfoConfig(str1, str2);
        if("BR".equals(str3)) {
        	config.setPosition(InfoPosition.BOTTOM_RIGHT);
        } else if("BL".equals(str3)) {
        	config.setPosition(InfoPosition.BOTTOM_LEFT);
        } else if("TR".equals(str3)) {
        	config.setPosition(InfoPosition.TOP_RIGHT);
        } else if("TL".equals(str3)) {
        	config.setPosition(InfoPosition.TOP_LEFT);
        }
        
		Info.display(config);
	}
}