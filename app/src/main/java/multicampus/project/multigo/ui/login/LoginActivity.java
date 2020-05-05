package multicampus.project.multigo.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import multicampus.project.multigo.MainActivity;
import multicampus.project.multigo.R;
import multicampus.project.multigo.utils.SharedPrefManager;

public class LoginActivity extends AppCompatActivity {
    final String TAG = this.getClass().getName();

    private Button mLoginBtn;
    private EditText mInputId;
    private EditText mInputPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!SharedPrefManager.getUserID(this).equals("")) {
            finish();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_login);

            initView();
            initListener();
        }
    }


    private void initView() {
        mLoginBtn = findViewById(R.id.login_btn);
        mInputId = findViewById(R.id.login_id);
        mInputPw = findViewById(R.id.login_pw);

        mInputId.clearFocus();
        mInputPw.clearFocus();

    }

    private void initListener() {
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessLogin(mInputId.getText().toString(), mInputPw.getText().toString());
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
            }
        });
    }

    private void resultLogin(final boolean res, final String id, final String pw) {
        if (res) {
            SharedPrefManager.writeUserInfo(getApplicationContext(), id);
            final String token = SharedPrefManager.getFCMToken(this);

            // id를 통해 해당 사용자의 레코드를 찾고 토큰을 업데이트
            final String[] query = {"token=" + token};
            try {
                // 로그인을 처리할 공간
            } catch (Exception e) {
                e.printStackTrace();
            }

            finish();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), getString(R.string.fail_login_access), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private void accessLogin(final String id, final String pw) {
        if (id.equals("") || pw.equals("")) {
            Toast.makeText(getApplicationContext(), getString(R.string.fail_login_access), Toast.LENGTH_SHORT).show();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final String[] query = {"id=" + id, "pw=" + pw};
//                        final String serverRes = HttpManager.internalServerAPI("user", query, "", "GET");

//                        final boolean loginResult = !serverRes.equals("[]");
//                        resultLogin(loginResult, id, pw);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}


