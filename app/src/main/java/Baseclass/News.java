package Baseclass;

import java.util.Date;

public class News {
    public String avatarUrl; //明显头像的Url
    public String name;  //明显的名字
    public int imgHeight;  //头像图片的高度

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(int imgHeight) {
        this.imgHeight = imgHeight;
    }
}
