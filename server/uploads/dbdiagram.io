Table user as U {
  id int [pk, increment] // auto-increment
  email String [unique]
  name String
  created_at timestamp
}

Table message {
  id int [pk]
  chatRoomId int [ref: - chatRoom.id]
  sender Sring
  content String
  sentAt String
 }
 
 Table chatRoom{
  id int [pk]
  usersId array [ref: < U.id]
  lastUpdated timestamp
  messageList array [ref: < message.id]
 }

Table appointment{
  id int [pk]
  chatId int [ref: - chatRoom.id]
  date String
  time String
  location String
}

Table appointmentList{
  id int [pk]
  userId int [ref: - U.id]
  appointment