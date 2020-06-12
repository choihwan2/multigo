package multicampus.project.multigo.fragment.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import multicampus.project.multigo.R;
import multicampus.project.multigo.data.ListsVO;
import multicampus.project.multigo.utils.AppHelper;
import multicampus.project.multigo.utils.SharedMsg;

public class PurchaseHistoryFragment extends Fragment implements HistoryPresenter.View {


    HistoryRecyclerViewAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_purchase_history, container, false);
        RecyclerView historyRv = root.findViewById(R.id.history_list);
        adapter = new HistoryRecyclerViewAdapter();
        historyRv.setAdapter(adapter);
        SharedMsg.getInstance().addMsg(AppHelper.GET_LIST + FirebaseAuth.getInstance().getCurrentUser().getUid());
        HistoryPresenter presenter = new HistoryPresenter(this);
        presenter.initData();
        return root;
    }

    @Override
    public void addData(List<ListsVO> data) {
        for (ListsVO item : data) {
            adapter.addItem(item);
        }
        adapter.notifyDataSetChanged();
    }
}
