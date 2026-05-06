package com.learning.certificate.repository;

import com.learning.common.entity.LearningCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LearningCertificateRepository
       extends JpaRepository
               <LearningCertificate, Long> {

    List<LearningCertificate> findByEmployeeId(
                              Long employeeId);
}
