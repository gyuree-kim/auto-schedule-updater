package com.android.frontend;


import java.util.Date;

public class MessageItem {
    private String userId;
    private String content;
    private Date sentAt;

    public MessageItem(String userId, String content, Date sentAt) {
        this.userId = userId;
        this.content = content;
        this.sentAt = sentAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Date getSentAt() {
        return sentAt;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }

    @Override
    public String toString() {
        return "MessageItem{" +
                "userId='" + userId + '\'' +
                ", content='" + content + '\'' +
                ", sentAt=" + sentAt +
                '}';
    }
}
