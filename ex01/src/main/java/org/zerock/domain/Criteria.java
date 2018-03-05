/**
 * 
 */
package org.zerock.domain;

/**
 * @author	: Juet
 * @date	: 2018. 1. 30.
 * @desc	: DAO ó���� ������ Criteria Ŭ���� 
 * 				MyBatis�� SQL Mapper���� ���� ��Ģ
 * 					=> #{page} �� ���� �Ķ���� ��� �� ���������� page �Ӽ��� getter�� �ش��ϴ� getPage() �޼ҵ� ȣ��
 * 
 * 				���� ���
 * 					 select * from tbl_board where bno > 0 order by bno desc limit #{pageStart}, #{perPageNum}
 * 				SQL������ #{pageStart}, #{perPageNum} �̶�� �Ķ���Ͱ� ���� ��.
 * 				�� SQL�� ���� �Ѵٸ�, �Ķ���ͷ� ���޵Ǵ� ��ü�� getPageStart(), getPerPageNum()�̶�� �޼ҵ带 ������ �� 
 * 
 * 				���������� ��� �޼ҵ尡 ȣ��Ǵ����� ���� ����//
 * 
 * 				Criteria '�˻� ����, �з� ����'
 * 
 */
public class Criteria {
	
	private int page;
	private int perPageNum;
	
	/*���� ���� ��� Ȯ���� ���� Test ����
	 * MVC ��Ʈ�ѷ��� �Ķ���� Ÿ�� �ڵ� ��ü �������� ������ �ް� ����ϴ� ��� Test
	 * 1. Criteria Ŭ������ ������ �޴� ���
	 * 2. Controller�� �޼ҵ� ���������� ���� �����Ͽ� ���� �� �ٷ� �޼����� ���� �ϴ� ���*/
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
	 * @desc	: MVC ���� Ŭ���� �Ķ���� Ÿ�� ��ü �ڵ� ���� ���� ���� Test �� ����
	 * 				���Ƿ� ������ �޼ҵ�
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
