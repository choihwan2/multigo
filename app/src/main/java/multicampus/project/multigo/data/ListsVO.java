package multicampus.project.multigo.data;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ListsVO {
	private int list_id;
	private String purchase_date;
	private int total;
	private String user_id;
	private int market_id;

	public int getMarket_id() {
		return market_id;
	}

	public void setMarket_id(int market_id) {
		this.market_id = market_id;
	}

	public int getList_id() {
		return list_id;
	}
	public void setList_id(int list_id) {
		this.list_id = list_id;
	}
	public String getPurchase_date() {
		try {
			Date nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(purchase_date);
			SimpleDateFormat f = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
			assert nowDate != null;
			purchase_date = f.format(nowDate);
		}catch (Exception e){
			e.printStackTrace();
		}

		return purchase_date;
	}
	public void setPurchase_date(String purchase_date) {
		this.purchase_date = purchase_date;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
}
