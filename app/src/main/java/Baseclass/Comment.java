package Baseclass;

public class Comment {
    private int teacherid;
    private int babyid;
    /*private String comment;*/
    private String url;
    private String babyname;
    private String teachername;
    public Comment(int teacherid,String url, String babyname,String teachername){
        this.teacherid=teacherid;
        this.babyid=babyid;
        /*this.comment=comment;*/
        this.babyname=babyname;
        this.teachername=teachername;
        this.url=url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(int teacherid) {
        this.teacherid = teacherid;
    }

    public int getBabyid() {
        return babyid;
    }

    public void setBabyid(int babyid) {
        this.babyid = babyid;
    }

    /*public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }*/

    public String getBabyname() {
        return babyname;
    }

    public void setBabyname(String babyname) {
        this.babyname = babyname;
    }

    public String getTeachername() {
        return teachername;
    }

    public void setTeachername(String teachername) {
        this.teachername = teachername;
    }
}
