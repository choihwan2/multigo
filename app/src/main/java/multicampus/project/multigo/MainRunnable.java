package multicampus.project.multigo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import multicampus.project.multigo.data.ListsVO;
import multicampus.project.multigo.utils.AppHelper;
import multicampus.project.multigo.utils.HttpManager;
import multicampus.project.multigo.utils.SharedMsg;

public class MainRunnable implements Runnable {

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
            Socket s = new Socket(HttpManager.HOST, HttpManager.PORT);
            Log.d("MainRunnable", "서버와 연결 성공!");
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            pw = new PrintWriter(s.getOutputStream());

            /*
                NOTE 서버와의 연결을 성공하면 그 후에 소켓통신으로 들어오는 응답을 처리하기 위한 Thread 를 생성해 실행한다.
             */
            Runnable revRunnable = () -> {
                try {
                    Log.d("revRunnable","revRunnable이 실행 되었습니다." +  Thread.currentThread().getName());
                    String revString = "";
                    while ((revString = br.readLine()) != null) {
                        Log.d("revRunnable",revString + "메시지 읽음");

                        if (revString.startsWith(AppHelper.GET_LIST)){
                            String jsonString = revString.replace(AppHelper.GET_LIST, "");
                            List<ListsVO> list = AppHelper.initListData(jsonString);
                            MainData.getInstance().setUserLists(list);
                            continue;
                        }
                        if(revString.startsWith(AppHelper.TERMINATE)){
                            SharedMsg.getInstance().addMsg(AppHelper.THREAD_STOP);
                            if(br != null)
                                br.close();
                            if(pw != null) {
                                Log.d("revRunnable","pw를 null 로 만듬!" + pw.toString());
                                pw.close();
                            }
                            s.close();
                            break;
                        }

                        if(revString.startsWith(AppHelper.LOGIN)){
                            SharedMsg.getInstance().addMsg(AppHelper.GET_LIST + FirebaseAuth.getInstance().getCurrentUser().getUid());
                            continue;
                        }
                        Message msg = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("data", revString);
                        msg.setData(bundle);

                        handler.sendMessage(msg);
                    }
                    Log.d("MainRunnable","while 끝남 들어옴");
                } catch (IOException e) {
                    Log.d("MainRunnable","서버와 연결이 실패했습니다.");
                    e.printStackTrace();
                }
            };

            MainActivity.executorService.execute(revRunnable);
            /*
               NOTE 처음 실행할때 사용자 ID 를 서버에 전송해 주어야한다 그 후 장바구니 받아옴.
             */
            SharedMsg.getInstance().addMsg(AppHelper.LOGIN + FirebaseAuth.getInstance().getCurrentUser().getUid());
            while (true) {
                String msg = sharedObj.popMsg();
                if(msg.equals(AppHelper.THREAD_STOP)){
                    Log.d("MainRunnable","죽음이 찾아왔습니다.");
                    break;
                }
                Log.d("MainRunnable", msg + "를 보냈다!!");
                pw.println(msg);
                pw.flush();  // pw 는 exception 을 발생시키지 않는다.
            }
            Log.d("MainRunnable","쓰레드가 죽었씁니다.");
        } catch (Exception e) { // 가장 위에 있는 try 의 catch 이다. 위의 pw가 아니다.
            e.printStackTrace();
        }
    }
}
