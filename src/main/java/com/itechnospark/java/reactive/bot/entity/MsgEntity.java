package com.itechnospark.java.reactive.bot.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "chat_msg")
public class MsgEntity {
    @Id
    private String id;
    private List<MsgBody> msgBody;
    private String receiverId;
    private String senderId;
}
