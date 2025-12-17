package com.clon.etoro.infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.clon.etoro.application.usecase.CreateCountryUseCase;
import com.clon.etoro.application.usecase.CreateUserUseCase;
import com.clon.etoro.application.usecase.DeleteCountryUseCase;
import com.clon.etoro.application.usecase.GetAllUserUseCase;
import com.clon.etoro.application.usecase.GetByIdCountryUseCase;
import com.clon.etoro.application.usecase.GetByIdUserUseCase;
import com.clon.etoro.application.usecase.UpdateCountryUseCase;
import com.clon.etoro.application.usecase.UpdateUserUseCase;
import com.clon.etoro.application.usecase.DeleteUserUseCase;
import com.clon.etoro.application.usecase.GetAllCountryUseCase;
import com.clon.etoro.domain.port.CountryRepositoryPort;
import com.clon.etoro.domain.port.UserRepositoryPort;
import com.clon.etoro.domain.service.UserDomainService;
import com.clon.etoro.infraestructure.adapter.InMemoryCountryRepositoryAdapter;
import com.clon.etoro.infraestructure.adapter.InMemoryUserRepositoryAdapter;
import com.clon.etoro.infraestructure.adapter.PostgresCountryRepositoryAdapter;
import com.clon.etoro.infraestructure.adapter.PostgresUserRepositoryAdapter;
import com.clon.etoro.infraestructure.repository.SpringDataCountryRepository;
import com.clon.etoro.infraestructure.repository.SpringDataUserRepository;

@Configuration
public class BeansConfig {

    @Bean
    UserRepositoryPort createUserRepository(SpringDataUserRepository springRepo) {
        // return new InMemoryUserRepositoryAdapter();
        return new PostgresUserRepositoryAdapter(springRepo);
    }

    @Bean
    CountryRepositoryPort createCountryRepository(SpringDataCountryRepository springRepo) {
        // return new InMemoryCountryRepositoryAdapter();
        return new PostgresCountryRepositoryAdapter(springRepo);
    }

    @Bean
    UserDomainService createUserDomainService(UserRepositoryPort repo, CountryRepositoryPort countryRepo) {
        return new UserDomainService(repo, countryRepo);
    }

    @Bean
    CreateUserUseCase createUserUseCase(UserRepositoryPort repo, UserDomainService userDomainService,
            CountryRepositoryPort countryRepo) {
        return new CreateUserUseCase(repo, userDomainService, countryRepo);
    }

    @Bean
    GetByIdUserUseCase getByIdUserUseCase(UserRepositoryPort repo, CountryRepositoryPort countryRepo) {
        return new GetByIdUserUseCase(repo, countryRepo);
    }

    @Bean
    GetAllUserUseCase getAllUserUseCase(UserRepositoryPort repo, CountryRepositoryPort countryRepo) {
        return new GetAllUserUseCase(repo, countryRepo);
    }

    @Bean
    UpdateUserUseCase updateUserUseCase(UserRepositoryPort repo, CountryRepositoryPort countryRepo,
            UserDomainService userDomainService) {
        return new UpdateUserUseCase(repo, countryRepo, userDomainService);
    }

    @Bean
    DeleteUserUseCase deleteUserUseCase(UserRepositoryPort repo) {
        return new DeleteUserUseCase(repo);
    }

    @Bean
    CreateCountryUseCase createCountryUseCase(CountryRepositoryPort repo) {
        return new CreateCountryUseCase(repo);
    }

    @Bean
    UpdateCountryUseCase updateCountryUseCase(CountryRepositoryPort repo) {
        return new UpdateCountryUseCase(repo);
    }

    @Bean
    DeleteCountryUseCase deleteCountryUseCase(CountryRepositoryPort repo) {
        return new DeleteCountryUseCase(repo);
    }

    @Bean
    GetByIdCountryUseCase getByIdCountryUseCase(CountryRepositoryPort repo) {
        return new GetByIdCountryUseCase(repo);
    }

    @Bean
    GetAllCountryUseCase getAllCountryUseCase(CountryRepositoryPort repo) {
        return new GetAllCountryUseCase(repo);
    }
}
