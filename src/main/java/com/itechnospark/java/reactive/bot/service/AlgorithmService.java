package com.itechnospark.java.reactive.bot.service;

import com.itechnospark.java.reactive.bot.entity.BotAlgorithmEntity;
import com.itechnospark.java.reactive.bot.model.request.AlgorithmRequest;
import com.itechnospark.java.reactive.bot.repository.BotAlgorithmRepository;
import lombok.extern.slf4j.Slf4j;
import opennlp.tools.tokenize.*;
import opennlp.tools.util.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AlgorithmService {
    @Autowired
    BotAlgorithmRepository botAlgorithmRepository;

    public Flux<BotAlgorithmEntity> feedAlogorithm(List<AlgorithmRequest> request ){
        List<BotAlgorithmEntity> entity=new ArrayList<>();
        request.forEach(req -> {
            BotAlgorithmEntity algorithm=new BotAlgorithmEntity();
            BeanUtils.copyProperties(req,algorithm);
            entity.add(algorithm);
        });


        return  botAlgorithmRepository.saveAll(entity);
    }


    public String teach(String data){

        try {
            InputStream inputStream = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));

            // Create a stream of lines from the InputStream
            ObjectStream<String> lineStream = new PlainTextByLineStream(() -> inputStream, StandardCharsets.UTF_8);

            // Create a stream of TokenSample objects
            ObjectStream<TokenSample> sampleStream = new TokenSampleStream(lineStream);

            // Training parameters
            TrainingParameters params = new TrainingParameters();
            params.put(TrainingParameters.ITERATIONS_PARAM, 3);
            params.put(TrainingParameters.CUTOFF_PARAM, 3);

            // Train tokenizer model
            TokenizerModel model = TokenizerME.train(sampleStream, new TokenizerFactory(), params);

            // Save the trained model
            File modelFile = new File("D:\\token/opennlp-en-ud-ewt-tokens-1.2-2.5.0.bin");
            try (FileOutputStream outputStream = new FileOutputStream(modelFile)) {
                model.serialize(outputStream);
            }

            return "Tokenizer model trained successfully. Saved at: " + modelFile.getAbsolutePath();
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            return "Training failed: " + e.getMessage();
        }
    }

    private File convertToFile(String data) throws IOException {
        File tempFile = File.createTempFile("training-data", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write(data);
        }
        return tempFile;
    }

    private boolean isValidTrainingData(String data) {
        String[] lines = data.split("\n");
        return lines.length > 1 && lines[0].split(" ").length > 1;
    }
}


