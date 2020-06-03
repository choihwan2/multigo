package multicampus.project.multigo.ui.basket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import multicampus.project.multigo.R;
import multicampus.project.multigo.data.ItemsVO;
import multicampus.project.multigo.data.ListsVO;
import multicampus.project.multigo.ui.basket.dummy.DummyContent;
import multicampus.project.multigo.utils.AppHelper;
import multicampus.project.multigo.utils.SharedMsg;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class BasketFragment extends Fragment implements LifecycleOwner {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private BasketRecyclerViewAdapter basketAdapter;
    private View view;

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

            Log.d("BasketFragment","getArgument 생성되었습니다!");
        }
        Log.d("BasketFragment","프래그먼트가 생성되었습니다!");
    }


    private Observer<ArrayList<ItemsVO>> userListUpdateObserver = new Observer<ArrayList<ItemsVO>>() {
        // NOTE ArrayList 를 보고 있다가 상품이 추가되면 새로 업데이트 해준다.
        @Override
        public void onChanged(ArrayList<ItemsVO> itemArrayList) {
            basketAdapter = new BasketRecyclerViewAdapter(itemArrayList, mListener);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            recyclerView.setAdapter(basketAdapter);

            Log.d("BasketFragment","onChanged 에 들어왔습니다.");
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_basket, container, false);

        recyclerView = view.findViewById(R.id.basket_list);

        BasketViewModel basketViewModel = ViewModelProviders.of(this).get(BasketViewModel.class);
        basketViewModel.getItemsVOMutableLiveData().observe(this, userListUpdateObserver);

        Button sendBtn = view.findViewById(R.id.send_basket_btn);
        sendBtn.setOnClickListener(v -> {
            SharedMsg.getInstance().addMsg(AppHelper.ADD_LIST + DummyContent.getSum());
            SharedMsg.getInstance().addMsg(AppHelper.ADD_DETAIL + DummyContent.getJsonItems());
        });

        FloatingActionButton floatingBtn = view.findViewById(R.id.floating_basket);
        floatingBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),QRScannerActivity.class);
            startActivity(intent);
        });


        Button addBtn = view.findViewById(R.id.add_basket_btn);
        addBtn.setOnClickListener(v -> {
            basketViewModel.addItem(new ItemsVO("001", "과자", 5000, 1));
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
    NOTE 아래의 인터페이스를 엑티비티에서 상속받아야만 이 리사이클러 뷰를 사용할 수 있게 만들어놨다.
     그 이유는 뷰에서 동작받는 것을 처리하려고
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(ItemsVO item);
    }
}
