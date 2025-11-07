package engine.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class OpenApiConfig {
//    @Bean
//    public OpenApiCustomizer hideInternalSchemas() {
//        return openApi -> {
//            var schemas = openApi.getComponents().getSchemas();
//
//            schemas.remove("PageableObject");
//            schemas.remove("SortObject");
//
//        };
//    }
    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8889");
        server.setDescription("Web Quiz Engine");

        Contact myContact = new Contact();
        myContact.setName("Serhii Vynohradov");
        myContact.email("serhiivynohradov@gmail.com");

        Info information = new Info()
                .title("Web Quiz Engine API")
                .version("1.0")
                .description("This API is used to create, solve and manage quizzes.")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}