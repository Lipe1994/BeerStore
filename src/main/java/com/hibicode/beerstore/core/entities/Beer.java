package com.hibicode.beerstore.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hibicode.beerstore.core.enums.BeerType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity //to jpa
@Data //to lombock
@EqualsAndHashCode(onlyExplicitlyIncluded = true) //to lombok create hash code only where i to set
public class Beer {

    @Id //TO JPA
    @SequenceGenerator(name = "beer_seq", sequenceName = "beer_seq", allocationSize = 1)//To postgres create a sequence to autoincrement id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "beer_seq")
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    @NotBlank(message = "beers-1")
    private String name;

    @NotNull(message = "beers-2")
    private BeerType type;

    @NotNull(message = "beers-3")
    @DecimalMin(value = "0", message = "beers-4")
    private BigDecimal volume;

    @JsonIgnore
    public boolean isNew() {
        return getId() == null;
    }

    @JsonIgnore
    public boolean alreadyExist() {
        return getId() != null;
    }
}
