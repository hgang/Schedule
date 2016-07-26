package com.heg.database.beans;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Entity mapped to table "PROJECT_BEAN".
 */
public class ProjectBean implements Parcelable {

    private Long id;
    private Integer platform_id;
    private String platform_name;
    private String type;
    private String name;
    private Double principal;
    private java.util.Date start_time;
    private java.util.Date end_time;
    private Double interest;
    private Double interest_rate;

    public ProjectBean() {
    }

    public ProjectBean(Long id) {
        this.id = id;
    }

    public ProjectBean(Long id, Integer platform_id, String platform_name, String type, String name, Double principal, java.util.Date start_time, java.util.Date end_time, Double interest, Double interest_rate) {
        this.id = id;
        this.platform_id = platform_id;
        this.platform_name = platform_name;
        this.type = type;
        this.name = name;
        this.principal = principal;
        this.start_time = start_time;
        this.end_time = end_time;
        this.interest = interest;
        this.interest_rate = interest_rate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPlatform_id() {
        return platform_id;
    }

    public void setPlatform_id(Integer platform_id) {
        this.platform_id = platform_id;
    }

    public String getPlatform_name() {
        return platform_name;
    }

    public void setPlatform_name(String platform_name) {
        this.platform_name = platform_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrincipal() {
        return principal;
    }

    public void setPrincipal(Double principal) {
        this.principal = principal;
    }

    public java.util.Date getStart_time() {
        return start_time;
    }

    public void setStart_time(java.util.Date start_time) {
        this.start_time = start_time;
    }

    public java.util.Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(java.util.Date end_time) {
        this.end_time = end_time;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Double getInterest_rate() {
        return interest_rate;
    }

    public void setInterest_rate(Double interest_rate) {
        this.interest_rate = interest_rate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.platform_id);
        dest.writeString(this.platform_name);
        dest.writeString(this.type);
        dest.writeString(this.name);
        dest.writeValue(this.principal);
        dest.writeLong(this.start_time != null ? this.start_time.getTime() : -1);
        dest.writeLong(this.end_time != null ? this.end_time.getTime() : -1);
        dest.writeValue(this.interest);
        dest.writeValue(this.interest_rate);
    }

    protected ProjectBean(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.platform_id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.platform_name = in.readString();
        this.type = in.readString();
        this.name = in.readString();
        this.principal = (Double) in.readValue(Double.class.getClassLoader());
        long tmpStart_time = in.readLong();
        this.start_time = tmpStart_time == -1 ? null : new Date(tmpStart_time);
        long tmpEnd_time = in.readLong();
        this.end_time = tmpEnd_time == -1 ? null : new Date(tmpEnd_time);
        this.interest = (Double) in.readValue(Double.class.getClassLoader());
        this.interest_rate = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<ProjectBean> CREATOR = new Parcelable.Creator<ProjectBean>() {
        @Override
        public ProjectBean createFromParcel(Parcel source) {
            return new ProjectBean(source);
        }

        @Override
        public ProjectBean[] newArray(int size) {
            return new ProjectBean[size];
        }
    };

    @Override
    public String toString() {
        return name + ", " + principal + ", " + interest_rate + ", " + start_time;
    }
}
