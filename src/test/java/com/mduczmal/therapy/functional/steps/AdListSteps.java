package com.mduczmal.therapy.functional.steps;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AdListSteps {

    private WebDriver driver;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
    }

    @Given("^I am on the home page$")
    public void I_visit_home_page() {
        driver.get("http://127.0.0.1:8080");
    }

    @Then("^The list of ads is displayed$")
    public void checkTitle() {
        List<WebElement> cards = driver.findElements(By.className("card"));
        assertTrue(cards.size() > 0);
    }

    @After()
    public void closeBrowser() {
        driver.quit();
    }
}

