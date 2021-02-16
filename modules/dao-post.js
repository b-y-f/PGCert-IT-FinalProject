const database = require("./database.js");

async function postBlog(post){
    const db = await database;

    await db.query(
        "insert into post (authorId,title,created_at,content) value (?,?,now() + INTERVAL 13 HOUR ,?);",
        [post.authorId, post.title, post.content]
    );

    //return a post id to make search result link to fulltext
    const postId = await db.query(
        "select id from post where id = LAST_INSERT_ID(); "
    )
    return postId[0];

}

async function retrieveAllPosts(){
    const db = await database;

    return db.query("select * from post order by created_at desc");
}


async function retrievPostsListFromUser(id){
    const db = await database;

    return db.query("select id,title,created_at from post where authorId = ?", [id]);
}


async function editBlog(edittedPost){
    const db = await database;

    db.query("update post set title = ? , content = ? , editted_at = now() + INTERVAL 13 HOUR where id = ?",[edittedPost.title,edittedPost.content,edittedPost.postId]);


}

//used for edit post
async function retrievePostById(postId){
    const db = await database;
    const result = await db.query("select post.*, u.username, u.avatar from post left join userinfo as u on post.authorId = u.id where post.id = ?",[postId]);
    return result[0];
}

async function deleteBlog(postId){
    const db = await database;

    db.query("delete from post where id = ?",[postId]);
}

async function getUsernameById(authorId){
    const db = await database;

    const result = db.query("select username from userinfo where id = ?",[authorId]);

    return result[0];
}


module.exports = {
    postBlog,
    retrieveAllPosts,
    retrievPostsListFromUser,
    retrievePostById,
    editBlog,
    deleteBlog,
    getUsernameById
};
