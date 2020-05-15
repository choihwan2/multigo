package multicampus.project.multigo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import multicampus.project.multigo.data.ItemsVO;
import multicampus.project.multigo.ui.basket.BasketFragment;
import multicampus.project.multigo.ui.history.PurchaseHistoryFragment;
import multicampus.project.multigo.utils.HttpManager;
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



        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Bundle bundle = msg.getData();
                String revString = bundle.getString("data");
                assert revString != null;
                if (revString.equals("@@Enter")){
                    Log.d("MainActivity","Enter 들어옴");
                    Toast.makeText(getApplicationContext(),"매장에 입장하였습니다.",Toast.LENGTH_SHORT).show();
                    navController.navigate(R.id.action_navigation_home_to_navigation_basket);
                }
                if (revString.equals("@@Exit")){
                    Toast.makeText(getApplicationContext(),"매장에서 퇴장했습니다.",Toast.LENGTH_SHORT).show();
                    Log.d("MainActivity","Exit 들어옴");
                }
                if (revString.startsWith("@@GetAllList ")){
                    /*
                    데이터를 요청한 후에 Fragment 와 Activity 가 둘다 가지고 있는 ViewModel 을 활용해서 데이터를 주고받자.
                     */
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
        finish();
    }

    @Override
    protected void onDestroy() {
        SharedMsg.getInstance().addMsg("@@Terminate");
        super.onDestroy();
    }
}

class MainRunnable implements Runnable {

    private BufferedReader br;
    private PrintWriter pw;
    private SharedMsg sharedObj = SharedMsg.getInstance();
    private Handler handler;

    MainRunnable(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            Socket s = new Socket(HttpManager.host, HttpManager.PORT);
            Log.d("MainActivity", "서버와 연결 성공!");
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            pw = new PrintWriter(s.getOutputStream());
            Runnable revRunnable = () -> {
                try {

                    String revString = "";
                    while ((revString = br.readLine()) != null) {
                        Log.d("MainActivity",revString + "메시지 읽음");
                        Message msg = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("data", revString);
                        msg.setData(bundle);

                        handler.sendMessage(msg);
                    }
                    Log.d("MainActivity","while 끝남 들어옴");
                    br.close();
                    pw.close();
                    s.close();
                } catch (IOException e) {
                    Log.d("MainActivity","서버와 연결이 실패했습니다.");
                    e.printStackTrace();
                }
            };
            MainActivity.executorService.execute(revRunnable);
            // 처음 실행할때 사용자 ID 를 서버에 전송해 주어야한다.
            SharedMsg.getInstance().addMsg("@@SetID " + FirebaseAuth.getInstance().getCurrentUser().getUid());

            while (true) {
                String msg = sharedObj.popMsg();
                Log.d("MainActivity", msg + "를 받았따!");
                pw.println(msg);
                pw.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
