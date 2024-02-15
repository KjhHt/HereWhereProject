export function createObserver() {
    const observer = new MutationObserver((mutations) => {
      mutations.forEach((mutation) => {
        if (mutation.addedNodes && mutation.addedNodes.length > 0) {
          for (let i = 0; i < mutation.addedNodes.length; i++) {
            // 추가된 노드가 .pac-container 클래스를 가지고 있으면 스타일 변경
            if (mutation.addedNodes[i].nodeType === Node.ELEMENT_NODE && mutation.addedNodes[i].classList.contains('pac-container')) {
              console.log(mutation.addedNodes[i])
              mutation.addedNodes[i].style.zIndex = '9999';
            }
          }
        }
      });
    });
  
    observer.observe(document.body, {
        childList: true
    });
  }

export function setPhotoUrl(photos,photoUrl){
    if (photos && photos.length > 0) {
      const photo = photos[0]; 
      const photoReference = photo.photo_reference;
    
      if (photoReference) {
        const apiKey='AIzaSyA16otojrK96bOUH8jhs4Fp4gC3Glt2xrY';
        photoUrl.value = `https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=${photoReference}&key=${apiKey}`;
      } else if (photo.getUrl) {
        photoUrl.value = photo.getUrl({maxWidth: 400});
      } else {
        photoUrl.value = 'place_default.png'; // 기본 이미지 URL을 설정하세요
      }
    } else {
      photoUrl.value = 'place_default.png'; // 기본 이미지 URL을 설정하세요
    }
  }