IoT Mağaza Ziyaretçi Takip Sistemi

Spring Boot, Eclipse Mosquitto (MQTT) ve PostgreSQL kullanılarak geliştirilmiş hafif, olay tabanlı (event-driven) bir IoT backend servisidir.


Mimari ve Neden MQTT?

Endüstriyel ortamlarda ve saha cihazlarında yalnızca geleneksel REST API'lerine güvenmek, ağ kesintileri sırasında veri kaybına yol açabilir. Bu projede MQTT Pub/Sub mimarisi tercih edilmiştir:
	Saha Cihazı Simülasyonu: IoT sensörleri veya saha cihazları, ziyaretçi telemetri verilerini bir MQTT broker'a yayınlar.
	Güvenilirlik: MQTT, düşük bant genişliğine sahip ortamlarda kararlı mesaj iletimi sağlar ve edge senaryolarını destekler.
	Backend İşleme: Spring Boot servisi merkezi bir abone (subscriber) olarak görev yapar; MQTT mesajlarını asenkron olarak yakalar ve PostgreSQL veritabanına kalıcı olarak kaydeder.

Kullanılan Teknolojiler

Backend: Java 21, Spring Boot 4, Spring Integration / MQTT, Spring Data JPA
Veritabanı: PostgreSQL
Mesajlaşma / IoT: Eclipse Mosquitto (MQTT Broker)
Konteynerleştirme:Docker & Docker Compose
Derleme Aracı: Maven

Hızlı Başlangıç ve Test Adımları

1) MQTT Broker'ı Docker Compose ile Başlatın:
   docker-compose up -d
   
2) Spring Boot Uygulamasını Çalıştırın
   
3)Bir IoT Sensör Mesajı Simüle Edin (MQTT ile Yayınla):
   
$json = '{"storeId":2,"peopleCount":10}'
[System.IO.File]::WriteAllText("$PWD\message.json", $json, [System.Text.UTF8Encoding]::new($false))
docker cp .\message.json mqtt_broker:/tmp/message.json
docker exec mqtt_broker mosquitto_pub -t "store/visitors" -f /tmp/message.json

REST API Üzerinden Doğrulayın: GET http://localhost:8080/total/{storeId}
