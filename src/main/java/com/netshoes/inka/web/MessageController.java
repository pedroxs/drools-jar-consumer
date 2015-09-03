package com.netshoes.inka.web;

import com.netshoes.inka.service.MessageService;
import com.netshoes.shipping_rules.Message;
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

        String result;

        switch (version) {
            case 1:
                messageService.setRuleUrl("shipping-rules-0.1.0.jar");
                messageService.setRuleFile("shipping-rules-0.1.0.jar");
                result = String.format("Rule version %d applied!", version);
                break;
            case 2:
                messageService.setRuleUrl("shipping-rules-0.2.0.jar");
                messageService.setRuleFile("shipping-rules-0.2.0.jar");
                result = String.format("Rule version %d applied!", version);
                break;
            default:
                result = String.format("No rule version %d found!", version);
        }

        return result;
    }

    @RequestMapping("/version")
    public String currentRule() {
        return String.format("Url version: %s \r\nFile version: %s", messageService.getRuleUrl(), messageService.getRuleFile());
    }
}
