package com.fasttutoring.lesson;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class LessonService {
    private String board = "";
    private HashSet<String> subscribersIds = new HashSet<>();
    private SimpMessagingTemplate messagingTemplate;

    public LessonService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendToAllSubscribersExcept(String exceptId, String body) {
        for (String subscriberId : subscribersIds) {
            if (!subscriberId.equals(exceptId)) {
                messagingTemplate.convertAndSend("/lesson/board-changes/" + subscriberId, body);
            }
        }
    }

    public void sendBoardTo(String id) {
        messagingTemplate.convertAndSend("/lesson/join/" + id, board);
    }


    public void addSubscriber(String sub) {
        subscribersIds.add(sub);
    }

    public void addToBoard(String value) {
        board += value;
    }

}
