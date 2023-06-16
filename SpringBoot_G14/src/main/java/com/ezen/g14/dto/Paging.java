package com.ezen.g14.dto;

import lombok.Data;

@Data
public class Paging {

	private int page = 1;
	private int totalCount;
	private int beginPage;
	private int endPage;
	private int displayRow = 10;
	private int displayPage = 10;
	private boolean prev;
	private boolean next;
	private int startNum;
	private int endNum;

	public void paging() { // public으로 바꿔서 페이징을 수동으로 호출하도록 만듦
		double temp = page / (double) displayPage;
		temp = Math.ceil(temp);
		endPage = (int) (temp * displayPage);
		beginPage = endPage - (displayPage - 1);
		int totalPage = (int) Math.ceil(totalCount / (double) displayRow);
		if (totalPage < endPage) {
			endPage = totalPage;
			next = false;
		} else {
			next = true;
		}
		prev = (beginPage == 1) ? false : true;
		startNum = (page - 1) * displayRow + 1;
		endNum = page * displayRow;
		System.out.println(beginPage + " " + endPage + " " + startNum + " " + endNum + " " + totalCount);
	}
}
