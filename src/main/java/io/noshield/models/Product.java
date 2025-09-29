package io.noshield.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    private String name;

    @Getter
    private String description;

    @Getter
    @Setter
    private String imageURL;

    @Getter
    private Boolean active;

    @OneToMany(mappedBy = "product")
    private List<License>  licenses;

    public Product() {}

    public Product(String name, String description, Boolean active) {
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
