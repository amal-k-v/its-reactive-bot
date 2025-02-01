package com.itechnospark.java.reactive.bot.controller;

import com.itechnospark.java.reactive.bot.entity.MsgEntity;
import com.itechnospark.java.reactive.bot.service.PushMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController()
public class MsgController {
    @Autowired
    PushMsgService pushMsgService;
    @PostMapping("/send")
    public Mono<ResponseEntity<MsgEntity>>sendMsg(@RequestBody String msg,
                                                  @RequestHeader String authToken,
                                                  @RequestHeader String receiverId
    ){
       return pushMsgService.sendMsg(msg, authToken, receiverId)
               .map(ResponseEntity::ok)
               .defaultIfEmpty(ResponseEntity.internalServerError().build());
    }



}
