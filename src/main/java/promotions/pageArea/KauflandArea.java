package promotions.pageArea;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import promotions.tools.web.BasePageObject;
import promotions.tools.web.BrowserManager;

public class KauflandArea extends BasePageObject {

    @FindBy(css = "")
    private WebElement w;

    public KauflandArea(BrowserManager browserManager){
        super(browserManager);
    }
}
