<template>
  <div ref="mapContainer"></div>
</template>

<script>
export default {
  name: "GoogleMaps",
  data() {
    return {
      map: null,
    };
  },
  mounted() {
    this.initMap();
  },
  methods: {
    initMap() {
      const script = document.createElement("script");
      script.src = `https://maps.googleapis.com/maps/api/js?key=AIzaSyDSBgwpqBzohbr-H4s4WbVkn5G8m_RKArw&callback=initMap`;
      script.async = true;
      script.defer = true;
      window.initMap = this.loadMap; // loadMap 메서드를 전역 콜백으로 설정
      document.head.appendChild(script);
    },
    loadMap() {
      /* global google */
      this.map = new google.maps.Map(this.$refs.mapContainer, {
        center: { lat: -34.397, lng: 150.644 },
        zoom: 8,
      });
      this.addTestEvent();
    },
    addEvent(event) {
      // 이벤트를 추가하는 코드...

      // 이벤트에 위치 정보가 있을 경우, 해당 위치에 마커를 추가
      if (event.location) {
        const marker = new google.maps.Marker({
          position: event.location, // event.location은 { lat: 위도, lng: 경도 } 형태의 객체여야 합니다.
          map: this.map,
        });

        // 필요한 경우, 마커 클릭 시 이벤트 정보를 표시하는 인포윈도우를 추가할 수 있습니다.
        const infowindow = new google.maps.InfoWindow({
          content: event.title, // 이벤트의 제목을 인포윈도우에 표시
        });

        marker.addListener("click", () => {
          infowindow.open(this.map, marker);
        });
      }
    },
  },
};
</script>

<style scoped>
div {
  width: 100%;
  height: 100%;
}
</style>
