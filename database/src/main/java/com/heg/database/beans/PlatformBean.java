package com.heg.database.beans;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "PLATFORM_BEAN".
 */
public class PlatformBean {

    private Long id;
    private String name;

    public PlatformBean() {
    }

    public PlatformBean(Long id) {
        this.id = id;
    }

    public PlatformBean(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
