import net.lightbody.bmp.proxy.ProxyServer;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class Sample7_InjectingJavaScript {

  @Test
  public void injectingJavaScript() throws Exception {
    ProxyServer bmp = new ProxyServer(8071);
    bmp.start();

    HttpResponseInterceptor injector = new JQueryInjector();
    bmp.addResponseInterceptor(injector);

    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setCapability(CapabilityType.PROXY, bmp.seleniumProxy());

    FirefoxDriver driver = new FirefoxDriver(caps);

    driver.get("http://the-internet.herokuapp.com/");
    String linkText = (String) driver.executeScript("return $(\"a\").text()");

    Assert.assertTrue(linkText.contains("Basic Auth"));

    driver.quit();

    bmp.stop();
  }

  public static class JQueryInjector implements HttpResponseInterceptor {

    @Override
    public void process(HttpResponse response, HttpContext context) throws HttpException, IOException {
      String contentType = response.getFirstHeader("Content-Type").getValue();
      System.out.println(contentType);
      if (contentType.startsWith("text/html")) {
        String text = EntityUtils.toString(response.getEntity());
        String newText = text.replace("</head>",
            "<script src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js\"></script></head>");

        response.removeHeaders("Content-Length");
        response.addHeader("Content-Length", "" + newText.length());
        response.setEntity(new StringEntity(newText));
      }

    }
  }
}
