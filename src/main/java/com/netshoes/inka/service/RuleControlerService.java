package com.netshoes.inka.service;

import com.netshoes.inka.model.RuleControl;
import com.netshoes.inka.repository.RuleControlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static java.lang.String.format;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * Created by pedroxs on 17/09/15.
 */
@Service
public class RuleControlerService {

    private static final String baseFileName = "shipping-rules-0.1.%d.jar";

    @Autowired
    private RuleControlRepository ruleControlRepository;

    public Optional<RuleControl> getActiveRule() {
        return ruleControlRepository.findByActiveIsTrue();
    }

    public void changeVersion(Integer version) {
        String formattedVersion = format(baseFileName, version);
        inactivateCurrentRule(formattedVersion);
        ruleControlRepository.save(new RuleControl(formattedVersion));
    }

    private void inactivateCurrentRule(String version) {
        ruleControlRepository.findByActiveIsTrue().ifPresent(activeRule -> {
            if (version.equals(activeRule.getVersion())) throw new RuntimeException("This is the current version!");
            activeRule.setActive(false);
            activeRule.setActiveTo(new Date());
            ruleControlRepository.save(activeRule);
        });
    }

    public Page<RuleControl> all(Pageable pageable) {
        return ruleControlRepository.findAll(pageable);
    }
}
