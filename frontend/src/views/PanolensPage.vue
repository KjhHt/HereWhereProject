<template>
  <transition name="fade" mode="out-in">
      <div v-if="showPanolensPage" class="panolens-page" ref="panolens-container">
        <panolens-scene>
          <panolens-image :src="currentPanoramaImage" />
        </panolens-scene>
        <section class="title" >
          <header>HERE WHERE</header>
          <article class="start-button" @click="goToAppPage">시작하기</article>
        </section>
      </div>
  </transition>
    </template>
    
    <script>
    /*
    import * as PANOLENS from 'panolens';
    
    export default {
      name: 'PanolensPage',
      data() {
        return {
          currentPanoramaImage: '',
          showPanolensPage: true, // 페이지가 보이는 상태로 초기화
        };
      },
      mounted() {
        this.updatePanoramaImage();
        this.initializePanolens();
      },
      methods: {
        updatePanoramaImage() {
          // 현재 시간을 기준으로 낮과 밤에 맞는 이미지 경로를 선택
          const currentTime = new Date().getHours();
          if (currentTime >= 6 && currentTime < 18) {
            this.currentPanoramaImage = require('@/assets/AdobeStock_358983728.webp');
          } else {
            this.currentPanoramaImage = require('@/assets/AdobeStock_495919355.jpeg');
          }
        },
        initializePanolens() {
          const viewer = new PANOLENS.Viewer({ 
            container: this.$refs['panolens-container'],
            controlBar: false,
            autoRotate: true, 
            autoRotateSpeed: 0.6,
          });
          const panorama = new PANOLENS.ImagePanorama(this.currentPanoramaImage); 
          viewer.add(panorama);
        },
        
        goToAppPage() {
            this.showPanolensPage = false;
            setTimeout(() => {
              // this.$router.push({ name: 'main' });
            this.$emit('selectPage','main')
            }, 1000); // 적절한 시간(1초) 후에 실제 페이지 이동
        },
      },
      watch: {
        currentPanoramaImage() {
          this.initializePanolens();
        },
      },
    };
    */
    </script>
    
    <style scoped>
    .panolens-page {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: 9999; /* 페이지 내 컨텐츠 위에 오도록 설정 */
    
  }
  
  
  
  
  .title {
    position: absolute;
    top: 50%; 
    left: 50%; 
    transform: translate(-50%, -50%); 
    text-align: center;
    width: 100%;
  }
  
  .title header {
    font-size: 72px;
    font-weight: 300;
    letter-spacing: 2px;
    color: white; 
    pointer-events: none;
  }
  
  .title article {
    font-size: 24px; 
    color: white;
    
  }
  
  .title article:hover {
    cursor: pointer;
   
  }
  
  .start-button {
      display: inline-block;
      cursor: pointer;
    }
  
  .fade-enter-active, .fade-leave-active {
    transition: transform 1s, opacity 1s;
  }
  .fade-enter, .fade-leave-to {
    transform: scale(2.0);
    opacity: 0;
  }
    </style>