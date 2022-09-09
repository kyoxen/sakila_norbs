package sakila_main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SakilaApplication {
    public static void main(String[] args) {
        System.out.println("Norbs Project");
        SpringApplication.run(SakilaApplication.class, args);
    }
}
