package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ViewVehicle_Page {

	private WebElement element = null;

	public WebElement txtbx_CarNo(WebDriver driver) {

		element = driver.findElement(By.name("Vrm"));

		return element;

	}

	public WebElement btn_Continue(WebDriver driver) {

		element = driver.findElement(By.name("Continue"));

		return element;

	}

}
