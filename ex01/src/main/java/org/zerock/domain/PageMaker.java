/**
 * 
 */
package org.zerock.domain;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author	: Juet
 * @date	: 2018. 2. 27.
 * @desc	: 페이징 처리용 클래스
 * 				외부에서 입력되는 데이터와 DB의 데이터를 사용, 계산 통해 페이징 처리
 * 				
 * 				Math.ceil() : 올림
 * 				Math.round() : 반올림
 * 				Math.floor() : 버림
 * 				Math.abs() : 절대값
 * 
 *  			페이징 처리를 위해서 필요한 변수들
 *  			Criteria.class
 *  			page : 현제 조회하는 페이지의 변호
 *  			perPageNum : 한 페이지당 출력하는 데이터의 개수
 *  			
 *  			DB
 *  			totalCount : SQL 쿼리 통한 데이터의 전체 개수
 *  		
 *  			PageMaker.class <= 계산 통해 값 정의
 *  			startPage
 *  			endPage
 *  			prev
 *  			next
 *  
 *  			페이징 처리 로직의 핵심은 전체 개수와 보여주고자 하는 페이지 개수 간 연산을 통해
 *  			endPage의 값을 찾아내는 것
 * 				
 */

public class PageMaker {

	private int totalCount;
	private int startPage;
	private int endPage;
	private boolean prev;
	private boolean next;
	private boolean test = true;
	private boolean what = false;
	
	private int displayPageNum = 10;
	
	private Criteria cri;
	
	/**
	 * 
	 */
	public PageMaker() {
		// TODO Auto-generated constructor stub
	}
	
	public void setCri(Criteria cri){
		this.cri = cri;
	}
	
	public void setTotalCount(int totalCount){
		this.totalCount = totalCount;
		
		calcData();
	}

	/**
	 * @author	: Juet
	 * @date	: 2018. 2. 27.
	 * @desc	: 페이징 처리에 표현될 보여지는 페이지 수, 첫 페이지 번호, 끝 페이지 번호
	 * 				이전, 다음 버튼 보여주기 등을 처리하는 메서드
	 * 				
	 *
	 */
	private void calcData(){
		
		endPage = (int) (Math.ceil(cri.getPage() / (double) displayPageNum) * displayPageNum);
		
		startPage = (endPage - displayPageNum) + 1;
		
		int tempEndPage = (int)(Math.ceil(totalCount / (double) cri.getPerPageNum()));
		
		if(endPage > tempEndPage){
			endPage = tempEndPage;
		}
		
		prev = startPage == 1? false : true;
		
		next = endPage * cri.getPerPageNum() >= totalCount? false : true;
	}
	
	/**
	 * @author	: Juet
	 * @date	: 2018. 3. 3.
	 * @desc	: Get 방식으로 이루어지는 Paging 처리
	 * 				페이지 이동 간에 데이터를 옮기기 위해 코드가 길어지는 경향 존재
	 * 				페이징 및 페이지 조회는 Uri를 복사해서 타인에게 전달하여 페이지를 직접 열어 볼 수 있는 방식으로
	 * 				구현하는 것이 정보 활용과 절달이 용이하기 때문에 Uri에 정보 담는 것이 좋음
	 * 				아래 UriComponents 객체를 선언하여 페이지 이동 간 Uri에 동적으로 정보 생성하여 
	 * 				담아둔다.
	 *
	 * @param page
	 * @return
	 */
	public String makeQuery(int page){
		
		UriComponents uriComponents =
				UriComponentsBuilder.newInstance()
				.queryParam("page", page)
				.queryParam("perPageNum", cri.getPerPageNum())
				.build();
		return uriComponents.toString();
	}
	
	/**
	 * @param displayPageNum the displayPageNum to set
	 */
	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}
	
	/**
	 * @param endPage the endPage to set
	 */
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	
	/**
	 * @param next the next to set
	 */
	public void setNext(boolean next) {
		this.next = next;
	}
	
	/**
	 * @param prev the prev to set
	 */
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	
	/**
	 * @param startPage the startPage to set
	 */
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	
	/**
	 * @return the cri
	 */
	public Criteria getCri() {
		return cri;
	}
	
	/**
	 * @return the displayPageNum
	 */
	public int getDisplayPageNum() {
		return displayPageNum;
	}
	
	/**
	 * @return the endPage
	 */
	public int getEndPage() {
		return endPage;
	}
	
	/**
	 * @return the startPage
	 */
	public int getStartPage() {
		return startPage;
	}
	
	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}
	
	/**
	 * @author	: Juet
	 * @date	: 2018. 3. 1.
	 * @desc	: JSP 파일에서 pageMaker.prev를 읽는데 문제가 자꾸 발생함
	 * 				javax.el.PropertyNotFoundException: Property [prev] not readable on type
	 * 				위 Exception을 뱉으며 계속해서 컴파일 되지 않음
	 * 
	 * 				구글링을 통해 알아본 결과 대표적인 원인들이 있었음
	 * 				1. 변수를 받을 때 quotation 안에 공백을 포함하는 경우
	 * 					Ex) <c:if test=" ${pageMaker.prev}"> 
	 * 				2. 대소문자 등 변수 명을 정확히 참조하지 못 하였을 때
	 * 					Ex) <c:if test=" ${pageMaker.Prev}"> 
	 * 				3. 참조하고자 하는 class가 public으로 선언되지 않은 경우
	 * 					Ex) class PageMaker{
	 * 						
	 * 						}
	 * 				앞서 다룬 세가지 원인 모두 확인하고 다시 선언해보았지만 해결되지 않음
	 * 				
	 * 				4. getter, setter, toString 등 override method가 모두 선언되지 않은 경우
	 * 				4번 항목 내용을 확인 하니 참조하고자 하는 변수에 대한 getter가 선언되어 있지 않음 
	 * 				아래 두개 메소드 선언하자 jsp 파일에서 정상적으로 변수 참조 함
	 * 
	 * 				8BOKNOTE님의 블로그에서 원인과 해결 방법 찾을 수 있었음
	 * 출처: http://8boknote.tistory.com/14 [8BOKNOTE]
	 *
	 * @return
	 */
	public boolean getPrev(){
		return prev;
	}
	
	public boolean getNext(){
		return next;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
	
}
