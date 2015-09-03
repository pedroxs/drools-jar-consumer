package com.netshoes.inka.service;

import com.netshoes.shipping_rules.Message;
import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by pedroxs on 01/09/15.
 */
@Service
public class MessageService {

    private String baseUrl = "https://github.com/pedroxs/drools-jar-consumer/blob/master/src/main/resources/rules/";
    private String urlRaw = "?raw=true";
    private String ruleUrl = baseUrl + "shipping-rules-0.1.0.jar" + urlRaw;

    private String basePath = "/opt/rules/";
    private String ruleFile = basePath + "shipping-rules-0.1.0.jar";

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
        Resource resource = ks.getResources().newFileSystemResource(ruleFile);
        KieRepository kr = ks.getRepository();
        KieModule km = kr.addKieModule(resource);
        KieContainer kc = ks.newKieContainer(km.getReleaseId());
        return kc.newKieSession();
    }

    public String getRuleFile() {
        return ruleFile.substring(basePath.length());
    }

    public void setRuleFile(String ruleFile) {
        this.ruleFile = basePath + ruleFile;
    }

    public Message produceRemoteMessage() throws IOException {
        KieSession kieSession = createRemoteKieSession();
        Message message = new Message();
        kieSession.insert(message);
        kieSession.fireAllRules();
        kieSession.dispose();
        return message;
    }

    private KieSession createRemoteKieSession() throws IOException {
        KieServices ks = KieServices.Factory.get();
        Resource resource = ks.getResources().newUrlResource(ruleUrl);
        InputStream inputStream = resource.getInputStream();
        KieRepository kr = ks.getRepository();
        KieModule km = kr.addKieModule(ks.getResources().newInputStreamResource(inputStream));
        KieContainer kc = ks.newKieContainer(km.getReleaseId());
        return kc.newKieSession();
    }

    public String getRuleUrl() {
        return ruleUrl.substring(baseUrl.length(), ruleUrl.length() - urlRaw.length());
    }

    public void setRuleUrl(String ruleUrl) {
        this.ruleUrl = baseUrl + ruleUrl + urlRaw;
    }
}
