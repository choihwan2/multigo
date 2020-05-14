package multicampus.project.multigo.ui.home;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<Bitmap> qrBitmap;
    private String mUserId,mUserName;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        qrBitmap = new MutableLiveData<>();

        assert user != null;
        mUserId = user.getUid();
        mUserName = user.getDisplayName();

    }

    public LiveData<String> getText() {
        mText.setValue("Hello, " + mUserName + "님");
        return mText;
    }

    public LiveData<Bitmap> getQr() {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(mUserId, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrBitmap.setValue(bitmap);
        } catch (Exception e) {
        }
        return qrBitmap;
    }
}