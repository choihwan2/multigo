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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import multicampus.project.multigo.R;
import multicampus.project.multigo.data.BasketItemVO;
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
public class BasketFragment extends Fragment{

    private OnListFragmentInteractionListener mListener;
    private DatabaseReference mBasketRef;

    private RecyclerView recyclerView;
    private BasketRecyclerViewAdapter2 mAdapter;
    private View view;

    public BasketFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBasketRef = FirebaseDatabase.getInstance().getReference().child(AppHelper.BASKET_REF).child(AppHelper.getUserId());

        Log.d("BasketFragment","프래그먼트가 생성되었습니다!");
    }


//    private Observer<ArrayList<ItemsVO>> userListUpdateObserver = new Observer<ArrayList<ItemsVO>>() {
//        // NOTE ArrayList 를 보고 있다가 상품이 추가되면 새로 업데이트 해준다.
//        @Override
//        public void onChanged(ArrayList<ItemsVO> itemArrayList) {
//            BasketRecyclerViewAdapter basketAdapter = new BasketRecyclerViewAdapter(itemArrayList, mListener);
//            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
//            recyclerView.setAdapter(basketAdapter);
//
//            Log.d("BasketFragment","onChanged 에 들어왔습니다.");
//        }
//    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_basket, container, false);

        recyclerView = view.findViewById(R.id.basket_list);


//        BasketViewModel basketViewModel = ViewModelProviders.of(this).get(BasketViewModel.class);
//        basketViewModel.getItemsVOMutableLiveData().observe(this, userListUpdateObserver);

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


//        Button addBtn = view.findViewById(R.id.add_basket_btn);

//        addBtn.setOnClickListener(v -> {
//            basketViewModel.addItem(new ItemsVO("001", "과자", 5000, 1));
//        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        mAdapter = new BasketRecyclerViewAdapter2(mListener,mBasketRef);
        recyclerView.setAdapter(mAdapter);
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
        void onListFragmentInteraction(BasketItemVO item);
    }
}
