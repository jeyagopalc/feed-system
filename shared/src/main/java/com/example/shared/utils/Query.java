package com.example.shared.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static com.example.shared.constants.SharedConstants.UPLOAD_DATE;

@Data
@AllArgsConstructor
public class Query {
    public static final String COLON_REGEX = "\\s*(?<!\\\\):\\s*";
    public static final String COMMA_REGEX = "\\s*(?<!\\\\),\\s*";
    public static List<String> dateKeys = Arrays.asList(UPLOAD_DATE);

    private String key;
    private String value;

    public static Query from(final String param) {
        String[] stringSplit = param.trim().split(COLON_REGEX);
        if(stringSplit.length == 2) {
            return new Query(stringSplit[0], stringSplit[1]);
        } else if(stringSplit.length == 4 && dateKeys.contains(stringSplit[0])) {
            return new Query(stringSplit[0], String.format("%s:%s:%s",stringSplit[0], stringSplit[1],
                    stringSplit[3]).replace("", "+"));
        }

        return new Query(stringSplit[0], null);
    }

    public static List<Query> toList(final String params) {
        List<Query> queryList = new ArrayList<>();
        String[] splitParams = params.trim().split(COMMA_REGEX);

        for(String param : splitParams) {
            queryList.add(from(param));
        }

        return queryList;
    }
}
