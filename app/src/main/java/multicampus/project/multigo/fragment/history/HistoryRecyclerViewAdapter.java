package multicampus.project.multigo.fragment.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import multicampus.project.multigo.R;
import multicampus.project.multigo.data.ListsVO;
import multicampus.project.multigo.fragment.basket.BasketFragment.OnListFragmentInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ListsVO} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder> {

    private final List<ListsVO> mValues;

    public HistoryRecyclerViewAdapter() {
        mValues = new ArrayList<ListsVO>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(String.valueOf(mValues.get(position).getList_id()));
        holder.mDateView.setText(mValues.get(position).getPurchase_date());
        holder.mPriceView.setText(String.valueOf(mValues.get(position).getTotal()));

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void addItem(ListsVO item){
        mValues.add(item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mNameView;
        final TextView mDateView;
        final TextView mPriceView;
        ListsVO mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = view.findViewById(R.id.history_item_title);
            mDateView = view.findViewById(R.id.history_item_date);
            mPriceView = view.findViewById(R.id.history_item_price);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDateView.getText() + "'";
        }
    }
}
