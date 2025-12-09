package com.clon.etoro.infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.clon.etoro.application.usecase.CreateUserUseCase;
import com.clon.etoro.application.usecase.GetAllUserUseCase;
import com.clon.etoro.domain.port.CountryRepository;
import com.clon.etoro.domain.port.UserRepository;
import com.clon.etoro.domain.service.UserDomainService;
import com.clon.etoro.infraestructure.adapter.InMemoryCountryRepositoryAdapter;
import com.clon.etoro.infraestructure.adapter.InMemoryUserRepositoryAdapter;
import com.clon.etoro.infraestructure.adapter.InMemoryUserRepositoryAdapter2;

@Configuration
public class BeansConfig {
	
	@Bean
	UserRepository createUserRepository() {
		return new InMemoryUserRepositoryAdapter2();
	}
	
	@Bean
	CountryRepository createCountryRepository() {
		return new InMemoryCountryRepositoryAdapter();
	}
	
	@Bean
	UserDomainService createUserDomainService(UserRepository repo) {
		return new UserDomainService(repo);
	}

	@Bean
    CreateUserUseCase createUserUseCase(UserRepository repo, UserDomainService userDomainService, CountryRepository countryRepo) {
        return new CreateUserUseCase(repo, userDomainService, countryRepo);
    }

    @Bean
    GetAllUserUseCase getAllUserUseCase(UserRepository repo) {
        return new GetAllUserUseCase(repo);
    }
}
