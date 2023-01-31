package com.fasttutoring.lesson;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LessonController {
    private SimpMessagingTemplate messagingTemplate;
    private LessonService lessonService;

    public LessonController(SimpMessagingTemplate messagingTemplate, LessonService lessonService) {
        this.messagingTemplate = messagingTemplate;
        this.lessonService = lessonService;
    }

    @MessageMapping("/draw/{id}")
    public void drawOnBoard(@DestinationVariable("id") String senderId, String body) {
        lessonService.addToBoard(body);
        lessonService.sendToAllSubscribersExcept(senderId, body);
    }

    @MessageMapping("/join/{id}")
    public void joinLesson(@DestinationVariable String id) {
        lessonService.addSubscriber(id);
        lessonService.sendBoardTo(id);
    }

    @GetMapping("/lesson")
    public String getLessonPage() {
        return "lesson.html";
    }
}
