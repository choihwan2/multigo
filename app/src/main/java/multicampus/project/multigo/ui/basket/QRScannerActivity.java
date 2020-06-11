package multicampus.project.multigo.ui.basket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import multicampus.project.multigo.R;
import multicampus.project.multigo.utils.AppHelper;
import multicampus.project.multigo.utils.SharedMsg;

public class QRScannerActivity extends AppCompatActivity {
    IntentIntegrator integrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scanner);

        integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                finish();
            } else {
                // NOTE QR 리더에 성공했을때 다시 MainActivity 로 돌아가야한다.
                if (result.getContents().startsWith(AppHelper.ITEM_START)) {
                    SharedMsg.getInstance().addMsg(AppHelper.GET_ITEM + result.getContents());
                }else{
                    Toast.makeText(this, "올바른 상품을 스캔해주세요", Toast.LENGTH_LONG).show();
                }
                integrator.initiateScan();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
