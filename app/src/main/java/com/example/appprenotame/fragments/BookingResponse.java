package com.example.appprenotame.fragments;

import android.os.Bundle;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import com.example.appprenotame.R;

public class BookingResponse extends Fragment {

    public BookingResponse() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking_response, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView msg = view.findViewById(R.id.bookingMessage);
        ImageView statusImage = view.findViewById(R.id.bookingStatusImage);
        ImageView back = view.findViewById(R.id.bookingBack);

        Bundle args = getArguments();
        boolean success = false;
        if (args != null) {
            if (args.containsKey("message")) {
                msg.setText(args.getString("message"));
            } else {
                msg.setText("Nessun messaggio disponibile");
            }
            success = args.getBoolean("success", false);
        } else {
            msg.setText("Nessun messaggio disponibile");
        }

        if (success) {
            statusImage.setImageResource(R.drawable.background_success);
        } else {
            statusImage.setImageResource(R.drawable.background_error);
        }

        back.setOnClickListener(v -> getParentFragmentManager().popBackStack());
    }
}