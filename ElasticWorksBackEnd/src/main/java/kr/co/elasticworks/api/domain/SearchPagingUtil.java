package kr.co.elasticworks.api.domain;

import lombok.Data;

@Data
public class SearchPagingUtil {
	private String searchType;
	private String searchKeyword;
	private int category;
	
	private int currentPageNo;

//	private int[] boardNumber; // 뷰에서 보여질 게시글 번호(유동적)

	private int boardsTotalCount; // 전체 글 수
	private int boardsPerPage; // 페이징당 출력할 글 개수

	private int pageTotalCount;
	private int pageSize; // 초기값으로 페이지범위를 10으로 셋팅

	private int startPageNo;
	private int endPageNo;

	private int firstBoardIndex;
	private int lastBoardIndex;

	private boolean prevPage;
	private boolean nextPage;

	public SearchPagingUtil() {
		this.currentPageNo = 1;
		this.boardsPerPage = 10;
		this.pageSize = 10;
		this.category = 0;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	
	public void setCategory(int category) {
		this.category = category;
	}

	public SearchPagingUtil(int currentPageNo, int boardsPerPage, int pageSize) {
		if (currentPageNo < 1) {
			currentPageNo = 1;
		}
		if (boardsPerPage < 1 || boardsPerPage > 100) {
			boardsPerPage = 10;
		}
		if (pageSize < 10 || pageSize > 20) {
			pageSize = 10;
		}

		this.currentPageNo = currentPageNo;
		this.boardsPerPage = boardsPerPage;
		this.pageSize = pageSize;
	}

	public void setTotalBoardCount(int boardsTotalCount) {
		this.boardsTotalCount = boardsTotalCount;

		if (boardsTotalCount > 0) {
			calculation();
		}
	}

//	public void setBoardNumber(int currentPageNo) {
//		if (this.currentPageNo != currentPageNo) {
//			this.boardNumber = int[] boardNumber;
//		}
//		if (currentPageNo * boardsPerPage >= boardsTotalCount) {
//			for (int i = (currentPageNo * boardsPerPage) - 9; i <= boardsTotalCount; i++) {
//				boardNumber[i - 1] = i;
//
//			}
//		} else {
//			for (int j = (currentPageNo * boardsPerPage) - 9; j <= (currentPageNo * boardsPerPage); j++) {
//				boardNumber[j - 1] = j;
//			}
//		}
//	}

	private void calculation() {

		/* 전체 페이지 수 (현재 페이지 번호가 전체 페이지 수보다 크면 현재 페이지 번호에 전체 페이지 수를 저장) */
		pageTotalCount = ((boardsTotalCount - 1) / getBoardsPerPage()) + 1;
		if (getCurrentPageNo() > pageTotalCount) {
			setCurrentPageNo(pageTotalCount);
		}

		/* 페이지 리스트의 첫 페이지 번호 */
		startPageNo = ((getCurrentPageNo() - 1) / getPageSize()) * getPageSize() + 1;

		/* 페이지 리스트의 마지막 페이지 번호 (마지막 페이지가 전체 페이지 수보다 크면 마지막 페이지에 전체 페이지 수를 저장) */
		endPageNo = startPageNo + getPageSize() - 1;
		if (endPageNo > pageTotalCount) {
			endPageNo = pageTotalCount;
		}

		/* SQL의 조건절에 사용되는 첫 RNUM */
		firstBoardIndex = (getCurrentPageNo() - 1) * getBoardsPerPage();

		/* SQL의 조건절에 사용되는 마지막 RNUM */
		lastBoardIndex = getCurrentPageNo() * getBoardsPerPage();

		/* 이전 페이지 존재 여부 */
		prevPage = startPageNo != 1;

		/* 다음 페이지 존재 여부 */
		nextPage = (endPageNo * getBoardsPerPage()) < boardsTotalCount;

		prevPage = (currentPageNo > 1) ? true : false;
		nextPage = (endPageNo < pageTotalCount || (startPageNo < endPageNo && currentPageNo < pageTotalCount)) ? true
				: false;

		

	}

}
