package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.notification.CreateNotificationDTO;
import com.bernardawj.notey.exception.NotificationServiceException;
import com.bernardawj.notey.repository.NotificationRepository;
import com.bernardawj.notey.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class NotificationServiceTests {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    // region Create Notification

    @Test
    public void invalidFromUserIdAndToUserId_OnCreateNotification_ShouldThrowException() {
        // Mock DTO
        CreateNotificationDTO createNotificationDTO = new CreateNotificationDTO();
        createNotificationDTO.setFromUserId(1);
        createNotificationDTO.setToUserId(2);

        // Mock the behavior of repository
        Mockito.when(this.userRepository.findUsersByFromUserIdAndToUserId(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(new ArrayList<>());

        // Check if method indeed throws an exception
        NotificationServiceException ex = Assertions.assertThrows(NotificationServiceException.class,
                () -> this.notificationService.createNotification(createNotificationDTO));

        // Check if exception message is the same
        Assertions.assertEquals("NotificationService.INVALID_USERS", ex.getMessage());
    }

    // endregion
}
