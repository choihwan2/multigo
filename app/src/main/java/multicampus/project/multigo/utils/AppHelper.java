package multicampus.project.multigo.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import multicampus.project.multigo.data.ListsVO;

public class AppHelper {

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
