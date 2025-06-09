package MainUtilities;

import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Page;
import Configurations.DriverFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.nio.file.*;
import org.slf4j.Logger;

public class TestListener implements ITestListener {

	private static final Logger log = LoggerUtil.getLogger(TestListener.class);

	private static final String ROOT_REPORT_DIR = System.getProperty("user.dir") + "/Automation Reports";
	private static final String REPORTS_DIR = ROOT_REPORT_DIR + "/Reports";
	private static final String SCREENSHOTS_DIR = ROOT_REPORT_DIR + "/Screenshots";

	@Override
	public void onStart(ITestContext context) {
		ExtentManager.init();
		createFoldersIfNotExist();
		log.info("Suite Started");
	}

	@Override
	public void onFinish(ITestContext context) {
		ExtentManager.flush();
		log.info("Suite Finished");
	}

	@Override
	public void onTestStart(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		ExtentManager.startTest(testName);
		DriverFactory.initDriver();
		log.info("Test Started: {}", testName);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		String path = takeScreenshot("Test Passed - " + testName);
		ExtentManager.getTest().pass("Test Passed ").addScreenCaptureFromPath(path);
		log.info("Test Passed: {}", testName);
		DriverFactory.closeDriver();
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		String path = takeScreenshot("Test Failed - " + testName);
		ExtentTest test = ExtentManager.getTest();
		test.fail(result.getThrowable()).addScreenCaptureFromPath(path);
		log.error("Test Failed: {}", testName, result.getThrowable());
		DriverFactory.closeDriver();
	}

	private String takeScreenshot(String name) {
		Page page = DriverFactory.getPage();
		try {
			String screenshotPath = SCREENSHOTS_DIR + "/" + name + ".png";
			Path path = Paths.get(screenshotPath);
			page.screenshot(new Page.ScreenshotOptions().setPath(path));
			log.info("Screenshot taken: {}", screenshotPath);
			return screenshotPath;
		} catch (Exception e) {
			log.error("Failed to take screenshot", e);
			return null;
		}
	}

	private void createFoldersIfNotExist() {
		try {
			Files.createDirectories(Paths.get(REPORTS_DIR));
			Path screenshotsPath = Paths.get(SCREENSHOTS_DIR);
			if (Files.exists(screenshotsPath)) {
				Files.list(screenshotsPath).forEach(path -> {
					try {
						Files.delete(path);
					} catch (IOException e) {
						log.warn("Failed to delete old screenshot: {}", path, e);
					}
				});
			}
			Files.createDirectories(screenshotsPath);
		} catch (IOException e) {
			log.error("Failed to create or clean folders", e);
		}
	}
}
