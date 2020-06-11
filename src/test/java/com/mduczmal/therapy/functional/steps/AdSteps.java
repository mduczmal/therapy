package com.mduczmal.therapy.functional.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AdSteps extends Steps {

    private WebDriver driver;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
    }

    @Given("^I visit the home page$")
    public void visitHomePage() {
        driver.get("http://localhost:8080");
    }

    @Given("^I see the ad details$")
    public void displayFirstAd() {
        driver.get("http://localhost:8080/ads/1");
    }

    @When("^I click the ad$")
    public void clickAd() {
        WebElement adDetailsLink = driver.findElement(By.className("card-link"));
        adDetailsLink.click();
    }

    @When("^I press the back button$")
    public void returnToHomePage() {
        WebElement goBackButton = driver.findElement(By.linkText("Wróć do listy ogłoszeń"));
        goBackButton.click();
    }

    @Then("^Ad details are displayed$")
    public void getAdDetails() {
        assertEquals("Ogłoszenie", driver.getTitle());
    }

    @Then("^The list of ads is displayed$")
    public void getListOfAds() {
        List<WebElement> cards = driver.findElements(By.className("card"));
        assertTrue(cards.size() > 0);
    }

    @When("^I accept the cookies$")
    public void acceptCookies() {
        driver.findElement(By.id("acceptCookies")).click();
    }

    @Then("^Info about cookies is displayed$")
    public void cookiesDisplayed() {
        assertTrue(driver.findElement(By.id("cookies")).isDisplayed());
    }

    @Then("^Info about cookies is not displayed$")
    public void cookiesNotDisplayed() {
        boolean invisible = new WebDriverWait(driver, 3).until(
                ExpectedConditions.invisibilityOf(driver.findElement(By.id("cookies")))
        );
        assertTrue(invisible);
    }


    @After()
    public void closeBrowser() {
        driver.quit();
    }

}
