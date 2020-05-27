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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import multicampus.project.multigo.data.ItemsVO;
import multicampus.project.multigo.ui.basket.BasketFragment;
import multicampus.project.multigo.utils.AppHelper;
import multicampus.project.multigo.utils.SharedMsg;


public class MainActivity extends AppCompatActivity implements BasketFragment.OnListFragmentInteractionListener {

    public static ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        Log.d("MainActivity","onCreate 생성됨");



        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Bundle bundle = msg.getData();
                String revString = bundle.getString("data");
                assert revString != null;
                if (revString.equals(AppHelper.ENTER)){
                    Log.d("MainActivity","Enter 들어옴");
                    Toast.makeText(getApplicationContext(),"매장에 입장하였습니다.",Toast.LENGTH_SHORT).show();
                    navController.navigate(R.id.action_navigation_home_to_navigation_basket);
                }
                if (revString.equals(AppHelper.EXIT)){
                    Toast.makeText(getApplicationContext(),"매장에서 퇴장했습니다.",Toast.LENGTH_SHORT).show();
                    Log.d("MainActivity","Exit 들어옴");
                }
            }
        };
        MainRunnable mainRunnable = new MainRunnable(handler);
        executorService.execute(mainRunnable);
    }

    @Override
    public void onListFragmentInteraction(ItemsVO item) {
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
        //SharedMsg.getInstance().addMsg(AppHelper.TERMINATE);

        //Log.d("MainActivity", "onDestory 호출");
        super.onDestroy();
    }
}
