import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class Main {

    private static WebDriver driver;

    public static void main(String[] args) {
        System.out.println("Тест по Selenium!");
        System.setProperty("webdriver.chrome.driver", "drv/chromedriver.exe");
        String baseUrl = "https://www.rgs.ru/";

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(baseUrl);

        driver.findElement(By.xpath("//ol/li/a[contains(text(),'Страхование')]")).click();
        driver.findElement(By.xpath("//ul/li[@class='adv-analytics-navigation-line3-link']/a[contains(text(),'ДМС')]")).click();

        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[contains(text(),'Отправить заявку')][contains(@class,'btn btn')]"))));

        compareText(driver.findElement(By.xpath("//span[@class='h1']")).getText(), "Добровольное медицинское страхование");

        driver.findElement(By.xpath("//*[contains(text(),'Отправить заявку')][contains(@class,'btn btn')]")).click();

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h4[@class='modal-title']"))));

        compareText(driver.findElement(By.xpath("//h4[@class='modal-title']")).getText(), "Заявка на добровольное медицинское страхование");

        fillField(By.name("LastName"), "Иванов");
        fillField(By.name("FirstName"), "Иван");
        fillField(By.name("MiddleName"), "Петров");
        fillField(By.name("Email"), "Тестовая версия");
        fillField(By.name("Comment"), "Кооооо");
        fillField(By.xpath("//*[contains(@data-bind,'Phone')]"), "9112223343");

        driver.findElement(By.cssSelector("input.checkbox")).click();
        new Select(driver.findElement(By.name("Region"))).selectByVisibleText("Москва");

        compareText(driver.findElement(By.name("LastName")).getAttribute("value"), "Иванов");

        driver.findElement(By.id("button-m")).click();

        compareText(driver.findElement(By.xpath("//*[text()='Эл. почта']/..//span")).getText(), "Введите адрес электронной почты");

        driver.quit();

    }

    public static void fillField(By locator, String value){
        driver.findElement(locator).click();
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(value);
    }

    public static void compareText(String actualText, String expectedText){
        if(actualText.contains(expectedText)){
            System.out.println("Искомый текст ЕСТЬ: " + expectedText);
        }
        else{
            System.err.println("Искомого текста НЕТ: " + expectedText + " Вместо него: " + actualText);
            driver.quit();
        }
    }
}
