package multicampus.project.multigo.ui.basket;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import multicampus.project.multigo.data.ItemsVO;

public class BasketViewModel extends ViewModel {
    MutableLiveData<ArrayList<ItemsVO>> itemLiveData;
    ArrayList<ItemsVO> itemArrayList;

    public BasketViewModel() {
        itemLiveData = new MutableLiveData<>();
        init();
    }

    public MutableLiveData<ArrayList<ItemsVO>> getItemsVOMutableLiveData() {
        return itemLiveData;
    }

    public void init(){
        populateList();
        itemLiveData.setValue(itemArrayList);
    }

    public void populateList(){
        ItemsVO item = new ItemsVO("1","사과",1000,1);
        itemArrayList = new ArrayList<>();
        itemArrayList.add(item);
    }

    public void addItem(ItemsVO itemsVO){
        itemArrayList.add(itemsVO);
        itemLiveData.postValue(itemArrayList);
    }
}
