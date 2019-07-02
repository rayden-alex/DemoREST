package com.samsolution.demo.controller;

import com.samsolution.demo.rabbit.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@ConditionalOnBean({RabbitAutoConfiguration.class})
@ConditionalOnProperty(value = "my.rabbit.disable", havingValue = "false", matchIfMissing = true)
@RequestMapping("/rabbit")
public class RabbitController {
    private final MessageSender messageSender;

    public RabbitController(@Autowired MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @GetMapping("/send/{id}")
    public String getSend(@PathVariable("id") Integer id) {
        messageSender.sendMessages(id);
        return "rabbit-" + id + " started...";
    }
}
