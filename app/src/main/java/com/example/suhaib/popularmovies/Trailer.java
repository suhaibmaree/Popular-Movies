package com.example.suhaib.popularmovies;

import java.io.Serializable;

/**
 * Created by suhaib on 10/7/18.
 */

public class Trailer implements Serializable {
    private String key;
    private String name;

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
