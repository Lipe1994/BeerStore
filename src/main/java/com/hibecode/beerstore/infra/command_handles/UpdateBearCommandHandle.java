package com.hibecode.beerstore.infra.command_handles;

import com.hibecode.beerstore.core.entities.Beer;
import com.hibecode.beerstore.core.exceptions.BusinessException;
import com.hibecode.beerstore.infra.repositories.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateBearCommandHandle {
    BeerRepository repository;

    UpdateBearCommandHandle(@Autowired BeerRepository repository)
    {
        this.repository = repository;
    }
    public Long handle(final Beer beer) throws BusinessException {

        if(beer.getId() == null)
        {
            throw new BusinessException("To update beer should have id.");
        }

        final var existsBeer = repository.findById(beer.getId());
        if(existsBeer.isEmpty())
        {
            throw new BusinessException("Beer not exists.");
        }

        return repository.save(beer).getId();
    }
}
