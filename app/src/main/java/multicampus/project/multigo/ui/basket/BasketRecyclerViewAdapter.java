package multicampus.project.multigo.ui.basket;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import multicampus.project.multigo.R;
import multicampus.project.multigo.ui.basket.BasketFragment.OnListFragmentInteractionListener;
import multicampus.project.multigo.data.ItemsVO;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ItemsVO} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class BasketRecyclerViewAdapter extends RecyclerView.Adapter<BasketRecyclerViewAdapter.ViewHolder> {

    private final List<ItemsVO> mValues;
    private final OnListFragmentInteractionListener mListener;

    public BasketRecyclerViewAdapter(List<ItemsVO> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_basket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).getName());
        holder.mNumberView.setText("1");
        holder.mPriceView.setText(String.valueOf(mValues.get(position).getPrice()));

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
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mNameView;
        final TextView mPriceView;
        final TextView mNumberView;
        final Button mDeleteBtn;
        ItemsVO mItem;

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
}
