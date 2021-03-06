Table user as U {
  _id int [pk] // auto-increment
  id String [unique]
  name String
  createdAt timestamp
}

Table message {
  _id int [pk]
  userId String [ref: > user._id]
  content String
  createdAt String
 }
 
Table event{
  _id int [pk]
  messageId int [ref: - message._id]
  type MessageType
  date Date
  location String
  createdAt Date
  updatedAt Date
}