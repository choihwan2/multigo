package multicampus.project.multigo.fragment.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import multicampus.project.multigo.main.MainActivity;
import multicampus.project.multigo.R;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button mAlreadySignUpBtn, mRegisterBtn, mCancelBtn;
    private EditText mId, mName, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initView();
        initListener();


    }

    public void initView(){
        mId = findViewById(R.id.userEmail);
        mName = findViewById(R.id.userName);
        mPassword = findViewById(R.id.sign_up_password_et);
        mAuth = FirebaseAuth.getInstance();

        mAlreadySignUpBtn = findViewById(R.id.login_now);
        mRegisterBtn = findViewById(R.id.registerBtn);
        mCancelBtn = findViewById(R.id.cancelBtn);
    }

    public void initListener(){
        mAlreadySignUpBtn.setOnClickListener(v ->{
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        mRegisterBtn.setOnClickListener(v -> {
            signUp(mId.getText().toString(),mPassword.getText().toString());
        });

        mCancelBtn.setOnClickListener(v -> {
           finish();
        });


    }

    private void signUp(String id, String password) {

        if (mName.getText().toString().equals("")){
            Toast.makeText(SignUpActivity.this, "이름을 입력해주세요",Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(id, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("LoginActivity", "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(mName.getText().toString()).build();
                        assert user != null;
                        user.updateProfile(profileUpdates);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("LoginActivity", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(SignUpActivity.this, "가입에 실패하였습니다.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
