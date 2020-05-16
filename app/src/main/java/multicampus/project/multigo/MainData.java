package multicampus.project.multigo;

import java.util.List;

import multicampus.project.multigo.data.ListsVO;
import multicampus.project.multigo.utils.HttpManager;

public class MainData {

    private MainData() {

    }

    private static class InnerInstanceClass {
        private static final MainData instance = new MainData();
    }

    public static MainData getInstance() {
        return MainData.InnerInstanceClass.instance;
    }

    private List<ListsVO> mLists;

    public MainData(List<ListsVO> lists) {
        this.mLists = lists;
    }

    public void setmLists(List<ListsVO> mLists) {
        this.mLists = mLists;
    }

    public List<ListsVO> getmLists() {
        return mLists;
    }
}
