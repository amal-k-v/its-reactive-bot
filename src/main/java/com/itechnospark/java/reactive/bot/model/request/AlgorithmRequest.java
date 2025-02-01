package com.itechnospark.java.reactive.bot.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlgorithmRequest {
    private String command;
    private String matchReply;
}
