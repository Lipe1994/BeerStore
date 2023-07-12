package com.hibecode.beerstore.infra.command_handles;


import com.hibecode.beerstore.core.entities.Beer;
import com.hibecode.beerstore.core.enums.BeerType;
import com.hibecode.beerstore.core.exceptions.BusinessException;
import com.hibecode.beerstore.infra.repositories.BeerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeleteBearCommandHandleTest {
    DeleteBearCommandHandle handle;
    @Mock
    private BeerRepository repository;

    @BeforeAll
    public void setup()
    {
        MockitoAnnotations.openMocks(this);
        handle = new DeleteBearCommandHandle(repository);
    }

    @Test
    public void should_retorn_error_if_beer_not_exist()
    {
        when(repository.findById(any())).thenReturn(Optional.empty());
        assertThrowsExactly(BusinessException.class, ()-> handle.handle(1l));
    }

    @Test
    public void should_delete_beer() throws BusinessException {
        final var beer = new Beer();

        beer.setName("Heineken");
        beer.setType(BeerType.LARGER);
        beer.setVolume(new BigDecimal(355));

        when(repository.findById(anyLong())).thenReturn(Optional.of(beer));

        handle.handle(anyLong());
        verify(repository).deleteById(anyLong());

    }
}
