package com.mayckgomes.dateplan_api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.mayckgomes.dateplan_api.dto.date.DateResponse;

public class SendNotification {

    public static void sendNewDate(String userNotificationToken, DateResponse date){

        try {

            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(date);

            Message message = Message.builder()
                    .putData("title","new: " + date.getTitle() )
                    .putData("body", jsonString)
                    .setToken(userNotificationToken)
                    .build();

            sendMessage(message);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void sendEditDate(String userNotificationToken, DateResponse date){

        try {

            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(date);

            Message message = Message.builder()
                    .putData("title","edit: " + date.getTitle())
                    .putData("body", jsonString)
                    .setToken(userNotificationToken)
                    .build();

            sendMessage(message);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void sendDelete(String userNotificationToken, DateResponse date){

        try {

            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(date);

            Message message = Message.builder()
                    .putData("title","delete: " + date.getTitle())
                    .putData("body",jsonString)
                    .setToken(userNotificationToken)
                    .build();

            sendMessage(message);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void sendInvite(String userNotificationToken){

        try {

            Message message = Message.builder()
                    .putData("title","request: ")
                    .setToken(userNotificationToken)
                    .build();

            sendMessage(message);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void sendRefresh(String userNotificationToken){

        try {

            Message message = Message.builder()
                    .putData("title","refresh")
                    .setToken(userNotificationToken)
                    .build();

            sendMessage(message);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void sendMessage(Message message){

        try {

            String response = FirebaseMessaging.getInstance().send(message);

            System.out.println("Successfully sent message: " + response);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
