package myApp.client;

import com.google.gwt.junit.client.GWTTestCase;

public class CompileGwtTest extends GWTTestCase {
  @Override
  public String getModuleName() {
    return "myApp.KFS";
  }

  public void testSandbox() {
    assertTrue(true);
  }
}
