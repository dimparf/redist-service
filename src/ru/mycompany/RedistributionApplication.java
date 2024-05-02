package ru.mycompany;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.mycompany.services.RequestRedistributionService;

@Slf4j
@SpringBootApplication
public class RedistributionApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(RedistributionApplication.class, args);
        var requestRedistributionService = context.getBean(RequestRedistributionService.class);
    }

}
