package com.neighbus.freeboard;

import java.time.LocalDateTime;

public class FreeboardDTO {
    private int id; // 게시글 id
    private String title;
    private String content;
    private int writer; // 작성자 id
    private LocalDateTime createdAt;
    private int viewCount;
    private String writerNickname;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getWriter() {
        return writer;
    }

    public void setWriter(int writer) {
        this.writer = writer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public String getWriterNickname() {
        return writerNickname;
    }

    public void setWriterNickname(String writerNickname) {
        this.writerNickname = writerNickname;
    }

    private String writerUsername;

    public String getWriterUsername() {
        return writerUsername;
    }

    public void setWriterUsername(String writerUsername) {
        this.writerUsername = writerUsername;
    }

    // 페이징 관련 필드
    private int searchAllCnt;      // 게시글 전체 개수
    private int selectPageNo = 1;  // 선택된 페이지 번호
    private int rowCnt = 10;       // 한번에 보여될 행의 개수
    private int beginPageNo;       // 보여줄 시작 페이지 번호
    private int endPageNo;         // 보여줄 끝 페이지 번호
    private int beginRowNo;        // 보여줄 시작 행 번호
    private int endRowNo;          // 보여줄 끝 행 번호

    // 검색
    private String keyword;        // 검색 키워드

    public int getSearchAllCnt() {
        return searchAllCnt;
    }

    public void setSearchAllCnt(int searchAllCnt) {
        this.searchAllCnt = searchAllCnt;
    }

    public int getSelectPageNo() {
        return selectPageNo;
    }

    public void setSelectPageNo(int selectPageNo) {
        this.selectPageNo = selectPageNo;
    }

    public int getRowCnt() {
        return rowCnt;
    }

    public void setRowCnt(int rowCnt) {
        this.rowCnt = rowCnt;
    }

    public int getBeginPageNo() {
        return beginPageNo;
    }

    public void setBeginPageNo(int beginPageNo) {
        this.beginPageNo = beginPageNo;
    }

    public int getEndPageNo() {
        return endPageNo;
    }

    public void setEndPageNo(int endPageNo) {
        this.endPageNo = endPageNo;
    }

    public int getBeginRowNo() {
        return beginRowNo;
    }

    public void setBeginRowNo(int beginRowNo) {
        this.beginRowNo = beginRowNo;
    }

    public int getEndRowNo() {
        return endRowNo;
    }

    public void setEndRowNo(int endRowNo) {
        this.endRowNo = endRowNo;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
