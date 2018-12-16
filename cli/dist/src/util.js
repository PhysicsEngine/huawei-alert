"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var xml2js = require('xml2js');
var js2xml = new xml2js.Builder();
var crypto = require('crypto');
var request = require('request');
var utilities = {};
var errorCodes = {
    "100002": "No support",
    "100003": "Access denied",
    "100004": "Busy",
    "108001": "Wrong username",
    "108002": "Wrong password",
    "108003": "Already logged in",
    "120001": "Voice busy",
    "125001": "Wrong __RequestVerificationToken header",
    "125002": "Bad request, generic",
    "125003": "Session tokens missing",
    "100008": "Unknown",
    "108006": "Wrong password",
};
utilities.contactRouter = function (uri, token, post, callback) {
    var options = {
        url: uri,
        headers: {
            'Cookie': token.cookies,
            '__RequestVerificationToken': token.token,
            'DNT': '1'
        }
    };
    if (post) {
        options.method = 'POST';
        if (typeof post === "object")
            options.form = js2xml.buildObject({ request: post });
        else
            options.form = post;
    }
    request(options, function (error, response, body) {
        if (error)
            callback(error, null);
        if (response.headers['set-cookie'])
            token.cookies = response.headers['set-cookie'][0].split(';')[0];
        if (response.headers['__requestverificationtoken'])
            token.token = response.headers['__requestverificationtoken'];
        xml2js.parseString(body, function (error, response) {
            if (response.error) {
                if (errorCodes[response.error.code])
                    callback(errorCodes[response.error.code]);
                else
                    callback(new Error(response.error.code + ': ' + response.error.message));
            }
            else {
                callback(error, response.response);
            }
        });
    });
};
console.log(utilities);
module.exports = utilities;
//# sourceMappingURL=util.js.map