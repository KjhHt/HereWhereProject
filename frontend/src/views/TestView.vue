<template lang="">
    <div>
        <h1>로그인 페이지</h1>
        <hr/>
        <form @submit.prevent="login">
            <input type="text" name="id" placeholder="Username" v-model="id"/><br/>
            <input type="password" name="pwd" placeholder="Password" v-model="pwd"/><br/>
            <button>로그인</button>
        </form>
        
        <a href="/oauth2/authorization/google">구글 로그인</a> |
        <a href="/oauth2/authorization/facebook">페이스북 로그인</a> |
        <a href="/oauth2/authorization/kakao">카카오 로그인</a> |
        <a href="/oauth2/authorization/naver">네이버 로그인</a>

        <button @:click="test">쿠키테스트</button>
        <button @:click="user">user권한 테스트 + 전화번호 인증</button>
        <button @:click="manager">manager권한 테스트</button>
        <button @:click="admin">admin권한 테스트</button>


    </div>
</template>
<script>
import axios from 'axios';

export default {
    data() {
        return {
            id : '',
            pwd : ''
        }
    },  
    methods: {
        login(){
            const formdata = new FormData();
            formdata.append("id",this.id);
            formdata.append("pwd",this.pwd);
            axios.post('http://localhost:8080/login',formdata)
            .then(res => {
                console.log(res.status);
                this.$router.replace("test");
            })
            .catch(error => console.log(error))
        },
        user(){
            const url = '/user/test';
            axios.get(url)
            .then(res => {
                console.log(res);
            })
            .catch(error => console.log(error))
        },
        manager(){
            const url = '/manager/test';
            axios.get(url)
            .then(res => {
                console.log(res);
               
            })
            .catch(error => console.log(error))
        },
        admin(){
            const url = '/admin/test';
            axios.get(url)
            .then(res => {
                console.log(res);
            })
            .catch(error => console.log(error))
        }
    },
}
</script>
<style lang="">
    
</style>
