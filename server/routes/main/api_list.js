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
        name: `${req.headers.host}/api/user/search/:id`,
        description: "search user by id",
        method: "get",
      },
      {
        name: `${req.headers.host}/api/user/search/:_id`,
        description: "search user by _id",
        method: "get",
      },
      {
        name: `${req.headers.host}/api/user/search/:name`,
        description: "search user by name",
        method: "get",
      },
      {
        name: `${req.headers.host}/api/user/update/:userId`,
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
        name: `${req.headers.host}/api/message/create`,
        description: "create new message",
        method: "post",
      },
      {
        name: `${req.headers.host}/api/message/remove/:messageId`,
        description: "remove message by messageId",
        method: "delete",
      },
      // appointment
      {
        name: `${req.headers.host}/api/appointment/create`,
        description: "create a new appointment",
        method: "post",
      },
      {
        name: `${req.headers.host}/api/appointment/remove/:id`,
        description: "remove appointment by id",
        method: "delete",
      },
      {
        name: `${req.headers.host}/api/appointment/update/:id`,
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
        name: `${req.headers.host}/api/chatRoom/create/:userId`,
        description: "create new chatRoom with userId",
        method: "post",
      },
      {
        name: `${req.headers.host}/api/chatRoom/search/:userId`,
        description: "search chatRoom by userId",
        method: "get",
      },
      {
        name: `${req.headers.host}/api/chatRoom/remove/:chatRoomId`,
        description: "remove chatRoom by chatRoomId",
        method: "delete",
      },
      {
        name: `${req.headers.host}/api/chatRoom/update/:chatRoomId`,
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