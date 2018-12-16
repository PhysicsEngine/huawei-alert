// import * as puppeteer from 'puppeteer';
import * as request from 'request-promise-native';
import * as router from 'dialog-router-api';
// import * as utilities from 'util';

export class Scraper {
  private baseUrl: string;

 constructor(baseUrl: string) {
  this.baseUrl = baseUrl;
 }

 debug() {
   console.log(this.baseUrl);
 }

 macAddresses(notification: string) {
  (async () => {
    const r = router.create({
      gateway: '192.168.128.1'
    });
    r.getToken(function(error, token) {
      r.getBasicSettings(token, function(error, response){
        r.login(token, 'admin', 'admin', () => {
          r.getHosts(token, (err, hosts) => {
            const macAddresses = hosts.Hosts[0].Host.map((h) => {
              return h.MacAddress[0];
            })
            const req = {
              'mac_addresses': macAddresses,
              'notification': notification,
              'device_id': 'dummy'
            };
            const url = 'https://huawei-alert-server.herokuapp.com/api/notification';
            const options = {
              method: 'POST',
              uri: url,
              body: req,
              json: true,
              headers: {
                'Content-Type': 'application/json'
              }
            };
            console.log(`All devices`);
            macAddresses.map(d => {
              console.log(`- ${d}`);
            });
            request.post(options);
            console.log(`Succeed to notify to ${notification}`);
          });
        });
      });
    });    
   })();   
 }
}