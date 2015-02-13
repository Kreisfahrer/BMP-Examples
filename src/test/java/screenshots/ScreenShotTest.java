package screenshots;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.screentaker.ViewportPastingStrategy;

/**
 * Created by andreystakhievich on 2/13/2015.
 */
public class ScreenShotTest {
    @Test
    public void wholeScreenTest() {
        WebDriver driver = new FirefoxDriver();

        driver.get("http://the-internet.herokuapp.com");
        driver.findElement(By.linkText("File Download")).click();

        new AShot().shootingStrategy(new ViewportPastingStrategy(10000)).takeScreenshot(driver).getImage();

        driver.quit();
    }
}
