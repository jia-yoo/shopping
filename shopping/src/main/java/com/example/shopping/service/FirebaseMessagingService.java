//package com.example.shopping.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.example.shopping.dto.NotificationMessage;
//import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.firebase.messaging.Message;
//import com.google.firebase.messaging.Notification;
//
//@Service
//public class FirebaseMessagingService {
//
//	@Autowired
//	private FirebaseMessaging firebaseMessaging;
//	
//	public String sendNotificationByToken(NotificationMessage notificationMessage) {
//		Notification notification = Notification
//				.builder()
//				.setTitle(notificationMessage.getTitle())
//				.setBody(notificationMessage.getBody())
//				.setImage(notificationMessage.getImage())
//				.build();
//		Message message = Message
//				.builder()
//				.setToken(notificationMessage.getRecipientToken())
//				.build();
//		
//		return "";
//	}
//	
//}
