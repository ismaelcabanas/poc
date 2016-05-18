package cabanas.garcia.ismael.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase que inicia la aplicación Spring Boot.
 * 
 * La anotación @SpringBootapplication supone lo siguiente:
 * <ul>
 * 	<li>@Configuration</li>
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
