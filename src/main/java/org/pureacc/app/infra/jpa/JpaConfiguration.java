package org.pureacc.app.infra.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "org.pureacc.app.infra.jpa.model")
@EnableJpaRepositories(basePackages = "org.pureacc.app.infra.jpa.dao")
@Configuration
class JpaConfiguration {
}
