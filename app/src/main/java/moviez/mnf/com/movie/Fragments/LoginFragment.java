package moviez.mnf.com.movie.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import moviez.mnf.com.movie.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    public LoginFragment() {
        // Required empty public constructor
    }
    public static LoginFragment getInstance() {
        String TAG = "logging";

        LoginFragment fragmentInstance = new LoginFragment();
        Bundle bundle = new Bundle();
        //Log.e(TAG,"value of pos is "+pos);

        return  fragmentInstance;

        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        return v;
    }


}
