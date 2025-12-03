package com.example.appprenotame.network.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.appprenotame.R;
import com.example.appprenotame.fragments.UserProfileFragment;
import com.example.appprenotame.network.models.response.EventData;
import java.util.List;

public class EventAdapter extends ArrayAdapter<EventData> {

    private Fragment fragment;
    public EventAdapter(@NonNull Fragment fragment, @NonNull List<EventData> events) {
        super(fragment.getContext(), 0, events);
        this.fragment = fragment;
    }
    public static class ViewHolder {
        ImageView immagineEvento;
        TextView dataInizio;
        TextView dataFine;
        TextView titolo;
        TextView posizione;
        ImageView immagineProfiloUtente;
        Button joinEvent;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_event , parent , false);
            viewHolder = new ViewHolder();

           viewHolder.immagineEvento = convertView.findViewById(R.id.imgEv);
           viewHolder.dataInizio = convertView.findViewById(R.id.dataStEv);
           viewHolder.dataFine = convertView.findViewById(R.id.dataEnEv);
           viewHolder.posizione = convertView.findViewById(R.id.locationEv);
           viewHolder.titolo = convertView.findViewById(R.id.titleEv);
           viewHolder.immagineProfiloUtente = convertView.findViewById(R.id.userProfileImage);
           viewHolder.joinEvent = convertView.findViewById(R.id.joinButton);

           convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        EventData evento = getItem(position);

        if (evento != null) {
            String url = evento.getImage_url();
            String url1 = evento.getImage_url();
            if (url != null && !url.isEmpty()) {
                Glide.with(getContext()).load(url).into(viewHolder.immagineEvento);
            }
            if (url1 != null && !url1.isEmpty()) {
                Glide.with(getContext()).load(url1).into(viewHolder.immagineProfiloUtente);
            }

            viewHolder.immagineProfiloUtente.setOnClickListener(v -> {
                fragment.getParentFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.fragment_container_view, new UserProfileFragment())
                        .addToBackStack(null)
                        .commit();

            });

            viewHolder.dataInizio.setText(evento.getDate_start());
            viewHolder.dataFine.setText(evento.getDate_end());
            viewHolder.titolo.setText(evento.getTitle());
            viewHolder.posizione.setText(evento.getLocation());

        }
        return convertView;
    }
}
