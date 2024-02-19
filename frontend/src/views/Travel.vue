<template>
        <div class="feature-section">
            <h2 class="left-aligned slide-in-from-right">여행지 추천 TOP5</h2>
            <div class="image-list slide-in-from-left">
              <div class="image-list">
                <div v-for="n in 5" :key="n" class="image-item">
                  <img :src="`https://picsum.photos/479/479?index=${n}`" class="rounded-img"/>
                  <div class="image-info">
                    <div class="info-top">
                      <span class="place-name">장소 이름</span>
                      <span class="rating">★3.0</span>
                    </div>
                    <div class="address">주소</div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="feature-section">
            <h2 class="left-aligned slide-in-from-right">MBTI 여행 추천 TOP5</h2>
            <div class="image-list slide-in-from-left">
              <div v-for="n in 5" :key="n" class="image-item">
                <img :src="`https://picsum.photos/479/479?index=${n+5}`" class="rounded-img"/>
                  <div class="image-info">
                    <div class="info-top">
                      <span class="place-name">장소 이름</span>
                      <span class="rating">★3.0</span>
                    </div>
                  <div class="address">주소</div>
                </div>
            </div>
          </div>
        </div>
          <div class="feature-section">
            <h2 class="left-aligned slide-in-from-right">영화 여행지 추천 TOP5</h2>
            <div class="image-list slide-in-from-left">
              <div v-for="n in 5" :key="n" class="image-item">
                <img :src="`https://picsum.photos/479/479?index=${n+10}`" class="rounded-img"/>
                  <div class="image-info">
                    <div class="info-top">
                      <span class="place-name">장소 이름</span>
                      <span class="rating">★3.0</span>
                    </div>
                  <div class="address">주소</div>
                </div>
            </div>
          </div>
        </div>
</template>
<script>
import { gsap } from 'gsap';
import { ScrollTrigger } from 'gsap/ScrollTrigger';

export default {
    name: 'TravelPage',
    mounted() {
    gsap.registerPlugin(ScrollTrigger);
    this.initGSAP();
  },
  methods: {
    initGSAP() {
      function animateFrom(elem, x, y) {
    elem.style.transform = `translate(${x}px, ${y}px)`;
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
          onEnter: function () { animateFrom(elem, 0, 100) },
          onEnterBack: function () { animateFrom(elem, 0,-100) },
          onLeave: function () { hide(elem) } // assure that the element is hidden when scrolled into view
        });
      });
      
      gsap.utils.toArray(".slide-in-from-left").forEach(function (elem) {
    hide(elem); // assure that the element is hidden when scrolled into view
    ScrollTrigger.create({
      trigger: elem,
      markers: false,
      onEnter: function () { animateFrom(elem, -100, 0) },
      onEnterBack: function () { animateFrom(elem, 100, 0) },
      onLeave: function () { hide(elem) } // assure that the element is hidden when scrolled into view
    });
  });

      gsap.utils.toArray(".slide-in-from-right").forEach(function (elem) {
    hide(elem); // assure that the element is hidden when scrolled into view
    ScrollTrigger.create({
      trigger: elem,
      markers: false,
      onEnter: function () { animateFrom(elem, 100, 0) },
      onEnterBack: function () { animateFrom(elem, -100, 0) },
      onLeave: function () { hide(elem) } // assure that the element is hidden when scrolled into view
    });
  });
    }
  }
    
}
</script>
<style scoped>
     body {
  font-weight: 300;
}


img {
  max-width: 100%;
}

.image-list {
  display: flex; /* 이미지를 가로로 나열 */
  gap: 40px;
}

.rounded-img {
  width: 100%;  /* 이미지 폭을 부모 요소의 100%로 설정 */
  height: auto;  /* 이미지 높이를 자동으로 설정 */
  border-radius: 10px;  /* 이미지 모서리를 둥글게 설정 */
  margin-bottom: 10px;
}

.image-list {
  display: grid;  /* 이미지 리스트를 그리드 레이아웃으로 설정 */
  gap: 40px;
  grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));  /* 최소 너비 320px의 열을 가능한 많이 만들되, 열 너비가 320px보다 커지면 공간을 고르게 분배 */
}

.image-list img:last-child {
  margin-right: 0; /* 마지막 이미지의 오른쪽 마진 제거 */
}

.left-aligned {
  text-align: left;
}

.feature-section:not(:last-child) {
  margin-bottom: 100px;
}

.image-item {
  display: flex; /* 이미지와 이미지 정보를 세로로 배치 */
  flex-direction: column; /* 요소들을 세로로 배치 */
}



.info-top {
  display: flex; /* 장소 이름과 별점을 한 줄에 표시 */
  justify-content: space-between; /* 장소 이름과 별점 사이에 공간 삽입 */
}

.address {
  text-align: left; /* 주소 왼쪽 정렬 */
  color:gray
}
.place-name {
  font-weight: bold;
}


</style>