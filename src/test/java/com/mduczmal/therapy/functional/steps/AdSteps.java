package com.mduczmal.therapy.functional.steps;

import com.mduczmal.therapy.InitData;
import com.mduczmal.therapy.ad.AdRepository;
import com.mduczmal.therapy.ad.comment.CommentRepository;
import com.mduczmal.therapy.therapist.TherapistRepository;
import com.mduczmal.therapy.user.AuthorityRepository;
import com.mduczmal.therapy.user.UserRepository;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AdSteps {
    @Autowired
    InitData init;
    @Autowired
    private AdRepository adRepository;
    @Autowired
    private TherapistRepository therapistRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;


    private static final String TELEPHONE_NUMBER = "Telefon";
    private static final String EMAIL_ADDRESS = "Email";
    private static final String DESCRIPTION = "Szczegółowe informacje o psychoterapii";
    private static final String BACK_TO_AD_LIST = "Wróć do listy ogłoszeń";
    private static final String TEST_COMMENT_TEXT = "TreśćKomentarza1";
    private static final String EDIT_AD = "Edytuj ogłoszenie";
    private static final String UPDATE_AD = "Zakończ edycję";
    private static final String CREATE_AD = "Dodaj ogłoszenie";
    private static final String SUBMIT_AD = "Dodaj ogłoszenie";
    private static final String DELETE_AD = "Usuń";
    private static final String DELETE_COMMENT = "Usuń";
    private static final String ADD_COMMENT = "Dodaj opinię";
    private static final int TEST_AD_WITH_COMMENTS = 3;
    private static final int TEST_NUM_COMMENTS = 3;
    private static final int TEST_AD_WITH_DETAILS = 2;
    private static final int TEST_NUM_ADS = 4;
    private WebDriver driver;
    private Integer adNum;
    private Map<String, String> buttonText;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        buttonText = new HashMap<>();
        buttonText.put("create ad", CREATE_AD);
        buttonText.put("save ad", SUBMIT_AD);
        buttonText.put("edit ad", EDIT_AD);
        buttonText.put("update ad", UPDATE_AD);
        buttonText.put("delete ad", DELETE_AD);
        buttonText.put("back", BACK_TO_AD_LIST);
        buttonText.put("add comment", ADD_COMMENT);
        buttonText.put("delete comment", DELETE_COMMENT);

        commentRepository.deleteAll();
        adRepository.deleteAll();
        authorityRepository.deleteAll();
        userRepository.deleteAll();
        therapistRepository.deleteAll();
        init.run();
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
        adNum = TEST_AD_WITH_COMMENTS;
    }

    @Given("^the ad has (email|telephone number)$")
    public void setTestAddWithDetails(String detail) {
        if (adNum == null || !adNum.equals(6)) {
            adNum = TEST_AD_WITH_DETAILS;
        }
    }

    private void login(String username, String password) {
        driver.get("http://localhost:8080/login");
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.tagName("button")).click();
    }

    @Given("^I am a moderator$")
    public void signInModerator() {
        login("admin", "admin");
    }

    @Given("^I am a therapist$")
    public void signInFirstTherapist() {
        login("Test1", "pass1");
    }

    @Given("^I have an ad$")
    public void signInThirdTherapist() {
        login("Test3", "pass3");
    }


    @Given("^I see a comment")
    public void displayAdWithComments() {
        driver.get(String.format("http://localhost:8080/ads/%d", TEST_AD_WITH_COMMENTS));
    }

    @When("^I click the ad$")
    public void clickAd() {
        if (adNum == null) {
            WebElement adDetailsLink = driver.findElement(By.className("card-link"));
            adDetailsLink.click();
        } else {
            List<WebElement> adDetailsLink = driver.findElements(By.className("card-link"));
            adDetailsLink.get(adNum).click();
        }
    }

    @When("I click the {string} button")
    public void clickButton(String buttonName) {
        String text = buttonText.get(buttonName);
        if (text == null) throw new IllegalArgumentException("Button name '" + buttonName +
                "' is not present in name to label map.");
        List<WebElement> buttons = driver.findElements(By.className("btn"));
        WebElement button = buttons.stream().filter(b -> b.getText().equals(text)).findAny().orElseThrow(
                () -> new IllegalArgumentException("Button with text: '" + text + "' not found on page"));
        button.click();
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
        var ads = cards.stream().filter(c -> !c.getAttribute("id").equals("cookies")).collect(Collectors.toList());
        assertThat(ads, hasSize(TEST_NUM_ADS));
    }

    @Then("^The comment is not displayed$")
    public void getCommentsAfterDelete() {
        List<WebElement> cardTexts = driver.findElements(By.className("card-text"));
        List<WebElement> commentTexts = cardTexts.stream().filter(w -> w.getText().equals(TEST_COMMENT_TEXT))
                .collect(Collectors.toList());
        assertThat(commentTexts, hasSize(TEST_NUM_COMMENTS-1));

    }

    @Then("^I see the comments$")
    public void getComments() {
        List<WebElement> cardTexts = driver.findElements(By.className("card-text"));
        List<WebElement> commentTexts = cardTexts.stream().filter(w -> w.getText().equals(TEST_COMMENT_TEXT))
                .collect(Collectors.toList());
        assertThat(commentTexts, hasSize(TEST_NUM_COMMENTS));
    }

    @Then("^the ad is not displayed$")
    public void getAdsAfterDelete() {
        List<WebElement> cards = driver.findElements(By.className("card"));
        var ads = cards.stream().filter(c -> !c.getAttribute("id").equals("cookies")).collect(Collectors.toList());
        assertThat(ads, hasSize(TEST_NUM_ADS-1));
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
