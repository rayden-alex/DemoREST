package com.samsolution.demo.controller;

import com.samsolution.demo.rabbit.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbit")
public class RabbitController {
    @Autowired
    private MessageSender messageSender;

    @GetMapping("/send/{id}")
    public String getSend(@PathVariable("id") Integer id) {
        messageSender.sendMessages(id);
        return "rabbit-" + id + " started...";
    }
}
