package myApp.client.utils;

import java.util.Map;

import com.google.gwt.dev.util.collect.HashMap;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;

import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsType;
import myApp.client.service.InterfaceCallback;


@JsType
public class JSCaller {
	public static InterfaceCallback callback;
	public static InterfaceCallback callback2;
	public static String returnParameter;
	@JsFunction
	public interface Foo {
		int exec(int x);
	}
	
	@JsFunction
	public interface returnParameter {
		String exec(String x);
	}

	static TextField field ; 
	
	public static Foo action2() {
		  return (x) -> x + 2;
	}
	
	public static Foo action3() {
		  return new Foo() {
			  @Override
			  public int exec(int x) {
				  field.setValue("가나다 : " + x);
				  Info.display("javascript", "call");
				  return x + 33; 
			  }
		  } ; 
	}
	
	public static Foo getHtml() {
		  return new Foo() {
			  @Override
			  public int exec(int x) {
				  if(callback != null){
						callback.execute();
						callback = null;
				  }
				  return x; 
			  }
		  } ; 
	}

	public static returnParameter returnValue() {
		  return new returnParameter() {
			  @Override
			  public String exec(String x) {
				  returnParameter = x;
				  callback2.execute();
//				  callback2 = null;
				  return x; 
			  }
		  } ; 
	}
	
	public static void call(TextField param) {
		field = param; 
//		field.setValue("dklfjsd");
//		Info.display("javascript", "call");
	}

	public static void setCallback(InterfaceCallback callback2) {
		callback = callback2;
	}
	
	public static void setCallback2(InterfaceCallback callback) {
		callback2 = callback;
	}

}