package promotions.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Component
public class LidlService {

    private static Logger logger = LogManager.getLogger();

    public void getCatalogueImagesForCurrentPromotion(String currentCatalogueUrl) throws IOException {
        HttpClient client = new DefaultHttpClient();

        String[] formatURL = currentCatalogueUrl.split("/html5");
        String catalogImagesUrl = formatURL[0].concat("/mobile.xml");
        HttpGet get = new HttpGet(catalogImagesUrl);

        HttpResponse response = client.execute(get);
        if(response.getStatusLine().getStatusCode() == 200){
            HttpEntity responseBody = response.getEntity();
//            Document doc = null;
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            try {
//                DocumentBuilder builder = factory.newDocumentBuilder();
//                doc = builder.parse(responseBody.getContent());
//            } catch (ParserConfigurationException e) {
//                e.printStackTrace();
//            } catch (IllegalStateException e) {
//                e.printStackTrace();
//            } catch (SAXException e) {
//                e.printStackTrace();
//            }

            JSONObject json = XML.toJSONObject(EntityUtils.toString(responseBody));
            System.out.println("Response as json: \n" +json);



        }
    }


}
