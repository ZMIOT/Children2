package Baseclass;

import java.io.Serializable;
import java.util.List;
/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2016/3/20
 * 描    述：
 * 修订历史：
 * ================================================
 */

/** 评论的图片信息 */
public class EvaluationPic implements Serializable {
    /*public List<EvaluationPic> attachments;*/
    /*public int attachmentId;
    public String imageId;*/
    public String imageUrl;       // 原图
    public String smallImageUrl;  // 缩略图

    public EvaluationPic(String imageUrl, String smallImageUrl) {
        this.imageUrl = imageUrl;
        this.smallImageUrl = smallImageUrl;
    }

    /* public int getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(int attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }*/

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    /*public List<EvaluationPic> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<EvaluationPic> attachments) {
        this.attachments = attachments;
    }*/
}