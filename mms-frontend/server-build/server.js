#!/usr/bin/env node
const compression = require('compression');
const express = require("express"),
    app = express(),
    bodyParser = require('body-parser'),
    errorHandler = require('errorhandler'),
    methodOverride = require('method-override'),
    hostname =  'localhost',
    port =  3000,
    publicDir =  __dirname + '/public/browser',
    path = require('path');
const fs = require('fs');

const brotli = require('brotli');
const zlib = require('zlib');
const device = require('express-device');

app.use(device.capture());


//require('express-http2-workaround')({ express:express, http2:http2, app:app });


const allowedExt = [
    '.js',
    '.ico',
    '.css',
    '.png',
    '.jpg',
    '.woff2',
    '.woff',
    '.ttf',
    '.svg',
    '.webp'
];

// compress all responses
app.use(compression());

const brotliSettings = {
    extension: 'br',
    skipLarger: true,
    mode: 1, // 0 = generic, 1 = text, 2 = font (WOFF2)
    quality: 10, // 0 - 11,
    lgwin: 12 // default
};
var dirs = ['public'];
dirs.forEach(dir => {
    fs.readdirSync(dir).forEach(file => {
        if (file.endsWith('.js') || file.endsWith('.css') || file.endsWith('.html')) {
            // brotli
            const result = brotli.compress(fs.readFileSync(dir + '/' + file), brotliSettings);
            fs.writeFileSync(dir + '/' + file + '.br', result);
            // gzip
            const fileContents = fs.createReadStream(dir + '/' + file);
            const writeStream = fs.createWriteStream(dir + '/' + file + '.gz');
            const zip = zlib.createGzip();
            fileContents
                .pipe(zip)
                .on('error', err => console.error(err))
                .pipe(writeStream)
                .on('error', err => console.error(err));
        }
    })
});


app.get("/*", function (req, res) {

    if (allowedExt.filter(function (ext) { return req.url.indexOf(ext) > 0; }).length > 0) {
           res.sendFile(path.resolve(publicDir + req.url));
    }
    else {
        res.sendFile(path.resolve(`${publicDir}/index.html`));
    }
});

app.use(function(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*", "/admin/*");
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
  res.header("Accept-Encoding", "deflate, br, gzip;q=1.0, *;q=0.5");
  res.setHeader('Content-Encoding', 'deflate');
  res.setHeader('Content-Encoding', 'gzip');
  res.setHeader('Content-Encoding', 'br');
  next();
});

app.use(methodOverride());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
  extended: true
}));

app.use(express.static(publicDir));
app.use(errorHandler({
  dumpExceptions: true,
  showStack: true
}));

// const options = {
//     key: fs.readFileSync(path.join(__dirname, '/privateKey.key')),
//     cert: fs.readFileSync(path.join(__dirname, '/certificate.crt'))
// };

app.listen(port, (err) => {
    if (err) {
        throw new Error(err);
    }

    console.log("MMS Bank Trading Client Portal listening at http://%s:%s", hostname, port);
});
