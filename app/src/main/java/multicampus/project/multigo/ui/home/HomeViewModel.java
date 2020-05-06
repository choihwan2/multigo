package multicampus.project.multigo.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Hello, Choi" + "님");
        assert user != null;
        Log.i("USER","getUid : " + user.getUid());
        Log.i("USER", "getEmail : " + user.getEmail());
    }

    public LiveData<String> getText() {
        return mText;
    }
}