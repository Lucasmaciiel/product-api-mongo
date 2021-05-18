package com.desafiojavaspringboot.uol.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "procuct")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotEmpty(message = "Field name is required")
    private String name;

    @NotEmpty(message = "Field description is required")
    private String description;

    @NotNull(message = "Field price is required")
    @Positive(message = "Field price must be positive")
    private Double price;

    @Override
    public String toString() {
        return "Product [id= " + id + ", Nome= " + name + ", Descrição: " + description + "]";
    }

}