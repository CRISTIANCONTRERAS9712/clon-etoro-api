package com.clon.etoro.infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.clon.etoro.application.usecase.CreateUserUseCase;
import com.clon.etoro.application.usecase.GetAllUserUseCase;
import com.clon.etoro.domain.port.CountryRepositoryPort;
import com.clon.etoro.domain.port.UserRepositoryPort;
import com.clon.etoro.domain.service.UserDomainService;
import com.clon.etoro.infraestructure.adapter.InMemoryCountryRepositoryAdapter;
import com.clon.etoro.infraestructure.adapter.InMemoryUserRepositoryAdapter;

@Configuration
public class BeansConfig {
	
	@Bean
	UserRepositoryPort createUserRepository() {
		return new InMemoryUserRepositoryAdapter();
	}
	
	@Bean
	CountryRepositoryPort createCountryRepository() {
		return new InMemoryCountryRepositoryAdapter();
	}
	
	@Bean
	UserDomainService createUserDomainService(UserRepositoryPort repo, CountryRepositoryPort countryRepo) {
		return new UserDomainService(repo, countryRepo);
	}

	@Bean
    CreateUserUseCase createUserUseCase(UserRepositoryPort repo, UserDomainService userDomainService, CountryRepositoryPort countryRepo) {
        return new CreateUserUseCase(repo, userDomainService, countryRepo);
    }

    @Bean
    GetAllUserUseCase getAllUserUseCase(UserRepositoryPort repo) {
        return new GetAllUserUseCase(repo);
    }
}
