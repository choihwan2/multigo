package multicampus.project.multigo.ui.history;

import java.util.ArrayList;
import java.util.List;

import multicampus.project.multigo.data.ListsVO;

public class HistoryPresenter {
    private HistoryPresenter.View view;
    public HistoryPresenter(HistoryPresenter.View view ) {
        this.view = view;
    }

    public void initData() {
        List<ListsVO> data = new ArrayList<>();
        view.addData(data);
    }

    public interface View {
        void addData(List<ListsVO> data);
    }
}
