package team.redrock.bean;

import team.redrock.common.Jurisdiction;

public class Student {
    private int id;
    private String name;
    private String stu_id;
    private String gender;
    private String class_num;
    private String major;
    private int grade;
    private String college;

    private String openId;
    private String head_url;
    private String nick_name;

    private int identity;
    private Jurisdiction jurisdiction = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getClass_num() {
        return class_num;
    }

    public void setClass_num(String class_num) {
        this.class_num = class_num;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public int getIdentity() {
        return identity;
    }

    public void setJurisdiction(Jurisdiction jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public Jurisdiction getJurisdiction() {
        return jurisdiction;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
        switch (identity) {
            case -1: this.jurisdiction = Jurisdiction.ADMIN; break;
            case 1 : this.jurisdiction = Jurisdiction.DUMBASS; break;
            case 2 : this.jurisdiction = Jurisdiction.SUPERSCHOLAR; break;
            default: this.jurisdiction = Jurisdiction.YOUKE;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(this.stu_id + "\t" + this.name);
        if (this.id > 0) sb.append("\t id:" + this.id);
        if (this.nick_name != null) sb.append("\t" + this.nick_name);
        if (this.jurisdiction != null) sb.append("\t" + this.jurisdiction.getIdentity());
        if (this.head_url != null) sb.append("\t" + this.head_url);
        return  sb.toString();
    }
}
