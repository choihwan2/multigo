package multicampus.project.multigo.data;

public class ItemsVO {
    private String item_id;
    private String name;
    private int price;
    private String image;

    ItemsVO(){
    }

    public ItemsVO(String item_id, String name, int price, int cnt,String image) {
        this.item_id = item_id;
        this.name = name;
        this.price = price;
        this.image = image;
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

    public BasketItemVO toBasketItem(int cnt) {
        BasketItemVO basketItemVO = new BasketItemVO(item_id, name, price * cnt, cnt,image);
        return basketItemVO;
    }


}
