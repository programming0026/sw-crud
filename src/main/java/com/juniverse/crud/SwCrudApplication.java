package com.juniverse.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** CRUD reactivo de categorías y productos con Spring WebFlux y R2DBC (H2 en memoria), con arquitectura hexagonal. */
@SpringBootApplication
public class SwCrudApplication {

	/**
	 * Arranca la aplicación.
	 * @param args argumentos de la línea de comandos
	 */
	public static void main(String[] args) {
		SpringApplication.run(SwCrudApplication.class, args);
	}

}
