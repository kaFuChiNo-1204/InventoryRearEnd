package com.housekeeperispurchase.utils;

import java.util.ArrayList;
import java.util.List;

public class ListString {
    public static List<String> objToList(Object obj) {
        List<String> result = new ArrayList<String>();
        if (obj instanceof ArrayList<?>) {
            for (Object o : (List<?>) obj) {
                result.add(String.class.cast(o));
            }
        }
        return result;
    }
}
