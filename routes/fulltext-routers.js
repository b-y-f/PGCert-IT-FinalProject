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

    // console.log(res.locals.comments[0].replies)
    // reply_id: 44,
    //     parent_id: 5,
    //     reply_date: 2021-02-07T12:18:01.000Z,
    //     child_content: '33',
    //     replier_id: 3,
    //     reply_to: 2,
    //     id: 3,
    //     username: 'byf',
    //     password: '$2b$10$Vd4cDItmLnLrkzfR9khuM.o/vrxH3k0ld1RCeKY.fd7Xtkxat.coy',
    //     dob: 2021-02-17T11:00:00.000Z,
    //     description: '',
    //     email: '',
    //     name: '',
    //     avatar: 'boy',
    //     replierUsername: 'byf',
    //     replierAvatar: 'boy'


    // username: 'byf',
    //     created_at: 2021-02-07T09:29:21.000Z,
    //     content: 'rgfg',
    //     avatar: 'boy',
    //     post_id: 2,
    //     poster_id: 3,
    //     commentId: 23,
    //     replies: [ meta: [Array] ]


    //make delete button and reply for each comment

    if (req.session.user) {
        res.locals.isValidUser = req.session.user;
        for (const c of res.locals.comments) {
            let commenterId = c.poster_id;
            c.isCommenter = res.locals.isValidUser.id === commenterId;
            for(const r of c.replies){
                let replierId = r.replier_id;
                r.isReplier = res.locals.isValidUser.id === replierId;
            }
        }
    }

    // modify the datetime to a human readable format
    const preFormat = await postDao.retrievePostById(postId);


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
