package io.noshield.models;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "licenses")
public class LicenseModel {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    private String key;

    @Getter
    private Boolean active;

    @OneToOne
    private ProductModel product;

    @Getter
    private Long userId;

    public LicenseModel() {}

    public LicenseModel(String key, Boolean active) {
        this.key = key;
        this.active = active;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }
}

