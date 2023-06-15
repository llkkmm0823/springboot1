package com.ezen.g14.dto;

import lombok.Data;

@Data
public class Paging {
   private int page = 1;         // 현재 화면에 표시할 페이지 번호
   private int totalCount;         // 총 게시물의 갯수
   private int displayRow = 10;   // 한 페이지에 몇 개의 게시물을 표시할지를 저장
   private int displayPage = 10;   // 이전과 다음 버큰 사이에 몇 개의 페이지를 표시할지를 저장
   private int beginPage;         // 현재 페이지에 표시될 시작 페이지 번호
   private int endPage;         // 현재 페이지에 표시될 끝 페이지 번호
   private boolean prev;         // prev 버튼 표시 여부
   private boolean next;         // next 버튼 표시 여부
   private int startNum;         // 현재 페이지에 표시될 게시물의 시작 번호
   private int endNum;            // 현재 페이지에 표시될 게시물의 끝 번호
   private int totalPage;         // 총 페이지 수

   private void paging() {
      // double temp = page / (double)displayPage;
      // temp = Math.ceil(temp);
      // endPage = (int)(temp * displayPage);
      endPage = (int)(Math.ceil(page / (double)displayPage) * displayPage);
      beginPage = endPage - (displayPage - 1);
      totalPage = (int)Math.ceil(totalCount / (double)displayRow);
      if (totalPage < endPage) {
         endPage = totalPage;
         next = false;
      } else {
         next = true;
      }
      prev = !(beginPage == 1);
      startNum = (page - 1) * displayRow + 1;
      endNum = page * displayRow;
      System.out.println(beginPage + " " + endPage + " " + startNum + " " + endNum + " " + totalCount);
   }

   public void setTotalCount(int totalCount) {
      this.totalCount = totalCount;
      paging();
   }
}