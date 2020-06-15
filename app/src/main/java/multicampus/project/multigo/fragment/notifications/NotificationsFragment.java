package multicampus.project.multigo.fragment.notifications;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import multicampus.project.multigo.R;
import multicampus.project.multigo.data.UserEnteredData;
import multicampus.project.multigo.utils.AppHelper;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference entranceRef = database.getReference().child("Entrance");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        final Button btn = root.findViewById(R.id.addFireBaseData_btn);
        btn.setOnClickListener(v ->{
            Log.d("Noti", entranceRef.toString());
            Log.d("Noti",FirebaseAuth.getInstance().getCurrentUser().getUid());
            UserEnteredData userEnteredData = new UserEnteredData("1",false);
            entranceRef.child(AppHelper.getUserId()).push().setValue(userEnteredData);
//            myRef.setValue("hello");
        });
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s));
        return root;
    }
}
