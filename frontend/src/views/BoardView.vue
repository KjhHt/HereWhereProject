<template>
  <button @click="writeForm" >글작성</button>


  <WriteForm v-if="open" @close="writeForm" @update="handleUpdate"/>
  
  <DetailForm v-if="detailOpen" 
      @Detailclose="detailForm" 
      :boardList="boardList" 
      :boardDetail="boardDetail" 
      :imageSrc="imageSrc" 
      :formatTimeAgo="formatTimeAgo" 
  />


  <div v-for="(boardItem) in boardList" :key="boardItem.board_no" ref="boardRef">
      <div class="item">
      <div class="image">
          <div>
            <img :src="boardItem['profileimage']" />
          </div>
      </div>
      <div class="details">
          <div class="header-container">
      <h1 class="d-inline">{{ boardItem.board_writer }}</h1>
      <p class="d-inline">{{ formatTimeAgo(boardItem.board_createtime) }}</p> <!-- You can also use a span tag here -->
      </div>
          <div :id="'carouselExample' + boardItem.board_no" class="carousel slide">
          <div class="carousel-inner">
            <div v-for="(images, key) in imageSrc" :key="key">
              <!--이부분 이미지-->
              <div v-if=" boardItem.board_no === key ">
                  <div v-for="(image, index) in images" :key="index" class="carousel-item" :class="{ active: index === 0 }">
                      <img :src="image" class="d-block w-100" alt="...">
                  </div>
              </div>
              <!--이부분-->
            </div>
          </div>
          <button class="carousel-control-prev" type="button" :data-bs-target="'#carouselExample' + boardItem.board_no" data-bs-slide="prev">
              <span class="carousel-control-prev-icon" aria-hidden="true"></span>
              <span class="visually-hidden">Previous</span>
          </button>
          <button class="carousel-control-next" type="button" :data-bs-target="'#carouselExample' + boardItem.board_no" data-bs-slide="next">
              <span class="carousel-control-next-icon" aria-hidden="true"></span>
              <span class="visually-hidden">Next</span>
          </button>
          </div>
          <div class="icons">
            <i @click="like(boardItem.board_no)" :id="'like'+boardItem.board_no" :class="boardItem.like?'bi bi-heart-fill':'bi bi-heart'"></i>
            <i @click="detailForm(boardItem.board_no,boardItem.board_writer,boardItem.profileimage)" class="bi bi-chat-left"></i>
            <i class="bi bi-send fill"></i>
          </div>    
          <div v-if="Number(boardItem.like_count) === 0">
            <div>가장 먼저 좋아요를 눌러보세요</div>
          </div>
          <div v-else>
            <div>좋아요 {{ boardItem.like_count }}개</div>
          </div>
          <!-- v-html로 바꿨는데 css손봐야할꺼같음 div > p 안먹는듯?-->
          <div v-html="boardItem.board_content"></div>
          <div v-if=" Number(boardItem.comment_count) && Number(boardItem.comment_count) !== 0 ">
            <div id="commentDiv" @click="detailForm(boardItem.board_no,boardItem.board_writer,boardItem.profileimage)" >댓글 {{ boardItem.comment_count }}개 모두 보기</div>
          </div>
          

      </div>
      </div>
  </div>
  
</template>

<script setup>
  import { ref , onMounted  } from 'vue';
  import WriteForm from '@/components/board/WriteForm.vue'
  import DetailForm from '@/components/detail/DetailView.vue'
  import axios from 'axios';

  const boardList = ref([]);
  const imageSrc = ref({});
  const image = ref({});
  const num = ref(0);
  const firstList = ref(false);
  const boardRef=ref(null);
  const open=ref(false);
  const detailOpen=ref(false);
  const boardDetail=ref([]);

  const writeForm=()=>{
      open.value=!open.value;
  }

  const detailForm=(no,writer,profileimage)=>{
    boardDetail.value[0]=no;
    boardDetail.value[1]=writer;
    boardDetail.value[2]=profileimage;
    detailOpen.value = !detailOpen.value;
  }


  const options = {
      root: document.querySelector("#scrollArea"),
      rootMargin: "0px",
      threshold: 0.5,
    };
  onMounted(async () => {
    await list(num.value);
    firstList.value=true;
    if(firstList.value){
      const target = boardRef.value[boardRef.value.length - 1];
      observer.observe(target);
    }
  });

  const observer = new IntersectionObserver(scrollEvent,options);

  function scrollEvent(entries,observe){
    entries.forEach((entry) => {
      if (entry.isIntersecting) {
          if(firstList.value){
            num.value += 3;
            list(num.value);  
            observe.unobserve(entry.target);
          }
        }
    });
  }

  function like(no){
    console.log(no);
    axios.get('http://localhost:8080/user/like?board_no='+no)
    .then(res => {
      console.log(res.data);
      if(res.data){
        console.log(boardList.value); 
        for (const board of boardList.value) {
          if(board.board_no === no){
            board.like_count++;
            board.like = true;
          }
        }
      }
      else{
        for (const board of boardList.value) {
          if(board.board_no === no){
            board.like_count--;
            board.like = false;
          }
        }
      }
    })
    .catch(err => console.log(err))
  }

  async function list(num) {
    try {
      const res = await axios.get('http://localhost:8080/board?num=' + num);
      const newBoardList = res.data;
      for (const board of newBoardList) {
        const filenames = board.board_imageFileName ? board.board_imageFileName.split(',') : [];
        if (!image.value[board.board_no]) {
          image.value[board.board_no] = [];
        }
        for (const filename of filenames) {
          image.value[board.board_no].push(`${filename}`);
        }

        await fetchProfileImage(board['profileimage'], board);

      }
    boardList.value = [...boardList.value, ...newBoardList];
    await test();
    if(firstList.value){
      const target = boardRef.value[boardRef.value.length - 1];
      observer.observe(target);
      if(newBoardList.length === 0){
        console.log('다가져옴')
        observer.disconnect();
      }
    }
    return Promise.resolve();
    }
    catch (err) {
      console.error(err);
    }
  }
  async function fetchProfileImage(profileimage, board) {

    if (profileimage === '0') {
      board['profileimage'] = require('@/assets/dino.jpg');
    } 
    else {
        if (profileimage.startsWith("D:") || profileimage.startsWith("E:") ) {
          const pathSegments = profileimage.split('\\');
          const lastSegment = pathSegments[pathSegments.length - 1];
          
          try {
            const res = await axios.get(`http://localhost:8080/profile/${lastSegment}`);
            const dataURI = `data:${res.headers['content-type']};base64,${res.data}`;
            board['profileimage'] = dataURI;
          } 
          catch (err) {
            console.error(err);
          }
        } 
        else {
          board['profileimage'] = profileimage;
        }
     }
  }

  async function test(){
      for (const key of Object.keys(image.value)) {
          const innerArray = image.value[key];
          for (const imageName of innerArray) {
              await updateMessage(key,imageName);
          }
      }
  }

  async function updateMessage(key,imageName) {
      try {
          const res = 
              await axios.get(`http://localhost:8080/image/${imageName}`,
              { responseType: 'arraybuffer' });
          const base64Image = await arrayBufferToBase64(res.data);
          imageSrc.value[key] = imageSrc.value[key] || [];
          imageSrc.value[key].push(base64Image);
      } 
      catch (err) {
          console.error(err);
      }
  }

  function arrayBufferToBase64(buffer) {
      const blob = new Blob([buffer]);
      return new Promise((resolve) => {
          const fileReader = new FileReader();
          fileReader.onload = function () {
          resolve(fileReader.result);
          };
          fileReader.readAsDataURL(blob);
      });
  }

   const handleUpdate = async (newBoard) => {
    console.log('확인용! : ',[newBoard]);
      const newBoardList = [newBoard];
      for (const board of newBoardList) {
        const filenames = board.board_imageFileName ? board.board_imageFileName.split(',') : [];
        if (!image.value[board.board_no]) {
          image.value[board.board_no] = [];
        }
        for (const filename of filenames) {
          image.value[board.board_no].push(`${filename}`);
        }
        console.log( board['profileimage'] );
        console.log( board );
        await fetchProfileImage(board['profileimage'], board);
      }
      boardList.value = [...[newBoard] , ...boardList.value];
      console.log(boardList.value);
      await test();
  }

  function formatTimeAgo(time){
    const currentTime = new Date();
    const postedTime = new Date(time);
    const timeDifference = currentTime - postedTime;

    const seconds = Math.floor(timeDifference) / (1000);
    const minutes = Math.floor(timeDifference / (1000 * 60));
    const hours = Math.floor(timeDifference / (1000 * 60 * 60));
    const days = Math.floor(timeDifference / (1000 * 60 * 60 * 24));
    
    if (seconds < 60) {
        return `${seconds.toFixed(0)}초 전`;
    } else if (minutes < 60) {
        return `${minutes}분 전`;
    } else if (hours <= 23) {
        return `${hours}시간 전`;
    } else {
        return `${days}일 전`;
    }
  }


</script>
  <style scoped>
  .header-container {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .item {
    display: flex;
    max-width: 60%;
    margin: 0 auto;
    .image {
      padding: 1.5em 2em;
      > div {
        position: relative;
        font-size: 0.9em;
        &::after {
          content: '';
          width: 100%;
          height: 0;
          position: absolute;
          top: 2.75em;
          left: 2.5em;
          z-index: -1;
        }
      }
      img {
        width: 60px;
        min-width: 60px;
        max-width: 60px;
        border-radius: 50%;
        height: 4em;
      }
      span {
        display: block;
        clear: both;
        padding: 0.25em 0;
        margin: 0.5em 0;
      }
    }
    .details {
      position: relative;
      flex-grow: 1;
      text-align: left;
      padding-top: 1em;
      h1 {
          font-size: 1.2em;
      }
      > div {
        border-radius: 0.5em;
        padding: 0.5em 0 1em 0;
        margin: 0;
        p {
          margin: 0;
          padding: 0;
          line-height: 150%;
        }
      }
      &::before {
        content: '';
        width: 0;
        height: 100%;
        border-left: 2px solid #6E748A;
        position: absolute;
        top: 0;
        left: -3.9em;
        z-index: -1;
      }
    }
  }
  .icons {
    display: flex;
    justify-content: flex-start;
    cursor: pointer;
    gap: 10px; /* 아이콘 사이의 간격 */
  }
  #commentDiv {
    cursor: pointer;
  }
  </style>