package team.redrock.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SeniorStudent extends Student {
    private String nation;
    private String speciality;
    private String job;
    private String talent;
    private String teacher;
    private String phone;
    private String qq;
    private String email;
    private String biography;
    private int term;

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getTalent() {
        return talent;
    }

    public void setTalent(String talent) {
        this.talent = talent;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        int nowTerm = Integer.valueOf(sdf.format(new Date()));
        String termS = String.valueOf(term);
        //设置当月或者下一月学霸身份有效
        if (termS.length() == 6 && (term == nowTerm || plusTerm(term) == nowTerm))
            setIdentity(2);
        else
            setIdentity(1);
    }

    private static int plusTerm(int term) {
        int newTerm = term + 1;
        String temp = String.valueOf(newTerm);
        String year = temp.substring(0, 4);
        int newYear = Integer.valueOf(year);
        String month = temp.substring(4);
        if ("13".equals(month)) {
            month = "01";
            newYear++;
        }
        return Integer.valueOf(newYear + month);
    }

}
