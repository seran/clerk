package io.noshield.repositories;

import io.noshield.models.LicenseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseRepository extends JpaRepository<LicenseModel, Long> {
}
