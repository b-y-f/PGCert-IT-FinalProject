const database = require("./database.js");

//init hash&salt
const bcrypt = require('bcrypt');
const saltRounds = 10;


async function createUser(user) {
    const db = await database;

    let hash = bcrypt.hashSync(user.password, saltRounds);

    const result = await db.query(
        'insert into userinfo (username,password,dob,description) value (?,?,?,?);',
        [user.username, hash, user.dob, user.description]
    );
}

async function retrieveUserWithCredentials(username, password) {
    const db = await database;

    const user = await db.query(
        "select * from userinfo where username = ?",
        [username]);

    if(user[0]!==undefined && bcrypt.compareSync(password, user[0].password)){
        return user[0];
    }else return undefined;

}

async function retrieveAllUsers() {
    const db = await database;

    return db.query("select * from userinfo");
}

async function getUserIdByUsername(username) {
    const db = await database;
    const result =  await db.query("select id from userinfo where username = ?", [username]);
    return result[0];
}

async function deleteUser(id) {
    const db = await database;
    await db.query("delete from userinfo where id = ?", [id]);
}


async function getAvatarById(id){
    const db = await database;
    const result = await db.query("select avatar from userinfo where id = ?",[id]);
    return result[0];
}

async function addMoreUserInfo(userDetail){

    const db = await database;

    await db.query("update userinfo set name = ?, email = ?, avatar = ? where id = ?",[userDetail.nickName, userDetail.email, userDetail.avatar, userDetail.userId]);

}



// TODO check user available
async function userCount(username){

    const db = await database;

    const userCtn = await db.query("select  count(*) as cntUser from userinfo where username= ? ",[username]);

    return userCtn[0].cntUser;

}


module.exports = {
    createUser,
    retrieveUserWithCredentials,
    deleteUser,
    retrieveAllUsers,
    getUserIdByUsername,
    getAvatarById,
    addMoreUserInfo,
    userCount
};