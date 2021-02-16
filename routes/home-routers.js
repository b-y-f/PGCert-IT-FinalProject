const express = require("express");
const router = express.Router();

const postDao = require("../modules/dao-post.js");
const userDao = require("../modules/dao-userinfo");
const verifyAuthenticated = require("../modules/verify-auth.js");

const QuillDeltaToHtmlConverter = require('quill-delta-to-html').QuillDeltaToHtmlConverter;


router.get('/', async function (req, res) {


    if (req.session.user) {

        res.locals.isValidUser = req.session.user;

        res.locals.avatar = (await userDao.getAvatarById(req.session.user.id)).avatar;

    }
    res.locals.message = req.query.message;

    const posts = await postDao.retrieveAllPosts();

    //--------------------
    posts.map(p => {
        if (p.content !== '') {
            let converter = new QuillDeltaToHtmlConverter(JSON.parse(p.content).ops, {});
            p.content = converter.convert();
        } else {
            console.log("Invalid post content format!!")
        }
    });
    //-----------------------------
    res.locals.posts = posts;

    res.render("home");


});

// after posted , redirect to homepage
router.post('/', verifyAuthenticated, async (req, res) => {

    const post = {
        title: req.body.postTitle,
        content: req.body.postContent,
        authorId: req.session.user.id,
    };


    const postId = await postDao.postBlog(post);
    // console.log(postId.id)
    //TODO uncomment only after elasticsearch server is running

    // index Elasticsearch
    // const {Client} = require('@elastic/elasticsearch');
    // const client = new Client({node: 'http://localhost:9200'});
    //
    //
    // await client.index({
    //     index: 'posts',
    //     body: {
    //         "authorUsername": req.session.user.username,// use pure text to perform better search
    //         "title": post.title,
    //         "content": req.body.txtContent,
    //         "create_time":(new Date()).toLocaleString("en-NZ"),
    //     }
    // }).catch(console.log)


    res.redirect('/?message=Post Successfully!');

});



module.exports = router;
