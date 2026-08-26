package com.clerk.register.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "products")
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String name;

    private String description;

    private String imageURL;

    private Boolean active;

    private Long ownerId;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @Setter(lombok.AccessLevel.NONE)
    private List<License>  licenses;

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
