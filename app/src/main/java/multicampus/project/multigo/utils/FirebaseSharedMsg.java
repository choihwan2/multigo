package multicampus.project.multigo.utils;

import java.util.LinkedList;
import java.util.Queue;

public class FirebaseSharedMsg {

    /*
        NOTE 안드로이드 앱 내의 스레드들이 사용하는 공유 객체
     */
    private Queue<String> msg_q = new LinkedList<String>();
    private final static Object MONITER = new Object();

    private FirebaseSharedMsg() {

    }

    public void addMsg(String msg) {
        synchronized (MONITER) {
            msg_q.add(msg);
            MONITER.notify();
        }
    }

    public String popMsg() {
        String msg = null;
        synchronized (MONITER) {
            while (msg_q.isEmpty()) {
                try {
                    MONITER.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            msg = msg_q.poll();
        }
        return msg;
    }

    private static class InnerInstanceClass{
        private static final FirebaseSharedMsg instance = new FirebaseSharedMsg();
    }

    public static FirebaseSharedMsg getInstance() {
        return InnerInstanceClass.instance;
    }
}

