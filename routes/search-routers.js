const express = require("express");
const router = express.Router();

const { Client } = require('@elastic/elasticsearch');
const client = new Client({node: 'http://localhost:9200'});

router.get("/search",(req, res) => {

// elasticsearch init


    res.locals.k = req.query.search;
    const result = client.search({
        q: req.params.keyword,
        index: 'posts',
        size: 20
    }).then(function (body) {
        var hits = body.hits.hits
        res.send(hits)
    }, function (error) {
        console.trace(error.message)
    });

    res.render("search-results");
});



module.exports = router;
