package com.example.appprenotame.network.models;

import android.content.Context;
import android.os.Bundle;
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
import com.example.appprenotame.fragments.AdminUpdateEventFragment;
import com.example.appprenotame.fragments.UserProfileFragment;
import com.example.appprenotame.fragments.BookingResponse;
import com.example.appprenotame.network.RetrofitClient;
import com.example.appprenotame.network.UserSession;
import com.example.appprenotame.network.models.api.BookingService;
import com.example.appprenotame.network.models.request.CreateBooking;
import com.example.appprenotame.network.models.response.ApiResponse;
import com.example.appprenotame.network.models.response.CreateBookingResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
            String url1 = evento.getUser_photo();
            if (url != null && !url.isEmpty()) {
                Glide.with(getContext()).load(url).into(viewHolder.immagineEvento);
            }
            if (url1 != null && !url1.isEmpty()) {
                Glide.with(getContext()).load(url1).into(viewHolder.immagineProfiloUtente);
            }

            viewHolder.immagineProfiloUtente.setOnClickListener(v -> {
                UserProfileFragment userProfileFragment = new UserProfileFragment();
                Bundle args = new Bundle();
                args.putInt("userId", evento.getCreated_by());
                userProfileFragment.setArguments(args);

                fragment.getParentFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.fragment_container_view, userProfileFragment)
                        .addToBackStack(null)
                        .commit();
            });

            viewHolder.immagineEvento.setOnClickListener(v -> {
                AdminUpdateEventFragment adminFrag = new AdminUpdateEventFragment();
                Bundle args = new Bundle();
                args.putInt("eventId", evento.getId());
                adminFrag.setArguments(args);

                fragment.getParentFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.fragment_container_view, adminFrag)
                        .addToBackStack(null)
                        .commit();
            });

            viewHolder.dataInizio.setText(evento.getDate_start());
            viewHolder.dataFine.setText(evento.getDate_end());
            viewHolder.titolo.setText(evento.getTitle());
            viewHolder.posizione.setText(evento.getLocation());

            viewHolder.joinEvent.setOnClickListener(v -> {
                if (UserSession.getInstance().getUser() == null) {
                    BookingResponse br = new BookingResponse();
                    Bundle b = new Bundle();
                    b.putString("message", "Devi prima effettuare il login");
                    br.setArguments(b);
                    fragment.getParentFragmentManager().beginTransaction()
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .replace(R.id.fragment_container_view, br)
                            .addToBackStack(null)
                            .commit();
                    return;
                }

                int currentUserId = UserSession.getInstance().getUser().getId();

                CreateBooking req = new CreateBooking(String.valueOf(evento.getId()), String.valueOf(currentUserId));
                BookingService bookingService = RetrofitClient.getClient().create(BookingService.class);
                bookingService.createBooking(req).enqueue(new Callback<ApiResponse<CreateBookingResponse>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<CreateBookingResponse>> call, Response<ApiResponse<CreateBookingResponse>> response) {
                        String message;
                        boolean successFlag = false;
                        if (response.isSuccessful() && response.body() != null) {
                            ApiResponse<CreateBookingResponse> body = response.body();
                            if (body.isSuccess()) {
                                successFlag = true;
                                CreateBookingResponse data = body.getData();
                                message = (data != null && data.getMessage() != null) ? data.getMessage() : "Prenotazione effettuata";
                            } else {
                                message = body.getMessagge() != null ? body.getMessagge() : "Errore nella prenotazione";
                            }
                        } else {
                            message = "Errore di rete: " + response.code();
                        }

                        BookingResponse br = new BookingResponse();
                        Bundle b = new Bundle();
                        b.putString("message", message);
                        b.putBoolean("success", successFlag);
                        br.setArguments(b);
                        fragment.getParentFragmentManager().beginTransaction()
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .replace(R.id.fragment_container_view, br)
                                .addToBackStack(null)
                                .commit();
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<CreateBookingResponse>> call, Throwable t) {
                        String message = "Errore di rete: " + t.getMessage();
                        BookingResponse br = new BookingResponse();
                        Bundle b = new Bundle();
                        b.putString("message", message);
                        b.putBoolean("success", false);
                        br.setArguments(b);
                        fragment.getParentFragmentManager().beginTransaction()
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .replace(R.id.fragment_container_view, br)
                                .addToBackStack(null)
                                .commit();
                    }
                });
            });

        }
        return convertView;
    }
}
