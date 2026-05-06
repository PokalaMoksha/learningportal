package com.learning.admin.repository;

import com.learning.common.entity.TemporaryAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TemporaryAccessRepository
       extends JpaRepository<TemporaryAccess, Long> {

    List<TemporaryAccess> findByCsmId(Long csmId);

    List<TemporaryAccess> findByIsActive(
                          boolean isActive);
}
