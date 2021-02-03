const express = require("express");
const router = express.Router();

const commentDao = require("../modules/dao-comment.js");
const postDao = require("../modules/dao-post.js");
const verifyAuthenticated = require("../modules/verify-auth.js");


router.get('/fulltext', async (req, res) => {
    if (req.session.user) {
        res.locals.isValidUser = req.session.user;
        res.locals.avatar = req.session.user.avatar;
    }

    let postId = req.query.id;
    req.session.postId = postId;

    res.locals.comments = await commentDao.retrieveAllCommentsForPost(postId);

    res.locals.post = await postDao.retrievePostById(postId);


    res.render('fulltext');
});

router.post('/post-comment', verifyAuthenticated, async (req, res) => {

    const comment = {
        postId: req.session.postId,
        posterId: req.session.user.id,
        content: req.body.comment,
    }

    await commentDao.createComment(comment);

    res.redirect(`/fulltext?id=${req.session.postId}`)
});


module.exports = router;