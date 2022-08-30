# Introduction

(50-100 words)
What does this app do?
What technoglies you have used? (e.g. Twitter REST API, HTTP client, mvn, Java libs, docker etc..)

Implement a Java app which can CRUD a Twitter post via Twitter REST API.

use twitter API v2: most up to date, only one available as of now in all dev tiers, including
Essentials
use oauth1a: simpler than oath2, yet gives access to Twitter API v2
oauth flow: client Grant Flow, i.e [micro]service to [micro]service
this app is trustworthy enough to use that simpler and faster flow

Create:
Create a tweet with a geotag and output the created tweet object (simplified version) in JSON
format.
Print error message (or exception) if tweet_text length is over 140 characters or geo tag is invalid
(latitude or longitude is out of range)

TwitterApp "post" "tweet_text" "latitude:longitude"

Arguments:
tweet_text - tweet_text cannot exceed 140 UTF-8 encoded characters.
latitude:longitude - Geo location.

Show/Read (with options)
Lookup a tweet by ID and print the tweet object in JSON format.
Print error message (or exception) if `tweet_id` is invalid
(e.g. non-digit characters, out of range) or optional `[field1,filed2]` is invalid.

Show all fields in JSON document if [field1,fields2] is empty.
Otherwise, only show user specified [fields] in the JSON document.
Print error message (or exception) if `tweet_id` is invalid (e.g. non-digit characters, out of
range)

TwitterApp show tweet_id [field1,fields2]

Arguments:
tweet_id - Tweet ID. Same as id_str in the tweet object
[field1,fields2]  - A comma-separated list of top-level fields from the tweet object
(similar to SELECT clause in SQL)

Delete:
Delete a list of tweets by id and output deleted tweet id and print deleted tweet object.

USAGE: TwitterApp delete [id1,id2,..]

Arguments:
tweet_ids - A comma-separated list of tweets.

# Quick Start

- Usage:
    - TwitterApp post|show|delete [options]

Post/Create:
TwitterApp "post" "tweet_text" "latitude:longitude"

Arguments:
tweet_text - tweet_text cannot exceed 140 UTF-8 encoded characters.
latitude:longitude - Geo location.

Read/Show
TwitterApp show tweet_id [field1,fields2]

Arguments:
tweet_id - Tweet ID. Same as id_str in the tweet object
[field1,fields2]  - A comma-separated list of top-level fields from the tweet object
(similar to SELECT clause in SQL)

Delete:

USAGE: TwitterApp delete [id1,id2,..]

Arguments:
tweet_ids - A comma-separated list of tweets.

- how to package your app using mvn?
- how to run your app with docker?

docker pull jrvs/twitter_app

docker run --rm \
-e consumerKey=YOUR_VALUE \
-e consumerSecret=YOUR_VALUE \
-e accessToken=YOUR_VALUE \
-e tokenSecret=YOUR_VALUE \
jrvs/twitter_app post "test post" "43:79"

Sample JSON output:
{
"created_at" : "Fri Jun 26 17:32:16 +0000 2020",
"id" : 1276568976764686343,
"id_str" : "1276568976764686343",
"text" : "test post",
"entities" : {
"hashtags" : [ ],
"user_mentions" : [ ]
},
"coordinates" : {
"coordinates" : [ 79.0, 43.0 ],
"type" : "Point"
},
"retweet_count" : 0,
"favorite_count" : 0,
"favorited" : false,
"retweeted" : false
}

docker run --rm \
-e consumerKey=YOUR_VALUE \
-e consumerSecret=YOUR_VALUE \
-e accessToken=YOUR_VALUE \
-e tokenSecret=YOUR_VALUE \
jrvs/twitter_app show 1276568976764686343

Sample JSON Ouput:
{
"created_at" : "Fri Jun 26 17:32:16 +0000 2020",
"id" : 1276568976764686343,
"id_str" : "1276568976764686343",
"text" : "test post",
"entities" : {
"hashtags" : [ ],
"user_mentions" : [ ]
},
"coordinates" : {
"coordinates" : [ 79.0, 43.0 ],
"type" : "Point"
},
"retweet_count" : 0,
"favorite_count" : 0,
"favorited" : false,
"retweeted" : false
}

#Print only selected fields (this is an optional feature)
docker run --rm \
-e consumerKey=YOUR_VALUE \
-e consumerSecret=YOUR_VALUE \
-e accessToken=YOUR_VALUE \
-e tokenSecret=YOUR_VALUE \
jrvs/twitter_app show 1276568976764686343 "id,text,coordinates"
{
"id" : 1276568976764686343,
"text" : "test post",
"coordinates" : {
"coordinates" : [ 79.0, 43.0 ],
"type" : "Point"
}
}

docker run --rm \
-e consumerKey=YOUR_VALUE \
-e consumerSecret=YOUR_VALUE \
-e accessToken=YOUR_VALUE \
-e tokenSecret=YOUR_VALUE \
jrvs/twitter_app delete 1200145224103841792

Sample JSON output:
{
"created_at" : "Fri Jun 26 17:32:16 +0000 2020",
"id" : 1276568976764686343,
"id_str" : "1276568976764686343",
"text" : "test post",
"entities" : {
"hashtags" : [ ],
"user_mentions" : [ ]
},
"coordinates" : {
"coordinates" : [ 79.0, 43.0 ],
"type" : "Point"
},
"retweet_count" : 0,
"favorite_count" : 0,
"favorited" : false,
"retweeted" : false
}

# Design

## UML diagram

## explain each component(app/main, controller, service, DAO) (30-50 words each)

app/main

controller

service

DAO

## Models

Talk about tweet model
-----------------------

Authn: OAuth 1.0a
https://developer.twitter.com/en/docs/authentication/oauth-1-0a
https://developer.twitter.com/en/docs/authentication/oauth-1-0a/percent-encoding-parameters

You have to sign each API request by passing several generated keys and tokens in an authorization
header. To start, you can generate several keys and tokens in your Twitter developer app’s details
page, including the following:
API key and secret:

oauth_consumer_key

oauth_consumer_secret

Think of these as the user name and password that represents your Twitter developer app when making
API requests.
Access token and secret:

oauth_token

oauth_token_secret

An access token and access token secret are user-specific credentials used to authenticate OAuth
1.0a API requests. They specify the Twitter account the request is made on behalf of.

You can generate your own access token and token secret if you would like your app to make requests
on behalf of the same Twitter account associated with your developer account on the Twitter
developer app's details page.

If you'd like to generate access tokens for a different user, see "Making requests on behalf of
users" below.


----------------------------------




Object Model:
---------------
https://developer.twitter.com/en/docs/twitter-api/data-dictionary/object-model/tweet

id (default)    string

text (default)   string
https://github.com/twitter/twitter-text/

author_id string

conversation_id string The Tweet ID of the original Tweet of the conversation (which includes direct
replies, replies of replies).

created_at date (ISO 8601)

geo object Contains details about the location tagged by the user in this Tweet, if they specified
one.

"geo": {
"coordinates": {
"type": "Point",
"coordinates": [
-73.99960455,
40.74168819
]
},
"place_id": "01a9a39529b27f36"
}

source string







Using Twitter APIv2
----------------------
example:

curl --request
GET 'https://api.twitter.com/2/tweets?ids=1212092628029698048&tweet.fields=attachments,author_id,context_annotations,created_at,entities,geo,id,in_reply_to_user_id,lang,possibly_sensitive,public_metrics,referenced_tweets,source,text,withheld&expansions=referenced_tweets.id'
--header 'Authorization: Bearer $BEARER_TOKEN'

{
"data": [
{
"id": "1212092628029698048",
"text": "We believe the best future version of our API will come from building it with YOU. Here’s
to another great year with everyone who builds on the Twitter platform. We can’t wait to continue
working with you in the new year. https://t.co/yvxdK6aOo2",
"possibly_sensitive": false,
"referenced_tweets": [
{
"type": "replied_to",
"id": "1212092627178287104"
}
],
"entities": {
"urls": [
{
"start": 222,
"end": 245,
"url": "https://t.co/yvxdK6aOo2",
"expanded_url": "https://twitter.com/LovesNandos/status/1211797914437259264/photo/1",
"display_url": "pic.twitter.com/yvxdK6aOo2"
}
],
"annotations": [
{
"start": 144,
"end": 150,
"probability": 0.626,
"type": "Product",
"normalized_text": "Twitter"
}
]
},
"author_id": "2244994945",
"public_metrics": {
"retweet_count": 8,
"reply_count": 2,
"like_count": 40,
"quote_count": 1
},
"lang": "en",
"created_at": "2019-12-31T19:26:16.000Z",
"source": "Twitter Web App",
"in_reply_to_user_id": "2244994945",
"attachments": {
"media_keys": [
"16_1211797899316740096"
]
},
"context_annotations": [
{
"domain": {
"id": "119",
"name": "Holiday",
"description": "Holidays like Christmas or Halloween"
},
"entity": {
"id": "1186637514896920576",
"name": " New Years Eve"
}
},
{
"domain": {
"id": "119",
"name": "Holiday",
"description": "Holidays like Christmas or Halloween"
},
"entity": {
"id": "1206982436287963136",
"name": "Happy New Year: It’s finally 2020 everywhere!",
"description": "Catch fireworks and other celebrations as people across the globe enter the new year.\nPhoto via @GettyImages "
}
},
{
"domain": {
"id": "46",
"name": "Brand Category",
"description": "Categories within Brand Verticals that narrow down the scope of Brands"
},
"entity": {
"id": "781974596752842752",
"name": "Services"
}
},
{
"domain": {
"id": "47",
"name": "Brand",
"description": "Brands and Companies"
},
"entity": {
"id": "10045225402",
"name": "Twitter"
}
},
{
"domain": {
"id": "119",
"name": "Holiday",
"description": "Holidays like Christmas or Halloween"
},
"entity": {
"id": "1206982436287963136",
"name": "Happy New Year: It’s finally 2020 everywhere!",
"description": "Catch fireworks and other celebrations as people across the globe enter the new year.\nPhoto via @GettyImages "
}
}
]
}
],
"includes": {
"tweets": [
{
"possibly_sensitive": false,
"referenced_tweets": [
{
"type": "replied_to",
"id": "1212092626247110657"
}
],
"text": "These launches would not be possible without the feedback you provided along the way, so
THANK YOU to everyone who has contributed your time and ideas. Have more feedback? Let us know
⬇️ https://t.co/Vxp4UKnuJ9",
"entities": {
"urls": [
{
"start": 187,
"end": 210,
"url": "https://t.co/Vxp4UKnuJ9",
"expanded_url": "https://twitterdevfeedback.uservoice.com/forums/921790-twitter-developer-labs",
"display_url": "twitterdevfeedback.uservoice.com/forums/921790-…",
"images": [
{
"url": "https://pbs.twimg.com/news_img/1261301555787108354/9yR4UVsa?format=png&name=orig",
"width": 100,
"height": 100
},
{
"url": "https://pbs.twimg.com/news_img/1261301555787108354/9yR4UVsa?format=png&name=150x150",
"width": 100,
"height": 100
}
],
"status": 200,
"title": "Twitter Developer Feedback",
"description": "Share your feedback for the Twitter developer platform",
"unwound_url": "https://twitterdevfeedback.uservoice.com/forums/921790-twitter-developer-labs"
}
]
},
"author_id": "2244994945",
"public_metrics": {
"retweet_count": 3,
"reply_count": 1,
"like_count": 17,
"quote_count": 0
},
"lang": "en",
"created_at": "2019-12-31T19:26:16.000Z",
"source": "Twitter Web App",
"in_reply_to_user_id": "2244994945",
"id": "1212092627178287104"
}
]
}
}

#### Tweet ID

{"id": 10765432100123456789, "id_str": "10765432100123456789"}
In Twitter APIs up to version 1.1, you should always use the string representation of the number to
avoid losing accuracy.
In newer versions of the API, all large integer values are represented as strings by default.
https://developer.twitter.com/en/docs/twitter-ids

####     

## Spring

- How you managed the dependencies using Spring?

# Test

How did you test you app using Junit and mockito?

## Deployment

How did you dockerize your app.

# Improvements

- Imporvement 1
- Imporvement 2
- Imporvement 3