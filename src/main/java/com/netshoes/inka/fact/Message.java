package com.netshoes.inka.fact;

import java.io.Serializable;

/**
 * Created by pedroxs on 01/09/15.
 */
public class Message implements Serializable {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
