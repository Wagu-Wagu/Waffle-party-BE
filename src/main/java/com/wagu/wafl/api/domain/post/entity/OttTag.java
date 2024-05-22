package com.wagu.wafl.api.domain.post.entity;

public enum OttTag {
    NETFLIX("넷플릭스"),
    WATCHA("왓챠"),
    DISNEY("디즈니"),
    WAVE("웨이브"),
    TIVING("티빙"),
    COUPANGPLAY("쿠팡플레이"),
    LAFTEL("라프텔"),
    NAVERSERIES("네이버시리즈"),
    ETC("기타");

    private String ottTag;

    OttTag(String ottTag) {
        this.ottTag = ottTag;
    }

    public String getOttTag() {
        return ottTag;
    }
}
