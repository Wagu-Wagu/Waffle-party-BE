package com.wagu.wafl.api.domain.alert.entity;

public enum AlertType {
    COMMENT("댓글"),
    REPLY("답댓글");

    private String type;

    AlertType(String type) { this.type = type;}

    public String getType() {return type;}
}
