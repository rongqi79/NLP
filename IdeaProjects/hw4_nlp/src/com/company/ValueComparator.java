package com.company;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rong on 2/16/2017.
 */
public class ValueComparator implements Comparator<String> {
    Map<String, HashMap<String, Integer>> base;

    public ValueComparator(Map<String, HashMap<String, Integer>> base) {
        this.base = base;
    }
    // Note: this comparator imposes orderings that are inconsistent with
    // equals.
    public int compare(String a, String b) {
        if (base.get(a).get() >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}