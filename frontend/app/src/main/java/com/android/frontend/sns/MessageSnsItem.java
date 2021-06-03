package com.android.frontend.sns;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class MessageSnsItem {
//    const message = new Schema({
//        chatRoomId: { type: Schema.Types.ObjectId, ref: 'ChatRooms', required: true },
//        sender: { type: String, ref: 'User', required: true },
//        content: { type: String, required: true},
//        isRead: Boolean,
//                createdAt: { type: Date, required: true }
//    },
    @SerializedName("chatRoomId")
    private String chatRoomId;
    @SerializedName("sender")
    private String sender;
    @SerializedName("content")
    private String content;
    @SerializedName("isRead")
    private Boolean isRead;
    @SerializedName("createdAt")
    private Date createdAt;
    //db에서 객체로 값을 읽어올때
    //파라미터가 비어있는 ㄴ생성자가 필요
    public MessageSnsItem(){

    }

    public MessageSnsItem(String chatRoomId, String sender, String content, Boolean isRead, Date createdAt) {
        this.chatRoomId = chatRoomId;
        this.sender = sender;
        this.content = content;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
