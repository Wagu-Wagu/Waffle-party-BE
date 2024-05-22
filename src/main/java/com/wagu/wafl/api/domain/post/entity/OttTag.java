package com.wagu.wafl.api.domain.post.entity;

public enum OttTag {
    NETFLIX("netflix"),
    WATCHA("watcha"),
    DISNEY("disney"),
    WAVE("wave"),
    TIVING("tiving"),
    COUPANGPLAY("coupangplay"),
    LAFTEL("laftel"),
    NAVERSERIES("naverseries"),
    ETC("etc");

    private String ottTag;

    OttTag(String ottTag) {
        this.ottTag = ottTag;
    }

    public String getOttTag() {
        return ottTag;
    }
}
