package com.neighbus.inquiry; 

// Lombok import 문을 모두 제거합니다. (import lombok.Getter, import lombok.Setter 등)

public class InquiryDto {
    // 필드는 그대로 유지
    private String name;
    private String email;
    private String category;
    private String title;
    private String content;
    private Integer writerId; 
    
    // ----------------------------------------------------
    // 수동으로 Getter와 Setter 메서드를 구현합니다.
    // ----------------------------------------------------

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getWriterId() {
        return writerId;
    }

    public void setWriterId(Integer writerId) {
        this.writerId = writerId;
    }
    
    // (선택 사항) 나머지 필드 (name, email, category)에 대한 Getter/Setter도 필요하다면 추가하세요.
    // 현재 Service/Mapper에서는 사용되지 않지만, 웹 개발의 표준입니다.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}