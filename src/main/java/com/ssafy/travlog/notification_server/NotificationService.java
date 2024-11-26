package com.ssafy.travlog.notification_server;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.ssafy.travlog.ai.ChatGptService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
	private final ChatGptService chatGptService;

	public void sendNotificationToAll(String title, String body) {
		try {
			// Create a message for a specific topic (e.g., "all")
			Message message = Message.builder()
				.setNotification(Notification.builder()
					.setTitle(title)
					.setBody(body)
					.build())
				.setTopic("all") // Replace "all" with your chosen topic name
				.build();

			// Send the message to FCM
			String response = FirebaseMessaging.getInstance().send(message);
			System.out.println("Successfully sent message: " + response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendAINotificationToAll() {
		sendNotificationToAll("AI 챌린지",
			chatGptService.getChatResponse("유저들이 10초의 영상 촬영으로 도전해볼 만한 미션 내용을 30자 이내로 작성해줘!"));
	}
}
