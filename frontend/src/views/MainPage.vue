<template>
  <div id="main-page">
    <div class="cInnerContent">
      <h1 class="header-section gs_reveal ipsType_center" ><strong>이미지 검색 여행 서비스, HERE WHERE에 오신 것을 환영합니다!!!</strong></h1>
      <div class="features">
        <div class="feature ipsSpacer_bottom_double ipsGrid ipsGrid_collapsePhone">
          <div class="featured-image-container ipsGrid_span5 gs_reveal gs_reveal_fromLeft">
            
              <img width="479" src="https://picsum.photos/479/479?index=1" alt="">
            
          </div>

          <div class="ipsGrid_span7 ipsType_left">
            <h2 class="heading_large gs_reveal"><strong>위치 검색 및 주변 명소 추천</strong></h2>
            <p class="gs_reveal">내용</p>
          </div>
        </div>

        <div class="feature ipsSpacer_bottom_double ipsGrid ipsGrid_collapsePhone">
          <div class="ipsGrid_span7 ipsType_right">
            <h2 class="heading_large gs_reveal"><strong>여행 일정 관리</strong></h2>
            <p class="gs_reveal">내용</p>
          </div>

          <div class="featured-image-container ipsGrid_span5 gs_reveal gs_reveal_fromRight">
            
              <img width="479" src="https://picsum.photos/479/479?index=2" alt="">
            
          </div>
        </div>

        <div class="feature ipsSpacer_bottom_double ipsGrid ipsGrid_collapsePhone">
          <div class="featured-image-container ipsGrid_span5 gs_reveal gs_reveal_fromLeft">
            
              <img width="479" src="https://picsum.photos/479/479?index=3" alt="">
            
          </div>

          <div class="ipsGrid_span7 ipsType_left">
            <h2 class="heading_large gs_reveal"><strong>여행 게시물 공유</strong></h2>
            <p class="gs_reveal">내용</p>
          </div>
        </div>

        <div class="feature ipsSpacer_bottom_double ipsGrid ipsGrid_collapsePhone">
          <div class="ipsGrid_span7 ipsType_right">
            <h2 class="heading_large gs_reveal"><strong>나의 여행 지도</strong></h2>
            <p class="gs_reveal">내용</p>
          </div>

          <div class="featured-image-container ipsGrid_span5 gs_reveal gs_reveal_fromRight">
            
              <img width="479" src="https://picsum.photos/479/479?index=4" alt="">
            
          </div>
        </div>

        <div class="feature ipsSpacer_bottom_double ipsGrid ipsGrid_collapsePhone">
          <div class="featured-image-container ipsGrid_span5 gs_reveal gs_reveal_fromLeft">
            
              <img width="479" src="https://picsum.photos/479/479?index=5" alt="">
            
          </div>

          <div class="ipsGrid_span7 ipsType_left">
            <h2 class="heading_large gs_reveal"><strong>매칭 시스템</strong></h2>
            <p class="gs_reveal">내용</p>
          </div>
        </div>

      </div>
    </div>

    
  </div>
</template>

<script>
import { gsap } from 'gsap';
import { ScrollTrigger } from 'gsap/ScrollTrigger';
// import PanolensPage from './PanolensPage.vue';

export default {
  // components:{
  //   PanolensPage
  // },
  mounted() {
    gsap.registerPlugin(ScrollTrigger);
    this.initGSAP();
  },
  methods: {
    initGSAP() {
      function animateFrom(elem, direction) {
        direction = direction || 1;
        var x = 0,
            y = direction * 100;
        if (elem.classList.contains("gs_reveal_fromLeft")) {
          x = -100;
          y = 0;
        } else if (elem.classList.contains("gs_reveal_fromRight")) {
          x = 100;
          y = 0;
        }
        elem.style.transform = "translate(" + x + "px, " + y + "px)";
        elem.style.opacity = "0";
        gsap.fromTo(elem, { x: x, y: y, autoAlpha: 0 }, {
          duration: 1.25,
          x: 0,
          y: 0,
          autoAlpha: 1,
          ease: "expo",
          overwrite: "auto"
        });
      }

      function hide(elem) {
        gsap.set(elem, { autoAlpha: 0 });
      }

      gsap.registerPlugin(ScrollTrigger);

      gsap.utils.toArray(".gs_reveal").forEach(function (elem) {
        hide(elem); // assure that the element is hidden when scrolled into view

        ScrollTrigger.create({
          trigger: elem,
          markers: false,
          onEnter: function () { animateFrom(elem) },
          onEnterBack: function () { animateFrom(elem, -1) },
          onLeave: function () { hide(elem) } // assure that the element is hidden when scrolled into view
        });
      });
    }
  }
};
</script>

<style scoped>
body {
  font-weight: 300;
}
.ipsType_right {
  text-align: right;
}
.ipsType_center {
  text-align: center;
}
.cInnerContent {
  max-width: 1240px;
  margin-left: auto;
  margin-right: auto;
}
.ipsSpacer_bottom_double {
  margin-top: 30px;
  margin-bottom: 30px;
}
.ipsGrid {
  display: inline-block;
  display: -ms-flexbox;
  display: flex;
  flex-wrap: wrap;
}
.ipsGrid::before, .ipsGrid::after {
  display: table;
  content: "";
  line-height: 0;
}
.ipsGrid > [class*="ipsGrid_span"] {
  display: block;
  width: 100%;
  min-height: 30px;
  box-sizing: border-box;
}
.ipsGrid > .ipsGrid_span5 {
  width: 40.42553191489362%;
}
.ipsGrid > .ipsGrid_span7 {
  width: 57.44680851063829%;
}
.ipsGrid > [class*="ipsGrid_span"] {
  float: left;
  margin-left: 2%;
}
.ipsGrid > [class*="ipsGrid_span"]:first-child {
  margin-left: 0;
}
.feature {
  display: flex;
  align-items: center;
}

.featured-image-container img {
  width: 100%; /* 이미지를 부모 요소의 100%로 설정하여 확장 */
  height: auto; /* 높이는 자동으로 조절하여 원본 비율 유지 */
}
h2.heading_large {
  font-size: 1.8em;
}
img {
  max-width: 100%;
}

.header-section {
  margin: 0px auto;
  padding: 200px; /* 추가로 내용과의 간격을 주기 위한 패딩 설정 (원하는 값으로 조절) */
  background-image: url('@/assets/female_travel.png'); /* 이미지 경로 설정 (상대 경로 또는 모듈 경로 사용) */
  background-size: cover; /* 배경 이미지 크기를 커버로 설정 */
  background-position: center; /* 배경 이미지 위치를 가운데로 설정 */
  position: relative; /* 배경 이미지를 포함한 상대적인 위치 설정 */
  color: #ffffff
}

.gs_reveal {
  opacity: 0;
  visibility: hidden;
  will-change: transform, opacity;
}

/* 트랜지션 효과 정의 */
.main-page-enter-active, .main-page-leave-active {
  transition: opacity 0.5s;
}

.main-page-enter, .main-page-leave-to {
  opacity: 1;
}
</style>