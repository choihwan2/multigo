package multicampus.project.multigo.ui.history;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import multicampus.project.multigo.R;

public class PurchaseHistoryFragment extends Fragment {

    private PurchaseHistoryViewModel historyViewModel;
    private Button btn;

    @Override
    public void onDestroy() {
        Log.i("Fragment", "onDestroy()호출");
        super.onDestroy();
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        historyViewModel
                =
                ViewModelProviders.of(this).get(PurchaseHistoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_purchase_history, container, false);
        final TextView textView = root.findViewById(R.id.text_history);
        btn = root.findViewById(R.id.btn_history);
        historyViewModel
                .getText().observe(getViewLifecycleOwner(), s -> textView.setText(s));

        btn.setOnClickListener(v -> historyViewModel
                .upText());


        return root;
    }
}
