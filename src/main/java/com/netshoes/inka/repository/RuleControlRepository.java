package com.netshoes.inka.repository;

import com.netshoes.inka.model.RuleControl;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Created by pedroxs on 17/09/15.
 */
public interface RuleControlRepository extends MongoRepository<RuleControl, String> {
    Optional<RuleControl> findByActiveIsTrue();
}
