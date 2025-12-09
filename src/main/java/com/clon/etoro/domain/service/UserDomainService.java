package com.clon.etoro.domain.service;

import java.time.LocalDate;
import java.time.Period;

import com.clon.etoro.domain.model.User;
import com.clon.etoro.domain.port.UserRepository;

public class UserDomainService {
    private final UserRepository userRepository;
//    private final CountryRepository countryRepository;

    public UserDomainService(
    		UserRepository u
//    		CountryRepository c
    		) {
        this.userRepository = u;
//        this.countryRepository = c;
    }

    public void validateUserCreation(User user) {
        if (userRepository.existsByEmail(user.getEmail()))
            throw new RuntimeException("Email exists");
        int years = Period.between(user.getBirthdate(), LocalDate.now()).getYears();
        if (years < 18)
            throw new RuntimeException("Underage");
        if (!user.getCountry().getActive())
			throw new RuntimeException("Country no activo");
            //throw new CountryNotActiveException(country);
    }
}