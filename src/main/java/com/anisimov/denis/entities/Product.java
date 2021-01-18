package com.anisimov.denis.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "products")
@ApiModel(description = "Класс, представляющий продукт в приложении.")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(notes = "Уникальный идентификатор продукта.", example = "1", required = true, position = 0)
    private Long id;

    @Column(name = "name")
    @ApiModelProperty(notes = "Название продукта.", example = "Milk", required = true, position = 1)
    @Size(min = 3, message = "Имя товара должно содержать минимум 3 символа")
    private String name;

    @Column(name = "description")
    @ApiModelProperty(notes = "Описание продукта.", example = "Молоко", required = true, position = 2)
    private String description;

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
