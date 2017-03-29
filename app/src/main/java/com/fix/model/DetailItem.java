package com.fix.model;

/**
 * Created by arg-20101-n on 29/3/17.
 */

public class DetailItem {
    String name;
    Integer resource;

    public DetailItem(String name, Integer resource) {
        this.name = name;
        this.resource = resource;
    }

    public String getName() {
        return name;
    }

    public Integer getResource() {
        return resource;
    }
}
