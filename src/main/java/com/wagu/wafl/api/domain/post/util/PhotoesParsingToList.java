package com.wagu.wafl.api.domain.post.util;

import java.util.List;

public class PhotoesParsingToList {

    public static List<String> toList(String phtoes) {
        return List.of(phtoes.substring(1, phtoes.length() -1).split(","));
    }
}
