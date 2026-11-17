package com.example.demo.repository;

import com.example.demo.model.VisitorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VisitorRepository extends JpaRepository<VisitorLog, Long> {

    @Query("SELECT SUM(v.peopleCount) FROM VisitorLog v WHERE v.storeId = :storeId")
    Long getTotalVisitorsByStore(@Param("storeId") Long storeId);
}
