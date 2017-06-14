package promotions.pageArea;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Component;
import promotions.tools.web.BasePageObject;

import java.util.List;

public class LidlArea{

    @FindBy(css = "section.smallcontent a.link-noarrow")
    List<WebElement> catalogues;

    public List<WebElement> getCatalogues() {
        return catalogues;
    }
}


