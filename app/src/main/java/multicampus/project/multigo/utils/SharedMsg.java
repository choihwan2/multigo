package multicampus.project.multigo.utils;

import java.util.LinkedList;
import java.util.Queue;

public class SharedMsg {

    private Queue<String> msg_q = new LinkedList<String>();
    private final static Object MONITER = new Object();

    private SharedMsg() {

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
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            msg = msg_q.poll();
        }
        return msg;
    }

    private static class InnerInstanceClass{
        private static final SharedMsg instance = new SharedMsg();
    }

    public static SharedMsg getInstance() {
        return InnerInstanceClass.instance;
    }
}

