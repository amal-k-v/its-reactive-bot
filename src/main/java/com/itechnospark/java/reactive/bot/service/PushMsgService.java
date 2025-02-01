package com.itechnospark.java.reactive.bot.service;

import com.itechnospark.java.reactive.bot.entity.MsgBody;
import com.itechnospark.java.reactive.bot.entity.MsgEntity;
import com.itechnospark.java.reactive.bot.repository.MsgRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class PushMsgService {
    @Autowired
    MsgRepository msgRepository;

    public Mono<MsgEntity> sendMsg(String msg, String senderId, String receiverId) {
        return msgRepository.findByReceiverIdAndSenderId(receiverId, senderId)
                .doOnNext(entity -> log.info("Found entity: {}", entity)).
                flatMap(msgEntity -> {
                    List<MsgBody> msgBody = msgEntity.getMsgBody();
                    msgBody.add(MsgBody.builder().msg(msg).sendTime(LocalDateTime.now()).build());
                    msgEntity.setMsgBody(msgBody);
                    return msgRepository.save(msgEntity);

                }).switchIfEmpty(Mono.defer(() -> {
                    MsgEntity newMsg = new MsgEntity();
                    newMsg.setReceiverId(receiverId);
                    newMsg.setSenderId(senderId);
                    newMsg.setMsgBody(Collections.singletonList(MsgBody.builder().msg(msg).sendTime(LocalDateTime.now()).build()));
                    return msgRepository.save(newMsg);
                })).doOnSuccess(savedEntity -> log.info("Message saved: {} ", savedEntity));


    }

}
