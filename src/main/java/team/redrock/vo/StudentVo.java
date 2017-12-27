package team.redrock.vo;

import team.redrock.bean.Student;
import team.redrock.common.Jurisdiction;

public class StudentVo {
    private int id;
    private String name;
    private String stu_id;
    private String gender;
    private String class_num;
    private String major;
    private int grade;
    private String college;
    private String head_url;
    private String nick_name;
    private boolean scholar;

    public StudentVo(Student stu) {
        this.id = stu.getId();
        this.name = stu.getName();
        this.stu_id = stu.getStu_id();
        this.gender = stu.getGender();
        this.class_num = stu.getClass_num();
        this.major = stu.getMajor();
        this.grade = stu.getGrade();
        this.college = stu.getCollege();
        this.head_url = stu.getHead_url();
        this.nick_name = stu.getNick_name();
        if (stu.getJurisdiction() == Jurisdiction.SUPERSCHOLAR)
            this.scholar = true;
    }

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

    public boolean isScholar() {
        return scholar;
    }

    public void setScholar(boolean scholar) {
        this.scholar = scholar;
    }
}
