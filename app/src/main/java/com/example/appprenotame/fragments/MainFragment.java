package com.example.appprenotame.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appprenotame.R;
import com.example.appprenotame.network.RetrofitClient;
import com.example.appprenotame.network.models.EventAdapter;
import com.example.appprenotame.network.models.api.EventService;
import com.example.appprenotame.network.models.response.ApiResponse;
import com.example.appprenotame.network.models.response.Category;
import com.example.appprenotame.network.models.response.EventData;
import com.example.appprenotame.network.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainFragment extends Fragment {

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ListView lisView = view.findViewById(R.id.listaEventi);


        boolean isAdmin = true;

        ImageButton plus = view.findViewById(R.id.buttonPlus);
        plus.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.fragment_container_view, new AdminCreateEvent())
                    .addToBackStack(null)
                    .commit();
        });

        TextView titolo = view.findViewById(R.id.textViewTitleHome);
        LinearLayout titoloWithAdminOptions = view.findViewById(R.id.linearLayoutTitleHome);

        if (isAdmin) {
            titoloWithAdminOptions.setVisibility(View.VISIBLE);
        } else {
            titolo.setVisibility(View.VISIBLE);
        }

        eventiDisponibili(lisView);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    private void eventiDisponibili(ListView lis) {
        EventService eventService = RetrofitClient.getClient().create(EventService.class);

        eventService.getEvents().enqueue(new Callback<ApiResponse<List<EventData>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<EventData>>> call, Response<ApiResponse<List<EventData>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<List<EventData>> body = response.body();
                    if (body.isSuccess()) {
                        List<EventData> lista = body.getData();
                        if (!lista.isEmpty()) {
                            List<EventData> listaEventiDisponibili = new ArrayList<>(lista);
                            Log.d("lista" , listaEventiDisponibili.toString());
                            EventAdapter eveAda = new EventAdapter(
                                   MainFragment.this , listaEventiDisponibili
                            );
                            lis.setAdapter(eveAda);
                        }
                    }
                    else {
                        Toast.makeText(getContext() , body.getMessagge() , Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getContext() , "Server error" , Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<EventData>>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext() , "Errore di rete" + " " + t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }
}