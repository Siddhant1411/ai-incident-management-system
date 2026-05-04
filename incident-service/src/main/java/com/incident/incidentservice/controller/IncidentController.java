package com.incident.incidentservice.controller;


import com.incident.incidentservice.dto.IncidentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/incidents")
@RequiredArgsConstructor
public class IncidentController {

    private final KafkaTemplate<String,IncidentEvent> kafkaTemplate;

    @PostMapping
    public String createIncident(@RequestBody IncidentEvent event){
        kafkaTemplate.send("incident-created" ,event);
        return  "Incident even published";
    }
}
