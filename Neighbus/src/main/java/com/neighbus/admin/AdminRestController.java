package com.neighbus.admin;

import com.neighbus.inquiry.InquiryService;
import com.neighbus.notice.NoticeDto;
import com.neighbus.notice.NoticeService;
import com.neighbus.freeboard.FreeboardService;
import com.neighbus.recruitment.RecruitmentDTO;
import com.neighbus.recruitment.RecruitmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.neighbus.account.AccountDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController // JSON 데이터를 반환하는 컨트롤러
@RequestMapping("/api/admin")
public class AdminRestController {

    private final InquiryService inquiryService;
    private final AdminService adminService;
    private final NoticeService noticeService;
    private final FreeboardService freeboardService;
    private final RecruitmentService recruitmentService;

    @Autowired // 의존성 주입
    public AdminRestController(InquiryService inquiryService, AdminService adminService, NoticeService noticeService, FreeboardService freeboardService, RecruitmentService recruitmentService) {
        this.inquiryService = inquiryService;
        this.adminService = adminService;
        this.noticeService = noticeService;
        this.freeboardService = freeboardService;
        this.recruitmentService = recruitmentService;
    }

    // 1. 회원 목록 조회 API
    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getUserList() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Map<String, Object>> users = adminService.getAllUsers();
            response.put("status", 1);
            response.put("data", users);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", 0);
            response.put("message", "회원 목록 조회 실패: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // 회원 삭제 API
    @PostMapping("/users/delete")
    public ResponseEntity<Map<String, Object>> deleteUser(@RequestBody Map<String, Integer> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            int userId = request.get("id");
            int result = adminService.deleteUser(userId);

            if (result == 1) {
                response.put("status", 1);
                response.put("message", "회원이 삭제되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", 0);
                response.put("message", "회원 삭제 실패");
                return ResponseEntity.internalServerError().body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", 0);
            response.put("message", "회원 삭제 중 오류 발생: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // 2. 문의 목록 조회 API
    @GetMapping("/inquiries")
    public List<Map<String, Object>> getInquiryList() {
        // (권한 체크 로직 필요)
        return inquiryService.getAllInquiries();
    }
    
    // 2. 답변 처리 및 상태 업데이트 API
    @PostMapping("/process-inquiry")
    public ResponseEntity<Map<String, Object>> processInquiry(@RequestBody Map<String, Object> payload) {
        Map<String, Object> response = new HashMap<>();

        try {
            // JS에서 넘어온 데이터 추출
            Object idObj = payload.get("inquiryId");

            Integer inquiryId = null;
            if (idObj instanceof Number) {
                inquiryId = ((Number) idObj).intValue();
            } else if (idObj != null) {
                inquiryId = Integer.parseInt(idObj.toString());
            }

            String action = (String) payload.get("action"); // 'answered' 또는 'closed'
            
            int newStatus = (action.equals("answered")) ? 2 : 3; 

            if (inquiryId == null) {
                response.put("status", 0);
                response.put("message", "문의 ID가 유효하지 않습니다.");
                return ResponseEntity.badRequest().body(response);
            }

            // DB 업데이트 (Service 호출)
            int result = inquiryService.updateInquiryState(inquiryId, newStatus); 

            if (result == 1) {
                response.put("status", 1);
                response.put("message", "상태가 성공적으로 업데이트되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", 0);
                response.put("message", "업데이트 실패: 해당 ID의 문의를 찾을 수 없습니다.");
                return ResponseEntity.internalServerError().body(response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", 0);
            response.put("message", "처리 중 서버 오류가 발생했습니다. 상세: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // ========== 공지사항 API ==========

    // 공지사항 목록 조회
    @GetMapping("/notices")
    public ResponseEntity<Map<String, Object>> getNoticeList() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Map<String, Object>> notices = noticeService.getAllNotices();
            response.put("status", 1);
            response.put("data", notices);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", 0);
            response.put("message", "공지사항 목록 조회 실패: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // 공지사항 등록
    @PostMapping("/notices/register")
    public ResponseEntity<Map<String, Object>> registerNotice(@RequestBody NoticeDto noticeDto, @AuthenticationPrincipal AccountDTO currentUser) {
        Map<String, Object> response = new HashMap<>();

        if (currentUser == null) {
            response.put("status", 0);
            response.put("message", "로그인이 필요합니다.");
            return ResponseEntity.status(401).body(response);
        }

        try {
            noticeDto.setWriter(currentUser.getId());
            int result = noticeService.registerNotice(noticeDto);

            if (result == 1) {
                response.put("status", 1);
                response.put("message", "공지사항이 등록되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", 0);
                response.put("message", "공지사항 등록 실패");
                return ResponseEntity.internalServerError().body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", 0);
            response.put("message", "공지사항 등록 중 오류 발생: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // 공지사항 수정
    @PostMapping("/notices/update")
    public ResponseEntity<Map<String, Object>> updateNotice(@RequestBody NoticeDto noticeDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            int result = noticeService.updateNotice(noticeDto);

            if (result == 1) {
                response.put("status", 1);
                response.put("message", "공지사항이 수정되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", 0);
                response.put("message", "공지사항 수정 실패");
                return ResponseEntity.internalServerError().body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", 0);
            response.put("message", "공지사항 수정 중 오류 발생: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // 공지사항 삭제
    @PostMapping("/notices/delete")
    public ResponseEntity<Map<String, Object>> deleteNotice(@RequestBody Map<String, Integer> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            int id = request.get("id");
            int result = noticeService.deleteNotice(id);

            if (result == 1) {
                response.put("status", 1);
                response.put("message", "공지사항이 삭제되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", 0);
                response.put("message", "공지사항 삭제 실패");
                return ResponseEntity.internalServerError().body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", 0);
            response.put("message", "공지사항 삭제 중 오류 발생: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // ========== 게시글 관리 API ==========

    // 게시글 목록 조회 (댓글 수 포함)
    @GetMapping("/posts")
    public ResponseEntity<Map<String, Object>> getPostList() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Map<String, Object>> posts = adminService.getPostsWithCommentCount();

            response.put("status", 1);
            response.put("data", posts);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", 0);
            response.put("message", "게시글 목록 조회 실패: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // 게시글 삭제 (관리자용)
    @PostMapping("/posts/delete")
    public ResponseEntity<Map<String, Object>> deletePost(@RequestBody Map<String, Integer> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            int postId = request.get("id");
            adminService.deletePost(postId);

            response.put("status", 1);
            response.put("message", "게시글이 삭제되었습니다.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", 0);
            response.put("message", "게시글 삭제 중 오류 발생: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // ========== 대시보드 API ==========

    // 대시보드 통계 조회
    @GetMapping("/dashboard/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, Object> stats = adminService.getDashboardStats();
            response.put("status", 1);
            response.put("data", stats);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", 0);
            response.put("message", "통계 조회 실패: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // 월별 가입자 수 조회
    @GetMapping("/dashboard/monthly-signups")
    public ResponseEntity<Map<String, Object>> getMonthlySignups() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Map<String, Object>> signups = adminService.getMonthlySignups();
            response.put("status", 1);
            response.put("data", signups);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", 0);
            response.put("message", "월별 가입자 수 조회 실패: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // 동아리별 회원 수 조회 (상위 5개)
    @GetMapping("/dashboard/top-clubs")
    public ResponseEntity<Map<String, Object>> getTopClubsByMembers() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Map<String, Object>> clubs = adminService.getTopClubsByMembers();
            response.put("status", 1);
            response.put("data", clubs);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", 0);
            response.put("message", "동아리별 회원 수 조회 실패: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // ========== 동아리 관리 API ==========

    // 동아리 목록 조회
    @GetMapping("/clubs")
    public ResponseEntity<Map<String, Object>> getClubList() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Map<String, Object>> clubs = adminService.getAllClubsWithMemberCount();
            response.put("status", 1);
            response.put("data", clubs);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", 0);
            response.put("message", "동아리 목록 조회 실패: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // 동아리 삭제
    @PostMapping("/clubs/delete")
    public ResponseEntity<Map<String, Object>> deleteClub(@RequestBody Map<String, Integer> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            int clubId = request.get("id");
            int result = adminService.deleteClub(clubId);

            if (result == 1) {
                response.put("status", 1);
                response.put("message", "동아리가 삭제되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", 0);
                response.put("message", "동아리 삭제 실패");
                return ResponseEntity.internalServerError().body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", 0);
            response.put("message", "동아리 삭제 중 오류 발생: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // ========== 모임 관리 API ==========

    // 모임 목록 조회
    @GetMapping("/gatherings")
    public ResponseEntity<Map<String, Object>> getGatheringList() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<RecruitmentDTO> recruitments = recruitmentService.findAllRecruitments();

            List<Map<String, Object>> gatheringsWithMemberCount = recruitments.stream()
                .map(recruitment -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", recruitment.getId());
                    map.put("clubId", recruitment.getClubId());
                    map.put("title", recruitment.getTitle());
                    map.put("content", recruitment.getContent());
                    map.put("writer", recruitment.getWriter());
                    map.put("address", recruitment.getAddress());
                    map.put("maxUser", recruitment.getMaxUser());
                    map.put("createdAt", recruitment.getCreated_at());
                    map.put("meetingDate", recruitment.getMeetingDate());
                    map.put("latitude", recruitment.getLatitude());
                    map.put("longitude", recruitment.getLongitude());

                    int memberCount = recruitmentService.countMembers(recruitment.getId());
                    map.put("memberCount", memberCount);

                    return map;
                })
                .collect(Collectors.toList());

            response.put("status", 1);
            response.put("data", gatheringsWithMemberCount);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", 0);
            response.put("message", "모임 목록 조회 실패: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // 모임 삭제
    @PostMapping("/gatherings/delete")
    public ResponseEntity<Map<String, Object>> deleteGathering(@RequestBody Map<String, Integer> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            int recruitmentId = request.get("id");
            int result = recruitmentService.deleteRecruitment(recruitmentId);

            if (result == 1) {
                response.put("status", 1);
                response.put("message", "모임이 삭제되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", 0);
                response.put("message", "모임 삭제 실패");
                return ResponseEntity.internalServerError().body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", 0);
            response.put("message", "모임 삭제 중 오류 발생: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // ========== 갤러리 관리 API ==========

    // 갤러리 목록 조회
    @GetMapping("/galleries")
    public ResponseEntity<Map<String, Object>> getGalleryList() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Map<String, Object>> galleries = adminService.getAllGalleries();
            response.put("status", 1);
            response.put("data", galleries);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", 0);
            response.put("message", "갤러리 목록 조회 실패: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // 갤러리 삭제
    @PostMapping("/galleries/delete")
    public ResponseEntity<Map<String, Object>> deleteGallery(@RequestBody Map<String, Integer> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            int galleryId = request.get("id");
            int result = adminService.deleteGallery(galleryId);

            if (result == 1) {
                response.put("status", 1);
                response.put("message", "갤러리가 삭제되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", 0);
                response.put("message", "갤러리 삭제 실패");
                return ResponseEntity.internalServerError().body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", 0);
            response.put("message", "갤러리 삭제 중 오류 발생: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // ==========================================
    // ▼▼▼ 여기부터 신고(Report) 관리 API 입니다 ▼▼▼
    // ==========================================

    // 1. 모든 신고 목록 조회
    @GetMapping("/reports")
    public ResponseEntity<Map<String, Object>> getReportList() {
        Map<String, Object> response = new HashMap<>();
        try {
            // AdminService에 getAllReports() 메서드가 있어야 함 (앞선 답변의 4번 참조)
            List<ReportDTO> reports = adminService.getAllReports();
            response.put("status", 1);
            response.put("data", reports); 
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", 0);
            response.put("message", "신고 목록 조회 실패: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // 2. 총 신고 수
    @GetMapping("/reports/totalCount")
    public ResponseEntity<Map<String, Object>> getReportTotalCount() {
        Map<String, Object> response = new HashMap<>();
        try {
            int count = adminService.getReportTotalCount();
            response.put("data", count);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // 3. 대기 중인 신고 수
    @GetMapping("/reports/pendingCount")
    public ResponseEntity<Map<String, Object>> getReportPendingCount() {
        return getCountByStatus("PENDING");
    }

    // 4. 처리 중인 신고 수
    @GetMapping("/reports/processingCount")
    public ResponseEntity<Map<String, Object>> getReportProcessingCount() {
        return getCountByStatus("PROCESSING");
    }

    // 5. 처리 완료된 신고 수
    @GetMapping("/reports/completedCount")
    public ResponseEntity<Map<String, Object>> getReportCompletedCount() {
        return getCountByStatus("COMPLETED");
    }

    // 상태별 카운트 공통 메서드
    private ResponseEntity<Map<String, Object>> getCountByStatus(String status) {
        Map<String, Object> response = new HashMap<>();
        try {
            int count = adminService.getReportStatusCount(status);
            response.put("data", count);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // 6. 신고 상태 변경
    @PostMapping("/reports/updateStatus")
    public ResponseEntity<Map<String, Object>> updateReportStatus(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // ID와 status 파라미터 추출
            int id = Integer.parseInt(request.get("id").toString());
            String status = (String) request.get("status");
            
            adminService.updateReportStatus(id, status);
            
            response.put("status", 1);
            response.put("message", "상태가 성공적으로 변경되었습니다.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", 0);
            response.put("message", "상태 변경 실패: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // 7. 신고 삭제
    @PostMapping("/reports/delete")
    public ResponseEntity<Map<String, Object>> deleteReport(@RequestBody Map<String, Integer> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            int id = request.get("id");
            adminService.deleteReport(id);
            
            response.put("status", 1);
            response.put("message", "신고 내역이 삭제되었습니다.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", 0);
            response.put("message", "삭제 실패: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}