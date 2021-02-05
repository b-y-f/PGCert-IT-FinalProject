const database = require("./database.js");


async function createComment(comment){
    const db = await database;
    await db.query(
        "insert into post_comment(post_id, poster_id, created_at, content) value (?,?, now(),?)", [comment.postId, comment.posterId, comment.content]
    );
}

async function retrieveAllCommentsForPost(postId){
    const  db = await database;
    const result = db.query(
        "select u.username, c.created_at,c.content,u.avatar, c.post_id, c.poster_id, c.id as 'commentId'  from userinfo as u, post_comment as c where c.poster_id = u.id and c.post_id = ? order by c.created_at desc ", [postId]
    );
    return result;
}


async function addReplyToComment(commentId, replyContent, replierId, replyTo ){
    const  db = await database;

    await db.query(
        "insert into comment_reply(parent_id, child_content, replier_id, reply_to) value (?,?,?,?)", [commentId, replyContent, replierId, replyTo]
    );

}



module.exports = {
    createComment,
    retrieveAllCommentsForPost,
    addReplyToComment
};
