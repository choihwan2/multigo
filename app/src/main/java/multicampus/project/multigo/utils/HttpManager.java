package multicampus.project.multigo.utils;

public class HttpManager {


    public static final String host = "";
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
