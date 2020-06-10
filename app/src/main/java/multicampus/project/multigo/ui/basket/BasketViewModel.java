package multicampus.project.multigo.ui.basket;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import multicampus.project.multigo.data.ItemsVO;

public class BasketViewModel extends ViewModel {
    private MutableLiveData<ArrayList<ItemsVO>> itemLiveData;
    private ArrayList<ItemsVO> itemArrayList;

    public BasketViewModel() {
        itemLiveData = new MutableLiveData<>();
        init();
    }

    MutableLiveData<ArrayList<ItemsVO>> getItemsVOMutableLiveData() {
        return itemLiveData;
    }

    private void init(){
        populateList();
        itemLiveData.setValue(itemArrayList);
    }

    private void populateList(){
        ItemsVO item = new ItemsVO("1","사과",1000,1);
        itemArrayList = new ArrayList<>();
        itemArrayList.add(item);
    }

    void addItem(ItemsVO itemsVO){
        itemArrayList.add(itemsVO);
        itemLiveData.postValue(itemArrayList);
    }
}
