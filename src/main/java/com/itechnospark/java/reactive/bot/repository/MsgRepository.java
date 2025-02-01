package com.itechnospark.java.reactive.bot.repository;

import com.itechnospark.java.reactive.bot.entity.MsgEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface MsgRepository extends ReactiveMongoRepository<MsgEntity, String> {


    Mono<MsgEntity> findByReceiverIdAndSenderId(String s,String r);
}
