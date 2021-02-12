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
    res.locals.status = req.query.status;

    res.render("login");
});

router.post("/login", async function (req, res) {

    const username = req.body.username;
    const password = req.body.password;
    const rememberMe = req.body.rememberMe;

    const user = await userDao.retrieveUserWithCredentials(username, password);

    if (user) {
        if(rememberMe==="checked"){
            res.cookie("user", req.body.username, {maxAge: 1000*60*60*24 ,httpOnly: false,signed:true});
        }
        req.session.user = user;
        res.redirect("/?message=Login Successfully!");
    } else {
        // Passwords don't match
        res.redirect("./login?message=Authentication failed!&status=alert-warning");
    }


});

router.get("/logout", function (req, res) {

    res.clearCookie('user');
    delete req.session.destroy(err => {
        res.redirect("./?message=Successfully logged out!");
    });

});


router.get("/signup", function (req, res) {
    res.locals.message = req.query.message;
    res.render("signup");
});

router.post("/signup", async (req, res) => {

    //JSON output
    if (req.body.password !== undefined) {
        const user = {
            username: req.body.username,
            password: req.body.password,
            dob: req.body.dob,
            description: req.body.description
        };

        await userDao.createUser(user);
        res.redirect("./login?message=Account created successfully!&status=alert-success");
    } else {

        //fix bug Unhandled promise rejection.
        //check username availability
        let username = req.body.username;
        let count = await userDao.userCount(username);

        if (count > 0) {
            res.status(500).send('false')
        } else {
            res.send('ok');
        }

    }

});


router.get('/edit-portfolio', verifyAuthenticated, (req, res) => {

    if (req.session.user) {
        res.locals.isValidUser = req.session.user;
    }

    //fsreader find names for avatar dir
    const avatarImgs = './public/images/avatars';
    const fs = require('fs');
    const avatars = [];

    fs.readdirSync(avatarImgs).forEach(i => {
        avatars.push(i);
    });

    res.locals.avatars = avatars;


    res.render('portfolio');
});

router.post('/edit-portfolio', verifyAuthenticated, async (req, res) => {
    const userDetail = {
        username: req.body.username,
        nickName: req.body.nickName,
        email: req.body.email,
        description: req.body.description,
        avatar: req.body.avatar,
        userId: req.session.user.id
    }

    await userDao.addMoreUserInfo(userDetail);
    //update the session as well other wise user have to re login to see a updated information
    req.session.user.avatar = userDetail.avatar;
    req.session.user.name = userDetail.nickName;
    req.session.user.username = userDetail.username;
    req.session.user.email = userDetail.email;
    req.session.user.description = userDetail.description;


    res.redirect('/?message=Your detailed information has been updated!!')
});


//need to stop refresh after unload the image!!
router.post("/upload-image", verifyAuthenticated, upload.single("avatarFile"), async function (req, res) {

    const fileInfo = req.file;

    // Move the image into the images folder
    const oldFileName = fileInfo.path;
    const newFileName = `./public/images/avatars/${fileInfo.originalname}`;
    fs.renameSync(oldFileName, newFileName);

    const image = await jimp.read(newFileName);
    image.resize(100, jimp.AUTO).write(`./public/images/avatars/${fileInfo.originalname}`);


});

router.post("/delete-account", async (req, res) => {
    await userDao.deleteUser(req.body.id);
    req.session.destroy();
    res.send('done');
});

router.get("/resetPwd", (req, res) => {
    res.locals.username = req.query.sc;

    res.render("resetPwd");
});

router.post("/resetPwd", async (req, res) => {
    const passWrd = req.body.password;
    const username = req.body.username;
    await userDao.resetPwd(username,passWrd);


    res.redirect("/login?message=Successfully reset password!&status=alert-success");

    //TODO decode

})


module.exports = router;
