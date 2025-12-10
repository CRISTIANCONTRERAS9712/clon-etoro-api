package com.clon.etoro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.core.publisher.Hooks;

@SpringBootApplication
public class ClonEtoroApiApplication {

	public static void main(String[] args) {
		//Activa el modo debug completo de Reactor
		Hooks.onOperatorDebug();
		SpringApplication.run(ClonEtoroApiApplication.class, args);
	}

}
