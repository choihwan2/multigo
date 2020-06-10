package multicampus.project.multigo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import multicampus.project.multigo.data.BasketItemVO;
import multicampus.project.multigo.data.ItemsVO;
import multicampus.project.multigo.data.ListsVO;
import multicampus.project.multigo.utils.AppHelper;
import multicampus.project.multigo.utils.HttpManager;
import multicampus.project.multigo.utils.SharedMsg;

public class MainRunnable implements Runnable {

    private BufferedReader br;
    private PrintWriter pw;
    private SharedMsg sharedObj = SharedMsg.getInstance();
    private Handler handler;
    private DatabaseReference mBasketReference = FirebaseDatabase.getInstance().getReference().child(AppHelper.BASKET_REF).child(AppHelper.getUserId());

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
                    Log.d("revRunnable", "revRunnable이 실행 되었습니다." + Thread.currentThread().getName());
                    String revString = "";
                    while ((revString = br.readLine()) != null) {
                        Log.d("revRunnable", revString + "메시지 읽음");

                        if (revString.startsWith(AppHelper.GET_LIST)) {
                            /*
                             NOTE 구매했던 내역을 받아오는 부분
                             */
                            String jsonString = revString.replace(AppHelper.GET_LIST, "");
                            List<ListsVO> list = AppHelper.initListData(jsonString);
                            MainData.getInstance().setUserLists(list);
                            continue;
                        }

                        if (revString.startsWith(AppHelper.LOGIN)) {
                            /*
                             NOTE 첫 로그인시 매장에 방문한 상태인지 확인
                             */
                            if(!revString.split(" ")[2].equals("0")){
                                revString = AppHelper.ENTER + " " + revString.split(" ")[2];
                                Log.d("MainRunnable", revString + "메시지를 엑티비티에 보냅니다.");
                            }
                            SharedMsg.getInstance().addMsg(AppHelper.GET_LIST + FirebaseAuth.getInstance().getCurrentUser().getUid());
                        }

                        if(revString.startsWith(AppHelper.GET_ITEM)){
                            revString = revString.replace(AppHelper.GET_ITEM,"");
                            Log.d("MainRunnable",revString + "을 받아와서 정제한다음 출력합니다.");
                            ItemsVO item = AppHelper.toJsonItemVO(revString);
                            BasketItemVO basketItem = item.toBasketItem();
                            mBasketReference.push().setValue(basketItem);
                            continue;
                        }

                        if (revString.startsWith(AppHelper.TERMINATE)) {
                            /*
                             NOTE 앱이 종료되었을 때 서버와 신호를 주고받아 Thread 가 DeadLock 으로 빠지는걸 방지한다.
                             */
                            SharedMsg.getInstance().addMsg(AppHelper.THREAD_STOP);
                            if (br != null)
                                br.close();
                            if (pw != null) {
                                Log.d("revRunnable", "pw를 null 로 만듬!" + pw.toString());
                                pw.close();
                            }
                            s.close();
                            break;
                        }
                        Message msg = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("data", revString);
                        msg.setData(bundle);

                        handler.sendMessage(msg);
                    }
                    Log.d("MainRunnable", "while 끝남 들어옴");
                } catch (IOException e) {
                    Log.d("MainRunnable", "서버와 연결이 실패했습니다.");
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
                if (msg.equals(AppHelper.THREAD_STOP)) {
                    // Thread 가 DeadLock 에 빠지지 않기 위해
                    break;
                }
                Log.d("MainRunnable", msg + "를 보냈다!!");
                pw.println(msg);
                pw.flush();  // pw 는 exception 을 발생시키지 않는다.
            }
        } catch (Exception e) { // 가장 위에 있는 try 의 catch 이다. 위의 pw가 아니다.
            e.printStackTrace();
        }
    }
}
