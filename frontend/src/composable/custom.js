export function createObserver() {
    const observer = new MutationObserver((mutations) => {
      mutations.forEach((mutation) => {
        if (mutation.addedNodes && mutation.addedNodes.length > 0) {
          for (let i = 0; i < mutation.addedNodes.length; i++) {
            // 추가된 노드가 .pac-container 클래스를 가지고 있으면 스타일 변경
            if (mutation.addedNodes[i].nodeType === Node.ELEMENT_NODE && mutation.addedNodes[i].classList.contains('pac-container')) {
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

export function routeHandler(event,arrivals){
  if(event.target.value!=='') {
    arrivals.placeholder= '목적지를 선택하거나 지도를 클릭하세요'
  }else{
    arrivals.placeholder='목적지를 선택하세요';
  }
}

export function degreesToRadians(degrees) {
  return degrees * Math.PI / 180;
}

export function getDistanceInKm(lat1, lon1, lat2, lon2) {
  var earthRadiusKm = 6371;

  var dLat = degreesToRadians(lat2-lat1);
  var dLon = degreesToRadians(lon2-lon1);

  lat1 = degreesToRadians(lat1);
  lat2 = degreesToRadians(lat2);

  var a = Math.sin(dLat/2) * Math.sin(dLat/2) +
          Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2); 
  var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
  return earthRadiusKm * c;
}