const express = require('express');
const router = express.Router();

// api list
router.get("/", function (req, res, next) {
  res.render("list", {
    title: "api list",
    apilist: [
      // user
      {
        name: `${req.headers.host}/api/user`,
        description: "get all users",
        method: "get",
      },
      {
        name: `${req.headers.host}/api/user/login`,
        description: "user authentication",
        method: "post",
      },
      {
        name: `${req.headers.host}/api/user/register`,
        description: "register new user",
        method: "post",
      },
      {
        name: `${req.headers.host}/api/user/id/:id`,
        description: "search user by id",
        method: "get",
      },
      {
        name: `${req.headers.host}/api/user/_id/:_id`,
        description: "search user by _id",
        method: "get",
      },
      {
        name: `${req.headers.host}/api/user/name/:name`,
        description: "search users by name",
        method: "get",
      },
      {
        name: `${req.headers.host}/api/user/:userId`,
        description: "update user by userId",
        method: "put",
      },
      // message
      {
        name: `${req.headers.host}/api/messages/:chatRoomId`,
        description: "get all messages by chatRoomId",
        method: "get",
      },
      {
        name: `${req.headers.host}/api/message/:content`,
        description: "get message with content",
        method: "get",
      },
      {
        name: `${req.headers.host}/api/message/:_id`,
        description: "get a message",
        method: "get",
      },
      {
        name: `${req.headers.host}/api/message`,
        description: "create new message",
        method: "post",
      },
      {
        name: `${req.headers.host}/api/message/:messageId`,
        description: "remove message by messageId",
        method: "delete",
      },
      // appointment
      {
        name: `${req.headers.host}/api/appointment`,
        description: "create a new appointment",
        method: "post",
      },
      {
        name: `${req.headers.host}/api/appointment/:id`,
        description: "remove appointment by id",
        method: "delete",
      },
      {
        name: `${req.headers.host}/api/appointment/:id`,
        description: "update appointment by id",
        method: "put",
      },
      {
        name: `${req.headers.host}/api/appointments/:userId`,
        description: "get all appointments by userId",
        method: "get",
      },
      // chatroom
      {
        name: `${req.headers.host}/api/chatRoom/:userId`,
        description: "create new chatRoom with userId",
        method: "post",
      },
      {
        name: `${req.headers.host}/api/chatRoom/:userId`,
        description: "search chatRoom by userId",
        method: "get",
      },
      {
        name: `${req.headers.host}/api/chatRoom/:chatRoomId`,
        description: "remove chatRoom by chatRoomId",
        method: "delete",
      },
      {
        name: `${req.headers.host}/api/chatRoom/:chatRoomId`,
        description: "update chatRoom by chatRoomId",
        method: "put",
      },
      {
        name: `${req.headers.host}/api/chatRooms/:userId`,
        description: "get all chatRooms by userId",
        method: "get",
      },
    ],
  });
});

module.exports = router;