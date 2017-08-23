package com.tahutelorcommunity.bakingapp.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tahutelorcommunity.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DescriptionFragment extends Fragment {

    public static final String DESCRIPTION_ARGS_KEY = "description";
    @BindView(R.id.step_detail_description)
    TextView text_view_step_detail_description;
    public DescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_description, container, false);
        ButterKnife.bind(this, view);
        if(getArguments() != null){
            if(getArguments().getString(DESCRIPTION_ARGS_KEY) != null){
                text_view_step_detail_description.setText(getArguments().getString(DESCRIPTION_ARGS_KEY));
            }
        }
        return view;
    }

}
