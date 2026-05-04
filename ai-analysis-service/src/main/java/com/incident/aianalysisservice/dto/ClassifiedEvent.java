package com.incident.aianalysisservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassifiedEvent {
    private String incidentId;
    private String type;
    private String priority;
}
