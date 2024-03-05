(function(){"use strict";try{if(typeof document!="undefined"){var t=document.createElement("style");t.appendChild(document.createTextNode(".div[data-v-9bed9119]{position:relative}.div:hover div[data-v-9bed9119]{display:block}.div-2[data-v-9bed9119]{position:relative;border-radius:.75rem;padding:1rem;width:fit-content;max-width:80%;margin-bottom:1rem;margin-left:auto}.div-3[data-v-9bed9119]{position:absolute;width:max-content;display:none;bottom:0px;border-radius:.25rem;left:0px;transform:translate(-50%) translateY(50%);box-shadow:#0003 0 0 2px;padding:.25rem;z-index:9999}.div-4[data-v-9bed9119]{position:relative;border-radius:.75rem;padding:1rem;width:fit-content;max-width:80%;margin-bottom:1rem;margin-right:auto}.div-5[data-v-9bed9119]{position:absolute;width:max-content;display:none;bottom:0px;right:0px;border-radius:.25rem;transform:translate(50%) translateY(50%);box-shadow:#0003 0 0 2px;padding:.25rem;z-index:9999}.input[data-v-46284fc5]:focus{outline:none}.svg[data-v-46284fc5]{height:1.5rem;width:1.5rkm}")),document.head.appendChild(t)}}catch(e){console.error("vite-plugin-css-injected-by-js",e)}})();
import { openBlock as l, createElementBlock as r, normalizeStyle as n, Fragment as v, renderList as L, createElementVNode as i, toDisplayString as u, createCommentVNode as b, createTextVNode as f, pushScopeId as H, popScopeId as y, createStaticVNode as w, resolveComponent as h, createVNode as g } from "vue";
const C = (t, o) => {
  const e = t.__vccOpts || t;
  for (const [c, d] of o)
    e[c] = d;
  return e;
}, k = {
  name: "chat-messages",
  props: [
    "bgColorChat",
    "chat",
    "bgColorMessagePerson",
    "textColorMessagePerson",
    "bgColorMessageTimestamp",
    "textColorMessageTimestamp",
    "bgColorMessageChatbot",
    "textColorMessageChatbot"
  ],
  data: () => ({ chatElementAdded: !1 }),
  updated() {
    const t = document.getElementById("chat-container");
    t && this.chatElementAdded && (this.scrollToEnd(t), this.chatElementAdded = !1);
  },
  watch: {
    onUpdateHook0: {
      handler() {
        document.getElementById("chat-container") && (this.chatElementAdded = !0);
      },
      immediate: !0
    }
  },
  computed: {
    onUpdateHook0() {
      return {
        0: this.chat.length
      };
    }
  },
  methods: {
    scrollToEnd: function(o) {
      const e = o.clientHeight, c = o.scrollHeight;
      o.scrollTop = c - e;
    }
  }
}, I = {
  key: 0,
  class: "div"
}, Z = {
  key: 1,
  class: "div"
};
function O(t, o, e, c, d, s) {
  return l(), r("div", {
    id: "chat-container",
    style: n({
      height: "100%",
      overscrollBehaviorY: "contain",
      overflowY: "auto",
      overflowX: "hidden",
      backgroundColor: e.bgColorChat || "#EAEEF3",
      padding: "1.5rem"
    })
  }, [
    (l(!0), r(v, null, L(e.chat, (a, m) => (l(), r("div", { key: m }, [
      a.type == "person" ? (l(), r("div", I, [
        i("div", {
          class: "div-2",
          style: n({
            backgroundColor: e.bgColorMessagePerson || "#025CDB",
            color: e.textColorMessagePerson || "white"
          })
        }, [
          a.timestamp ? (l(), r("div", {
            key: 0,
            class: "div-3",
            style: n({
              backgroundColor: e.bgColorMessageTimestamp || "white",
              color: e.textColorMessageTimestamp || "black"
            })
          }, u(a.timestamp), 5)) : b("", !0),
          f(" " + u(a.message), 1)
        ], 4)
      ])) : b("", !0),
      a.type == "chatbot" ? (l(), r("div", Z, [
        i("div", {
          class: "div-4",
          style: n({
            backgroundColor: e.bgColorMessageChatbot || "white",
            color: e.textColorMessageChatbot || "black"
          })
        }, [
          a.timestamp ? (l(), r("div", {
            key: 0,
            class: "div-5",
            style: n({
              backgroundColor: e.bgColorMessageTimestamp || "white",
              color: e.textColorMessageTimestamp || "black"
            })
          }, u(a.timestamp), 5)) : b("", !0),
          f(" " + u(a.message), 1)
        ], 4)
      ])) : b("", !0)
    ]))), 128))
  ], 4);
}
const T = /* @__PURE__ */ C(k, [["render", O], ["__scopeId", "data-v-9bed9119"]]);
const S = {
  name: "chat-input",
  props: [
    "onSend",
    "inputHeight",
    "bgColorInput",
    "textColorInput",
    "inputPlaceholder"
  ],
  data: () => ({ inputField: "" }),
  methods: {
    sendMessage(t) {
      t.preventDefault(), this.onSend && this.onSend(this.inputField), this.inputField = "";
    }
  }
}, p = (t) => (H("data-v-46284fc5"), t = t(), y(), t), P = ["placeholder", "value"], V = ["strokeWidth"], B = /* @__PURE__ */ p(() => /* @__PURE__ */ i("path", {
  "stroke-linecap": "round",
  "stroke-linejoin": "round",
  d: "M6 12L3.269 3.126A59.768 59.768 0 0121.485 12 59.77 59.77 0 013.27 20.876L5.999 12zm0 0h7.5"
}, null, -1)), E = [
  B
];

function startSpeechToText() {
  console.log('STT를 시작합니다.');
  // 여기에 STT 기능을 구현할 코드를 추가할 수 있습니다.
  console.log('STT를 시작합니다.');
  // 여기에 STT 기능을 구현할 코드를 추가할 수 있습니다.
  if (!('webkitSpeechRecognition' in window)) {
    console.log('Your browser does not support Speech Recognition.')
  } else {
    const recognition = new (window.SpeechRecognition || window.webkitSpeechRecognition || window.mozSpeechRecognition || window.msSpeechRecognition)()
    recognition.lang = 'Microsoft Heami - Korean (Korean)', 'Google US English'
    recognition.interimResults = true

    recognition.onspeechstart = () => {
      console.log('Recognition Start!')
    }

    recognition.onspeechend = () => {
      console.log('Recognition Stop!')
      recognition.stop()
    }

    recognition.onresult = async event => {
      console.log('event.results:', event.results)
      const transcript = Array.from(event.results).map(results => results[0].transcript).join("")
      console.log('Transcript:', transcript)

      // 여기에 STT 결과를 처리하는 코드를 추가할 수 있습니다.
    }

    recognition.onerror = event => {
      console.error('Speech recognition error: ' + event.error)
    }

    recognition.start()
  }
}
// @@@@@@@@@@@@@새로운버튼추가
function D(t, o, e, c, d, s) {
  // r("form" >> onSubmit 핸들러는 sendMessage 메서드를 호출합니다.
  return l(), r("form", {
    onSubmit: o[1] || (o[1] = (...a) => s.sendMessage && s.sendMessage(...a)),
    style: n({
      display: "flex",
      height: e.inputHeight || "60px",
      backgroundColor: e.bgColorInput || "white",
      color: e.textColorInput || "black"
    })
  }, [
    i("input", {
      class: "input",
      placeholder: e.inputPlaceholder || "Type your message here",
      onInput: o[0] || (o[0] = (a) => t.inputField = a.target.value),
      value: t.inputField,
      style: n({
        borderWidth: "0px",
        backgroundColor: e.bgColorInput || "white",
        color: e.textColorInput || "black",
        width: "100%",
        marginLeft: "1rem",
        fontSize: "1rem"
      })
    }, null, 44, P),
    // 첫 번째 버튼 (이전에 사용하던 버튼)
    i("button", {
      onClick: () => {
        // 첫 번째 버튼이 클릭되었을 때 실행될 동작 구현
        console.log("이전 버튼이 클릭되었습니다.");
        // 여기에 sendMessage 메서드 호출을 추가할 수 있음
      },
      style: n({
        backgroundColor: "transparent", // SVG 색상과 일치하도록 투명 배경 설정
        flexShrink: "0",
        all: "unset",
        cursor: "pointer",
        margin: "auto 1rem"
      })
    }, [
      (l(), r("svg", {
        xmlns: "http://www.w3.org/2000/svg",
        fill: "none",
        viewBox: "0 0 24 24",
        stroke: "currentColor",
        class: "svg",
        strokeWidth: 1.5
      }, E, 8, V))
    ], 4),
    // 두 번째 버튼 (새로운 버튼)
    i("button", {
      onClick: (event) => {
        // 두 번째 버튼이 클릭되었을 때 실행될 동작 구현
        console.log("새로운 버튼이 클릭되었습니다.");
        // 기본 동작 중지
        event.preventDefault();

        // 두 번째 버튼 클릭 이벤트 핸들러
        startSpeechToText();
      },
      style: n({
        backgroundColor: "blue", // 버튼의 배경색
        color: "white", // 버튼 텍스트 색상
        flexShrink: "0",
        all: "unset",
        cursor: "pointer",
        margin: "auto 1rem"
      })
    }, "stt") // 버튼 텍스트
  ], 36);
}


// @@@@@@@@@@@@@@@주석으로 남기기@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
// function D(t, o, e, c, d, s) {
//   return l(), r("form", {
//     onSubmit: o[1] || (o[1] = (...a) => s.sendMessage && s.sendMessage(...a)),
//     style: n({
//       display: "flex",
//       height: e.inputHeight || "60px",
//       backgroundColor: e.bgColorInput || "white",
//       color: e.textColorInput || "black"
//     })
//   }, [
//     i("input", {
//       class: "input",
//       placeholder: e.inputPlaceholder || "Type your message here",
//       onInput: o[0] || (o[0] = (a) => t.inputField = a.target.value),
//       value: t.inputField,
//       style: n({
//         borderWidth: "0px",
//         backgroundColor: e.bgColorInput || "white",
//         color: e.textColorInput || "black",
//         width: "100%",
//         marginLeft: "1rem",
//         fontSize: "1rem"
//       })
//     }, null, 44, P),
//     i("button", {
//       style: n({
//         backgroundColor: e.bgColorInput || "white",
//         flexShrink: "0",
//         all: "unset",
//         cursor: "pointer",
//         margin: "auto 1rem"
//       })
//     }, [
//       (l(), r("svg", {
//         xmlns: "http://www.w3.org/2000/svg",
//         fill: "none",
//         viewBox: "0 0 24 24",
//         stroke: "currentColor",
//         class: "svg",
//         strokeWidth: 1.5
//       }, E, 8, V))
//     ], 4)
//   ], 36);
// }
// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
const F = /* @__PURE__ */ C(S, [["render", D], ["__scopeId", "data-v-46284fc5"]]), z = {
  name: "chat-icon"
}, A = { style: {
  height: "32px",
  width: "32px"
} }, W = /* @__PURE__ */ w('<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M19.3259 5.77772L18.4944 6.33329V6.33329L19.3259 5.77772ZM19.3259 16.2223L18.4944 15.6667V15.6667L19.3259 16.2223ZM18.2223 17.3259L17.6667 16.4944H17.6667L18.2223 17.3259ZM14 17.9986L13.9956 16.9986C13.4451 17.001 13 17.4481 13 17.9986H14ZM14 18L14.8944 18.4472C14.9639 18.3084 15 18.1552 15 18H14ZM10 18H9C9 18.1552 9.03615 18.3084 9.10557 18.4472L10 18ZM10 17.9986H11C11 17.4481 10.5549 17.001 10.0044 16.9986L10 17.9986ZM5.77772 17.3259L6.33329 16.4944H6.33329L5.77772 17.3259ZM4.67412 16.2223L5.50559 15.6667L5.50559 15.6667L4.67412 16.2223ZM4.67412 5.77772L5.50559 6.33329L4.67412 5.77772ZM5.77772 4.67412L6.33329 5.50559L5.77772 4.67412ZM18.2223 4.67412L17.6667 5.50559L17.6667 5.50559L18.2223 4.67412ZM21 11C21 9.61635 21.0012 8.50334 20.9106 7.61264C20.8183 6.70523 20.6225 5.91829 20.1573 5.22215L18.4944 6.33329C18.7034 6.64604 18.8446 7.06578 18.9209 7.81505C18.9988 8.58104 19 9.57473 19 11H21ZM20.1573 16.7779C20.6225 16.0817 20.8183 15.2948 20.9106 14.3874C21.0012 13.4967 21 12.3836 21 11H19C19 12.4253 18.9988 13.419 18.9209 14.1849C18.8446 14.9342 18.7034 15.354 18.4944 15.6667L20.1573 16.7779ZM18.7779 18.1573C19.3238 17.7926 19.7926 17.3238 20.1573 16.7779L18.4944 15.6667C18.2755 15.9943 17.9943 16.2755 17.6667 16.4944L18.7779 18.1573ZM14.0044 18.9986C15.0785 18.9939 15.9763 18.9739 16.7267 18.8701C17.4931 18.7642 18.1699 18.5636 18.7779 18.1573L17.6667 16.4944C17.3934 16.6771 17.0378 16.8081 16.4528 16.889C15.8518 16.9721 15.0792 16.9939 13.9956 16.9986L14.0044 18.9986ZM15 18V17.9986H13V18H15ZM13.7889 20.6584L14.8944 18.4472L13.1056 17.5528L12 19.7639L13.7889 20.6584ZM10.2111 20.6584C10.9482 22.1325 13.0518 22.1325 13.7889 20.6584L12 19.7639L12 19.7639L10.2111 20.6584ZM9.10557 18.4472L10.2111 20.6584L12 19.7639L10.8944 17.5528L9.10557 18.4472ZM9 17.9986V18H11V17.9986H9ZM5.22215 18.1573C5.83014 18.5636 6.50685 18.7642 7.2733 18.8701C8.02368 18.9739 8.92154 18.9939 9.99564 18.9986L10.0044 16.9986C8.92075 16.9939 8.14815 16.9721 7.54716 16.889C6.96223 16.8081 6.60665 16.6771 6.33329 16.4944L5.22215 18.1573ZM3.84265 16.7779C4.20744 17.3238 4.6762 17.7926 5.22215 18.1573L6.33329 16.4944C6.00572 16.2755 5.72447 15.9943 5.50559 15.6667L3.84265 16.7779ZM3 11C3 12.3836 2.99879 13.4967 3.0894 14.3874C3.18171 15.2948 3.3775 16.0817 3.84265 16.7779L5.50559 15.6667C5.29662 15.354 5.15535 14.9342 5.07913 14.1849C5.00121 13.419 5 12.4253 5 11H3ZM3.84265 5.22215C3.3775 5.91829 3.18171 6.70523 3.0894 7.61264C2.99879 8.50334 3 9.61635 3 11H5C5 9.57473 5.00121 8.58104 5.07913 7.81505C5.15535 7.06578 5.29662 6.64604 5.50559 6.33329L3.84265 5.22215ZM5.22215 3.84265C4.6762 4.20744 4.20744 4.6762 3.84265 5.22215L5.50559 6.33329C5.72447 6.00572 6.00572 5.72447 6.33329 5.50559L5.22215 3.84265ZM11 3C9.61635 3 8.50334 2.99879 7.61264 3.0894C6.70523 3.18171 5.91829 3.3775 5.22215 3.84265L6.33329 5.50559C6.64604 5.29662 7.06578 5.15535 7.81505 5.07913C8.58104 5.00121 9.57473 5 11 5V3ZM13 3H11V5H13V3ZM18.7779 3.84265C18.0817 3.3775 17.2948 3.18171 16.3874 3.0894C15.4967 2.99879 14.3836 3 13 3V5C14.4253 5 15.419 5.00121 16.1849 5.07913C16.9342 5.15535 17.354 5.29662 17.6667 5.50559L18.7779 3.84265ZM20.1573 5.22215C19.7926 4.6762 19.3238 4.20744 18.7779 3.84265L17.6667 5.50559C17.9943 5.72447 18.2755 6.00572 18.4944 6.33329L20.1573 5.22215Z"></path><circle cx="16" cy="11" r="1" stroke-linecap="round"></circle><circle cx="12" cy="11" r="1" stroke-linecap="round"></circle><circle cx="8" cy="11" r="1" stroke-linecap="round"></circle></svg>', 1), Y = [
  W
];
function N(t, o, e, c, d, s) {
  return l(), r("div", A, Y);
}
const x = /* @__PURE__ */ C(z, [["render", N]]), R = {
  name: "chat-header",
  components: { ChatIcon: x },
  props: [
    "headerHeight",
    "bgColorHeader",
    "bgColorIcon",
    "fillColorIcon",
    "textColorHeader",
    "offline",
    "colorOffline",
    "colorOnline",
    "closeChat"
  ]
}, j = { style: {
  display: "flex"
} }, U = { key: 0 }, X = { key: 1 }, q = /* @__PURE__ */ i("path", {
  "stroke-linecap": "round",
  "stroke-linejoin": "round",
  d: "M6 18L18 6M6 6l12 12"
}, null, -1), G = [
  q
];
function J(t, o, e, c, d, s) {
  const a = h("chat-icon");
  return l(), r("div", {
    style: n({
      display: "flex",
      height: e.headerHeight || "90px",
      flexShrink: "0",
      backgroundColor: e.bgColorHeader || "white"
    })
  }, [
    i("div", {
      style: n({
        position: "flex",
        borderRadius: "50%",
        backgroundColor: e.bgColorIcon || "#025CDB",
        marginTop: "auto",
        marginBottom: "auto",
        marginLeft: "1.25rem",
        marginRight: "1.25rem",
        padding: "0.75rem",
        fill: e.fillColorIcon || "white"
      })
    }, [
      g(a)
    ], 4),
    i("div", {
      style: n({
        marginTop: "auto",
        marginBottom: "auto",
        width: "100%",
        color: e.textColorHeader || "black"
      })
    }, [
      i("div", {
        style: n({
          fontWeight: "600",
          fontSize: "1.10rem",
          lineHeight: "1.5rem"
        })
      }, " HereWhere 챗봇 ", 4),
      i("div", ({style: N({ display : "none"})}), j, [
        i("div", {
          style: n({
            borderRadius: "50%",
            height: "8px",
            width: "8px",
            margin: "auto 4px",
            backgroundColor: e.offline ? e.colorOffline || "red" : e.colorOnline || "green"
          })
        }, null, 4),
        e.offline ? (l(), r("div", U, "Offline")) : (l(), r("div", X, "Online"))
      ])
    ], 4),
    (l(), r("svg", {
      xmlns: "http://www.w3.org/2000/svg",
      viewBox: "0 0 24 24",
      stroke: "currentColor",
      "stroke-width": "2",
      onClick: o[0] || (o[0] = (m) => e.closeChat()),
      style: n({
        height: "1.75rem",
        width: "1.75rem",
        flexShrink: "0",
        margin: "1rem",
        cursor: "pointer",
        color: e.textColorHeader || "black"
      })
    }, G, 4))
  ], 4);
}
const K = /* @__PURE__ */ C(R, [["render", J]]), Q = {
  name: "chat",
  components: {
    ChatIcon: x,
    ChatHeader: K,
    ChatMessages: T,
    ChatInput: F
  },
  props: [
    "bgColorIcon",
    "margin",
    "fillColorIcon",
    "width",
    "height",
    "boxShadow",
    "headerHeight",
    "bgColorHeader",
    "textColorHeader",
    "offline",
    "colorOffline",
    "colorOnline",
    "bgColorChat",
    "bgColorMessageChatbot",
    "bgColorMessagePerson",
    "bgColorMessageTimestamp",
    "textColorMessageChatbot",
    "textColorMessagePerson",
    "textColorMessageTimestamp",
    "chat",
    "onSend",
    "inputHeight",
    "bgColorInput",
    "textColorInput",
    "inputPlaceholder"
  ],
  data: () => ({ chatOpen: !1 }),
  methods: {
    stateCloseChat() {
      this.chatOpen = !1;
    },
    stateOpenChat() {
      this.chatOpen = !0;
    }
  }
}, $ = { style: {
  display: "flex",
  flexDirection: "column",
  height: "100%"
} };
function ee(t, o, e, c, d, s) {
  const a = h("chat-icon"), m = h("chat-header"), _ = h("chat-messages"), M = h("chat-input");
  return l(), r("div", null, [
    i("div", {
      onClick: o[0] || (o[0] = (te) => s.stateOpenChat()),
      style: n({
        position: "fixed",
        bottom: "0px",
        right: "0px",
        overflow: "hidden",
        transform: t.chatOpen ? "translateY(100%)" : "translateY(0%)",
        transitionTimingFunction: "cubic-bezier(0.4, 0, 0.2, 1)",
        transitionDuration: "300ms",
        transitionDelay: t.chatOpen ? "0ms" : "300ms",
        zIndex: "9998" 
      })
    }, [
      i("div", {
        style: n({
          position: "flex",
          borderRadius: "50%",
          cursor: "pointer",
          backgroundColor: e.bgColorIcon || "#025CDB",
          margin: e.margin || "20px",
          padding: "0.75rem",
          fill: e.fillColorIcon || "white"
        })
      }, [
        g(a)
      ], 4)
    ], 4),
    i("div", {
      style: n({
        position: "fixed",
        bottom: "0px",
        right: "0px",
        maxHeight: "100%",
        maxWidth: "100%",
        width: e.width || "410px",
        height: e.height || "700px",
        overflow: "hidden",
        transform: t.chatOpen ? "translateY(0%)" : "translateY(100%)",
        transitionTimingFunction: "cubic-bezier(0.4, 0, 0.2, 1)",
        transitionDuration: "300ms",
        transitionDelay: t.chatOpen ? "300ms" : "0ms",
        zIndex: "9998" 
      })
    }, [
      i("div", {
        style: n({
          margin: e.margin || "20px",
          overflow: "hidden",
          borderRadius: "0.75rem",
          boxShadow: e.boxShadow || "rgba(0,0,0,0.4) 0 0 6px",
          maxHeight: e.margin ? "calc(100% - " + e.margin + " - " + e.margin + ")" : "calc(100% - 40px)",
          maxWidth: e.margin ? "calc(100% - " + e.margin + " - " + e.margin + ")" : "calc(100% - 40px)",
          width: e.width && e.margin ? "calc(" + e.width + " - " + e.margin + " - " + e.margin + ")" : e.width ? "calc(" + e.width + " - 40px)" : e.margin ? "calc(410px - " + e.margin + " - " + e.margin + ")" : "calc(410px - 40px)",
          height: e.height && e.margin ? "calc(" + e.height + " - " + e.margin + " - " + e.margin + ")" : e.height ? "calc(" + e.height + " - 40px)" : e.margin ? "calc(700px - " + e.margin + " - " + e.margin + ")" : "calc(700px - 40px)"
        })
      }, [
        i("div", $, [
          g(m, {
            closeChat: s.stateCloseChat,
            headerHeight: e.headerHeight,
            bgColorHeader: e.bgColorHeader,
            bgColorIcon: e.bgColorIcon,
            textColorHeader: e.textColorHeader,
            fillColorIcon: e.fillColorIcon,
            offline: e.offline,
            colorOffline: e.colorOffline,
            colorOnline: e.colorOnline
          }, null, 8, ["closeChat", "headerHeight", "bgColorHeader", "bgColorIcon", "textColorHeader", "fillColorIcon", "offline", "colorOffline", "colorOnline"]),
          g(_, {
            bgColorChat: e.bgColorChat,
            bgColorMessageChatbot: e.bgColorMessageChatbot,
            bgColorMessagePerson: e.bgColorMessagePerson,
            bgColorMessageTimestamp: e.bgColorMessageTimestamp,
            textColorMessageChatbot: e.textColorMessageChatbot,
            textColorMessagePerson: e.textColorMessagePerson,
            textColorMessageTimestamp: e.textColorMessageTimestamp,
            chat: e.chat
          }, null, 8, ["bgColorChat", "bgColorMessageChatbot", "bgColorMessagePerson", "bgColorMessageTimestamp", "textColorMessageChatbot", "textColorMessagePerson", "textColorMessageTimestamp", "chat"]),
          g(M, {
            onSend: e.onSend,
            inputHeight: e.inputHeight,
            bgColorInput: e.bgColorInput,
            textColorInput: e.textColorInput,
            inputPlaceholder: e.inputPlaceholder
          }, null, 8, ["onSend", "inputHeight", "bgColorInput", "textColorInput", "inputPlaceholder"])
        ])
      ], 4)
    ], 4)
  ]);
}
const ne = /* @__PURE__ */ C(Q, [["render", ee]]);
export {
  ne as Chat
};
