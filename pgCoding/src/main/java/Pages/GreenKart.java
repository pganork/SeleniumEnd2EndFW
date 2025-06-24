package Pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class GreenKart {

    ChromeDriver driver;

    @FindBy(xpath="//span[text()='Veg/fruit name']/parent::th")
    WebElement sortingElement;

    @FindBy(xpath = "//tbody//tr/td[1]")
    List<WebElement> listElement;

    @FindBy(xpath = "//a[@aria-label='Next']")
    WebElement next;
    public GreenKart(ChromeDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean checkSorting(){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(sortingElement));

        sortingElement.click();

        List<String> originalList = listElement.stream().map(s->s.getText()).collect(Collectors.toList());

        List<String> postSorting = originalList.stream().sorted().collect(Collectors.toList());

       boolean caseTest =  originalList.equals(postSorting);

       return caseTest;

    }

    public void checkPrice(){
        List<String> priceList;
        do {
            priceList = listElement.stream().filter(s -> s.getText().equalsIgnoreCase("Chocolate")).map(
                    s -> findPrice(s)).collect(Collectors.toList());

            priceList.forEach(s -> System.out.println(s));
            if(priceList.size()>0){break;}
            next.click();



        }while(priceList.size()<1);

    }

    private String findPrice(WebElement s) {


        String price = s.findElement(By.xpath("following-sibling::td[1]")).getText();
        return price;

    }
}
