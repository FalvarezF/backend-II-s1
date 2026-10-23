package com.petsalud.backendpetsalud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class BackendpetsaludApplication {

	public static void main(String[] args) {

		// Carga las variables del .env en las propiedades del sistema
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });


		SpringApplication.run(BackendpetsaludApplication.class, args);
	}

}
