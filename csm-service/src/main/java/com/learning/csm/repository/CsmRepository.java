package com.learning.csm.repository;

import com.learning.common.entity.Csm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CsmRepository
       extends JpaRepository<Csm, Long> {

    Optional<Csm> findByUserId(Long userId);

    List<Csm> findAll();
}
