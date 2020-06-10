package multicampus.project.multigo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import multicampus.project.multigo.data.BasketItemVO;
import multicampus.project.multigo.data.ItemsVO;
import multicampus.project.multigo.data.userEnteredData;
import multicampus.project.multigo.ui.basket.BasketFragment;
import multicampus.project.multigo.utils.AppHelper;
import multicampus.project.multigo.utils.SharedMsg;


public class MainActivity extends AppCompatActivity implements BasketFragment.OnListFragmentInteractionListener {

    public static ExecutorService executorService = Executors.newCachedThreadPool();
    private DatabaseReference mEntranceRef;
    private NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);



        AppHelper.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mEntranceRef = FirebaseDatabase.getInstance().getReference().child(AppHelper.ENTRANCE_REF).child(AppHelper.getUserId());

        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Bundle bundle = msg.getData();
                String revString = bundle.getString("data");
                assert revString != null;
                if (revString.startsWith(AppHelper.ENTER)){
                    Log.d("MainActivity","Enter 들어옴");
                    Log.d("MainActivity", revString + "메시지 받음");
                    Toast.makeText(getApplicationContext(),"매장에 입장하였습니다.",Toast.LENGTH_SHORT).show();
                    mEntranceRef.setValue(new userEnteredData(revString.split(" ")[1],true));
                }
                if (revString.startsWith(AppHelper.EXIT)){
                    Toast.makeText(getApplicationContext(),"매장에서 퇴장했습니다.",Toast.LENGTH_SHORT).show();
                    mEntranceRef.setValue(new userEnteredData("-1",false));
                }
            }
        };
        MainRunnable mainRunnable = new MainRunnable(handler);
        executorService.execute(mainRunnable);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ValueEventListener entranceListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userEnteredData entDate = dataSnapshot.getValue(userEnteredData.class);
                if(entDate != null) {
                    AppHelper.setIsEntered(entDate.isEnterState());
                    Log.d("MainActivity","enterState 변경됨");
                    if(entDate.isEnterState()){
                        navController.navigate(R.id.action_navigation_home_to_navigation_basket);
                    }else{
                        navController.navigate(R.id.action_navigation_home_refresh);
                    }
                }else {
                    mEntranceRef.setValue(new userEnteredData("-1",false));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("MainActivity","마켓입장여부 실패");
            }
        };
        mEntranceRef.addValueEventListener(entranceListener);
    }

    @Override
    public void onListFragmentInteraction(BasketItemVO item) {
        Log.d("MainActivity",item.getItem_id());
    }

    @Override
    public void onBackPressed() {
        SharedMsg.getInstance().addMsg(AppHelper.TERMINATE);
        Log.d("MainActivity", "onBackPressed 호출");
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
