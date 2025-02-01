package com.itechnospark.java.reactive.bot.repository;

import com.itechnospark.java.reactive.bot.entity.BotAlgorithmEntity;
import com.itechnospark.java.reactive.bot.entity.MsgEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface BotAlgorithmRepository extends ReactiveMongoRepository<BotAlgorithmEntity, String> {


}
