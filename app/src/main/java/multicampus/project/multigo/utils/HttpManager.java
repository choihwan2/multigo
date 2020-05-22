package multicampus.project.multigo.utils;

public class HttpManager {


    /*
        NOTE : Main Server IP and Port
     */
    public static final String HOST = "70.12.60.104";
    public static final int PORT = 6020;



    private HttpManager(){

    }

    private static class InnerInstanceClass{
        private static final HttpManager instance = new HttpManager();
    }

    public static HttpManager getInstance(){
        return  InnerInstanceClass.instance;
    }

}
