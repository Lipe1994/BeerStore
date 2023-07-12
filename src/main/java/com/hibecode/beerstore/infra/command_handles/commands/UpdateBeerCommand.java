package com.hibecode.beerstore.infra.command_handles.commands;

import com.hibecode.beerstore.core.enums.BeerType;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class UpdateBeerCommand {
        @NotNull
        @NotBlank(message = "beers-1")
        private String name;

        @NotNull(message = "beers-2")
        private BeerType type;

        @NotNull(message = "beers-3")
        @DecimalMin(value = "0", message = "beers-4")
        private BigDecimal volume;
}
