package com.hibecode.beerstore.infra.command_handles;

import com.hibecode.beerstore.core.entities.Beer;
import com.hibecode.beerstore.core.exceptions.BusinessException;
import com.hibecode.beerstore.infra.repositories.BeerRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateBearCommandHandle {
    BeerRepository repository;

    CreateBearCommandHandle(@Autowired BeerRepository repository)
    {
        this.repository = repository;
    }
    public Long handle(final Beer beer) throws BusinessException {

        final var existsBeer = repository.findByNameAndType(beer.getName(), beer.getType());
        if(existsBeer.isPresent())
        {
            throw new BusinessException("Beer alread exists");
        }

        return repository.save(beer).getId();
    }
}
