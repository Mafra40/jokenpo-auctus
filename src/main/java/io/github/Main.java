package io.github;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {



    public static void main(String[] args) {
        //método que inicializa a aplicação
       // System.setProperty("server.servlet.context-path", "/app/jokenpo");
        SpringApplication.run(Main.class,args);
    }
}
