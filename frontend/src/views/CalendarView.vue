<template>
  <span
    class="items-center border bg-white flex flex-col pb-12 border-solid border-black"
    ><span
      class="justify-center items-stretch shadow-sm bg-white self-stretch flex gap-5 p-5 max-md:max-w-full max-md:flex-wrap"
    >
      <div
        class="justify-center items-center bg-white flex aspect-square flex-col w-10 h-10"
      >
        <img
          loading="lazy"
          srcSet="https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=3d7c6fc0fcce4487a00fd2640a53e23a&width=100 100w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=3d7c6fc0fcce4487a00fd2640a53e23a&width=200 200w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=3d7c6fc0fcce4487a00fd2640a53e23a&width=400 400w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=3d7c6fc0fcce4487a00fd2640a53e23a&width=800 800w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=3d7c6fc0fcce4487a00fd2640a53e23a&width=1200 1200w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=3d7c6fc0fcce4487a00fd2640a53e23a&width=1600 1600w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=3d7c6fc0fcce4487a00fd2640a53e23a&width=2000 2000w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=3d7c6fc0fcce4487a00fd2640a53e23a&width=2400 2400w"
          class="aspect-square object-contain object-center w-full overflow-hidden"
        />
      </div>
    </span>

    <div class="demo-app">
      <div style="position: relative">
        <div class="main">
          <div class="flex">
            <div class="wrapper">
              <div
                class="tooltip-right"
                data-tooltip="날짜를 선택하거나 드래그해서 일정을 생성할 수 있고, 생성된 일정을 드래그하면 일정변경을, 일정을 클릭하면 삭제할 수 있습니다."
              >
                <i
                  class="fas fa-question-circle"
                  style="display: inline-block"
                  focusable="false"
                  aria-hidden="true"
                ></i>
              </div>
            </div>
          </div>
        </div>

        <div id="calendar-container">
          <FullCalendar class="demo-app-calendar" :options="calendarOptions">
            <template v-slot:eventContent="arg">
              <b>{{ arg.timeText }}</b>
              <i>{{ arg.event.title }}</i>
            </template>
          </FullCalendar>
        </div>
      </div>
    </div>
    <div
      class="justify-center items-center self-stretch flex w-full flex-col mt-7 px-16 py-12 max-md:max-w-full max-md:px-5"
    >
      <div class="w-full max-w-[1100px] my-2.5 max-md:max-w-full">
        <div
          class="gap-5 flex max-md:flex-col max-md:items-stretch max-md:gap-0"
        >
          <div
            class="flex flex-col items-stretch w-[42%] max-md:w-full max-md:ml-0"
          >
            <div
              class="self-stretch bg-black bg-opacity-10 flex flex-col justify-center items-stretch w-full my-auto rounded-md max-md:max-w-full max-md:mt-10"
            >
              <div
                class="flex-col overflow-hidden relative flex min-h-[597px] w-full justify-center items-center px-16 py-12 max-md:max-w-full max-md:px-5"
              >
                <img
                  loading="lazy"
                  src="https://cdn.builder.io/api/v1/image/assets/TEMP/2c5af012301e63e95fad1eb000806853b9e905a721d1430d58f857bcb44d3353?apiKey=3d7c6fc0fcce4487a00fd2640a53e23a"
                  class="absolute h-full w-full object-cover object-center inset-0"
                />
                <span
                  class="relative flex w-[101px] max-w-full flex-col items-center mt-56 mb-44 max-md:my-10"
                >
                  <img
                    loading="lazy"
                    src="https://cdn.builder.io/api/v1/image/assets/TEMP/a66a85f21c6405a2a1e5577e853b4f2b3706bdda8b87512606a971b6eefdee56?apiKey=3d7c6fc0fcce4487a00fd2640a53e23a"
                    class="aspect-square object-contain object-center w-6 overflow-hidden max-w-full"
                  />
                  <div
                    class="text-black text-center text-base font-medium leading-6 self-stretch mt-2.5"
                  >
                    이오로비스트로
                  </div>
                </span>
              </div>
            </div>
          </div>
          <div
            class="flex flex-col items-stretch w-[58%] ml-5 max-md:w-full max-md:ml-0"
          >
            <span
              class="justify-center items-stretch self-stretch flex grow flex-col pt-3.5 max-md:max-w-full max-md:mt-10"
            >
              <div
                class="text-black text-4xl font-bold leading-10 max-md:max-w-full"
              >
                예약 및 일정
              </div>
              <div
                class="justify-center border mt-1.5 p-4 rounded-md border-solid border-black border-opacity-10 max-md:max-w-full"
                v-for="event in currentEvents"
                :key="event.id"
              >
                <div
                  class="gap-5 flex max-md:flex-col max-md:items-stretch max-md:gap-0"
                >
                  <div
                    class="flex flex-col items-stretch w-[18%] max-md:w-full max-md:ml-0"
                  >
                    <div
                      class="self-stretch bg-zinc-300 bg-opacity-50 flex w-[100px] shrink-0 h-[100px] flex-col mx-auto max-md:mt-8"
                    >
                      <!-- 여기에 일정에 관련된 이미지 또는 아이콘을 표시 -->
                    </div>
                  </div>
                  <div
                    class="flex flex-col items-stretch w-[82%] ml-5 max-md:w-full max-md:ml-0"
                  >
                    <span
                      class="items-stretch flex flex-col my-auto max-md:max-w-full max-md:mt-10"
                    >
                      <div
                        class="text-black text-xl font-medium leading-7 max-md:max-w-full"
                      >
                        {{ event.startStr }}
                        <!-- 일정의 시작 시간 표시 -->
                      </div>
                      <div
                        class="text-black text-base leading-6 mt-2 max-md:max-w-full"
                      >
                        {{ event.title }}
                        <!-- 일정의 제목 표시 -->
                      </div>
                    </span>
                  </div>
                </div>
              </div>
            </span>
          </div>
        </div>
      </div>
    </div>
    <div
      class="justify-center text-black text-center text-xl leading-7 whitespace-nowrap mt-5 mb-80 max-md:mb-10"
    >
      © 2024 Web Project. All rights reserved.
    </div>
  </span>
</template>
<script>
import { defineComponent } from "vue";
import FullCalendar from "@fullcalendar/vue3";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";
import { INITIAL_EVENTS, createEventId } from '../utils/event-utils';

export default defineComponent({
  components: {
    FullCalendar,
  },
  data() {
    return {
      // isSidebarVisible: false, // 사이드바의 표시 상태! 토글
      calendarOptions: {
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
          right: "next today",
        },
      },
      currentEvents: [],
    };
  },
  methods: {
    toggleSidebar() {
      this.isSidebarVisible = !this.isSidebarVisible;
    },
    handleWeekendsToggle() {
      this.calendarOptions.weekends = !this.calendarOptions.weekends; // update a property
    },
    handleDateSelect(selectInfo) {
      let title = prompt("이벤트 제목을 입력해주세요");
      let calendarApi = selectInfo.view.calendar;
      calendarApi.unselect(); // clear date selection

      if (title) {
        calendarApi.addEvent({
          id: createEventId(),
          title,
          start: selectInfo.startStr,
          end: selectInfo.endStr,
          allDay: selectInfo.allDay,
        });
      }
    },
    handleEventClick(clickInfo) {
      if (
        confirm(`정말 해당일정을 지우시겠습니까? '${clickInfo.event.title}'`)
      ) {
        clickInfo.event.remove();
      }
    },
    handleEvents(events) {
      this.currentEvents = events;
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
</style>
