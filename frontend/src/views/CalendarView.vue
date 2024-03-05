<template>
  <div class="demo-app" style="width: 50%; margin: auto">
    <div style="position: relative; margin-top: 50px">
      <div id="v-step-0" class="calendar-container">
        <FullCalendar class="demo-app-calendar" :options="calendarOptions">
          <template v-slot:eventContent="arg">
            <!-- Add id to elements as needed for the tour -->
            <b id="v-step-1">{{ arg.timeText }}</b>
            <i id="v-step-2">{{ arg.event.title }}</i>
            <i id="v-step-9">{{ arg.event - item }}</i>
          </template>
        </FullCalendar>
      </div>
    </div>
    <VTour ref="tour" :steps="steps" />
  </div>
  <div
    class="events-and-map-container"
    style="display: flex; justify-content: center"
  >
    <div
      class="events-container equal-width"
      style="width: 100%; max-width: 500px; margin-right: 20px"
    >
      <div class="event-header">예약 및 일정</div>
      <!-- 가정: 'currentEvents'는 해당 이벤트 데이터의 배열입니다. -->
      <!-- 해당 데이터를 루프 돌면서 아래의 HTML 구조를 생성합니다. -->
      <div class="event-item" v-for="event in currentEvents" :key="event.id">
        <div class="event-image-container">
          <!-- 여기에 일정에 관련된 이미지 또는 아이콘을 표시 -->
        </div>
        <div class="event-content">
          <div class="event-time">{{ event.startStr }}</div>
          <div class="event-title">{{ event.title }}</div>
        </div>
      </div>
    </div>
    <google-maps
      style="
        width: 90%;
        max-width: 500px;
        height: 500px;
        margin-top: 20px;
        margin-bottom: 20px;
      "
    ></google-maps>
  </div>
</template>
<script>
import { defineComponent, ref, onMounted } from "vue";
import FullCalendar from "@fullcalendar/vue3";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";
import { INITIAL_EVENTS, createEventId } from "../utils/event-utils";
import GoogleMaps from "../components/GoogleMaps.vue"; // GoogleMaps 컴포넌트를 import

export default defineComponent({
  components: {
    FullCalendar,
    GoogleMaps,
  },
  setup() {
    const steps = ref([
      {
        target: "#v-step-0",
        header: {
          title: "Calendar",
        },
        content: `herewhere 일정 관리 달력입니다.`,
      },
      {
        target: "#v-step-1",
        header: {
          title: "Time",
        },
        content: `일정의 시간이 표시됩니다.`,
      },
      {
        target: "#v-step-3",
        content: "직전달로 이동합니다.",
      },
      {
        target: "#v-step-4",
        content: "다음달로 이동합니다.",
      },
      {
        target: "#v-step-5",
        content: "오늘날짜로 이동합니다.",
      },
      {
        target: "#v-step-6",
        content: "월별로 일정을 보여줍니다.",
      },
      {
        target: "#v-step-7",
        content: "주별로 일정을 보여줍니다.",
      },
      {
        target: "#v-step-8",
        content: "일별로 일정을 보여줍니다.",
      },
      {
        target: "#v-step-9",
        content: "예약 및 일정목록을 한눈에 볼 수 있습니다.",
      },
    ]);

    const tour = ref(null);
    onMounted(() => {
      // 투어가 시작된 후에 버튼에 id를 동적으로 추가
      document.querySelector(".fc-prev-button").id = "v-step-3";
      document.querySelector(".fc-next-button").id = "v-step-4";
      document.querySelector(".fc-today-button").id = "v-step-5";
      document.querySelector(".fc-dayGridMonth-button").id = "v-step-6";
      document.querySelector(".fc-timeGridWeek-button").id = "v-step-7";
      document.querySelector(".fc-timeGridDay-button").id = "v-step-8";

      // 그런 다음 투어를 시작
      tour.value.startTour();
      const testEvent = {
        id: 1, // 이벤트 ID
        title: "테스트 이벤트", // 이벤트 제목
        start: "2024-02-01T10:00:00", // 이벤트 시작 시간
        end: "2024-02-01T12:00:00", // 이벤트 종료 시간
      };

      this.calendarOptions.initialEvents.push(testEvent);
    });

    return {
      tour,
      steps,
    };
  },
  data() {
    return {
      // isSidebarVisible: false, // 사이드바의 표시 상태! 토글
      calendarOptions: {
        aspectRatio: 2, // 기본값 : 1.35 | Type : Float
        height: "auto", // 달력이 부모 요소에 맞춰 높이가 자동 조절되도록 설정
        handleWindowResize: true,
        windowResizeDelay: 100,
        plugins: [
          dayGridPlugin,
          timeGridPlugin,
          interactionPlugin, // needed for dateClick
        ],
        initialView: "dayGridMonth",
        initialEvents: INITIAL_EVENTS, // alternatively, use the `events` setting to fetch from a feed
        editable: true,
        selectable: true,
        selectMirror: true,
        dayMaxEvents: true,
        weekends: true,
        select: this.handleDateSelect,
        eventClick: this.handleEventClick,
        eventsSet: this.handleEvents,
        dragScroll: true,
        locale: "ko",
        /* you can update a remote database when these fire:
                eventAdd:
                eventChange:
                eventRemove:
                */
        dayCellContent: function (info) {
          var number = document.createElement("a");
          number.classList.add("fc-daygrid-day-number");
          number.innerHTML = info.dayNumberText.replace("일", "");
          if (info.view.type === "dayGridMonth") {
            return { domNodes: [number] };
          } else {
            return { domNodes: [] };
          }
        },
        headerToolbar: {
          left: "prev",
          center: "title",
          right: "next today dayGridMonth,timeGridWeek,timeGridDay",
        },
      },
      currentEvents: [],
    };
  },
  methods: {
    sortEventsByDate() {
      this.currentEvents.sort((a, b) => {
        // 날짜를 Date 객체로 변환하여 비교
        return new Date(a.start) - new Date(b.start); // 'startStr'대신 'start'를 사용했다고 가정
      });
      // 배열이 변경되었으므로 Vue에게 알리기 위해 배열을 업데이트
      this.currentEvents = [...this.currentEvents];
    },
    toggleSidebar() {
      this.isSidebarVisible = !this.isSidebarVisible;
    },
    handleWeekendsToggle() {
      this.calendarOptions.weekends = !this.calendarOptions.weekends; // update a property
    },
    handleDateSelect(selectInfo) {
      Swal.fire({
        didOpen: () => {
          document.body.style.paddingRight = "0";
        },
        didClose: () => {
          document.body.style.paddingRight = "0";
        },
        title: "일정을 입력해주세요",
        input: "text",
        inputAttributes: {
          autocapitalize: "off",
        },
        showCancelButton: true,
        confirmButtonText: "추가",
        cancelButtonText: "취소",
        showLoaderOnConfirm: true,
        preConfirm: (title) => {
          if (title) {
            let calendarApi = selectInfo.view.calendar;
            calendarApi.unselect(); // clear date selection
            calendarApi.addEvent({
              id: createEventId(),
              title,
              start: selectInfo.startStr,
              end: selectInfo.endStr,
              allDay: selectInfo.allDay,
            });
          }
        },
        allowOutsideClick: () => !Swal.isLoading(),
      }).then((result) => {
        if (result.isConfirmed && result.value) {
          Swal.fire({
            title: "성공!",
            text: "일정이 추가되었습니다.",
            icon: "success",
          });
        }
      });
    },
    addEvent(newEvent) {
      this.currentEvents.push(newEvent);
      this.sortEventsByDate(); // 이벤트 추가 후 정렬
    },
    handleEventClick(clickInfo) {
      Swal.fire({
        didOpen: () => {
          document.body.style.paddingRight = "0";
        },
        didClose: () => {
          document.body.style.paddingRight = "0";
        },
        title: `"${clickInfo.event.title}"일정을 삭제하시겠습니까?`,
        text: `일정은 복구할 수 없습니다.`,
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "삭제",
        cancelButtonText: "취소",
      }).then((result) => {
        if (result.isConfirmed) {
          clickInfo.event.remove();
          Swal.fire({
            title: "삭제 완료!",
            text: "일정이 성공적으로 삭제되었습니다.",
            icon: "success",
          });
        }
      });
    },
    deleteEvent(eventId) {
      this.currentEvents = this.currentEvents.filter(
        (event) => event.id !== eventId
      );
      this.sortEventsByDate(); // 이벤트 삭제 후 정렬
    },
    handleEvents(events) {
      this.currentEvents = events;
      this.sortEventsByDate(); // 이벤트를 새로 받을 때마다 정렬
    },
  },
});
</script>

<style lang='css'>
/* @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500&display=swap');

* {
    font-family: 'Roboto', sans-serif;
} */
@import url("https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;400;600&display=swap");

* {
  font-family: "Open Sans", sans-serif;
}

h2 {
  margin: 0;
  font-size: 18px;
  color: #333;
}

ul {
  margin: 0;
  padding: 0 0 0 1.5em;
}

li {
  margin: 1.5em 0;
  padding: 0;
}

b {
  /* used for event dates/times */
  margin-right: 3px;
}

.demo-app {
  display: flex;
  min-height: 100%;
  font-size: 14px;
}
.demo-app-sidebar {
  width: 250px;
  line-height: 1.5;
  background: #f8f9fa;
  border-right: 1px solid #dee2e6;
  box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
}

.demo-app-sidebar-section {
  padding: 2em;
}

.demo-app-main {
  flex-grow: 1;
  padding: 3em;
  background: #f5f5f5;
}

button {
  padding: 10px 15px;
  background: #000;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background 0.3s;
}

button:hover {
  background: #333;
}

.fc {
  width: 800px;
  margin: 0 auto;
}

.custom-tooltip {
  --bs-tooltip-bg: var(--bd-violet-bg);
  --bs-tooltip-color: var(--bs-white);
}

#calendar-container .fc .fc-toolbar-title {
  color: black !important;
  text-align: center !important;
}

#calendar-container .fc .fc-event-title {
  color: black !important;
}

.fc-day a {
  color: black;
  text-decoration: none;
}

.fc-col-header-cell .fc-scrollgrid-sync-inner {
  background-color: #3333339f;
}

[data-tooltip] {
  cursor: pointer;
  display: inline-block;
  line-height: 1;
  position: relative;
}

[data-tooltip].tooltip-right::before {
  border-style: solid;
  border-width: 0.3rem;
  content: "";
  opacity: 0;
  position: absolute;
  transition: opacity 0.2s;
  visibility: hidden;
  border-color: transparent rgba(51, 51, 51, 0.9) transparent transparent;
  top: 50%;
  left: calc(110% - 0.3rem);
  margin-top: -0.3rem;
}

[data-tooltip].tooltip-right::after {
  top: -0.3rem;
  left: calc(110% + 0.3rem);
}

[data-tooltip]::after {
  background-color: rgba(51, 51, 51, 0.9);
  border-radius: 0.3rem;
  color: #fff;
  content: attr(data-tooltip);
  font-size: 85%;
  font-weight: normal;
  line-height: 1.15rem;
  opacity: 0;
  padding: 0.25rem 0.5rem;
  position: absolute;
  text-align: center;
  text-transform: none;
  transition: opacity 0.2s;
  visibility: hidden;
  white-space: normal;
  width: 195px;
  z-index: 1;
}

[data-tooltip]:hover::after,
[data-tooltip].tooltip-right:hover::before {
  visibility: visible;
  opacity: 1;
}

.fc-toolbar.fc-header-toolbar {
}

.flex {
  display: flex;
  justify-content: center;
  align-items: center;
}

.fc-col-header .fc-day-sat .fc-scrollgrid-sync-inner {
  background-color: #3e8ae0;
}

.fc-col-header .fc-day-sun .fc-scrollgrid-sync-inner {
  background-color: #ec658e;
}

.fc-col-header-cell .fc-col-header-cell-cushion {
  color: #fff;
}

.fc-day-sun a {
  color: red;
  text-decoration: none;
}

.fc-day-sat a {
  color: blue;
  text-decoration: none;
}

.fc-day-sat {
  /* background-color: #eaf6f6;  */
}

.fc-day-sun {
  /* background-color: #ffebeb;  */
}
@media (max-width: 991px) {
  .header {
    flex-wrap: wrap;
  }
}
.events-container {
  width: 100%;
  margin-left: 5px;
  display: flex;
  flex-direction: column;
  padding-top: 14px;
}

.event-header {
  font-size: 2.5rem; /* 40px */
  font-weight: bold;
  color: black;
  line-height: 40px;
  margin-bottom: 6px;
}

.event-item {
  display: flex;
  flex-direction: row;
  align-items: flex-start;
  padding: 16px;
  border: 1px solid #adadad9f;
  border-opacity: 10%;
  border-radius: 4px;
  margin-top: 6px;
}

.event-image-container {
  width: 100px;
  height: 100px;
  background-color: #e3e3e3;
  opacity: 0.5;
  margin-right: 20px; /* 이미지와 내용 사이의 간격 */
}

.event-content {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.event-time {
  font-size: 1.25rem; /* 20px */
  color: black;
  font-weight: medium;
  margin-bottom: 8px;
}

.event-title {
  font-size: 1rem; /* 16px */
  color: black;
}

@media screen and (max-width: 400px) {
  .events-container {
    margin-left: 0;
  }

  .event-item {
    flex-direction: column;
  }

  .event-image-container {
    margin: 0 auto 16px; /* 가운데 정렬 및 아래쪽 여백 */
  }
}
#calendar-container {
  height: 35vh; /* 뷰포트 높이에 맞춥니다 */
  width: 100%; /* 뷰포트 너비에 맞춥니다 */
}
.equal-width {
  flex: 0 1 42%; /* flex-grow, flex-shrink, flex-basis */
  max-width: 42%; /* 최대 너비도 42%로 설정 */
}

/* 반응형 디자인을 위해 md 브레이크포인트 이하에서는 100% 너비를 가지도록 설정 */
@media (max-width: 768px) {
  .equal-width {
    flex-basis: 100%;
    max-width: 100%;
  }
}
@media (min-width: 800px) {
  .google-maps {
    width: 100%;
    max-width: 800px;
    margin-right: 20px;
  }
}
</style>
