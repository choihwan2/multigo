package multicampus.project.multigo.ui.home;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

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
    private String userId;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        qrBitmap = new MutableLiveData<>();

        assert user != null;
        userId = user.getUid();

        mText.setValue("Hello, Choi" + "님");

    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<Bitmap> getQr() {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(userId, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrBitmap.setValue(bitmap);
        } catch (Exception e) {
        }
        return qrBitmap;
    }
}