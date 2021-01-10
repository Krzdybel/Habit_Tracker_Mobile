package com.example.pracainzynierska.adapter;

import android.content.Context;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pracainzynierska.LoginActivity;
import com.example.pracainzynierska.R;
import com.example.pracainzynierska.retrofit.RetrofitClient;


import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder>{

    private ArrayList<EventsItem> mEventsList;
    private Context context;

    public EventsAdapter(Context context, ArrayList<EventsItem> mEventsList){
        this.context=context;
        this.mEventsList = mEventsList;
    }


    static class EventsViewHolder extends RecyclerView.ViewHolder{

        private TextView event_name, event_date;
        private LinearLayout eventsItem;
        private ImageView deleteImageView;
        private Button colorViewButton;


        EventsViewHolder(@NonNull View itemView) {
            super(itemView);
            event_name = itemView.findViewById(R.id.event_name);
            event_date = itemView.findViewById(R.id.event_date);
            deleteImageView = itemView.findViewById(R.id.delete_image_view);
            eventsItem = itemView.findViewById(R.id.eventsItem);
            colorViewButton = itemView.findViewById(R.id.button);
        }

    }

    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_item,parent,false);
        final EventsViewHolder evh = new EventsViewHolder(v);

//        evh.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext,""+ evh.getAdapterPosition(), Toast.LENGTH_SHORT).show();
//            }
//        });

        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final EventsViewHolder holder, final int position) {
        final EventsItem currentItem = mEventsList.get(position);
        holder.event_name.setText(String.valueOf(currentItem.getmText1()));
        holder.event_date.setText(String.valueOf(currentItem.getmText2()));
        holder.colorViewButton.setBackgroundResource(setColor(currentItem.getColor()));
        holder.eventsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Nr: "+ currentItem.getmText1(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Usunieto element "+currentItem.getId(), Toast.LENGTH_SHORT).show();
                deleteEvents(currentItem.getId());
                mEventsList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(),mEventsList.size());
            }
        });

    }


    @Override
    public int getItemCount() {
        return mEventsList.size();
    }

//    public EventsAdapter(Context context, ArrayList<EventsItem> eventsList){
//        this.context=context;
//        mEventsList = eventsList;
//    }

    public int setColor(String color){
        if(color.equals("lightBlue")){
            return R.color.blue;
        }

        if(color.equals("salmon")){
            return R.color.red;
        }

        if(color.equals("lightGrey")){
            return R.color.grey;
        }

        if(color.equals("lightGreen")){
            return R.color.azure;
        }
        return R.color.white;
    }

    private void deleteEvents(long eventId){
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .deleteEvents(eventId, getAccessToken());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(context, "POMYSLNIE USUNIETO", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public String getAccessToken(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(LoginActivity.SHARED_PREFS, MODE_PRIVATE);
        String value = "Bearer " + sharedPreferences.getString(LoginActivity.ACCESS_TOKEN, "");
        return value;
    }

}
