import net.lightbody.bmp.proxy.ProxyServer;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Sample8_DownloadingFiles {

  @Test
  public void downloadingFiles() throws Exception {
    ProxyServer bmp = new ProxyServer(8071);
    bmp.start();

    HttpResponseInterceptor downloader = new FileDownloader()
        .addContentType("application/pdf");
    bmp.addResponseInterceptor(downloader);

    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setCapability(CapabilityType.PROXY, bmp.seleniumProxy());

    WebDriver driver = new FirefoxDriver(caps);

    driver.get("http://the-internet.herokuapp.com");
    driver.findElement(By.linkText("File Download")).click();
    driver.findElement(By.linkText("hello-world.pdf")).click();

    File downloadedFile = new File (driver.findElement(By.tagName("body")).getText());
    System.out.println(downloadedFile);
    System.out.println(FileUtils.generateMD5(downloadedFile));
    assertTrue(downloadedFile.exists());
    assertEquals(FileUtils.generateMD5(downloadedFile), "cb9d8dec03ed0a1dfbcac481f91c53cb");

    Thread.sleep(30000);

    driver.quit();

    bmp.stop();
  }

  public static class FileDownloader implements HttpResponseInterceptor {

    private Set<String> contentTypes = new HashSet<String>();
    private File tempDir = null;
    private File tempFile = null;

    public FileDownloader addContentType(String contentType) {
      contentTypes.add(contentType);
      return this;
    }

    @Override
    public void process(HttpResponse response, HttpContext context) throws HttpException, IOException {
      String contentType = response.getFirstHeader("Content-Type").getValue();
      if (contentTypes.contains(contentType)) {
        String postfix = contentType.substring(contentType.indexOf('/') + 1);
        tempFile = File.createTempFile("downloaded", "."+postfix, tempDir);
        tempFile.deleteOnExit();

        FileOutputStream outputStream = new FileOutputStream(tempFile);
        outputStream.write(EntityUtils.toByteArray(response.getEntity()));
        outputStream.close();

        response.removeHeaders("Content-Type");
        response.removeHeaders("Content-Encoding");
        response.removeHeaders("Content-Disposition");
        response.removeHeaders("Content-Length");

        response.addHeader("Content-Type", "text/html");
        response.addHeader("Content-Length", "" + tempFile.getAbsolutePath().length());
        response.setEntity(new StringEntity(tempFile.getAbsolutePath()));
      }
    }
  }
}
