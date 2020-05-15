package multicampus.project.multigo.utils;

public class HttpManager {


    public static final String host = "70.12.229.25";
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
