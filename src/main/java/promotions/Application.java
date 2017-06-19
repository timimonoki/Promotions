package promotions;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import promotions.jobs.LidlJob;
import promotions.jobs.LidlTrigger;


@SpringBootApplication
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args)  {
        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(Application.class)
                .properties("spring.config.name:application,configuration,shops,quartz",
                        "spring.config.location:classpath:/,classpath:/,classpath:/,classpath:/")
                .build().run(args);


        ConfigurableEnvironment environment = applicationContext.getEnvironment();
    }
}
