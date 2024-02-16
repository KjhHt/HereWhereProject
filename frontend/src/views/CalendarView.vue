<script>
import { defineComponent } from 'vue'
import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid'
import timeGridPlugin from '@fullcalendar/timegrid'
import interactionPlugin from '@fullcalendar/interaction'
import { INITIAL_EVENTS, createEventId } from '../utils/event-utils'

export default defineComponent({
  components: {
    FullCalendar,
  },
  data() {
    return {
      calendarOptions: {
        plugins: [
          dayGridPlugin,
          timeGridPlugin,
          interactionPlugin // needed for dateClick
        ],
        headerToolbar: {
          left: 'prev,next today',
          center: 'title',
          right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        initialView: 'dayGridMonth',
        firstDay: 1,
        initialEvents: INITIAL_EVENTS, // alternatively, use the `events` setting to fetch from a feed
        editable: true,
        selectable: true,
        selectMirror: true,
        dayMaxEvents: true,
        weekends: true,
        select: this.handleDateSelect,
        eventClick: this.handleEventClick,
        eventsSet: this.handleEvents,
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
      },
      currentEvents: [],
    }
  },
  methods: {
    handleWeekendsToggle() {
      this.calendarOptions.weekends = !this.calendarOptions.weekends // update a property
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
  handleEventClick(clickInfo) {
  Swal.fire({
    title: '어떤 작업을 수행하시겠습니까?',
    showDenyButton: true,
    showCancelButton: true,
    confirmButtonText: `수정`,
    denyButtonText: `삭제`,
  }).then((result) => {
    /* Read more about isConfirmed, isDenied below */
    if (result.isConfirmed) {
  Swal.fire({
    title: '일정을 수정하세요',
    html:
      '<input id="swal-input1" class="swal2-input" placeholder="이름">' +
      '<input id="swal-input2" class="swal2-input" placeholder="시작 날짜 (YYYY-MM-DD 형식)">' +
      '<input id="swal-input3" class="swal2-input" placeholder="끝나는 날짜 (-MM-DD 형식)">',
    focusConfirm: false,
    preConfirm: () => {
      return [
        document.getElementById('swal-input1').value,
        document.getElementById('swal-input2').value,
        document.getElementById('swal-input3').value
      ]
    }
  }).then((result) => {
    if (result.isConfirmed) {
      // 사용자가 입력한 값에 접근
      const [title, start, end] = result.value;
      const startDate = new Date(start);
      const endDate = new Date(end);
      // 이벤트 정보 수정
      clickInfo.event.setProp('title', title);
      clickInfo.event.setStart(start);
      clickInfo.event.setEnd(end);
      clickInfo.event.setStart(startDate.toISOString());
      clickInfo.event.setEnd(endDate.toISOString());

      Swal.fire({
        title: "수정 완료!",
        text: "일정이 성공적으로 수정되었습니다.",
        icon: "success",
      });
    }
  });
} else if (result.isDenied) {
      Swal.fire({
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
    }
  })
},
    handleEvents(events) {
      this.currentEvents = events
    },
  }
})
</script>
<template>
  <div class='demo-app'>
    <div class='demo-app-sidebar'>
      <div class='demo-app-sidebar-section'>
        <h2>달력 사용법</h2>
        <ul>
          <li>날짜를 선택하면 새 이벤트를 생성하라는 메시지가 표시됩니다</li>
          <li>Drag, drop으로 이벤트를 조정할 수 있습니다</li>
          <li>삭제할 이벤트를 클릭합니다</li>
        </ul>
      </div>
      <div class='demo-app-sidebar-section'>
        <h2>모든 이벤트 ({{ currentEvents.length }})</h2>
        <ul>
          <li v-for='event in currentEvents' :key='event.id'>
            <b>{{ event.startStr }}</b>
            <i>{{ event.title }}</i>
          </li>
        </ul>
      </div>.
    </div>
    <div class='demo-app-main'>
      <FullCalendar
        class='demo-app-calendar'
        :options='calendarOptions'
      >
        <template v-slot:eventContent='arg'>
          <b>{{ arg.timeText }}</b>
          <i>{{ arg.event.title }}</i>
        </template>
      </FullCalendar>
    </div>
  </div>
</template>

<style lang='css'>
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500&display=swap');

h2 {
  margin: 0;
  font-size: 16px;
}

ul {
  margin: 0;
  padding: 0 0 0 1.5em;
}

li {
  margin: 1.5em 0;
  padding: 0;
}

b { /* used for event dates/times */
  margin-right: 3px;
}

.demo-app {
  display: flex;
  min-height: 100%;
  /* font-family: Arial, Helvetica Neue, Helvetica, sans-serif; */
      font-family: 'Roboto', sans-serif;
  font-size: 14px;
}

.demo-app-sidebar {
  width: 300px;
  line-height: 1.5;
  background: #eaf9ff;
  border-right: 1px solid #d3e2e8;
  text-align: left;
}

.demo-app-sidebar-section {
  padding: 2em;
}

.demo-app-main {
  flex-grow: 1;
  padding: 3em;
}

.fc { /* the calendar root */
  max-width: 1100px;
  margin: 0 auto;
}
.fc-day a {
  color: black;
  text-decoration: none;
}
.fc-day-sun a {
  color: red;
  text-decoration: none;
}
.fc-day-sat a {
  color: blue;
  text-decoration: none;
}
</style>
