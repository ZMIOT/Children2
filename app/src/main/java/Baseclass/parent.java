package Baseclass;

public class parent {
    public int parentid;
    public String username;
    public String nickname;
    public String birth;
    public String autograph;
    public String sex;
    public String touxiang;
    public String password;
    public int childid;
    public String childname;

    public parent(int parentid,String username,String nickname,String birth,String autograph,String sex,String touxiang,String password,int childid,String childname){
        this.parentid=parentid;
        this.username=username;
        this.nickname=nickname;
        this.birth=birth;
        this.autograph=autograph;
        this.sex=sex;
        this.touxiang=touxiang;
        this.password=password;
        this.childid=childid;
        this.childname=childname;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getAutograph() {
        return autograph;
    }

    public void setAutograph(String autograph) {
        this.autograph = autograph;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(String touxiang) {
        this.touxiang = touxiang;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getChildid() {
        return childid;
    }

    public void setChildid(int childid) {
        this.childid = childid;
    }

    public String getChildname() {
        return childname;
    }

    public void setChildname(String childname) {
        this.childname = childname;
    }
}
