package com.hibecode.beerstore.infra.command_handles;

import com.hibecode.beerstore.core.entities.Beer;
import com.hibecode.beerstore.core.exceptions.BusinessException;
import com.hibecode.beerstore.infra.repositories.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteBearCommandHandle {
    BeerRepository repository;

    DeleteBearCommandHandle(@Autowired BeerRepository repository)
    {
        this.repository = repository;
    }
    public void handle(final Long id) throws BusinessException {

        final var existsBeer = repository.findById(id);
        if(existsBeer.isEmpty())
        {
            throw new BusinessException("Beer not exists");
        }

        repository.deleteById(id);
    }
}
