package multicampus.project.multigo.data;

public class LaisVO {
	private int list_id;
	private String item_id;
	private int cnt;

	public LaisVO(int list_id, String item_id, int cnt) {
		this.list_id = list_id;
		this.item_id = item_id;
		this.cnt = cnt;
	}

	public int getList_id() {
		return list_id;
	}
	public void setList_id(int list_id) {
		this.list_id = list_id;
	}
	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
}
