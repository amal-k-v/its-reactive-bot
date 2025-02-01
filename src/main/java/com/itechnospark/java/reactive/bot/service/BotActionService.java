package com.itechnospark.java.reactive.bot.service;

import com.itechnospark.java.reactive.bot.model.request.BotRequest;
import com.itechnospark.java.reactive.bot.model.response.BotResponse;
import com.itechnospark.java.reactive.bot.repository.BotAlgorithmRepository;
import lombok.extern.slf4j.Slf4j;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@Service
@Slf4j
public class BotActionService {
    @Autowired
    BotAlgorithmRepository algorithmRepository;

    public Mono<BotResponse> executeBotAction(BotRequest request){
        log.info("querying for {}", request.getMessage());

        // Load the OpenNLP tokenizer model
        try (InputStream modelIn = new FileInputStream("D:\\token/opennlp-en-ud-ewt-tokens-1.2-2.5.0.bin")) {
            TokenizerModel model = new TokenizerModel(modelIn);
            Tokenizer tokenizer = new TokenizerME(model);

            // Tokenize the input message
            String[] tokens = tokenizer.tokenize(request.getMessage());
            log.info("tokens {}", Arrays.toString(tokens));

            return algorithmRepository.findAll()
                    .filter(al -> Arrays.stream(tokens)
                            .anyMatch(s -> al.getCommand().toLowerCase().contains(s.toLowerCase()))
                    )
                    .map(al -> BotResponse.builder().reply(al.getMatchReply()).build())
                    .next()
                    .switchIfEmpty(Mono.just(BotResponse.builder().reply("Sorry, this is out of my scope").build()));

        } catch (IOException e) {
            log.error("Failed to load OpenNLP tokenizer model", e);
            return Mono.just(BotResponse.builder().reply("An error occurred while processing your request").build());
        }


    }

}
