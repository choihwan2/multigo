package multicampus.project.multigo.ui.basket;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import multicampus.project.multigo.R;
import multicampus.project.multigo.data.ItemsVO;
import multicampus.project.multigo.data.ListsVO;
import multicampus.project.multigo.ui.basket.dummy.DummyContent;
import multicampus.project.multigo.utils.SharedMsg;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class BasketFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    public BasketFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static BasketFragment newInstance(int columnCount) {
        BasketFragment fragment = new BasketFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basket, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.basket_list);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),mColumnCount));
        recyclerView.setAdapter(new BasketRecyclerViewAdapter(DummyContent.ITEMS, mListener));

        Button sendBtn = view.findViewById(R.id.send_basket_btn);
        sendBtn.setOnClickListener(v -> {
            SharedMsg.getInstance().addMsg("@@AddList " + DummyContent.getSum());
            SharedMsg.getInstance().addMsg("@@AddLais " + DummyContent.getJsonItems());
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /*
    아래의 인터페이스를 엑티비티에서 상속받아야만 이 리사이클러 뷰를 사용할 수 있게 만들어놨따.
    그 이유는 뷰에서 동작받는 것을 처리하려고
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(ItemsVO item);
    }
}
