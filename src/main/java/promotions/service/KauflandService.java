package promotions.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.lightbody.bmp.core.har.Har;
import org.springframework.stereotype.Service;
import promotions.tools.web.BrowserManager;

@Service
public class KauflandService extends BaseService{

    public void obtainCurrentCatalogImages() throws Exception {
        setUp(configurations);

        browserManager.getProxy().newHar("kaufland.ro");
        browserManager.openUrl("https://www.kaufland.ro/oferte/cataloage-cu-reduceri.html");
        Har har = browserManager.getProxy().getHar();
        ObjectMapper mapper = new ObjectMapper();
        String harString = mapper.writeValueAsString(har);
        System.out.println("Har file content is: \n" +harString);
    }
}
