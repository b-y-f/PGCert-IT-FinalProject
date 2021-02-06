const express = require("express");
const router = express.Router();

const commentDao = require("../modules/dao-comment.js");
const postDao = require("../modules/dao-post.js");
require("../modules/verify-auth.js");
const QuillDeltaToHtmlConverter = require('quill-delta-to-html').QuillDeltaToHtmlConverter;


router.get('/fulltext', async (req, res) => {



    let postId = req.query.id;
    req.session.postId = postId;

    res.locals.comments = await commentDao.retrieveAllCommentsForPost(postId);

    //make delete button and reply for each comment

    if (req.session.user) {
        res.locals.isValidUser = req.session.user;
        for (const c of res.locals.comments) {
            let commenterId = c.poster_id;
            c.isCommenter = res.locals.isValidUser.id === commenterId;
        }
    }



    // modify the datetime to a human readable format
    const preFormat = await postDao.retrievePostById(postId);


    //=============database names=======================
    // {
    //     reply_id: 1,
    //         parent_id: 1,
    //     reply_date: 2020-01-01T11:00:00.000Z,
    //     child_content: 'nihao!!',
    //     replier_id: 1,
    //     reply_to: 1
    // },


    // {
    //     username: 'admin',
    //         created_at: 2020-08-07T12:00:00.000Z,
    //     content: 'this is a commend content',
    //     avatar: 'default',
    //     post_id: 1,
    //     poster_id: 1,
    //     commentId: 1,
    //     replies: [ [Object], [Object], meta: [Array] ]
    // },

    // {
    //     reply_id: 2,
    //         parent_id: 1,
    //     reply_date: 2020-02-01T11:00:00.000Z,
    //     child_content: 'hi!',
    //     replier_id: 2,
    //     reply_to: 1,
    //     id: 2,
    //     username: 'guest',
    //     password: '654321',
    //     dob: 1990-05-19T12:00:00.000Z,
    //     description: "I'm good girl",
    //     email: null,
    //     name: null,
    //     avatar: 'default',
    //     replierUsername: 'guest',
    //     replierAvatar: 'default'
    // },

    //=============database names=======================


    const createTime = preFormat.created_at;
    options = {
        year: 'numeric', month: 'numeric', day: 'numeric',
        hour: 'numeric', minute: 'numeric', second: 'numeric',
        hour12: true,
        timeZone: 'Pacific/Auckland'
    };
    const newTime = new Intl.DateTimeFormat('en-nz', options).format(createTime);

    //render blog fulltext
    if (preFormat.content !== '') {
        let converter = new QuillDeltaToHtmlConverter(JSON.parse(preFormat.content).ops, {});
        preFormat.content = converter.convert();
    }

    // console.log(res.locals.comments);


    res.locals.post = {
        username: preFormat.username,
        title: preFormat.title,
        avatar: preFormat.avatar,
        content: preFormat.content,
        created_at: newTime
    };

    res.render('fulltext');
})


module.exports = router;
