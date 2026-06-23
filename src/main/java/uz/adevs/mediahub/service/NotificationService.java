package uz.adevs.mediahub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.adevs.mediahub.model.Notification;
import uz.adevs.mediahub.model.User;
import uz.adevs.mediahub.repository.NotificationRepository;
import uz.adevs.mediahub.repository.UserRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public void notifyUser(Long userId, String type, String title, String message, String module, Long relatedId) {
        userRepository.findById(userId).ifPresent(user -> {
            Notification n = Notification.builder()
                    .user(user).notifType(type).title(title).message(message)
                    .relatedModule(module).relatedId(relatedId).build();
            notificationRepository.save(n);
        });
    }

    public void notifyOrg(Long orgId, String type, String title, String message, String module, Long relatedId) {
        List<User> users = userRepository.findByTashkilotIdAndStatus(orgId, uz.adevs.mediahub.constants.UserStatus.ACTIVE);
        users.forEach(u -> notifyUser(u.getId(), type, title, message, module, relatedId));
    }

    public List<Notification> getByUser(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public long countUnread(Long userId) {
        return notificationRepository.countByUserIdAndIsReadFalse(userId);
    }

    public void markRead(Long id) {
        notificationRepository.findById(id).ifPresent(n -> {
            n.setIsRead(true);
            n.setReadAt(java.time.LocalDateTime.now());
            notificationRepository.save(n);
        });
    }

    public void markAllRead(Long userId) {
        notificationRepository.findByUserIdAndIsReadFalse(userId).forEach(n -> {
            n.setIsRead(true);
            n.setReadAt(java.time.LocalDateTime.now());
            notificationRepository.save(n);
        });
    }
}
