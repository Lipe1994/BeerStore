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

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UpdateBearCommandHandleTest {
    UpdateBearCommandHandle handle;
    @Mock
    private BeerRepository repository;

    @BeforeAll
    public void setup()
    {
        MockitoAnnotations.openMocks(this);
        handle = new UpdateBearCommandHandle(repository);
    }

    @Test
    public void should_have_id()
    {
        final var beer = new Beer();
        beer.setName("Heineken");
        beer.setType(BeerType.LARGER);
        beer.setVolume(new BigDecimal(355));

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrowsExactly(BusinessException.class, ()-> handle.handle(beer));
    }

    @Test
    public void should_return_error_if_beer_not_exist()
    {
        final var beer = new Beer();
        beer.setId(1l);
        beer.setName("Heineken");
        beer.setType(BeerType.LARGER);
        beer.setVolume(new BigDecimal(355));

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrowsExactly(BusinessException.class, ()-> handle.handle(beer));
    }

    @Test
    public void should_return_id_when_update_beer() throws BusinessException {
        final var beer = new Beer();
        beer.setId(1l);
        beer.setName("Heineken");
        beer.setType(BeerType.LARGER);
        beer.setVolume(new BigDecimal(355));

        when(repository.findById(anyLong())).thenReturn(Optional.of(beer));
        when(repository.save(any())).thenReturn(beer);

        assertInstanceOf(Long.class, handle.handle(beer));
    }
}
