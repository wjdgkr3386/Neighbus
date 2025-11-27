var stompClient = null;

function connect() {
    // 1. 웹소켓 연결 (엔드포인트: /ws-stomp)
    var socket = new SockJS('/ws-stomp');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('웹소켓 연결 성공: ' + frame);

        // 2. 알림 구독 (개인 큐: /user/queue/notifications)
        stompClient.subscribe('/user/queue/notifications', function (message) {
            
            // 3. 알림 도착 시 실행할 동작
            console.log("알림 내용: " + message.body);
            alert(message.body); // 테스트용 경고창
            
            // 나중에는 여기에 '읽지 않은 알림 갯수' 갱신 로직 등을 넣습니다.
        });
    });
}

// 페이지 로드 시 자동으로 연결 시도
document.addEventListener("DOMContentLoaded", function() {
    connect();
});