const express = require("express");
const router = express.Router();

const userDao = require("../modules/dao-userinfo.js");
const verifyAuthenticated = require("../modules/verify-auth.js");

router.use(function (req, res, next) {
    res.locals.user = req.session.user;
    next();
});

router.get("/login", function (req, res) {
    res.locals.message = req.query.message;

    res.render("login");


});

router.post("/login", async function (req, res) {

    const username = req.body.username;
    const password = req.body.password;

    const user = await userDao.retrieveUserWithCredentials(username, password);

    if (user) {
        req.session.user = user;
        res.redirect("/?message=Login Successfully!");
    } else {
        // Passwords don't match
        res.redirect("./login?message=Authentication failed!");
    }


});

router.get("/logout", function (req, res) {
    if (req.session.user) {
        delete req.session.user;
        delete req.session.userId;
        delete req.session.postId;
    }
    res.redirect("./?message=Successfully logged out!");
});


router.get("/signup", function (req, res) {
    res.locals.message = req.query.message;
    res.render("signup");
});

router.post("/signup", async (req, res) => {

    //JSON output
    const user = {
        username: req.body.username,
        password: req.body.password,
        dob: req.body.dob,
        description: req.body.description
    };


    await userDao.createUser(user);
    res.redirect("./login?message=Account created successfully!");


});


router.get('/edit-portfolio',verifyAuthenticated, (req, res) => {
    res.render('portfolio');
});


module.exports = router;
