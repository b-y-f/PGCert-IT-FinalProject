const database = require("./database.js");


async function createComment(comment) {
    const db = await database;
    await db.query(
        "insert into post_comment(post_id, poster_id, created_at, content) value (?,?, now() + interval 13 hour,?)", [comment.postId, comment.posterId, comment.content]
    );
}

async function retrieveAllCommentsForPost(postId) {
    const db = await database;

    const result = await db.query(
        "select u.username, c.created_at,c.content,u.avatar, c.post_id, c.poster_id, c.id as 'commentId' from userinfo as u, post_comment as c where c.poster_id = u.id and c.post_id = ? order by c.created_at desc", [postId]
    );

    for (const comment of result) {
        let commentId = comment.commentId;
        const replies = await db.query("select *, u.username as replierUsername ,u.avatar as replierAvatar from comment_reply,userinfo as u where parent_id = ? and replier_id=u.id",[commentId]);
        comment.replies = replies;

    }


    return result;
}


async function addReplyToComment(commentId, replyDate, replyContent, replierId, replyTo) {
    const db = await database;

    await db.query(
        "insert into comment_reply(parent_id, reply_date, child_content, replier_id, reply_to) value (?,now()+ interval 13 hour,?,?,?)", [commentId, replyDate, replyContent, replierId, replyTo]
    );
}

async function deleteComments(commentId){

    const db = await database;

    await db.query(
        "delete from post_comment where id = ?",[commentId]
    )

    //delete sub-replies

    await db.query(
        "delete from comment_reply where parent_id = ?",[commentId]
    )
}

async function deleteReplies(replyId){

    const db = await database;

    await db.query(
        "delete from comment_reply where reply_id = ?",[replyId]
    )

}


module.exports = {
    createComment,
    retrieveAllCommentsForPost,
    addReplyToComment,
    deleteComments,
    deleteReplies
};
