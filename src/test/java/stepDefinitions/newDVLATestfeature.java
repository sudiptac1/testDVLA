
package stepDefinitions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.apache.tika.Tika;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.io.FileUtils;

import org.openqa.selenium.OutputType;

import com.test.testDVLAProject.service.FileService;
import com.test.testDVLAProject.service.IFileService;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import helper.FileListReaderFactory;
import pageObjects.ConfirmVehicle_Page;
import pageObjects.StartNow_Page;
import pageObjects.ViewVehicle_Page;

public class newDVLATestfeature {

	private final static Logger logger = Logger.getLogger(newDVLATestfeature.class);
	private final IFileService fs = new FileService();
	private List<File> files = null;
	private final Map<String, List<String>> fileVehicleDetails = new HashMap<>();
	private final Map<String, List<String>> outputVehicleDetails = new HashMap<>();

	WebDriver driver = null;

	@Before
	public void StartBrowser() {
		System.setProperty("webdriver.chrome.driver", "Lib//chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		logger.info("browser starting");
	}

	@After
	public void quitBrowser() {
		driver.quit();
	}

	@Given("^list of files where each file contains list of vehicle registration details$")
	public void list_of_files_where_each_file_contains_list_of_vehicle_registration_details() throws Throwable {
		files = fs.getFiles();
		Tika tika = new Tika();
		for (File file : files) {
			String mimeType = tika.detect(file);
			FileListReaderFactory.getFileListReaderInstance(mimeType).readFileList(file, fileVehicleDetails);
		}

		logger.info("File has been read");

	}

	@Given("^I open DVLA website And Click startNow button in DVLA website$")
	public void i_open_DVLA_website_And_Click_startNow_button_in_DVLA_website() throws Throwable {

		driver.get("https://www.gov.uk/get-vehicle-information-from-dvla");
		StartNow_Page stPage = new StartNow_Page();
		stPage.btn_StartNow(driver).click();
		logger.info("startNow button click in first page");

	}

	@When("^I enter each car registration  no from files in the page, I collect the output in a list$")
	public void i_enter_each_car_registration_no_from_files_in_the_page_I_collect_the_output_in_a_list()
			throws Throwable {

		WebElement element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.elementToBeClickable(By.name("Continue")));

		logger.info("in view page");

		for (String regNo : fileVehicleDetails.keySet()) {
			processWebsiteOutput(regNo);
		}

	}

	@Then("^I verified output data are matched with the car registration details in the given  files$")
	public void i_verified_output_data_are_matched_with_the_car_registration_details_in_the_given_files()
			throws Throwable {

		for (String regNo : fileVehicleDetails.keySet()) {
			List<String> fileDetails = fileVehicleDetails.get(regNo);
			List<String> outputDetails = outputVehicleDetails.get(regNo);
			Assert.assertArrayEquals(
					"output data are not matched with the car registration details in the given  files",
					fileDetails.toArray(), outputDetails.toArray());
		}

	}

	private void processWebsiteOutput(String regNo) throws Exception {
		System.out.println("regNo" + regNo);
		enterRegNo(regNo);

		WebElement element1 = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.elementToBeClickable(By.name("Continue")));

		logger.info("in confirm page");

		List<String> carinfolist1 = captureOutput();

		logger.info("output list created to put into hashmap");
		outputVehicleDetails.put(regNo, carinfolist1);

		ConfirmVehicle_Page cpage = new ConfirmVehicle_Page();
		cpage.ele_SelectOption(driver).click();
		cpage.btn_Continue(driver).click();
		WebElement element2 = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.elementToBeClickable(By.name("Continue")));

		logger.info("in view page again");

	}

	private void enterRegNo(String regNo) throws Exception {
		ViewVehicle_Page vpage = new ViewVehicle_Page();
		vpage.txtbx_CarNo(driver).sendKeys(regNo);

		this.takeScreenShot(driver, "Screenshots//test" + regNo + "Input" + ".png");
		vpage.btn_Continue(driver).click();

		logger.info("in view page");
	}

	private List<String> captureOutput() throws Exception {
		ConfirmVehicle_Page cpage = new ConfirmVehicle_Page();

		List<String> carinfolist = new ArrayList();

		String carNo = cpage.ele_CarNo(driver).getText();
		String carMake = cpage.ele_Make(driver).getText();
		String carColour = cpage.ele_Colour(driver).getText();

		carinfolist.add(carNo);
		carinfolist.add(carMake);
		carinfolist.add(carColour);

		this.takeScreenShot(driver, "Screenshots//test" + carNo + ".png");
		return carinfolist;
	}

	private void takeScreenShot(WebDriver webdriver, String fileWithPath) throws Exception {

		TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile = new File(fileWithPath);
		FileUtils.copyFile(SrcFile, DestFile);

	}

}