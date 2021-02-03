const express = require("express");
const router = express.Router();

const postDao = require("../modules/dao-post.js");

const verifyAuthenticated = require("../modules/verify-auth.js");

// create a post / reuse it for edit
router.get('/createpost', verifyAuthenticated, async (req, res) => {

    res.locals.isValidUser = req.session.user;
    res.locals.avatar = req.session.user.avatar;
    //ediit the post
    let editPostId = req.query.editid;
    if (editPostId !== undefined) {
        const post = await postDao.retrievePostById(editPostId);
        res.locals.title = post.title;
        res.locals.content = post.content;
        res.locals.edit = 'editpost';
        req.session.postId = editPostId;
    }

    res.render('postblog');


});


router.post('/editpost', verifyAuthenticated, async (req, res) => {

    const edittedPost = {
        title: req.body.postTitle,
        content: req.body.postContent,
        postId: req.session.postId,
    }

    await postDao.editBlog(edittedPost);
    res.redirect('/?message=Edit Successfully!')
})

// manage user's article
router.get('/post-manager', verifyAuthenticated, async (req, res) => {

    res.locals.isValidUser = req.session.user;
    res.locals.avatar = req.session.user.avatar;
    res.locals.posts = await postDao.retrievPostsListFromUser(req.session.user.id);

    //delete post

    let deleteid = req.query.deleteid;
    if (deleteid !== undefined) {
        await postDao.deleteBlog(deleteid);
        res.redirect('/post-manager');
    }

    //edit post
    let editid = req.query.editid;
    if (editid !== undefined) {
        await postDao.deleteBlog(deleteid);
        res.redirect('/post-manager');
    }


    res.render('post-manager');
})


module.exports = router;