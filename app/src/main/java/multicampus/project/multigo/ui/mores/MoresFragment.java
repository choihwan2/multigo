package multicampus.project.multigo.ui.mores;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import multicampus.project.multigo.R;
import multicampus.project.multigo.ui.login.LoginActivity;
import multicampus.project.multigo.utils.AppHelper;
import multicampus.project.multigo.utils.SharedMsg;

public class MoresFragment extends Fragment {

    private MoreViewModel moreViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        moreViewModel =
                ViewModelProviders.of(this).get(MoreViewModel.class);
        View root = inflater.inflate(R.layout.fragment_more, container, false);
        final TextView logout = root.findViewById(R.id.logout_setting_btn);
        logout.setOnClickListener(v ->{
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            SharedMsg.getInstance().addMsg(AppHelper.TERMINATE);
            startActivity(intent);
            Objects.requireNonNull(getActivity()).finish();
        });


        return root;
    }
}
