package multicampus.project.multigo.ui.basket.data;

public class ItemsVO {
	private String item_id;
	private String name;
	private int price;

	public ItemsVO(String item_id, String name, int price) {
		this.item_id = item_id;
		this.name = name;
		this.price = price;
	}

	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
}
