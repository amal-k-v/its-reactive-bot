package com.itechnospark.java.reactive.bot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "bot_algorithm")
public class BotAlgorithmEntity {
    @Id
    private String id;
    private String command;
    private String matchReply;
}
