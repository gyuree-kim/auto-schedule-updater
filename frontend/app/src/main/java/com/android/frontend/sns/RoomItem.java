package com.android.frontend.sns;

//        users: [{ type: Schema.Types.ObjectId, ref: 'User', required: true }],
//        messages: [{ type: Schema.Types.ObjectId, ref: 'Message' }],
//        createdAt: Date,
//        recentMsg: { type: Schema.Types.ObjectId, ref: 'Message' }
//        },
//        {
//        timestamps: true

//        users: [{ type: Schema.Types.ObjectId, ref: 'User', required: true }],
//        messages: [{ type: Schema.Types.ObjectId, ref: 'Message' }],
//        createdAt: Date,
//        recentMsg: { type: Schema.Types.ObjectId, ref: 'Message' }
//        },
//        {
//        timestamps: true

public class RoomItem {
    private int room_id; //채팅방 고유 id
    private String last_sender; //최근 보낸 사람
    private String last_message;    //최근 보낸 메세지,/
    private String last_time;   //최근 메세지의 시간
    private int last_notread;   //최근 안읽은 메세지

    public RoomItem(){

    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getLast_sender() {
        return last_sender;
    }

    public void setLast_sender(String last_sender) {
        this.last_sender = last_sender;
    }

    public String getLast_message() {
        return last_message;
    }

    public void setLast_message(String last_message) {
        this.last_message = last_message;
    }

    public String getLast_time() {
        return last_time;
    }

    public void setLast_time(String last_time) {
        this.last_time = last_time;
    }

    public int getLast_notread() {
        return last_notread;
    }

    public void setLast_notread(int last_notread) {
        this.last_notread = last_notread;
    }
}
