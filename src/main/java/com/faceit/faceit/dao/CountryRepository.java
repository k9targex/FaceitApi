package com.faceit.faceit.dao;

import com.faceit.faceit.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Country findCountryByCountryName(String country);
}