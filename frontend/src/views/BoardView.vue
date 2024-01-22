<template>

<div class="feed">
    <!-- 글 작성 버튼 -->
  <button class="post-btn" @click="showPostModal = true">
      <i class="bi bi-plus-lg"></i> 
    </button>
    <!-- 글 작성 모달 -->
    <div class="modal" v-if="showPostModal">
      <div class="modal-content">
        <span class="close-button" @click="showPostModal = false">×</span>
        <textarea v-model="newPost" placeholder="여기에 글을 작성하세요"></textarea>
        <button @click="submitPost">글 작성</button>
      </div>
    </div>
  </div>

  <div class="feed">
      <div class="post" v-for="post in displayedPosts" :key="post.id">
        <div class="post-header">
          <div class="user">
            <img class="avatar" :src="post.user.avatar" :alt="post.user.username" />
            <span class="username">{{ post.user.username }}</span>
          </div>
        </div>
        <img class="post-image" :src="post.imageUrl" alt="Post" />
          <div class="post-actions">

  <button class="action-button" @click="toggleLike(post)">{{ post.liked ? '❤️' : '🤍' }}</button>

            <button class="action-button" @click="openCommentModal(post)">💬</button>
            <div v-if="post.showCommentModal">
          <div class="modal">
          <div class="modal-content">
          <span class="close-button" @click="closeCommentModal(post)">×</span>
              <textarea v-model="newComment" placeholder="댓글을 입력하세요"></textarea>
            <button @click="submitComment">댓글 작성</button>
          </div>
        </div>
      </div>

          <button class="action-button" @click="toggleSocialShareButtons(post)">🔗</button>
            <div class="modal" v-if="post.showSocialShareButtons">
              <div class="modal-content">
                <h3>Share this post</h3>
                  <button type="button">Facebook에 공유하기</button>
                  <button type="button">Twitter에 공유하기</button>
                  <button type="button">Instagram에 공유하기</button>
                  <button type="button" @click="toggleSocialShareButtons(post)">Close</button>
              </div>
          </div>
                  
          </div>
      </div>
      <div v-if="isLoading" class="loading">Loading...</div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      // showCommentModal: false, // 댓글 모달의 표시 상태를 관리
      newComment: '', // 새로 작성하는 댓글을 저장
      allPosts: [], // 모든 포스트를 저장할 배열
      displayedPosts: [], // 화면에 보여질 포스트를 저장할 배열
      page: 0,
      isLoading: false,
      showPostModal: false,  // 글 작성 모달의 보이기/숨기기를 제어하는 데이터
      newPost: ''  // 사용자가 입력한 글 내용을 저장하는 데이터
    };
  },
  async mounted() {
    this.fetchAllPosts(); // 동기적으로 더미 데이터를 가져옴
    this.displayPosts(); // 초기 화면에 게시물 5개를 보여줌
    window.addEventListener('scroll', this.handleScroll);
  },
  beforeUnmount() {
    window.removeEventListener('scroll', this.handleScroll);
  },
  methods: {
     toggleSocialShareButtons(post) {
      this.displayedPosts = this.displayedPosts.map(p => {
        if (p.id === post.id) {
          return { ...p, showSocialShareButtons: !p.showSocialShareButtons };
        } else {
          return p;
        }
      });
    },
     toggleLike(post) {
      this.displayedPosts = this.displayedPosts.map(p => {
        if (p.id === post.id) {
          return { ...p, liked: !p.liked };
        } else {
          return p;
        }
      });
    },
   openCommentModal(post) {
      this.displayedPosts = this.displayedPosts.map(p => {
        if (p.id === post.id) {
          return { ...p, showCommentModal: true };
        } else {
          return { ...p, showCommentModal: false };
        }
      });
    },
    closeCommentModal(post) {
      this.displayedPosts = this.displayedPosts.map(p => {
        if (p.id === post.id) {
          return { ...p, showCommentModal: false };
        } else {
          return p;
        }
      });
    },
    submitComment() {
      // 댓글을 서버로 전송하는 로직을 추가합니다
      console.log(this.newComment);
      this.newComment = '';
      this.closeCommentModal();
    },
     submitPost() {
      // 글 작성 처리를 여기서 합니다.
      // 예를 들어, 서버에 POST 요청을 보내거나 Vuex 액션을 디스패치할 수 있습니다.

      // 글 작성 처리가 끝나면 모달을 닫습니다.
      this.showPostModal = false;
      this.newPost = '';
    },
    fetchAllPosts() {
      // 더미 데이터 하드코딩
      const dummyPosts = [
        {
          id: 1,
          showSocialShareButtons: false,
          liked: false,
          showCommentModal: false,
          user: {
            username: 'user1',
            avatar: require('@/assets/Cat.png'),
          },
          imageUrl: require('@/assets/jeju-depo.jpg'),
        },
        {
          id: 2,
          user: {
            username: 'user2',
            avatar: require('@/assets/Cat.png'),
          },
          imageUrl: require('@/assets/jeju-hanlimpark.jpeg'),
        },
         {
          id: 3,
          user: {
            username: 'user1',
            avatar: require('@/assets/Cat.png'),
          },
          imageUrl: require('@/assets/jeju-manjanggul.jpg'),
        },
        {
          id: 4,
          user: {
            username: 'user2',
            avatar: require('@/assets/Cat.png'),
          },
          imageUrl: require('@/assets/jeju-hanlimpark.jpeg'),
        },
         {
          id: 5,
          user: {
            username: 'user1',
            avatar: require('@/assets/Cat.png'),
          },
          imageUrl: require('@/assets/jeju-manjanggul.jpg'),
        },
        {
          id: 6,
          user: {
            username: 'user2',
            avatar: require('@/assets/Cat.png'),
          },
          imageUrl: require('@/assets/jeju-hanlimpark.jpeg'),
        },
         {
          id: 7,
          user: {
            username: 'user1',
            avatar: require('@/assets/Cat.png'),
          },
          imageUrl: require('@/assets/jeju-depo.jpg'),
        },
        {
          id: 8,
          user: {
            username: 'user2',
            avatar: require('@/assets/Cat.png'),
          },
          imageUrl: require('@/assets/jeju-manjanggul.jpg'),
        },
         {
          id: 9,
          user: {
            username: 'user1',
            avatar: require('@/assets/Cat.png'),
          },
          imageUrl: require('@/assets/jeju-depo.jpg'),
        },
        {
          id: 10,
          user: {
            username: 'user2',
            avatar: require('@/assets/Cat.png'),
          },
          imageUrl: require('@/assets/jeju-hanlimpark.jpeg'),
        },
      ];
      this.allPosts = dummyPosts;
    },
     displayPosts() {
      // 이미 모든 게시물을 불러왔거나 로딩 중이라면 함수를 빠져나감
      if (this.allPostsLoaded || this.isLoading) return;
      this.isLoading = true; // 로딩 시작
      // 비동기 작업을 시뮬레이션하기 위해 setTimeout을 사용
      setTimeout(() => {
        const newPosts = this.allPosts.slice(this.page * 5, (this.page + 1) * 5);
        this.displayedPosts.push(...newPosts);
        this.page++;
        this.isLoading = false; // 로딩 완료
        if (newPosts.length < 5 || this.displayedPosts.length === this.allPosts.length) {
          this.allPostsLoaded = true; // 더 이상 불러올 게시물이 없음
        }
      }, 1000); // 1초 지연으로 로딩 시간을 시뮬레이션
    },
    handleScroll() {
      // 스크롤 위치가 문서의 끝에 가까우면 추가 게시물을 로드
      const bottomOfWindow = window.innerHeight + window.scrollY >= document.documentElement.offsetHeight;
      if (bottomOfWindow && !this.isLoading) {
        this.displayPosts();
      }
    },
  },
};
</script>

<style scoped>
.feed {
  margin: 0 auto;
  max-width: 600px;
  padding: 20px 0;
}

.post {
  background-color: white;
  margin-bottom: 20px;
  border-radius: 5px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.12), 0 1px 2px rgba(0, 0, 0, 0.24);
}

.post-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 15px;
  border-bottom: 1px solid #efefef;
}

.user {
  display: flex;
  align-items: center;
}

.avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  margin-right: 10px;
}

.username {
  font-weight: 600;
}

.post-image {
  width: 100%;
  height: auto;
}

.post-actions {
  display: flex;
  align-items: center;
  padding: 10px 15px;
  border-bottom: 1px solid #efefef;
}

.action-button {
  background: none;
  border: none;
  font-size: 20px;
  margin-right: 15px;
  cursor: pointer;
}
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}
.modal-content {
  background: rgba(255, 255, 255, 0.651);
  padding: 20px;
  border-radius: 5px;
  width: 80%;
  max-width: 500px;
}
.post-btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: #3897f0;  /* 인스타그램의 메인 색상 */
  color: #fff;  /* 아이콘 색상 */
  border: none;
  cursor: pointer;
  font-size: 20px;
  transition: background-color 0.3s;
}

.post-btn:hover {
  background-color: #0679e6;  /* 버튼을 호버했을 때의 색상 */
}
</style>
