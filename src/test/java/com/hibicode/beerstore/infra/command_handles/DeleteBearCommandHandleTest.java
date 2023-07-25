package com.hibicode.beerstore.infra.command_handles;


import com.hibicode.beerstore.core.entities.Beer;
import com.hibicode.beerstore.core.enums.BeerType;
import com.hibicode.beerstore.core.exceptions.BusinessException;
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
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeleteBearCommandHandleTest {
    DeleteBearCommandHandle handle;
    Beer beer;

    @Mock
    private BeerRepository repository;

    @BeforeAll
    public void setup()
    {
        MockitoAnnotations.openMocks(this);
        handle = new DeleteBearCommandHandle(repository);

        beer = new Beer();
        beer.setId(1l);
        beer.setName("Heineken");
        beer.setType(BeerType.LARGER);
        beer.setVolume(new BigDecimal(355));
    }

    @Test
    public void should_return_error_if_beer_not_exist()
    {
        when(repository.findById(any())).thenReturn(Optional.empty());
        assertThrowsExactly(BusinessException.class, ()-> handle.handle(1l));
    }

    @Test
    public void should_delete_beer() throws BusinessException {
        when(repository.findById(anyLong())).thenReturn(Optional.of(beer));

        handle.handle(anyLong());
        verify(repository).deleteById(anyLong());

    }
}
