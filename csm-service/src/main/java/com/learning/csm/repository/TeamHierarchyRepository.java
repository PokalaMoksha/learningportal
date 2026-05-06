package com.learning.csm.repository;

import com.learning.common.entity.TeamHierarchy;
import org.springframework.data.jpa.repository
       .JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeamHierarchyRepository
       extends JpaRepository
               <TeamHierarchy, Long> {

    List<TeamHierarchy> findByCsmId(
                        Long csmId);

    List<TeamHierarchy> findByManagerId(
                        Long managerId);

    Optional<TeamHierarchy> findByEmployeeId(
                            Long employeeId);

    boolean existsByCsmIdAndManagerId(
            Long csmId, Long managerId);

    boolean existsByCsmIdAndEmployeeId(
            Long csmId, Long employeeId);

    boolean existsByManagerIdAndEmployeeId(
            Long managerId, Long employeeId);
}
