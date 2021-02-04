const express = require("express");
const router = express.Router();

const userDao = require("../modules/dao-userinfo.js");
const verifyAuthenticated = require("../modules/verify-auth.js");

// Setup fs and jimp for upload
const fs = require("fs");
const jimp = require("jimp");

// Setup multer (files will temporarily be saved in the "temp" folder).
const path = require("path");
const multer = require("multer");
const upload = multer({
    dest: path.join(__dirname, "../temp")
});

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
        delete req.session.postId;
    }
    res.redirect("./?message=Successfully logged out!");
});


router.get("/signup", function (req, res) {
    res.locals.message = req.query.message;
    res.render("signup");
});

router.post("/signup", async (req, res) => {


    //fix bug Unhandled promise rejection.
    //check username availability
    let username = req.body.username;
    let count = await userDao.userCount(username);

    if (count > 0) {
        res.status(500).send('false')
    } else {
        res.send('ok');
    }

    //JSON output
    if (req.body.password !== undefined) {
        const user = {
            username: req.body.username,
            password: req.body.password,
            dob: req.body.dob,
            description: req.body.description
        };

        await userDao.createUser(user);
        res.redirect("./login?message=Account created successfully!");
    }

});


router.get('/edit-portfolio', verifyAuthenticated, (req, res) => {

    res.locals.isValidUser = req.session.user;
    res.locals.avatar = req.session.user.avatar;

    res.locals.uploadedAvatar = req.query.avatarName;
    res.render('portfolio');
});

router.post('/edit-portfolio', verifyAuthenticated, async (req, res) => {
    const userDetail = {
        nickName: req.body.nickName,
        email: req.body.email,
        avatar: req.body.avatar,
        userId: req.session.user.id
    }

    req.session.user.avatar = req.body.avatar;
    await userDao.addMoreUserInfo(userDetail);

    res.redirect('/?message=Your detailed information has been updated!!')
});


//TODO need to stop refresh after unload the image!!
router.post("/uploadimage", verifyAuthenticated, upload.single("avatarFile"), async function (req, res) {

    const fileInfo = req.file;

    // Move the image into the images folder
    const oldFileName = fileInfo.path;
    const newFileName = `./public/images/avatars/${fileInfo.originalname}`;
    fs.renameSync(oldFileName, newFileName);

    const image = await jimp.read(newFileName);
    image.resize(100, jimp.AUTO).write(`./public/images/avatars/${fileInfo.originalname}`);


    res.redirect(`/edit-portfolio?avatarName=${fileInfo.originalname}`);
});


module.exports = router;
