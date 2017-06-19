package promotions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.scheduling.annotation.EnableScheduling;



@SpringBootApplication
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args)  {
        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(Application.class)
                .properties("spring.config.name:application,configuration,shops",
                        "spring.config.location:classpath:/,classpath:/,classpath:/")
                .build().run(args);


        ConfigurableEnvironment environment = applicationContext.getEnvironment();
    }
}
