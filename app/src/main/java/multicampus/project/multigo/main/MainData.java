package multicampus.project.multigo.main;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import multicampus.project.multigo.data.BasketItemVO;
import multicampus.project.multigo.data.ListsVO;
import multicampus.project.multigo.utils.AppHelper;

public class MainData {


    private List<ListsVO> userLists;
    private List<BasketItemVO> basketList;
    private List<String> basketKeys;

    public List<BasketItemVO> getBasketList() {
        return basketList;
    }

    public void setBasketList(List<BasketItemVO> basketList) {
        this.basketList = basketList;
    }

    public List<String> getBasketKeys() {
        return basketKeys;
    }

    public void setBasketKeys(List<String> basketKeys) {
        this.basketKeys = basketKeys;
    }

    private MainData() {

    }

    private static class InnerInstanceClass {
        private static final MainData instance = new MainData();
    }

    public static MainData getInstance() {
        return MainData.InnerInstanceClass.instance;
    }



    public void setUserLists(List<ListsVO> userLists) {
        this.userLists = userLists;
    }

    public List<ListsVO> getUserLists() {
        return userLists;
    }

    public int getSum() {
        int sum = 0;
        for (BasketItemVO item : basketList) {
            sum += item.getPrice();
        }
        return sum;
    }

    public void clearData(){
        basketList.clear();
        basketKeys.clear();
        FirebaseDatabase.getInstance().getReference().child(AppHelper.BASKET_REF).child(AppHelper.getUserId()).removeValue();
    }
}
