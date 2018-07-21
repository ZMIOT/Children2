package Baseclass;

import android.graphics.Bitmap;
import android.net.Uri;

public class Teacher {
    private String name;
    private String sex;
    private int age;
    private String summary;
    private String phone;
    private Uri Image;

    public Teacher(String name, String sex, int age, String summary, String phone) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.summary = summary;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Uri getImage() {
        return Image;
    }

    public void setImage(Uri image) {
        Image = image;
    }
}
