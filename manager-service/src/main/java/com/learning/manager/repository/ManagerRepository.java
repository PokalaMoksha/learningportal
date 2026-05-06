package com.learning.manager.repository;

import com.learning.common.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ManagerRepository
       extends JpaRepository<Manager, Long> {

    Optional<Manager> findByUserId(Long userId);

    List<Manager> findByCsmId(Long csmId);

    boolean existsByIdAndCsmId(
            Long id, Long csmId);
}