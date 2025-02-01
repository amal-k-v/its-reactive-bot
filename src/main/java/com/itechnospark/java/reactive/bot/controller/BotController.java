package com.itechnospark.java.reactive.bot.controller;

import com.itechnospark.java.reactive.bot.entity.MsgEntity;
import com.itechnospark.java.reactive.bot.model.request.BotRequest;
import com.itechnospark.java.reactive.bot.model.response.BotResponse;
import com.itechnospark.java.reactive.bot.service.BotActionService;
import com.itechnospark.java.reactive.bot.service.PushMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController()
@CrossOrigin(origins = "http://localhost:3000")
public class BotController {
    @Autowired
    BotActionService botActionService;


    @PostMapping("/bot")
    public Mono<ResponseEntity<BotResponse>>botAction(@RequestBody BotRequest request
                                                 ){
        return  botActionService.executeBotAction(request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.internalServerError().build());
    }

}
