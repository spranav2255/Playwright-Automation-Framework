package MainUtilities;

import com.microsoft.playwright.Page;
import Configurations.DriverFactory;
import com.aventstack.extentreports.ExtentTest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class TestListener {
	private static final org.slf4j.Logger log = LoggerUtil.getLogger(TestListener.class);

	private static final String ROOT_REPORT_DIR = System.getProperty("user.dir") + "/Automation Reports";

	private static final String REPORTS_DIR = ROOT_REPORT_DIR + "/Reports";
	private static final String SCREENSHOTS_DIR = ROOT_REPORT_DIR + "/Screenshots";

	public static void onTestStart(String testName) {
		ExtentManager.startTest(testName);
		DriverFactory.initDriver();
		log.info("Test Started: {}", testName);
	}

	public static void onTestSuccess(String testName) {
		String path = takeScreenshot("pass_" + testName);
		ExtentManager.getTest().pass("Test Passed").addScreenCaptureFromPath(path);
		log.info("Test Passed: {}", testName);
		DriverFactory.closeDriver();
	}

	public static void onTestFailure(String testName, Throwable error) {
		String path = takeScreenshot("fail_" + testName);
		ExtentTest test = ExtentManager.getTest();
		test.fail(error).addScreenCaptureFromPath(path);
		log.error("Test Failed: {}", testName, error);
		DriverFactory.closeDriver();
	}

	public static void onSuiteStart() {
		ExtentManager.init(); // Pass custom path
		log.info("Test Suite Started");
		createFoldersIfNotExist();
	}

	public static void onSuiteFinish() {
		ExtentManager.flush();
		log.info("Test Suite Finished");
	}

	private static String takeScreenshot(String name) {
		Page page = DriverFactory.getPage();

		try {
			createFoldersIfNotExist();
			String screenshotPath = SCREENSHOTS_DIR + "/" + name + ".png";
			Path path = Paths.get(screenshotPath);
			page.screenshot(new Page.ScreenshotOptions().setPath(path));
			return screenshotPath;
		} catch (Exception e) {
			log.error("Failed to take screenshot", e);
			return null;
		}
	}

	private static void createFoldersIfNotExist() {
		try {
			Files.createDirectories(Paths.get(SCREENSHOTS_DIR));
			Files.createDirectories(Paths.get(REPORTS_DIR));
		} catch (IOException e) {
			log.error("Failed to create folders", e);
		}
	}
}
