package com.netshoes.inka.service;

import com.netshoes.inka.fact.Message;
import com.netshoes.inka.model.RuleControl;
import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by pedroxs on 01/09/15.
 */
@Service
public class MessageService {

    private static final String baseUrl = "https://github.com/pedroxs/drools-jar-consumer/blob/master/src/main/resources/rules/";
    private static final String urlRaw = "?raw=true";

    private static final String basePath = "/opt/rules/";

    @Autowired
    private RuleControlerService ruleControlerService;

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
        Resource resource = ks.getResources().newFileSystemResource(getRuleFile());
        KieRepository kr = ks.getRepository();
        KieModule km = kr.addKieModule(resource);
        KieContainer kc = ks.newKieContainer(km.getReleaseId());
        return kc.newKieSession();
    }

    public String getRuleFile() {
        return basePath + currentVersion();
    }

    //TODO: cache
    private String currentVersion() {
        return ruleControlerService.getActiveRule().orElseThrow(() -> new RuntimeException("No active rule defined!")).getVersion();
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
        Resource resource = ks.getResources().newUrlResource(getRuleUrl());
        InputStream inputStream = resource.getInputStream();
        KieRepository kr = ks.getRepository();
        KieModule km = kr.addKieModule(ks.getResources().newInputStreamResource(inputStream));
        KieContainer kc = ks.newKieContainer(km.getReleaseId());
        return kc.newKieSession();
    }

    public String getRuleUrl() {
        return baseUrl + currentVersion() + urlRaw;
    }

}
