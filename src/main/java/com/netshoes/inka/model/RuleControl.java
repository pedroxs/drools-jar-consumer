package com.netshoes.inka.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by pedroxs on 17/09/15.
 */
@Document
public class RuleControl {

    @Id
    private String id;
    private String version;
    private Boolean active;
    private Date activeSince;
    private Date activeTo;

    public RuleControl(String version) {
        this.version = version;
        this.active = true;
        this.activeSince = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getActiveSince() {
        return activeSince;
    }

    public void setActiveSince(Date activeSince) {
        this.activeSince = activeSince;
    }

    public Date getActiveTo() {
        return activeTo;
    }

    public void setActiveTo(Date activeTo) {
        this.activeTo = activeTo;
    }
}
