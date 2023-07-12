package com.hibecode.beerstore.infra.command_handles.commands;

import com.hibecode.beerstore.core.enums.BeerType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CreateBeerCommand {
        @NotNull(message = "beers-0")
        private Long id;

        @NotNull
        @NotBlank(message = "beers-1")
        private String name;

        @NotNull(message = "beers-2")
        private BeerType type;

        @NotNull(message = "beers-3")
        @DecimalMin(value = "0", message = "beers-4")
        private BigDecimal volume;
}
