package uz.adevs.mediahub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import uz.adevs.mediahub.model.AuditLog;
import uz.adevs.mediahub.model.User;
import uz.adevs.mediahub.repository.AuditLogRepository;
import uz.adevs.mediahub.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final UserRepository userRepository;

    @Async
    public void log(Long userId, String action, String objectType, Long objectId, String oldVal, String newVal) {
        User user = userId != null ? userRepository.findById(userId).orElse(null) : null;
        AuditLog log = AuditLog.builder()
                .user(user).action(action).objectType(objectType)
                .objectId(objectId).oldValue(oldVal).newValue(newVal).build();
        auditLogRepository.save(log);
    }
}
