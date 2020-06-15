package multicampus.project.multigo.fragment.history;

import java.util.List;

import multicampus.project.multigo.main.MainData;
import multicampus.project.multigo.data.ListsVO;

public class HistoryPresenter {
    private HistoryPresenter.View view;
    public HistoryPresenter(HistoryPresenter.View view ) {
        this.view = view;
    }

    public void initData() {
        List<ListsVO> data = MainData.getInstance().getUserLists();
        view.addData(data);
    }

    public interface View {
        void addData(List<ListsVO> data);
    }
}
