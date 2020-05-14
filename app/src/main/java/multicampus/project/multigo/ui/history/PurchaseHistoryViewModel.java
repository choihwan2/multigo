package multicampus.project.multigo.ui.history;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PurchaseHistoryViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private int cntNum = 0;

    public PurchaseHistoryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment" + cntNum);
        Log.i("PurchaseHistory" , "PurchaseHistory() 실행");
    }

    public LiveData<String> getText() {
        Log.i("PurchaseHistory","getText() 호출");
        return mText;
    }

    public void upText(){
        for(int i = 0; i< 5; i++){
            cntNum++;
            mText.postValue("This is dashboard framgent " + cntNum); // data 변환시 사용
        }
    }
}