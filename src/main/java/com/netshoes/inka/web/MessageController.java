package com.netshoes.inka.web;

import com.netshoes.inka.model.RuleControl;
import com.netshoes.inka.service.MessageService;
import com.netshoes.inka.fact.Message;
import com.netshoes.inka.service.RuleControlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * Created by pedroxs on 01/09/15.
 */
@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private RuleControlerService ruleControlerService;

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
        ruleControlerService.changeVersion(version);
        return String.format("Rule version %d applied!", version);
    }

    @RequestMapping("/version")
    public ResponseEntity currentRule() {
        Optional<RuleControl> activeRule = ruleControlerService.getActiveRule();
        if (activeRule.isPresent()) {
            return ResponseEntity.ok(activeRule.get());
        }
        return ResponseEntity.noContent().build();
    }

    @RequestMapping("/version/history")
    public Page<RuleControl> ruleHistory(@PageableDefault(sort = "activeSince", direction = DESC) Pageable pageable) {
        return ruleControlerService.all(pageable);
    }
}
