package everreal;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.*;

public class StepDefinitions {
    WebDriver driver = new ChromeDriver();
    WebDriverWait wait = new WebDriverWait(driver, 10);

    @Given("the user is logged in to website")
    public void the_user_is_logged_in_to_website() {
        login_to_website("ulhas.deshmukh@gmail.com", "2g%88zSnCZ4KwmYV");
    }

    public void login_to_website(String username, String password) {
        driver.manage().window().fullscreen();
        driver.get("https://acme-qa.everreal.co/accounts/login");
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        //To wait for element visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/app/users/me']")));
    }

    @Given("user is on viewing page")
    public void user_is_on_viewing_page() {
        navigateToViewingPage();
    }

    private void navigateToViewingPage() {
        driver.findElement(By.xpath("//a[@href='/app/tenant/viewings']")).click();
        //To wait for element visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span/span[contains(text(),'Viewings')]")));
    }

    @When("user select listing on viewing page")
    public void user_select_listing_on_viewing_page() {
        selectListing();
    }

    private void selectListing() {
        driver.findElement(
                By.xpath("//div[contains(text(),'Nice apartment on Erika-Mann-str - QA interview listing')]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span/span[contains(text(),'Viewing details')]")));
        //clear web cookie notice shown at footer
        driver.findElement((By.xpath("//span[contains(text(),'clear')]"))).click();
    }

    @Then("viewing details page should be shown with no appointment selected")
    public void viewing_details_page_should_be_shown_with_no_appointment_selected() {

        assertTrue( "Appointment shown under selected section", isNoAppointmentSelected());
    }

    @Given("user is on viewing details page")
    public void user_is_on_viewing_details_page() {
        navigateToListingDetailsPage();
    }

    @When("user select an appointment")
    public void user_select_an_appointment() {
        /*
        following xpath can also be used instead of finding element by direct text
        //h5/span[contains(text(),'Below you')]/parent::h5//following-sibling::div//span[contains(text(),'Date')]
         */
        driver.findElement(
                By.xpath("//span[contains(text(),'March 1, 2020 4:30 PM')]")).click();
    }

    @When("click on save button")
    public void click_on_save_button() {
        scrollToElementAndClick("//button//span[contains(text(),'Save')]");

    }

    @Then("confirmation messagebox shown")
    public void confirmation_messagebox_shown() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button//span[contains(text(),'Ok')]")));
        driver.findElement(
                By.xpath("//button//span[contains(text(),'Ok')]")).click();
    }

    @Then("selected appointment is shown under selected appointment section")
    public void selected_appointment_is_shown_under_selected_appointment_section() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h5/span[contains(text(),'Your selected')]/parent::h5/parent::div/" +
                        "following-sibling::div//span[contains(text(),'March 1, 2020')]")));
    }

    @When("user delete an appointment")
    public void user_delete_an_appointment() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button//span[contains(text(),'delete')]")));
        driver.findElement(
                By.xpath("//button//span[contains(text(),'delete')]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button//span[contains(text(),'Ok')]")));
        driver.findElement(
                By.xpath("//button//span[contains(text(),'Ok')]")).click();

    }

    @Then("there will be no appointment shown under selected appointment section")
    public void there_will_be_no_appointment_shown_under_selected_appointment_section() {
       assertTrue( "Appointment shown under selected section", isNoAppointmentSelected());
    }

    private boolean isNoAppointmentSelected() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h5/span[contains(text(),'You did not select any appointment yet:')]")));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @When("user click on can not make it during those times link")
    public void user_click_on_can_not_make_it_during_those_times_link() {
        scrollToElementAndClick("//span[contains(text(),'Cannot make it during those times')]");
    }

    public void scrollToElementAndClick(String xpath_locator) {
        WebElement element = driver.findElement(By.xpath(xpath_locator));
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.click();
        actions.perform();
    }

    @Then("message input box shown to user")
    public void message_input_box_shown_to_user() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div[role='dialog']")));
    }

    @Then("user able to send message")
    public void user_able_to_send_message() {
        driver.findElement(By.id("message")).sendKeys("is it possible on next saturday?");
        driver.findElement(
                By.xpath("//button//span[contains(text(),'Ok')]")).click();
    }

    @When("user change language from English to Deutsch")
    public void user_change_language_from_English_to_Deutsch() {
        changeLanguageTo("Deutsch");
    }

    @Then("page locale should be change to Deutsch")
    public void page_locale_should_be_change_to_Deutsch() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[contains(text(),'Details zum Besichtigungstermin')]")));
    }

    @Then("change local back to English")
    public void change_local_back_to_English() {

        changeLanguageTo("English");
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[contains(text(),'Viewing details')]")));

    }
    private void changeLanguageTo(String language){
        String languageFrom = "", languageTo = "";
        if (language.equals("English")) {
            languageFrom = "Deutsch";
            languageTo = "English";

        }else if (language.equals("Deutsch")){
            languageFrom = "English";
            languageTo = "Deutsch";
        }

        String languageToLocator = "//ul[@role='listbox']/li[contains(text(),'" + languageTo + "')]";
        String languageFromLocator = "//li[@role='menuitem']/following-sibling::div//div[@role='button' and " +
                "contains(text(),'" + languageFrom + "')]";

        WebElement language_dropdown = driver.findElement(By.xpath
                (languageFromLocator));
        language_dropdown.click();
        driver.findElement(By.xpath(languageToLocator)).click();

    }
    private void navigateToListingDetailsPage(){
        navigateToViewingPage();
        selectListing();
    }
    @After
    public void tearDown(Scenario scenario){
        if (scenario.isFailed()) {
            // Take a screenshot...
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png", scenario.getName()); // ... and embed it in the report.
        }
        driver.quit();
    }
}