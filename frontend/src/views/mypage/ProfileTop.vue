<template>
  <div class="container-fluid overcover">
      <div class="container profile-box">
          <div class="top-cover">
              <div class="covwe-inn">
                <header class="profile-header">
                  <div class="container">
                    <div class="profile">
                      <div class="profile-image">
                        <!-- 프로필 이미지 -->
                        <img src="https://images.unsplash.com/photo-1513721032312-6a18a42c8763?w=152&h=152&fit=crop&crop=faces" alt="">
                      </div>
                      <div class="profile-user-info">
                      <!-- 유저 이름 및 추천 아이콘 -->
                      <div class="profile-user-settings">
                        <h1 class="profile-user-name">janedoe_</h1>
                        <img src="@/assets/user-plus.png" alt="Recommened Account" width="34" height="32">
                      </div>
                      <!-- 포스트, 팔로워, 팔로잉 수 -->
                      <div class="profile-stats">
                        <ul>
                          <li><span class="profile-stat-count">164</span> posts</li>
                          <li><span class="profile-stat-count">188</span> followers</li>
                          <li><span class="profile-stat-count">206</span> following</li>
                        </ul>
                      </div>
                    </div>
                  </div>
                </div>  
              </header> 
            </div>
        </div>
          <ul class="nav nav-tabs" id="myTab" role="tablist">
            <li class="nav-item">
              <a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="true">Home</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false">Profile</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" id="profile-tab" data-toggle="tab" href="#resume" role="tab" aria-controls="profile" aria-selected="false">Resume</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" id="profile-tab" data-toggle="tab" href="#gallery" role="tab" aria-controls="profile" aria-selected="false">Portfolio</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" id="contact-tab" data-toggle="tab" href="#contact" role="tab" aria-controls="contact" aria-selected="false">Contact</a>
            </li>
          </ul>
      
      </div>
  </div>

  </template>
<script setup>

</script>
<style scoped>
/* =======================================================================
Template Name: Dil Hospital
Author:  SmartEye Adminpanel
Author URI: www.smarteyeapps.com
Version: 1.0
coder name:Prabin Raja
Description: This Template is created for web template
======================================================================= */
/* ===================================== Import Less ================================== */

/* ===================================== Basic CSS ================================== */
* {
margin: 0px;
padding: 0px;
list-style: none; }

img {
max-width: 100%; }

a {
text-decoration: none;
outline: none;
color: #444; }

a:hover {
color: #444; }

ul {
margin-bottom: 0;
padding-left: 0; }

a:hover,
a:focus,
input,
textarea {
text-decoration: none;
outline: none; }

.center {
text-align: center; }

.left {
text-align: left; }

.right {
text-align: right; }

.cp {
cursor: pointer; }


p {
margin-bottom: 0px;
width: 100%; }

.no-padding {
padding: 0px; }

.no-margin {
margin: 0px; }




.h-100 {
height: 100%; }


.container-fluid {
padding: 0px; }

h1, h2, h3, h4, h5, h6 {
font-family: "mouse-500", Arial, Helvetica, sans-serif; }

strong {
font-family: "mouse-500", Arial, Helvetica, sans-serif; }






.page-nav {
padding: 40px;
text-align: center;
padding-top: 160px; }
.page-nav ul {
  float: none;
  margin: auto; }
.page-nav h2 {
  font-size: 36px;
  width: 100%;
  color: #444; }
  @media screen and (max-width: 600px) {
    .page-nav h2 {
      font-size: 26px; } }
.page-nav ul li {
  float: left;
  margin-right: 10px;
  margin-top: 10px;
  font-size: 16px; }
  .page-nav ul li i {
    width: 30px;
    text-align: center;
    color: #444; }
  .page-nav ul li a {
    color: #444; }

.btn-success {
background-color: #00ab9f;
border-color: #00ab9f; }
.btn-success:hover {
  background-color: #00ab9f !important;
  border-color: #00ab9f !important; }
.btn-success:active {
  background-color: #00ab9f !important;
  border-color: #00ab9f !important; }
.btn-success:focus {
  background-color: #00ab9f !important;
  border-color: #00ab9f !important;
  box-shadow: none !important; }

.btn-info {
background-color: #4f6dcd;
border-color: #4f6dcd; }
.btn-info:hover {
  background-color: #4f6dcd !important;
  border-color: #4f6dcd !important; }
.btn-info:active {
  background-color: #4f6dcd !important;
  border-color: #4f6dcd !important; }
.btn-info:focus {
  background-color: #4f6dcd !important;
  border-color: #4f6dcd !important;
  box-shadow: none !important; }

.btn {
box-shadow: 0 3px 1px -2px rgba(0, 0, 0, 0.2), 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 1px 5px 0 rgba(0, 0, 0, 0.12);
border-radius: 2px; }

.form-control:focus {
box-shadow: none !important;
border: 2px solid  #00a8df; }

.btn-light {
background-color: #FFF;
color: #3F3F3F; }

.collapse.show {
display: block !important; }

.form-control:focus {
box-shadow: none;
border: 2px solid #0d7a40 !important; }

.form-control {
background-color: #F8F8F8;
margin-bottom: 20px; }
.form-control:focus {
  background-color: #FFF;
  border-color: #CCC; }

.container {
max-width: 1100px; }
@media screen and (max-width: 1060px) {
  .container {
    max-width: 100%;
    overflow: hidden; } }

@media screen and (max-width: 1060px) {
.overcover {
  padding: 10px; } }

/* ===================================== Resume Template CSS ================================== */
.top-cover {
/* background-image: url(../../../public/bg.jpg); */
background-size: cover;
width: 100%; }


.covwe-inn {
  background-color: rgba(0, 0, 0, 0);
  width: 100%;
  height: 100%;
  padding: 30px;
  display: flex; }
  @media screen and (max-width: 450px) {
    .covwe-inn {
      padding: 10px; } }
  .covwe-inn .img-c {
    padding: 20px; }
    .covwe-inn .img-c img {
      border-radius: 50%;
      background-color: #FFF;
      padding: 5px;
      box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.16), 0 2px 10px 0 rgba(0, 0, 0, 0.12);
      margin: auto; }
  .covwe-inn .tit-det {
    padding: 20px;
    padding-left: 20px;
    margin: auto;
     }
    .overcover .covwe-inn .tit-det h2 {
      color: #000000;
      font-size: 2.0rem; }
      @media screen and (max-width: 450px) {
        .overcover .covwe-inn .tit-det h2 {
          font-size: 1.8rem; } }
    .overcover .covwe-inn .tit-det p {
      color: #000000;
      font-size: 1.0rem;
      text-indent: 20px; }
  @media screen and (max-width: 767px) {
    .overcover .covwe-inn {
      text-align: center; } }

.profile-box {
box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.16), 0 2px 10px 0 rgba(0, 0, 0, 0.12);
padding: 0px; }

.nav-tabs {
background-color: #FFF; }
.nav-tabs li a {
  padding: 10px 60px;
  font-size: 1.2rem; }
  @media screen and (max-width: 974px) {
    .nav-tabs li a {
      padding: 10px 30px;
      font-size: 1rem; } }
  @media screen and (max-width: 619px) {
    .nav-tabs li a {
      padding: 10px 10px; } }
  @media screen and (max-width: 420px) {
    .nav-tabs li a {
      font-size: .9rem;
      padding: 10px 5px; } }
.nav-tabs .active {
  border-radius: 0px;
  border-bottom: 3px solid #0062cc !important; }

  .profile {
  display: flex;
  padding: 2rem 0;
}

.profile::after {
  content: "";
  display: block;
  clear: both;
}

.profile-image {
  float: left;
  width: 152px; /* Adjust as needed */
  display: flex;
  justify-content: center;
  align-items: center;
  
}

.profile-image img {
  border-radius: 50%;
}

.profile-user-settings,
.profile-stats {
  float: left;
  width: calc(100% - 160px); /* Adjust as needed */
}

.profile-user-settings {
  display: flex;
  align-items: center;
}

.profile-user-name {
  display: inline-block;
  font-size: 1.7rem; /* Adjust as needed */
  font-weight: 300;
}

.profile-user-settings img {
  margin-left: 10px; /* 원하는 간격으로 조절하세요. */
}

.profile-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 0; /* 원하는 패딩 값으로 조절하세요. */
}
.profile-user-info {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-left: 20px;
}
.profile-stats {
  margin-top: 2.3rem;
}
.profile-stats ul {
  display: flex;
  list-style: none;
  padding: 0;
}
.profile-stats li {
  font-size: 1.0rem;
  line-height: 1.5;
  margin-right: 1rem;
  
}

.profile-stats li:last-of-type {
  margin-right: 0;
}

.profile-stats li span {
  display: inline-block;
  margin-right: 5px; /* 원하는 간격 조절 */
}

.profile-stats,
.profile-user-name {
  white-space: nowrap; /* 여러 줄 방지 */
}


.profile-stat-count
 {
  font-weight: 600;
}

.profile-images {
  display: flex;
}


.profile-user-settings,
.profile-stats {
  margin-top: 0;
}
</style>