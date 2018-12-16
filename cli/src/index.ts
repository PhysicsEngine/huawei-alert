import * as pack from '../package.json';
import * as program from 'commander';
import {Scraper} from "./scraper";

program.version(pack["version"])
  .option('-n, --notification [sitefile]', "Notification target", (val) => val.toString())
  .parse(process.argv);

let scraper = new Scraper("http://web.setting/html")

scraper.macAddresses(program.notification);
