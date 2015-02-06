import net.lightbody.bmp.proxy.ProxyServer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Sample1_StartWithProxy {

  @Test
  public void startWithProxy() throws Exception {
    ProxyServer bmp = new ProxyServer(8071);
    bmp.start();

    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setCapability(CapabilityType.PROXY, bmp.seleniumProxy());

    WebDriver driver = new FirefoxDriver(caps);

    driver.get("http://onliner.by/");
    Assert.assertTrue(driver.getTitle().contains("Onliner.by"));

    driver.quit();

    bmp.stop();
  }
}
