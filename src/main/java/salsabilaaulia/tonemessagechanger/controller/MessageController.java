package salsabilaaulia.tonemessagechanger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import salsabilaaulia.tonemessagechanger.dto.MessageRequest;
import salsabilaaulia.tonemessagechanger.service.MessageService;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    MessageService messageService;

    @PostMapping("/change-tone")
    public ResponseEntity<?> changeTone(@RequestBody MessageRequest messageRequest) {
        String modifiedMessage = messageService.changeTone(messageRequest);
        return ResponseEntity.ok(modifiedMessage);
    }
}
