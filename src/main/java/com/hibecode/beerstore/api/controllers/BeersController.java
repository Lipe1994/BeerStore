package com.hibecode.beerstore.api.controllers;

import com.hibecode.beerstore.core.entities.Beer;
import com.hibecode.beerstore.core.exceptions.BusinessException;
import com.hibecode.beerstore.infra.command_handles.CreateBearCommandHandle;
import com.hibecode.beerstore.infra.command_handles.DeleteBearCommandHandle;
import com.hibecode.beerstore.infra.command_handles.UpdateBearCommandHandle;
import com.hibecode.beerstore.infra.repositories.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("beers")
public class BeersController {

    @Autowired
    private BeerRepository repository;
    @Autowired
    CreateBearCommandHandle createBearCommandhandle;
    @Autowired
    UpdateBearCommandHandle updateBearCommandhandle;
    @Autowired
    DeleteBearCommandHandle deleteBearCommandhandle;

    @GetMapping
    public List<Beer> beers()
    {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long beer(@RequestBody @Valid  Beer beer)
    {
        return createBearCommandhandle.handle(beer);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Long beer(@PathVariable Long id, @RequestBody @Valid  Beer beer)
    {
        if (id != beer.getId()) {
            throw new BusinessException("Id of resource is inconsistent from body of request");
        }

        return updateBearCommandhandle.handle(beer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void beer(@PathVariable Long id)
    {
        deleteBearCommandhandle.handle(id);
    }
}
