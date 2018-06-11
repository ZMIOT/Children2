package Baseclass;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.List;

public class Grade {
    public List<EvaluationPic> Attachments;
    public Uri UserAvatar;
    public String time;
    public String Title;
   /* public int ImageResource;*/
    public String content;
    public String username;

    public Uri getUserAvatar() {
        return UserAvatar;
    }

    public void setUserAvatar(Uri userAvatar) {
        UserAvatar = userAvatar;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<EvaluationPic> getAttachments() {
        return Attachments;
    }

    public void setAttachments(List<EvaluationPic> attachments) {
        Attachments = attachments;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Grade(){

    }
    public Grade(Uri UserAvatar, String time, String title, int ImageResource)
    {
        this.UserAvatar=UserAvatar;
        this.time=time;
        this.Title=title;
    }
}
