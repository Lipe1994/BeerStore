package com.hibicode.beerstore.infra.command_handles;

import com.hibicode.beerstore.core.entities.Beer;
import com.hibicode.beerstore.core.exceptions.BusinessException;
import com.hibicode.beerstore.infra.command_handles.commands.CreateBeerCommand;
import com.hibicode.beerstore.infra.repositories.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateBearCommandHandle {
    BeerRepository repository;

    CreateBearCommandHandle(@Autowired BeerRepository repository)
    {
        this.repository = repository;
    }
    public Long handle(final CreateBeerCommand command) throws BusinessException {

        final var existsBeer = repository.findByNameAndType(command.getName(), command.getType());
        if(existsBeer.isPresent())
        {
            throw new BusinessException("Beer alread exists");
        }

        final var beer = new Beer();
        beer.setId(command.getId());
        beer.setName(command.getName());
        beer.setType(command.getType());

        return repository.save(beer).getId();
    }
}
