package multicampus.project.multigo.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import multicampus.project.multigo.data.BasketItemVO;
import multicampus.project.multigo.data.ItemsVO;
import multicampus.project.multigo.data.ListsVO;

public class AppHelper {

    private AppHelper(){

    }

    public static AppHelper getInstance() {
        return InnerInstanceClass.instance;
    }

    private static class InnerInstanceClass{
        private static final AppHelper instance = new AppHelper();
    }

    /*
        NOTE : Server 와의 프로토콜
     */

    public static final String ENTER = "@@Enter";
    public static final String EXIT = "@@Exit";
    public static final String LOGIN = "@@SetID ";
    public static final String GET_ITEM = "@@GetItem ";
    public static final String GET_LIST = "@@GetAllList ";
    public static final String GET_DETAIL = "@@GetAllLais ";
    public static final String ADD_LIST = "@@AddList ";
    public static final String ADD_DETAIL = "@@AddLais ";
    public static final String TERMINATE = "@@Terminate";
    public static final String THREAD_STOP = "@@STOP";
    public static final String ITEM_START = "ITEM";

    /*
        NOTE : Firebase 에서 사용되는 정보들
     */

    private static String userId = "";
    public static final String ENTRANCE_REF = "Entrance";
    public static final String BASKET_REF = "Basket";
    public static boolean isEntered;

    /*
        NOTE Jackson Library 에서 사용되는 변수들
     */
    private static ObjectMapper mapper = new ObjectMapper();
    private static TypeReference<ArrayList<ListsVO>> listTypeRef = new TypeReference<ArrayList<ListsVO>>() {};

    /*
     * NOTE 서버에서 받아온 구매 리스트 String 을 Json 형태로 변환하는 함수
     */
    public static List<ListsVO> initListData(String revString) {
        List<ListsVO> list = null;
        try {
            list = mapper.readValue(revString, listTypeRef);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ItemsVO toJsonItemVO(String revString){
        ItemsVO item = null;
        try {
            item = mapper.readValue(revString,ItemsVO.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return item;
    }

    public static String toJsonStringList(List<BasketItemVO> list){
        String jsonString= "";
        try {
            jsonString = mapper.writeValueAsString(list);

        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonString;
    }



    public static void setIsEntered(boolean isEntered) {
        AppHelper.isEntered = isEntered;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        AppHelper.userId = userId;
    }
}
