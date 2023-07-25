package com.hibicode.beerstore.infra.repositories;

import com.hibicode.beerstore.core.entities.Beer;
import com.hibicode.beerstore.core.enums.BeerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BeerRepository extends JpaRepository<Beer, Long> {

    Optional<Beer> findByNameAndType(String name, BeerType type);
}
