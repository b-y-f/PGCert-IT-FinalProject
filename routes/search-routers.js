const express = require("express");
const router = express.Router();

const {Client} = require('@elastic/elasticsearch');
const client = new Client({node: 'http://localhost:9200'});

router.get("/search", async (req, res) => {

    // elasticsearch init

    await client.indices.refresh({index: 'posts'})

    res.locals.keyword = req.query.search;
    const {body} = await client.search({
        index: 'posts',
        body: {
            query: {
                multi_match :{
                    query: res.locals.keyword,
                    fields: ["authorUsername", "title", "content", "create_time"],
                    fuzziness: "AUTO"
                }
            },
            highlight: {
                fields: {
                    "*": {"pre_tags" : ["<em class='text-warning fw-bold'>"], "post_tags" : ["</em>"] }
                }
            }
        }
    }).catch(err => {
        console.log(err);
    });
    // console.log(body.hits.hits);
    res.locals.results = body.hits.hits;


    res.render("search-results");
});


module.exports = router;
