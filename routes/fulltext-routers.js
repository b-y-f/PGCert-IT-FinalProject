const express = require("express");
const router = express.Router();

const commentDao = require("../modules/dao-comment.js");
const postDao = require("../modules/dao-post.js");
require("../modules/verify-auth.js");
const QuillDeltaToHtmlConverter = require('quill-delta-to-html').QuillDeltaToHtmlConverter;


router.get('/fulltext', async (req, res) => {
    if (req.session.user) {
        res.locals.isValidUser = req.session.user;
        console.log(res.locals.isValidUser.id);
    }



    let postId = req.query.id;
    req.session.postId = postId;

    res.locals.comments = await commentDao.retrieveAllCommentsForPost(postId);

    //TODO make delete button and reply for each comment

    // modify the datetime to a human readable format
    const preFormat = await postDao.retrievePostById(postId);

    const createTime = preFormat.created_at;
    options = {
        year: 'numeric', month: 'numeric', day: 'numeric',
        hour: 'numeric', minute: 'numeric', second: 'numeric',
        hour12: true,
        timeZone: 'Pacific/Auckland'
    };
    const newTime = new Intl.DateTimeFormat('en-nz',options).format(createTime);

     //render blog fulltext
     if (preFormat.content!==''){
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
