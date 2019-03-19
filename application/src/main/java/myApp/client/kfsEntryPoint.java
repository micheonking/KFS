package myApp.client;

import com.google.gwt.core.client.EntryPoint;

import myApp.client.vi.ContactPointer;
import myApp.client.vi.LoginPage;
import myApp.client.vi.MainLogin;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class kfsEntryPoint implements EntryPoint {

	@Override
	public void onModuleLoad() {
		myApp.client.vi.ContactPointer login = new ContactPointer();
//		myApp.client.vi.LoginPage login = new LoginPage();
//		myApp.client.vi.MainLogin login = new MainLogin();
		login.open();  
	} 
}

