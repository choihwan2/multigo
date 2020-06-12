package multicampus.project.multigo.fragment.mores;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MoreViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MoreViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("설명이 들어갈 공간입니다.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}