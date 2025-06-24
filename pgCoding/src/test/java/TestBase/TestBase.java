package TestBase;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;


public class TestBase {

	public static WebDriver driver;
	public static Properties prop;
    public static Properties Param = null;
	public static ExtentReports extent;
	public static ExtentTest test;
	public WebDriverWait wait;


	String browserName;
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");


	public void init() throws IOException {

		Param = new Properties();
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\Param.properties");
		Param.load(fs);

		this.browserName = Param.getProperty("browser");

	}public WebDriver launchBrowser(String browserName, boolean isHeadless) {
		WebDriver driver = null;

		switch (browserName.toLowerCase()) {
			case "chrome":
				//System.setProperty("webdriver.chrome.driver", chrome_driver); Selenium 4 should auto load
				ChromeOptions chromeOptions = new ChromeOptions();
				if (isHeadless) chromeOptions.addArguments("--headless");
				chromeOptions.addArguments("--disable-extensions");
				chromeOptions.addArguments("--disable-notifications");
				chromeOptions.addArguments("--disable-popup-blocking");
				chromeOptions.addArguments("window-size=1200,800");
				chromeOptions.addArguments("--remote-allow-origins=*");
				driver = new ChromeDriver(chromeOptions);

				wait =  new WebDriverWait(driver, Duration.ofSeconds(20));
				break;

			case "firefox":
				//System.setProperty("webdriver.gecko.driver", firefox_driver);
				FirefoxOptions firefoxOptions = new FirefoxOptions();
				if (isHeadless) firefoxOptions.addArguments("--headless");
				firefoxOptions.addPreference("dom.webnotifications.enabled", false);
				firefoxOptions.addPreference("dom.disable_beforeunload", true);
				firefoxOptions.addArguments("--width=1200");
				firefoxOptions.addArguments("--height=800");
				driver = new FirefoxDriver(firefoxOptions);
				break;

			case "edge":
				//System.setProperty("webdriver.edge.driver", edge_driver);
				EdgeOptions edgeOptions = new EdgeOptions();
				if (isHeadless) edgeOptions.addArguments("--headless");
				edgeOptions.addArguments("--disable-extensions");
				edgeOptions.addArguments("--disable-notifications");
				edgeOptions.addArguments("window-size=1200,800");
				driver = new EdgeDriver(edgeOptions);
				break;

			case "ie":
			case "internet explorer":
				//System.setProperty("webdriver.ie.driver", ie_driver);
				driver = new InternetExplorerDriver();
				break;

			default:
				System.out.println("Invalid browser name: " + browserName);
				return null;
		}

		if (driver != null) {
			driver.manage().deleteAllCookies();
			System.out.println("Browser launched successfully at: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		}

		return driver;
	}

	@BeforeSuite
	public void setUpExtentReport() {
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/ExtentReport.html");
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);

		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("User Name", "Prajwal G");
	}

	@AfterSuite
	public void tearDownExtentReport() {
		extent.flush(); // VERY IMPORTANT
	}

}