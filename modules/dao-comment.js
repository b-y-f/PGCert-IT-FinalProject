const database = require("./database.js");


async function createComment(comment){
    const db = await database;
    db.query(
        "insert into post_comment(post_id, poster_id, created_at, content) value (?,?, now(),?)",[comment.postId, comment.posterId, comment.content]
    );
}

async function retrieveAllCommentsForPost(postId){
    const  db = await database;
    const result = db.query(
        "select u.username, c.created_at,c.content,u.avatar  from userinfo as u, post_comment as c where c.poster_id = u.id and c.post_id = ?", [postId]
    );
    return result
}




module.exports = {
    createComment,
    retrieveAllCommentsForPost
};