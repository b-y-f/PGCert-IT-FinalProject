const mariadb = require("mariadb");

const USER_NAME = 'yb55';
const USER_PASS = '3219159';

const database = mariadb.createConnection({
    host: 'db.trex-sandwich.com',
    database: USER_NAME,
    user: USER_NAME,
    password: USER_PASS
});

module.exports = database;
