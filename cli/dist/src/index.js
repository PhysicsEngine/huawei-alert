"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var pack = require("../package.json");
var program = require("commander");
var scraper_1 = require("./scraper");
program.version(pack["version"])
    .option('-n, --notification [sitefile]', "Notification target", function (val) { return val.toString(); })
    .parse(process.argv);
var scraper = new scraper_1.Scraper("http://web.setting/html");
scraper.macAddresses(program.notification);
//# sourceMappingURL=index.js.map