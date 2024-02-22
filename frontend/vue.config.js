const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  outputDir: '../src/main/resources/static', // Build Directory
    devServer: {   
        // proxy: {
        //   '/':{
        //     target:process.env.VUE_APP_API_URL, // Spring Boot Server
        //     changeOrigin:true,
        //   },
        //    '/logout':{
        //     target:process.env.VUE_APP_API_URL, // Spring Boot Server
        //      changeOrigin:true,
        //   },
        //   '/oauth2':{
        //     target:process.env.VUE_APP_API_URL, // Spring Boot Servernpm
        //     changeOrigin:true,
        //     '^/oauth2':'/login/oauth2/code/'
        //   }
        // }       
    }
})