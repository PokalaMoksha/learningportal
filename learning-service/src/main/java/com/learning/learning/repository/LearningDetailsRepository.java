package com.learning.learning.repository;

import com.learning.common.entity.LearningDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LearningDetailsRepository
       extends JpaRepository<LearningDetails, Long> {

    List<LearningDetails> findByEmployeeId(
                          Long employeeId);

    Optional<LearningDetails>
            findFirstByEmployeeId(Long employeeId);
}
