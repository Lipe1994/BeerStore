package com.hibicode.beerstore.infra.command_handles;

import com.hibicode.beerstore.core.exceptions.BusinessException;
import com.hibicode.beerstore.infra.command_handles.commands.UpdateBeerCommand;
import com.hibicode.beerstore.infra.repositories.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateBearCommandHandle {
    BeerRepository repository;

    UpdateBearCommandHandle(@Autowired BeerRepository repository)
    {
        this.repository = repository;
    }
    public Long handle(final Long id, final UpdateBeerCommand command) throws BusinessException {

        if(id == null || id == 0)
        {
            throw new BusinessException("To update beer should have id.");
        }

        final var existsBeer = repository.findById(id);
        if(existsBeer.isEmpty())
        {
            throw new BusinessException("Beer not exists.");
        }

        existsBeer.get().setName(command.getName());
        existsBeer.get().setType(command.getType());
        existsBeer.get().setVolume(command.getVolume());

        return repository.save(existsBeer.get()).getId();
    }
}
