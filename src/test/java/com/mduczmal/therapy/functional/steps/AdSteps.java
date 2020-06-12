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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class AdSteps extends Steps {

    private static final String TELEPHONE_NUMBER = "Telefon";
    private static final String EMAIL_ADDRESS = "Email";
    private static final String DESCRIPTION = "Szczegółowe informacje o psychoterapii";
    private static final String BACK_TO_AD_LIST = "Wróć do listy ogłoszeń";
    private static final String AD_DETAILS_TITLE = "Ogłoszenie";
    private static final String TEST_COMMENT_TEXT = "TreśćKomentarza1";
    private static final int TEST_ADD_WITH_COMMENTS = 3;
    private static final int TEST_ADD_WITH_DETAILS = 2;
    private WebDriver driver;
    private Integer adNum;

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

    @Given("^the ad has comments$")
    public void setTestAddWithComments() {
        adNum = TEST_ADD_WITH_COMMENTS;
    }

    @Given("^the ad has (email|telephone number)$")
    public void setTestAddWithDetails(String detail) {
        if (adNum == null || !adNum.equals(6)) {
            adNum = TEST_ADD_WITH_DETAILS;
        }
    }


    @When("^I click the ad$")
    public void clickAd() {
        System.out.println(adNum);
        if (adNum == null) {
            WebElement adDetailsLink = driver.findElement(By.className("card-link"));
            adDetailsLink.click();
        } else {
            List<WebElement> adDetailsLink = driver.findElements(By.className("card-link"));
            adDetailsLink.get(adNum).click();
        }
    }

    @When("^I press the back button$")
    public void returnToHomePage() {
        WebElement goBackButton = driver.findElement(By.linkText(BACK_TO_AD_LIST));
        goBackButton.click();
    }

    @Then("^name and surname is displayed$")
    public void getNameAndSurname() {
        boolean nameAndSurname = !driver.findElement(By.tagName("h3")).getText().isBlank();
        assertTrue(nameAndSurname);
    }

    @Then("^address is displayed$")
    public void getAddress() {
        boolean address = !driver.findElement(By.tagName("h5")).getText().isBlank();
        assertTrue(address);
    }

    private void getDetail(String label) {
        List<WebElement> detailsLabels = driver.findElements(By.className("rounded-left"));
        Optional<WebElement> detailLabel = detailsLabels.stream()
                .filter(w -> w.getText().equals(label)).findAny();
        boolean detailLabelPresent = detailLabel.isPresent();
        boolean detailPresent = false;
        if (detailLabelPresent) {
            String xpath = String.format("//div[contains(text(),'%s')]/following-sibling::div", label);
            detailPresent = !(driver.findElement(By.xpath(xpath)).getText().isBlank());
        }
        assertTrue(detailLabelPresent);
        assertTrue(detailPresent);
    }

    @Then("^email is displayed$")
    public void getEmail() {
        getDetail(EMAIL_ADDRESS);
    }

    @Then("^telephone number is displayed$")
    public void getTelephoneNumber() {
        getDetail(TELEPHONE_NUMBER);
    }

    @Then("^the list of ads is displayed$")
    public void getListOfAds() {
        List<WebElement> cards = driver.findElements(By.className("card"));
        assertTrue(cards.size() > 0);
    }

    @Then("^I see the comments$")
    public void getComments() {
        List<WebElement> cardTexts = driver.findElements(By.className("card-text"));
        List<WebElement> commentTexts = cardTexts.stream().filter(w -> w.getText().equals(TEST_COMMENT_TEXT))
                .collect(Collectors.toList());
        assertTrue(commentTexts.size() > 0);

    }

    @When("^I accept the cookies$")
    public void acceptCookies() {
        driver.findElement(By.id("acceptCookies")).click();
    }

    @Then("^info about cookies is displayed$")
    public void cookiesDisplayed() {
        assertTrue(driver.findElement(By.id("cookies")).isDisplayed());
    }

    @Then("^info about cookies is not displayed$")
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
