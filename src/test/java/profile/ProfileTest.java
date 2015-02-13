package profile;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class ProfileTest {
    @Test
    public void pluginTest() throws IOException {
        //for chrome
//        ChromeOptions options = new ChromeOptions();
//        options.addExtensions(new File("/path/to/extension.crx"));
//        ChromeDriver driver = new ChromeDriver(options);
        File file = new File(".\\src\\main\\resources\\firebug.xpi");
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.addExtension(file);
        firefoxProfile.setPreference("extensions.firebug.currentVersion", "2.0.8"); // Avoid   tartup screen
        WebDriver driver = new FirefoxDriver(firefoxProfile);

        driver.get("http://the-internet.herokuapp.com");

        driver.quit();
    }
}
