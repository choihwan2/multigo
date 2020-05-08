package multicampus.project.multigo.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import multicampus.project.multigo.MainActivity;
import multicampus.project.multigo.R;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button mSignInBtn;
    private Button mSignUpBtn;
    private EditText mInputId;
    private EditText mInputPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            setContentView(R.layout.activity_login);
            initView();
            initListener();
        } else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }


    private void initView() {
        mSignInBtn = findViewById(R.id.login_sign_in);
        mSignUpBtn = findViewById(R.id.login_sign_up);
        mInputId = findViewById(R.id.login_id);
        mInputPw = findViewById(R.id.login_pw);

        mInputId.clearFocus();
        mInputPw.clearFocus();

    }

    private void checkLogin() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {

        }
    }

    private void initListener() {
        mSignInBtn.setOnClickListener(v -> signIn(mInputId.getText().toString(), mInputPw.getText().toString()));

        mSignUpBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
        });
    }

    private void signIn(String id, String pw) {
        if (id.equals("") || pw.equals("")) {
            return;
        }
        mAuth.signInWithEmailAndPassword(id, pw)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("LoginActivity", "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("LoginActivity", "signInWithEmail:failure", task.getException());
                        Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다. 아이디나 비밀번호, 네트워크 환경을 확인해주세요.",
                                Toast.LENGTH_SHORT).show();
                    }

                });
    }

}


