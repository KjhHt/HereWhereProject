const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  outputDir: '../src/main/resources/static', // Build Directory
    devServer: {
        proxy: {
          '/':{
            target:'http://localhost:8080', // Spring Boot Server
            changeOrigin:true,
          },
          '/oauth2':{
            target:'http://localhost:8080', // Spring Boot Server
            changeOrigin:true,
            '^/oauth2':'/login/oauth2/code/'
          }
        }
    }
})