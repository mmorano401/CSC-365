package methods;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public getRec getRec(){
        return new getRec();
    }

    @Bean
    public getTitleMap getTitleMap(){
        return new getTitleMap();
    }

    @Bean
    public getUrls getUrls(){
        return new getUrls();
    }
}
