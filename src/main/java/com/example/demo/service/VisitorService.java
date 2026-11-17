package com.example.demo.service;

import com.example.demo.model.VisitorLog;
import com.example.demo.repository.VisitorRepository;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

@Service
public class VisitorService {

    private final VisitorRepository visitorRepository;
    private final ObjectMapper objectMapper;

    public VisitorService(VisitorRepository visitorRepository, ObjectMapper objectMapper) {
        this.visitorRepository = visitorRepository;
        this.objectMapper = objectMapper;
    }

    // MQTT'den gelen JSON verisini işleyip veritabanına kaydeden metod
//    public void processMqttMessage(String payload) {
//        try {
//            VisitorLogDto dto = objectMapper.readValue(payload, VisitorLogDto.class);
//
//            VisitorLog log = new VisitorLog();
//            log.setStoreId(dto.getStoreId());
//            log.setPeopleCount(dto.getPeopleCount());
//            log.setTimestamp(dto.getTimestamp() != null ? dto.getTimestamp() : LocalDateTime.now());
//
//            visitorRepository.save(log);
//            System.out.println("Ziyaretçi verisi MQTT'den alındı ve kaydedildi! Mağaza ID: " + dto.getStoreId());
//
//        } catch (Exception e) {
//            System.err.println("MQTT mesajı işlenirken hata oluştu: " + e.getMessage());
//        }
//    }

    public void processMqttMessage(String payload) {

        System.out.println("GELEN HAM MQTT MESAJI: [" + payload + "]");

        try {
            VisitorLogDto dto =
                    objectMapper.readValue(payload, VisitorLogDto.class);

            System.out.println("JSON BAŞARIYLA OKUNDU!");
            System.out.println("Store ID: " + dto.getStoreId());
            System.out.println("People Count: " + dto.getPeopleCount());

            VisitorLog log = new VisitorLog();

            log.setStoreId(dto.getStoreId());
            log.setPeopleCount(dto.getPeopleCount());

            log.setTimestamp(
                    dto.getTimestamp() != null
                            ? dto.getTimestamp()
                            : LocalDateTime.now()
            );

            visitorRepository.save(log);

            System.out.println("VERİTABANINA KAYIT BAŞARILI!");

        } catch (Exception e) {
            System.err.println("MQTT MESAJI İŞLENİRKEN HATA:");
            e.printStackTrace();
        }
    }

    // Gelen JSON'ı karşılayacak DTO sınıfı
    public static class VisitorLogDto {
        private Long storeId;
        private int peopleCount;
        private LocalDateTime timestamp;

        public Long getStoreId() { return storeId; }
        public void setStoreId(Long storeId) { this.storeId = storeId; }
        public int getPeopleCount() { return peopleCount; }
        public void setPeopleCount(int peopleCount) { this.peopleCount = peopleCount; }
        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    }
}
