drop table if exists userinfo;
drop table if exists post;
drop table if exists post_comment;
drop table if exists comment_reply;

create table if not exists userinfo
(
    id          int         not null auto_increment,
    username    varchar(64) not null,
    password    varchar(64) not null,
    dob         date        not null,
    description text        not null,
    email       varchar(64) null default null,
    name        varchar(64) null default null,
    avatar      varchar(10) null default 'default',
    constraint pk_user primary key (id, username)

);

insert into userinfo(username, password, dob, description)
    value
    ('admin', '123456', '1992-08-01', 'I''m good boy'),
    ('guest', '654321', '1990-05-20', 'I''m good girl');

create table if not exists post
(
    id         int         not null auto_increment,
    authorId   int references userinfo (id),
    title      varchar(80) not null,
    created_at datetime    not null,
    editted_at datetime    null,
    content    text        null default null,
    primary key (id)

);

# insert into post(authorId, title, created_at, editted_at, content)
#     value
#     (4, 'GameStop', '1990-05-07', '2020-01-01', 'bb'),
#     (4, 'Test', '1990-05-20', '2015-05-07', 'I''m b');

create table if not exists post_comment
(
    id         int          not null auto_increment, #comment id
    post_id    int references post (id),
    poster_id  int references userinfo (id),
    created_at datetime     not null,
    content    varchar(500) null default null,
    primary key (id)
);


insert into post_comment(post_id, poster_id, created_at, content)
    value
    (1, 1, '2020-08-08', 'this is a commend content'),
    (1, 2, '2020-08-08', 'test comment');

create table if not exists comment_reply
(
    reply_id      int          not null auto_increment,
    parent_id     int references post_comment (id),
    reply_date datetime not null ,
    child_content varchar(500) null default null,
    replier_id    int references userinfo (id),
    reply_to      int references post_comment (poster_id),
    primary key (reply_id)
);


insert into comment_reply(parent_id, child_content, replier_id, reply_to,reply_date)
    value(1, 'nihao!!', 1, 1,'2020-01-02'),
    (1,'hi!',2,1,'2020-02-02');



# retrieveAllCommentsForPost
select u.username, c.created_at,c.content,u.avatar, c.post_id, c.poster_id, c.id as 'commentId'  from userinfo as u, post_comment as c where c.poster_id = u.id and c.post_id = 1 order by c.created_at desc


