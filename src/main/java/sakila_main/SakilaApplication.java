package sakila_main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SakilaApplication {
    public static void main(String[] args) {
      /*  System.out.println("Norbs Project");
        String firstname="";
        String lastname="";
        for (int i = 1; i <= 1000; i++) {
            firstname= firstname + "sku,"+i;
        }
        for (int i = 1; i <= 1000; i++) {
            lastname= lastname +"bku,"+i;
        }
        firstname = firstname.trim();
        lastname = lastname.trim();
        System.out.print(firstname);
        System.out.println("\n");
        System.out.print(lastname);*/
        SpringApplication.run(SakilaApplication.class, args);

     /*   for (int i = 4208; i <= 4328 ; i++) {
            System.out.print(i+",");
        }*/


    }
}
