package com.netshoes.inka.service;

import com.netshoes.shipping_rules.Message;
import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

/**
 * Created by pedroxs on 01/09/15.
 */
@Service
public class MessageService {

    private String basePath = "/opt/rules/";
    private String ruleUrl = basePath + "shipping-rules-0.1.0.jar";

    public Message produceMessage() {
        KieSession kieSession = createKieSession();
        Message message = new Message();
        kieSession.insert(message);
        kieSession.fireAllRules();
        kieSession.dispose();
        return message;
    }

    private KieSession createKieSession() {
        KieServices ks = KieServices.Factory.get();
        Resource resource = ks.getResources().newFileSystemResource(ruleUrl);
        KieRepository kr = ks.getRepository();
        KieModule km = kr.addKieModule(resource);
        KieContainer kc = ks.newKieContainer(km.getReleaseId());
        return kc.newKieSession();
    }

    public String getRuleUrl() {
        return ruleUrl.substring(basePath.length());
    }

    public void setRuleUrl(String ruleUrl) {
        this.ruleUrl = basePath + ruleUrl;
    }
}
