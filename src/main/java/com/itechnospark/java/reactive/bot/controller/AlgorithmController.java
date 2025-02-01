package com.itechnospark.java.reactive.bot.controller;

import com.itechnospark.java.reactive.bot.entity.BotAlgorithmEntity;
import com.itechnospark.java.reactive.bot.model.request.AlgorithmRequest;
import com.itechnospark.java.reactive.bot.model.request.BotRequest;
import com.itechnospark.java.reactive.bot.model.response.BotResponse;
import com.itechnospark.java.reactive.bot.service.AlgorithmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController()
public class AlgorithmController {
    @Autowired
    AlgorithmService algorithmService;
    @PostMapping("/feed/algorithm")
    public Flux<BotAlgorithmEntity> botAction(@RequestBody List<AlgorithmRequest> request
    ){
        return  algorithmService.feedAlogorithm(request);

    }

    @PostMapping("/train")
    public String teach(@RequestBody String data
    ){
        return  algorithmService.teach(data);

    }
}
