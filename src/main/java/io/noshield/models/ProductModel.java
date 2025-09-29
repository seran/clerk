package io.noshield.models;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "products")
public class ProductModel {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    private String name;

    @Getter
    private String description;

    @Getter
    private Boolean active;

    public ProductModel() {}

    public ProductModel(String name, String description, Boolean active) {
        this.name = name;
        this.description = description;
        this.active = active;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

}
