package com.tekcapsule.capsule.application.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Component
public final class AppConfig {

    @Value("#{environment.APPLICATION_ENVIRONMENT}")
    private String stage;
}
