package multicampus.project.multigo.fragment.basket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import multicampus.project.multigo.R;
import multicampus.project.multigo.data.BasketItemVO;
import multicampus.project.multigo.fragment.basket.BasketFragment.OnListFragmentInteractionListener;
import multicampus.project.multigo.main.MainData;

/**
 * {@link RecyclerView.Adapter} that can display a {@link BasketItemVO} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class BasketRecyclerViewAdapter extends RecyclerView.Adapter<BasketRecyclerViewAdapter.ViewHolder> {

    private List<BasketItemVO> mBasketItems = new ArrayList<>();
    private List<String> mItemsId = new ArrayList<>();
    private final OnListFragmentInteractionListener mListener;
    private DatabaseReference mDatabaseRef;
    private ChildEventListener mChildEventListener;

    public BasketRecyclerViewAdapter(OnListFragmentInteractionListener listener, DatabaseReference ref) {
        mListener = listener;
        mDatabaseRef = ref;

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BasketItemVO item = dataSnapshot.getValue(BasketItemVO.class);

                mItemsId.add(dataSnapshot.getKey());
                mBasketItems.add(item);
                MainData.getInstance().setBasketKeys(mItemsId);
                MainData.getInstance().setBasketList(mBasketItems);
                notifyItemInserted(mBasketItems.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        ref.addChildEventListener(childEventListener);

        mChildEventListener = childEventListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_basket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mBasketItems.get(position);
        holder.mNameView.setText(mBasketItems.get(position).getName());
        holder.mNumberView.setText(String.valueOf(mBasketItems.get(position).getCnt()));
        holder.mPriceView.setText(String.valueOf(mBasketItems.get(position).getPrice()));

        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
//                mListener.onListFragmentInteraction(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBasketItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mNameView;
        final TextView mPriceView;
        final TextView mNumberView;
        final Button mDeleteBtn;
        BasketItemVO mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = view.findViewById(R.id.product_name);
            mNumberView = view.findViewById(R.id.product_count);
            mPriceView = view.findViewById(R.id.product_price);
            mDeleteBtn = view.findViewById(R.id.product_cancel_btn);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNumberView.getText() + "'";
        }
    }

    public void cleanupListener(){
        if(mChildEventListener != null){
            mDatabaseRef.removeEventListener(mChildEventListener);
        }
    }
}
