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
      const apiKey= process.env.VUE_APP_GOOGLE_API_KEY;
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

export function setPhotoUrl_(photos) {
  let photoUrl = 'place_default.png'; // 기본 이미지 URL
  if (photos && photos.length > 0) {
      const photo = photos[0]; 
      const photoReference = photo.photo_reference;

      if (photoReference) {
          const apiKey= process.env.VUE_APP_GOOGLE_API_KEY;
          photoUrl = `https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=${photoReference}&key=${apiKey}`;
      } else if (photo.getUrl) {
          photoUrl = photo.getUrl({maxWidth: 400});
      }
  }
  return photoUrl;
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

export function formattedDate(date){
  let dateStr = date
  let dateObj = new Date(dateStr)
  return dateObj.toISOString().split('T')[0]
}

export function nextSevenDay(){
  // 현재 날짜를 가져옵니다.
  let date = new Date();

  // 현재 날짜에 하루를 더합니다.
  date.setDate(date.getDate() + 7);

  // 년, 월, 일을 가져옵니다.
  let year = date.getFullYear();
  let month = date.getMonth() + 1; // JavaScript의 월은 0부터 시작하므로 1을 더해줍니다.
  let day = date.getDate();

  // 월과 일이 한 자리 수일 경우 앞에 0을 붙여 두 자리로 만듭니다.
  if (month < 10) month = '0' + month;
  if (day < 10) day = '0' + day;

  // 년, 월, 일을 '-'로 연결하여 날짜 문자열을 만듭니다.
  let nextDay = year + '-' + month + '-' + day;
  return nextDay
}