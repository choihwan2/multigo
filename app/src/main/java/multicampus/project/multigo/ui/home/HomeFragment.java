package multicampus.project.multigo.ui.home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;

import multicampus.project.multigo.R;
import multicampus.project.multigo.utils.AppHelper;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Button btn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.id_home_tv);
        final ImageView qrView = root.findViewById(R.id.qr_home_img);
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        homeViewModel.getQr().observe(getViewLifecycleOwner(), qrView::setImageBitmap);


        btn = root.findViewById(R.id.goBasket_home_btn);
        if(AppHelper.isEntered){
            btn.setVisibility(View.VISIBLE);
        }else{
            btn.setVisibility(View.INVISIBLE);
        }
        btn.setOnClickListener(v -> {
//            btmNav.getMenu().getItem(R.id.navigation_home).setVisible(false);
//            btmNav.getMenu().getItem(R.id.navigation_basket).setVisible(true);
            Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_navigation_basket);
        });

        return root;
    }

}
