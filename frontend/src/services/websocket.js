import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

let stompClient = null;

export function getStompClient(userId) {
    if (!stompClient) {
        const socket = new SockJS(process.env.VUE_APP_API_URL + '/wss');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, () => {
            stompClient.subscribe(`/user/${userId}/queue/notification`, function(notification) {
                // 알림 메시지를 받으면 실행할 코드...
                console.log(notification);
            });
        });
    }
  return stompClient;
}