package cabanas.garcia.ismael.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase que inicia la aplicación Spring Boot.
 * 
 * La anotación @SpringBootapplication supone lo siguiente:
 * <ul>
 * 	<li>@Configuration, indica que esta clase es como un origen de definición de beans</li>
 *  <li>@EnableAutoConfiguration, indica a Spring Boot que empiece a añadir beans al classpath</li>
 *  <li>Generalmente añade @EnableWebMvc, si encuentra spring-webmvc en el classpath </li>
 *  <li>@ComponentScan, le dice a Spring que busque otros componentes a partir de este paquete</li>
 * </ul>
 * @author Ismael
 *
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args){
		SpringApplication.run(Application.class, args);
	}
}
