import net.lightbody.bmp.proxy.ProxyServer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Sample2_Authentication {

  @Test
  public void autoBasicAuthorization() throws Exception {
    ProxyServer bmp = new ProxyServer(8071);
    bmp.start();

    //bmp.autoBasicAuthorization("the-internet.herokuapp.com", "admin", "admin");
    bmp.autoBasicAuthorization("", "admin", "admin");

    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setCapability(CapabilityType.PROXY, bmp.seleniumProxy());

    WebDriver driver = new ChromeDriver(caps);

    driver.get("http://the-internet.herokuapp.com/");
    driver.findElement(By.linkText("Basic Auth")).click();
    Thread.sleep(4000);
    Assert.assertTrue(driver.getPageSource().contains("You must have the proper credentials."));
    driver.quit();

    bmp.stop();
  }
}
