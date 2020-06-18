package multicampus.project.multigo.data;

public class BasketItemVO {
    private int list_id;
    private String item_id;
    private String name;
    private String image;
    private int price;
    private int cnt;

    BasketItemVO(){

    }

    public BasketItemVO(String item_id, String name, int price, int cnt,String image) {
        this.list_id = 1;
        this.item_id = item_id;
        this.name = name;
        this.price = price;
        this.cnt = cnt;
        this.image = image;
    }

    public int getList_id() {
        return list_id;
    }

    public void setList_id(int list_id) {
        this.list_id = list_id;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
