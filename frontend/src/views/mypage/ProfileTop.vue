<template>
    <div class="container-fluid overcover">
        <div class="container profile-box">
            <div class="top-cover">
                <div class="covwe-inn">
                    <div class="row no-margin">
                        <div class="col-md-3 img-c">
                            <img src="urban-20230228144115810458.jpg" alt="">
                        </div>
                        <div class="col-md-9 tit-det">
                            <h2>Angelina Smith / Web Designer</h2>
                            <p>
                            Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries,text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries,
                            </p>
                        </div>
                    </div>
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
    background-color: rgba(0, 0, 0, 0.4);
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
      padding-left: 10px;
      margin: auto; }
      .overcover .covwe-inn .tit-det h2 {
        color: #FFF;
        font-size: 2.2rem; }
        @media screen and (max-width: 450px) {
          .overcover .covwe-inn .tit-det h2 {
            font-size: 1.8rem; } }
      .overcover .covwe-inn .tit-det p {
        color: #FFF;
        font-size: .9rem;
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



</style>