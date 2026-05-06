package com.learning.notification.repository;

import com.learning.common.entity.NotificationLog;
import com.learning.common.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationLogRepository
       extends JpaRepository<NotificationLog, Long> {

    List<NotificationLog> findByStatus(
                          NotificationStatus status);

    List<NotificationLog> findByEmployeeId(
                          Long employeeId);
}
