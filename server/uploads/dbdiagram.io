//// -- LEVEL 1
//// -- Tables and References

// Creating tables
Table user as U {
  id int [pk, increment] // auto-increment
  email String [unique]
  name String
  created_at timestamp
}

Table message {
  id int [pk]
  sender Sring
  content String
  sentAt String
 }
 
 Table chatRoom{
  id int [pk]
  users array [ref: < U.id]
  lastUpdated timestamp
  messageList array [ref: < message.id]
 }

Table appointment{
  id int [pk]
  date String
  time String
  location String
}

Table appointmentList{
  id int [pk]
  chatId int [ref: < chatRoom.id]
  appointmentList array [ref: < appointment.id]
}
// Creating references
// You can also define relaionship separately
// > many-to-one; < one-to-many; - one-to-one
//----------------------------------------------//
