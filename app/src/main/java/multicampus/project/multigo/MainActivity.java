package multicampus.project.multigo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import multicampus.project.multigo.ui.basket.ItemFragment;
import multicampus.project.multigo.ui.basket.data.ItemsVO;
import multicampus.project.multigo.utils.HttpManager;
import multicampus.project.multigo.utils.SharedMsg;


public class MainActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {

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
                String lightStatus = bundle.getString("data");
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
//        super.onBackPressed();
        Log.d("MainActivity","onBackPressed 호출");
        finish();
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
            Log.d("MainRunnable", "서버와 연결 성공!");
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            pw = new PrintWriter(s.getOutputStream());
            Runnable revRunnable = () -> {
                try {
                    String revString = "";
                    while ((revString = br.readLine()) != null) {

                        Message msg = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("data", revString);
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }

                } catch (IOException e) {
                    Log.d("MainRunnable","서버와 연결이 실패했습니다.");
                    e.printStackTrace();
                }
            };
            MainActivity.executorService.execute(revRunnable);

            while (true) {
                String msg = sharedObj.popMsg();
                Log.i("MainRunnable", msg);
                pw.println(msg);
                pw.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
