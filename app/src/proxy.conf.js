const PROXY_CONFIG = [
    {
      context: ['/films', '/users', '/login','/home','/register','/rateAvg','/rating'],
      target: 'http://localhost:8080',
      secure: true,
      logLevel: 'debug',
    }
  ]
  
  module.exports = PROXY_CONFIG;