package com.onloadcode.quartz.lesson;

import com.github.lalyos.jfiglet.FigletFont;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Created by   : <B>OnloadCode.com</B>
 *
 * <p>Date      : 6/12/2020<br>
 * Project      : <B>quarts-scheduling-spring-boot-mongodb </B><br>
 */
@Slf4j
@SpringBootApplication
public class Application {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Command line runner command line runner.
     *
     * @param ctx the ctx
     * @return the command line runner
     */
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> log.info(
                FigletFont.convertOneLine("Quarts Scheduling using Rest API with Spring Boot and MongoDB "));
    }

}
