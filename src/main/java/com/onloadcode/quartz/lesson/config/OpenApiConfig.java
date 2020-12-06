package com.onloadcode.quartz.lesson.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
/**
 * Created by   : <B>OnloadCode.com</B>
 *
 * <p>Date      : 6/12/2020<br>
 * Project      : <B>quarts-scheduling-spring-boot-mongodb </B><br>
 */

@Configuration
public class OpenApiConfig {
    /**
     * The constant DEFAULT_CONTACT.
     */
    public static final Contact DEFAULT_CONTACT =
            new Contact()
                    .name("Onload Code")
                    .email("author@onloadcode.com")
                    .url("www.onloadcode.com");

    /**
     * The constant LICENSE.
     */
    public static final License LICENSE =
            new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0.html");

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
            new HashSet<>(Arrays.asList("application/json", "application/xml"));
    /**
     * The Api controller package.
     */
    @Value("${api-package}")
    public String API_CONTROLLER_PACKAGE;
    /**
     * The Application name.
     */
    @Value("${application.name}")
    public String APPLICATION_NAME;
    /**
     * The Application description.
     */
    @Value("${application.description}")
    public String APPLICATION_DESCRIPTION;

    /**
     * Custom open api open api.
     *
     * @return the open api
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().components(new Components()).info(apiEndPointsInfo());
    }

    private Info apiEndPointsInfo() {
        return new Info()
                .title(APPLICATION_NAME)
                .description(APPLICATION_DESCRIPTION)
                .contact(DEFAULT_CONTACT)
                .license(LICENSE)
                .version("1.0.0");
    }
}