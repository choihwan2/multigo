package multicampus.project.multigo.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yydcdut.sdlv.SlideAndDragListView;

import multicampus.project.multigo.MainActivity;
import multicampus.project.multigo.R;
import multicampus.project.multigo.data.userEnteredData;
import multicampus.project.multigo.utils.AppHelper;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        final Button btn = root.findViewById(R.id.addFireBaseData_btn);
        btn.setOnClickListener(v ->{
            Log.d("Noti",myRef.toString());
            Log.d("Noti",FirebaseAuth.getInstance().getCurrentUser().getUid());
            userEnteredData userEnteredData = new userEnteredData(FirebaseAuth.getInstance().getCurrentUser().getUid(),false);
            myRef.child(AppHelper.getUserId()).push().setValue(userEnteredData);
//            myRef.setValue("hello");
        });
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s));
        return root;
    }
}
