package com.hibicode.beerstore.api.controllers;

import com.hibicode.beerstore.core.entities.Beer;
import com.hibicode.beerstore.infra.command_handles.CreateBearCommandHandle;
import com.hibicode.beerstore.infra.command_handles.DeleteBearCommandHandle;
import com.hibicode.beerstore.infra.command_handles.UpdateBearCommandHandle;
import com.hibicode.beerstore.infra.command_handles.commands.CreateBeerCommand;
import com.hibicode.beerstore.infra.command_handles.commands.UpdateBeerCommand;
import com.hibicode.beerstore.infra.repositories.BeerRepository;
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
    private CreateBearCommandHandle createBearCommandhandle;
    @Autowired
    private UpdateBearCommandHandle updateBearCommandhandle;
    @Autowired
    private DeleteBearCommandHandle deleteBearCommandhandle;

    @GetMapping
    public List<Beer> beers()
    {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long beer(@RequestBody @Valid CreateBeerCommand command)
    {
        return createBearCommandhandle.handle(command);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Long beer(@PathVariable Long id, @RequestBody @Valid UpdateBeerCommand command)
    {
        return updateBearCommandhandle.handle(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void beer(@PathVariable Long id)
    {
        deleteBearCommandhandle.handle(id);
    }
}
