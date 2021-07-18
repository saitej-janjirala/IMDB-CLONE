package com.saitejajanjirala.imdbclone.db;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;

public class IdsToStringConverter {

    @TypeConverter
    public String listToStringConverter(List<Integer> ids) {
        String val = "";
        for (int i : ids) {
            val += i + ",";
        }
        if (val.equals("")) {
            return val;
        }
        return val.substring(0, val.length() - 1);

    }

    @TypeConverter
    public List<Integer>  stringToList(String x){
        List<Integer> list=new ArrayList<>();
        if(x.length()==0) return list;
        else if(x.length()==1) {
            list.add(Integer.parseInt(x));
        }
        else {
            for (String i : x.split(",")) {
                list.add(Integer.parseInt(i));
            }
        }
        return list;
    }
}