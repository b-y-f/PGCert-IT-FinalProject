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

    //convert time into local
    res.locals.comments.map(c=>{
        c.created_at = new Date(c.created_at).toLocaleString('en-NZ');
    });
    res.locals.comments.map(c=>{
        c.replies.map(r=>{
            r.reply_date = new Date(r.reply_date).toLocaleString('en-NZ');
        })
    })
    //

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
    const post = await postDao.retrievePostById(postId);


    const createTime = post.created_at;
    options = {
        year: 'numeric', month: 'numeric', day: 'numeric',
        hour: 'numeric', minute: 'numeric', second: 'numeric',
        hour12: true,
    };
    const humanReadbleTime = new Date(createTime).toLocaleString('en-NZ',options);

    //render blog fulltext
    if (post.content !== '') {
        let converter = new QuillDeltaToHtmlConverter(JSON.parse(post.content).ops, {});
        post.content = converter.convert();
    }

    res.locals.post = post;
    res.locals.post.created_at = humanReadbleTime;

    if (res.locals.post.editted_at!==null){
        res.locals.post.editted_at = new Date(res.locals.post.editted_at).toLocaleString('en-NZ');
    }

    res.render('fulltext');
})


module.exports = router;
