package com.clerk.register.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class LicenseTest {
    @Test
    void activateSetsActive() {
        License license = new License("ACME-0001-ALICE", false);
        license.activate();

        assertThat(license.getActive()).isTrue();
    }

    @Test
    void deactivateClearsActive() {
        License license = new License("ACME-0001-ALICE", true);

        license.deactivate();

        assertThat(license.getActive()).isFalse();
    }
}
