package multicampus.project.multigo.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import multicampus.project.multigo.data.ListsVO;

public class AppHelper {

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

    /*
     * NOTE 구매 리스트를 받아오는 함수
     */
    public static List<ListsVO> initListData(String revString) {
        List<ListsVO> list = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            list = mapper.readValue(revString, new TypeReference<ArrayList<ListsVO>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
