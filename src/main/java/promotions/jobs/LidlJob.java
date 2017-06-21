package promotions.jobs;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import promotions.controller.LidlController;
import promotions.exceptions.CurrentCatalogException;
import promotions.pageArea.LidlArea;
import promotions.service.LidlService;
import promotions.tools.web.BrowserManager;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;

@Component
public class LidlJob extends QuartzJobBean {

    @Autowired
    LidlController lidlController;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        try {
//            lidlController.obtainCurrentCatalogImages();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
