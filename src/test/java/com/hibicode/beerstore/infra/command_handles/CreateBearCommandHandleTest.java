package com.hibicode.beerstore.infra.command_handles;


import com.hibicode.beerstore.core.entities.Beer;
import com.hibicode.beerstore.core.enums.BeerType;
import com.hibicode.beerstore.core.exceptions.BusinessException;
import com.hibicode.beerstore.infra.command_handles.commands.CreateBeerCommand;
import com.hibicode.beerstore.infra.repositories.BeerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreateBearCommandHandleTest {
    CreateBearCommandHandle handle;
    Beer beer;
    @Mock
    private BeerRepository repository;

    @BeforeAll
    public void setup()
    {
        MockitoAnnotations.openMocks(this);
        handle = new CreateBearCommandHandle(repository);

        beer = new Beer();
        beer.setId(1l);
        beer.setName("Heineken");
        beer.setType(BeerType.LARGER);
        beer.setVolume(new BigDecimal(355));
    }

    @Test
    public void should_deny_creation_of_beer_that_exists()
    {
        final var command = new CreateBeerCommand();
        command.setName("Heineken");
        command.setType(BeerType.LARGER);
        command.setVolume(new BigDecimal(355));

        when(repository.findByNameAndType(any(), any())).thenReturn(Optional.of(beer));

        assertThrowsExactly(BusinessException.class, ()-> handle.handle(command));
    }

    @Test
    public void should_return_id_when_create_beer() throws BusinessException {
        final var command = new CreateBeerCommand();

        command.setName("Heineken");
        command.setType(BeerType.LARGER);
        command.setVolume(new BigDecimal(355));

        when(repository.findByNameAndType(any(), any())).thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(beer);

        assertInstanceOf(Long.class, handle.handle(command));
    }
}
