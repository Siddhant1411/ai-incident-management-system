# 🚀 AI Incident Management System

An **event-driven microservices system** that leverages **Kafka + AI (Ollama LLM)** to automatically classify incidents in real-time.

---

## 🧩 Architecture
Incident Service -> Kafka Topic: incident-created -> AI Analysis Service -> Ollama (LLM - llama3) -> Kafka Topic: incident-classified -> Downstream Consumers

## ⚙️ Tech Stack

- Java 17
- Spring Boot
- Apache Kafka
- Docker (Kafka + Zookeeper)
- Ollama (LLM - llama3)
- REST APIs
- Jackson (JSON parsing)

AI Integration (Ollama)
Model: llama3
Runs locally:
ollama run llama3
Prompt Strategy
You are a strict classifier.
Return ONLY valid JSON.
No explanation.
{ "type": "DB_ERROR|API_ERROR|GENERAL", 
  "priority": "HIGH|MEDIUM|LOW" }



 - Example

Input: database connection error

Output:
type: DB_ERROR
priority: HIGH


📌 Future Enhancements
Retry mechanism (Kafka backoff)
Dead Letter Queue (DLQ)
Circuit Breaker (Resilience4j)
Monitoring (Prometheus + Grafana)


Key Features
Event-driven architecture (Kafka)
AI-powered classification
Microservices-based design
Loose coupling between services
Real-time processing

