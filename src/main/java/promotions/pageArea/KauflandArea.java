package promotions.pageArea;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import promotions.tools.web.BasePageObject;
import promotions.tools.web.BrowserManager;

public class KauflandArea extends BasePageObject {

    @FindBy(xpath = "/html/body/div[2]/main/div/div[1]/div/div/div[2]/div/a/div[2]/div[1]/figure/img")
    private WebElement currentCatalog;

    public KauflandArea(BrowserManager browserManager){
        super(browserManager);
    }
}
