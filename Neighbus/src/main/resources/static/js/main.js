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
// 알림 모달 열기 함수
function openNotificationModal() {
    // 1. 모달 객체 가져오기 (Bootstrap 5)
    var myModal = new bootstrap.Modal(document.getElementById('notificationModal'));
    
    // 2. 서버에서 데이터 가져오기 (AJAX fetch)
    fetch('/api/notifications')
        .then(response => response.json())
        .then(data => {
            const listArea = document.getElementById('notificationList');
            listArea.innerHTML = ""; // 기존 목록 초기화

            if (data.length === 0) {
                listArea.innerHTML = '<li class="list-group-item text-center">새로운 알림이 없습니다.</li>';
            } else {
                // 3. 데이터를 하나씩 HTML로 만들어서 추가
                data.forEach(noti => {
                    // 읽음 여부에 따라 배경색 다르게 (읽었으면 회색조)
                    let bgClass = noti.isRead == 1 ? "bg-light text-muted" : "bg-white fw-bold";
                    
                    let item = `
                        <li class="list-group-item ${bgClass}">
                            <a href="${noti.url}" class="text-decoration-none text-dark d-block">
                                <small class="text-primary">[${noti.notificationType}]</small>
                                <div class="mb-1">${noti.content}</div>
                                <small class="text-secondary" style="font-size: 0.8rem;">
                                    ${new Date(noti.createdAt).toLocaleString()}
                                </small>
                            </a>
                        </li>
                    `;
                    listArea.innerHTML += item;
                });
            }
            // 4. 모달 띄우기
            myModal.show();
        })
        .catch(error => console.error('Error:', error));
}

// 페이지 로드 시 자동으로 연결 시도
document.addEventListener("DOMContentLoaded", function() {
    connect();
});