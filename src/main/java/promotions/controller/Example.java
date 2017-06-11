package promotions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import promotions.model.Promotii;
import promotions.repository.Promotions_Repository;
import tools.web.BrowserManager;

@RestController
@RequestMapping(value = "/")
public class Example {

    @Autowired
    private Promotions_Repository promotionsRepository;

    BrowserManager browserManager;

    @RequestMapping(value = "/ok")
    public String testAppIsWorking(){
        Promotii promotii = new Promotii("Ceva");
        promotionsRepository.save(promotii);
        return "It is working!!";
    }

    @RequestMapping(value = "/google")
    public void openGoogle(){
        browserManager = new BrowserManager();
        browserManager.openUrl("https://www.google.com");
    }
}
