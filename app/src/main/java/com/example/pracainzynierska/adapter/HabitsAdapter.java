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
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class HabitsAdapter extends RecyclerView.Adapter<HabitsAdapter.HabitsViewHolder> {

    List<HabitsItem> mHabitsList;
    Context context;

    public HabitsAdapter(Context context, ArrayList<HabitsItem> mHabitsList){
        this.context = context;
        this.mHabitsList = mHabitsList;
    }

    public class HabitsViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView mHabitName;
        private LinearLayout habitsItem;
        private Button colorViewButton, doneButton;
        public ImageView deleteImageView;

        public HabitsViewHolder(@NonNull View itemView) {
            super(itemView);
            mHabitName = itemView.findViewById(R.id.habit_name);
            imageView = itemView.findViewById(R.id.delete_image_view1);
            colorViewButton = itemView.findViewById(R.id.color_view_button1);
            deleteImageView = itemView.findViewById(R.id.delete_image_view1);
            doneButton = itemView.findViewById(R.id.doneButton);
        }
    }

    @NonNull
    @Override
    public HabitsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.habits_item,parent, false);
        final HabitsViewHolder hvh = new HabitsViewHolder(v);
        return hvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final HabitsViewHolder holder, int position) {
        final HabitsItem currentItem = mHabitsList.get(position);
        holder.mHabitName.setText(String.valueOf(currentItem.getmTextName()));
        holder.colorViewButton.setBackgroundResource(setColor(currentItem.getColor()));
        holder.doneButton.setClickable(currentItem.isDone());
        if(currentItem.isDone() == true){
            holder.doneButton.setBackgroundResource(R.color.green);
            holder.doneButton.setText("ZROBIONE!");
        }

        holder.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.doneButton.setBackgroundResource(R.color.green);
                holder.doneButton.setText("ZROBIONE!");
                doneHabits(currentItem.getId());
            }
        });

        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Usunieto element "+currentItem.getId(), Toast.LENGTH_SHORT).show();
                deleteHabits(currentItem.getId());
                mHabitsList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), mHabitsList.size());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("clicked item done? "+currentItem.isDone());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHabitsList.size();
    }

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

    private void deleteHabits(long habitId){
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .deleteHabits(habitId, getAccessToken());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(context, "POMYSLNIE USUNIETO", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void doneHabits(long habitId){
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .doneHabit(habitId, getAccessToken());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(context, "IS DONE", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getAccessToken(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(LoginActivity.SHARED_PREFS, MODE_PRIVATE);
        String value = "Bearer " + sharedPreferences.getString(LoginActivity.ACCESS_TOKEN, "");
        return value;
    }


}
