package promotions.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import org.json.JSONObject;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import promotions.model.Promotii;
import promotions.repository.Promotions_Repository;
import promotions.tools.web.BrowserManager;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/")
public class Example {

    @Autowired
    private Promotions_Repository promotionsRepository;

    BrowserManager browserManager;
    List<String> imageURLs = new ArrayList<String>();

    @RequestMapping(value = "/ok")
    public String testAppIsWorking(){
        Promotii promotii = new Promotii("Ceva");
        promotionsRepository.save(promotii);
        return "It is working!!";
    }

    @RequestMapping(value = "/google")
    public void openGoogle() throws MalformedURLException, JsonProcessingException {
        browserManager = new BrowserManager();
        browserManager.openUrl("https://www.google.com");

        for (LogEntry entry : browserManager.getDriver().manage().logs().get(LogType.CLIENT)) {
            ObjectMapper mapper = new ObjectMapper();
            String jsonEntry = mapper.writeValueAsString(entry);

            String jsonMessage = JsonPath.parse(jsonEntry).read("$.message");
            String jsonMethod = JsonPath.parse(jsonMessage).read("$.message.method");

            // System.out.println(entry.toString());

            if (jsonMethod.contains("Network.responseReceived")) {
                Map<String, String> headers = JsonPath.parse(jsonMessage).read("$.message.params.response.headers");
                if (!headers.isEmpty()) {
                    String contentType = JsonPath.parse(jsonMessage).read("$.message.params.response.headers.content-type");
                    System.out.println(jsonMessage);
                    System.out.println(contentType);
                    if(contentType.equals("image/png")){
                        String imageUrl = JsonPath.parse(jsonMessage).read("$.message.params.response.url");
                        imageURLs.add(imageUrl);
                    }
                }
            }
        }
        for(String imageURL : imageURLs){
            System.out.println("Image urls are the following: \n" +imageURL + "\n");
        }

        browserManager.closeBrowser();
        browserManager.close();
    }

    @RequestMapping(value = "/lidl")
    public void getLidlCalatogue() throws MalformedURLException, JsonProcessingException {
        browserManager = new BrowserManager();
        browserManager.openUrl("http://www.lidl.ro/ro/2829.htm");

        for (LogEntry entry : browserManager.getDriver().manage().logs().get(LogType.PERFORMANCE)) {
            ObjectMapper mapper = new ObjectMapper();
            String jsonEntry = mapper.writeValueAsString(entry);

            System.out.println(entry.toString());
        }

        browserManager.closeBrowser();
        browserManager.close();
    }
}
