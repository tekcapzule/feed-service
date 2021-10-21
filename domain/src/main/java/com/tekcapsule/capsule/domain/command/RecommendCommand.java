package com.tekcapsule.capsule.domain.command;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapsule.core.domain.Command;
import lombok.Builder;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class RecommendCommand extends Command {
    private String capsuleId;
}