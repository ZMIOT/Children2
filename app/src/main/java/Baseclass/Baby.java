package Baseclass;

public class Baby {
    private int babyid;
    private String babyname;
    private String sex;
    private String birth;
    private String parentname;
    private String classname;

    private String summary;
    private String parent_phone;
    private String relationship;

    /*public Baby(int babyid, String babyname, String sex, String birth, String parentname, String classname, String teacher, String summary, String parent_phone) {
        this.babyid = babyid;
        this.babyname = babyname;
        this.sex = sex;
        this.birth = birth;
        this.parentname = parentname;
        this.classname = classname;

        this.summary = summary;
        this.parent_phone = parent_phone;
    }*/

    public Baby(int babyid, String babyname,String classname,  String summary,String parent_phone,String sex, String birth, String parentname,   String relationship) {
        this.babyid = babyid;
        this.babyname = babyname;
        this.sex = sex;
        this.birth = birth;
        this.parentname = parentname;
        this.classname = classname;
        this.summary = summary;
        this.parent_phone = parent_phone;
        this.relationship = relationship;
    }

    public Baby(int babyid, String babyname, String sex, String birth, String classname, String summary, String parent_phone) {
        this.babyid = babyid;
        this.babyname = babyname;
        this.sex = sex;
        this.birth = birth;
        this.classname = classname;

        this.summary = summary;
        this.parent_phone = parent_phone;
    }

    public Baby(int babyid, String babyname, String sex, String birth, String relationship) {
        this.babyid = babyid;
        this.babyname = babyname;
        this.sex = sex;
        this.birth = birth;
        this.relationship = relationship;
    }


    public int getBabyid() {
        return babyid;
    }

    public void setBabyid(int babyid) {
        this.babyid = babyid;
    }

    public String getBabyname() {
        return babyname;
    }

    public void setBabyname(String babyname) {
        this.babyname = babyname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getParentname() {
        return parentname;
    }

    public void setParentname(String parentname) {
        this.parentname = parentname;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getParent_phone() {
        return parent_phone;
    }

    public void setParent_phone(String parent_phone) {
        this.parent_phone = parent_phone;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
}
