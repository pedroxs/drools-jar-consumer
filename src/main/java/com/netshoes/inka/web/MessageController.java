package com.netshoes.inka.web;

import com.netshoes.inka.service.MessageService;
import com.netshoes.inka.fact.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by pedroxs on 01/09/15.
 */
@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @RequestMapping("/message")
    public Message shout() {
        return messageService.produceMessage();
    }

    @RequestMapping("/remote-message")
    public Message remoteShout() throws IOException {
        return messageService.produceRemoteMessage();
    }

    @RequestMapping("/rule")
    public String changeRules(@RequestParam("version") Integer version) {
        String fileName = "shipping-rules-0.1.%d.jar";
        messageService.setRuleUrl(String.format(fileName, version));
        messageService.setRuleFile(String.format(fileName, version));
        return String.format("Rule version %d applied!", version);
    }

    @RequestMapping("/version")
    public String currentRule() {
        return String.format("Url version: %s \r\nFile version: %s", messageService.getRuleUrl(), messageService.getRuleFile());
    }
}
