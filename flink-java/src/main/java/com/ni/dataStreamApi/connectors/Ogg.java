package com.ni.dataStreamApi.connectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@Getter
@Setter
@AllArgsConstructor
public class Ogg {
    private String table;
    private String op_type;
    private String op_ts;
    private String current_ts;
    private String pos;
    private ArrayList<String> primary_keys;
    private LinkedHashMap<String,Object> after;
}
