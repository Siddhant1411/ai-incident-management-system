package com.incident.aianalysisservice.consumer;
import com.incident.aianalysisservice.dto.ClassifiedEvent;
import com.incident.aianalysisservice.dto.IncidentEvent;
import com.incident.aianalysisservice.service.OllamaClient;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class IncidentConsumer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final OllamaClient ollamaClient;


    @KafkaListener(topics = "incident-created", groupId = "ai-group")
    public void consume(IncidentEvent event) {

        System.out.println("🔥 Received event: " + event);

        Map<String, String> aiResult = ollamaClient.analyze(event.getDescription());

        String type = aiResult.get("type");
        String priority = aiResult.get("priority");
        ClassifiedEvent classified = new ClassifiedEvent(
                event.getIncidentId(),
                type,
                priority
        );

        kafkaTemplate.send("incident-classified", classified);

        System.out.println("✅ Published classified event: " + classified);
    }
}