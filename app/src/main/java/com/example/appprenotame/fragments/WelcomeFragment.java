package com.example.appprenotame.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appprenotame.R;

public class WelcomeFragment extends Fragment {

    public WelcomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textGetStarted = view.findViewById(R.id.textView_GetStarted);
        String testo = textGetStarted.getText().toString();

        SpannableString spannable = new SpannableString(testo);
        spannable.setSpan(new UnderlineSpan(), 0, testo.length(), 0);
        textGetStarted.setText(spannable);

        LinearLayout getStartedLayout = view.findViewById(R.id.LinearGetStarted);
        getStartedLayout.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.fragment_container_view, new MainFragment())
                    .addToBackStack(null)
                    .commit();
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }
}