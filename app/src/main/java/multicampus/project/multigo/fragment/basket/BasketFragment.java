package multicampus.project.multigo.fragment.basket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import multicampus.project.multigo.R;
import multicampus.project.multigo.data.BasketItemVO;
import multicampus.project.multigo.utils.AppHelper;

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
    private BasketRecyclerViewAdapter mAdapter;
    private View view;

    public BasketFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBasketRef = FirebaseDatabase.getInstance().getReference().child(AppHelper.BASKET_REF).child(AppHelper.getUserId());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_basket, container, false);

        recyclerView = view.findViewById(R.id.basket_list);

        FloatingActionButton floatingBtn = view.findViewById(R.id.floating_basket);
        floatingBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),QRScannerActivity.class);
            startActivity(intent);
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter = new BasketRecyclerViewAdapter(mListener,mBasketRef);
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
        mAdapter.cleanupListener();
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
