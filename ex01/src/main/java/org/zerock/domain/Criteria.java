/**
 * 
 */
package org.zerock.domain;

/**
 * @author	: Juet
 * @date	: 2018. 1. 30.
 * @desc	: DAO 처리를 도와줄 Criteria 클래스 
 * 				MyBatis의 SQL Mapper에는 공통 규칙
 * 					=> #{page} 와 같은 파라미터 사용 시 내부적으로 page 속성의 getter에 해당하는 getPage() 메소드 호출
 * 
 * 				예를 들어
 * 					 select * from tbl_board where bno > 0 order by bno desc limit #{pageStart}, #{perPageNum}
 * 				SQL문에는 #{pageStart}, #{perPageNum} 이라는 파라미터가 존재 함.
 * 				위 SQL을 실행 한다면, 파라미터로 전달되는 객체는 getPageStart(), getPerPageNum()이라는 메소드를 가지면 됨 
 * 
 * 				내부적으로 어떻게 메소드가 호출되는지는 알지 못함//
 * 
 * 				Criteria '검색 기준, 분류 기준'
 * 
 */
public class Criteria {
	
	private int page;
	private int perPageNum;
	
	/*변수 전달 방식 확인을 위한 Test 변수
	 * MVC 컨트롤러의 파라미터 타입 자동 객체 생성에서 변수를 받고 사용하는 방식 Test
	 * 1. Criteria 클래스에 변수를 받는 경우
	 * 2. Controller에 메소드 지역변수로 변수 선언하여 받은 후 바로 메세지로 리턴 하는 경우*/
	private String testMessage;
	
	public Criteria() {
		this.page = 1;
		this.perPageNum = 10;
	}
	
	public void setPage(int page) {
		
		if(page <=0){
			this.page = 1;
			return;
		}
		
		this.page = page;
	}
	
	public void setPerPageNum(int perPageNum) {
		
		if(perPageNum <= 0 || perPageNum > 100){
			this.perPageNum = 10;
			return;
		}
		
		this.perPageNum = perPageNum;
	}
	
	public int getPage(){
		return page;
	}
	
	/**
	 * @author	: Juet
	 * @date	: 2018. 1. 31.
	 * @desc	: method for MyBatis SQL Mapper
	 *
	 * @return
	 */
	public int getPageStart(){
		
		return(this.page -1 )*perPageNum;
	}
	
	/**
	 * @author	: Juet
	 * @date	: 2018. 1. 31.
	 * @desc	: method for MyBatis SQL Mapper
	 *
	 * @return
	 */
	public int getPerPageNum() {
		
		return this.perPageNum;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Criteria [page="+page+", "
				+ "perPageNum="+perPageNum +"]";
	}
	
	/**
	 * @author	: Juet
	 * @date	: 2018. 2. 26.
	 * @desc	: MVC 모델의 클래스 파라미터 타입 객체 자동 생성 변수 전달 Test 를 위해
	 * 				임의로 생성한 메소드
	 *
	 * @return
	 */
	public String getTestMessage(){
		return this.testMessage;
	}
	
	/**
	 * @param testMessage the testMessage to set
	 */
	public void setTestMessage(String testMessage) {
		this.testMessage = testMessage;
	}

}
