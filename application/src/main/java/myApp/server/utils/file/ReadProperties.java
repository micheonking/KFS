package myApp.server.utils.file;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import org.apache.ibatis.io.Resources;

public class ReadProperties {
	String propertie = "";
	
	public ReadProperties() {
		this.readProperties();
	}
	public ReadProperties(String str) {
		this.readProperties(str);
	}
	
	private Properties readProperties() {
		Properties properties = new Properties();
		String resource = "properties/myApp.properties";
		
        try {
        	/*
        	ClassLoader cl;
            cl = Thread.currentThread().getContextClassLoader();
            if(cl == null){
            	cl = ClassLoader.getSystemClassLoader();
            }
            URL url = cl.getResource("properties/upload.properties");
            System.out.println(url.getPath());
        	*/
    		Reader reader = Resources.getResourceAsReader(resource);
    		properties.load(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        return properties;
	}
	
	private void readProperties(String str) {
		Properties properties = this.readProperties();
		propertie = properties.getProperty(str);
	}
	
	public String getProperty() {
		return propertie;
	}
}
