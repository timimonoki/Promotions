package promotions.pageArea;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Component;
import promotions.tools.web.BasePageObject;
import promotions.tools.web.BrowserManager;

import java.net.MalformedURLException;
import java.util.List;

public class LidlArea extends BasePageObject{

    @FindBy(css = "#content > section > p > a > img")
    List<WebElement> catalogs;

    public LidlArea(BrowserManager browserManager) throws MalformedURLException {
        super(browserManager);
    }

    public List<WebElement> getCatalogs() {
        return catalogs;
    }
}



