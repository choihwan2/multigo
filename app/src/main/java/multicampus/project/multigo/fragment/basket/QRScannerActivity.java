package multicampus.project.multigo.fragment.basket;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import multicampus.project.multigo.R;
import multicampus.project.multigo.utils.AppHelper;
import multicampus.project.multigo.utils.FirebaseSharedMsg;
import multicampus.project.multigo.utils.SharedMsg;

public class QRScannerActivity extends AppCompatActivity {
    private DatabaseReference mBasketReference = FirebaseDatabase.getInstance().getReference().child(AppHelper.BASKET_REF).child(AppHelper.getUserId());

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
                    makeInputDialog();
                } else {
                    Toast.makeText(this, "올바른 상품을 스캔해주세요", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void makeInputDialog() {
        final EditText inputEditText = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("상품 구입");
        builder.setMessage("구입하고자 하는 수량을 입력해주세요!");
        builder.setView(inputEditText);
        builder.setPositiveButton("구매",
                (dialog, which) -> {
                    FirebaseSharedMsg.getInstance().addMsg(inputEditText.getText().toString());
                    integrator.initiateScan();
                });
        builder.setNegativeButton("취소",
                (dialog, which) -> {
                    FirebaseSharedMsg.getInstance().addMsg("0");
                    integrator.initiateScan();
                });
        builder.show();
    }
}
