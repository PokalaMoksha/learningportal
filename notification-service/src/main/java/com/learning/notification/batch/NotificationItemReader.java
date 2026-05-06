package com.learning.notification.batch;

import com.learning.common.entity.NotificationLog;
import com.learning.common.enums.NotificationStatus;
import com.learning.notification.repository
       .NotificationLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationItemReader
       implements ItemReader<NotificationLog> {

    private final NotificationLogRepository
                  notificationLogRepository;

    private Iterator<NotificationLog> iterator;

    @Override
    public NotificationLog read() {

        if (iterator == null) {
            List<NotificationLog> logs =
                notificationLogRepository
                    .findByStatus(
                     NotificationStatus.PENDING);
            iterator = logs.iterator();
            log.info("Found {} pending " +
                     "notifications",
                     logs.size());
        }

        if (iterator.hasNext()) {
            return iterator.next();
        }

        iterator = null;
        return null;
    }
}
