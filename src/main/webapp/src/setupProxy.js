const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
    app.use(
        '/forecast',
        createProxyMiddleware({
            target: 'https://api.darksky.net/',
            changeOrigin: true,
        })
    );
    app.use(
        '/api',
        createProxyMiddleware({
            target: 'http://localhost:8080',
            changeOrigin: true,
        })
    );
};