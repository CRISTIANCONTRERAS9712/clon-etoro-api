package com.clon.etoro.infraestructure.config;

import com.clon.etoro.application.usecase.*;
import com.clon.etoro.domain.model.User;
import com.clon.etoro.infraestructure.adapter.*;
import com.clon.etoro.infraestructure.repository.SpringDataPositionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.clon.etoro.domain.port.AssetRepositoryPort;
import com.clon.etoro.domain.port.CountryRepositoryPort;
import com.clon.etoro.domain.port.UserRepositoryPort;
import com.clon.etoro.domain.port.PositionRepositoryPort;
import com.clon.etoro.domain.service.UserDomainService;
import com.clon.etoro.infraestructure.repository.SpringDataAssetRepository;
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
    AssetRepositoryPort createAssetRepository(SpringDataAssetRepository springRepo) {
        // return new InMemoryAssetRepositoryAdapter();
        return new PostgresAssetRepositoryAdapter(springRepo);
    }

    @Bean
    PositionRepositoryPort createPositionRepository(SpringDataPositionRepository springRepo) {
        // return new InMemoryPositionRepositoryAdapter();
        return new PostgresPositionRepositoryAdapter(springRepo);
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

    @Bean
    CreateAssetUseCase createAssetUseCase(AssetRepositoryPort repo) {
        return new CreateAssetUseCase(repo);
    }

    @Bean
    UpdateAssetUseCase updateAssetUseCase(AssetRepositoryPort repo) {
        return new UpdateAssetUseCase(repo);
    }

    @Bean
    DeleteAssetUseCase deleteAssetUseCase(AssetRepositoryPort repo) {
        return new DeleteAssetUseCase(repo);
    }

    @Bean
    GetByIdAssetUseCase getByIdAssetUseCase(AssetRepositoryPort repo) {
        return new GetByIdAssetUseCase(repo);
    }

    @Bean
    GetAllAssetUseCase getAllAssetUseCase(AssetRepositoryPort repo) {
        return new GetAllAssetUseCase(repo);
    }

    @Bean
    CreatePositionUseCase createPositionUseCase(
            PositionRepositoryPort positionRepositoryPort, UserRepositoryPort userRepositoryPort, AssetRepositoryPort assetRepositoryPort) {
        return new CreatePositionUseCase(positionRepositoryPort, userRepositoryPort, assetRepositoryPort);
    }

    @Bean
    UpdatePositionUseCase updatePositionUseCase(
            PositionRepositoryPort positionRepositoryPort, UserRepositoryPort userRepositoryPort, AssetRepositoryPort assetRepositoryPort) {
        return new UpdatePositionUseCase(positionRepositoryPort, userRepositoryPort, assetRepositoryPort);
    }

    @Bean
    GetAllPositionUseCase getAllPositionUseCase(
            PositionRepositoryPort positionRepositoryPort) {
        return new GetAllPositionUseCase(positionRepositoryPort);
    }

    @Bean
    GetByIdPositionUseCase getByIdPositionUseCase(
            PositionRepositoryPort positionRepositoryPort) {
        return new GetByIdPositionUseCase(positionRepositoryPort);
    }

    @Bean
    DeletePositionUseCase deletePositionUseCase(
            PositionRepositoryPort positionRepositoryPort) {
        return new DeletePositionUseCase(positionRepositoryPort);
    }
}