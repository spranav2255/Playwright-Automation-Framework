package MainUtilities;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {
	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	private static final String ROOT_REPORT_DIR = System.getProperty("user.dir") + "/Automation Reports";

	private static final String REPORTS_DIR = ROOT_REPORT_DIR + "/Reports";

	public static void init() {
		String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
		String reportPath = REPORTS_DIR + "/Automation Report_" + timestamp + ".html";

		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);

	}

	public static void flush() {
		extent.flush();
	}

	public static void startTest(String testName) {
		test.set(extent.createTest(testName));
	}

	public static ExtentTest getTest() {
		return test.get();
	}
}
