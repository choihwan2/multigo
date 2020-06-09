package multicampus.project.multigo;

import java.util.List;

import multicampus.project.multigo.data.ListsVO;

public class MainData {


    private List<ListsVO> userLists;

    private MainData() {

    }

    private static class InnerInstanceClass {
        private static final MainData instance = new MainData();
    }

    public static MainData getInstance() {
        return MainData.InnerInstanceClass.instance;
    }



    public void setUserLists(List<ListsVO> userLists) {
        this.userLists = userLists;
    }

    public List<ListsVO> getUserLists() {
        return userLists;
    }
}
