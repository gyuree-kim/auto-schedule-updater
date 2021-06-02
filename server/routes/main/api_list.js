const express = require('express');
const router = express.Router();

// api list
router.get("/", function (req, res, next) {
  res.render("list", {
    title: "api list",
    apilist: [
      // user
      {
        name: `${req.headers.host}/api/users`,
        description: "get all users",
        method: "get",
      },
      {
        name: `${req.headers.host}/api/users/login`,
        description: "user authentication",
        method: "post",
      },
      {
        name: `${req.headers.host}/api/users/register`,
        description: "register new user",
        method: "post",
      },
      {
        name: `${req.headers.host}/api/users/id/:id`,
        description: "search user by id",
        method: "get",
      },
      {
        name: `${req.headers.host}/api/users/_id/:_id`,
        description: "search user by _id",
        method: "get",
      },
      {
        name: `${req.headers.host}/api/users/name/:name`,
        description: "search users by name",
        method: "get",
      },
      {
        name: `${req.headers.host}/api/users/:userId`,
        description: "update user by userId",
        method: "put",
      },
      {
        name: `${req.headers.host}/api/users/:userId`,
        description: "update user by userId",
        method: "put",
      },
      {
        name: `${req.headers.host}/api/users/:id`,
        description: "get all messages by user id",
        method: "delete",
      },
      // message
      {
        name: `${req.headers.host}/api/messages/:content`,
        description: "get message with content",
        method: "get",
      },
      {
        name: `${req.headers.host}/api/messages/:_id`,
        description: "get a message",
        method: "get",
      },
      {
        name: `${req.headers.host}/api/messages`,
        description: "create new message",
        method: "post",
      },
      {
        name: `${req.headers.host}/api/messages/:messageId`,
        description: "remove message by messageId",
        method: "delete",
      },
      // appointment
      {
        name: `${req.headers.host}/api/appointments`,
        description: "create a new appointment",
        method: "post",
      },
      {
        name: `${req.headers.host}/api/appointments/_id/:_id`,
        description: "remove appointment by id",
        method: "delete",
      },
      {
        name: `${req.headers.host}/api/appointments/_id/:_id`,
        description: "update appointment by id",
        method: "put",
      },
      {
        name: `${req.headers.host}/api/appointments`,
        description: "get all appointments",
        method: "get",
      },
      {
        name: `${req.headers.host}/api/appointments/userId/:userId`,
        description: "get all appointments by userId",
        method: "get",
      },
      {
        name: `${req.headers.host}/api/appointments/_id/:_id`,
        description: "get all appointments by _id",
        method: "get",
      },
      // chatroom
      {
        name: `${req.headers.host}/api/chatRooms/:userId`,
        description: "create new chatRoom with userId",
        method: "post",
      },
      {
        name: `${req.headers.host}/api/chatRooms/:userId`,
        description: "get all chatRooms by userId",
        method: "get",
      },
      {
        name: `${req.headers.host}/api/chatRooms/:chatRoomId/recent-message`,
        description: "get recent message by chatRoomId",
        method: "get",
      },      
      {
        name: `${req.headers.host}/api/chatRooms/:chatRoomId`,
        description: "update chatRoom by chatRoomId",
        method: "put",
      },
      {
        name: `${req.headers.host}/api/chatRooms/:chatRoomId`,
        description: "remove chatRoom by chatRoomId",
        method: "delete",
      }
    ],
  });
});

module.exports = router;