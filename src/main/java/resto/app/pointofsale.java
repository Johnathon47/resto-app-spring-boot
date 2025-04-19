package resto.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"components"})
public class pointofsale {

	public static void main(String[] args) {
		SpringApplication.run(pointofsale.class, args);
	}

}
