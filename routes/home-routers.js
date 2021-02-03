const express = require("express");
const router = express.Router();

const postDao = require("../modules/dao-post.js");
const userDao = require("../modules/dao-userinfo");
const verifyAuthenticated = require("../modules/verify-auth.js");


router.get('/', async function (req, res) {


    if (req.session.user) {

        res.locals.isValidUser = req.session.user;

        res.locals.avatar = (await userDao.getAvatarById(req.session.user.id)).avatar;

    }
    res.locals.message = req.query.message;

    res.locals.posts = await postDao.retrieveAllPosts();

    res.render("home");

});

// after posted , redirect to homepage
router.post('/', verifyAuthenticated, async (req, res) => {

    const post = {
        title: req.body.postTitle,
        content: req.body.postContent,
        authorId: req.session.user.id,
    };

    await postDao.postBlog(post);
    res.redirect('/?message=Post Successfully!');

});


module.exports = router;