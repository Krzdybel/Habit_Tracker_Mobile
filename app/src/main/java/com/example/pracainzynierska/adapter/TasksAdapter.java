package com.example.pracainzynierska.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> {

    List<TasksItem> mTasksList;
    Context context;
    private Cursor cursor;

    public TasksAdapter(Context context, ArrayList<TasksItem> mTasksList){
        this.context=context;
        this.mTasksList = mTasksList;
    }


    public static class TasksViewHolder extends RecyclerView.ViewHolder{

        public ImageView deleteImageView;
        public TextView task_id, task_name,task_date;
        LinearLayout tasksItem;
        private CheckBox checkBox;
        private Button colorViewButton;


        public TasksViewHolder(@NonNull final View itemView) {
            super(itemView);
            task_name = itemView.findViewById(R.id.task_name);
            task_date = itemView.findViewById(R.id.task_date);
            deleteImageView = itemView.findViewById(R.id.delete_image_view);
            checkBox = itemView.findViewById(R.id.checkBox);
            tasksItem = itemView.findViewById(R.id.tasksItem);
            colorViewButton = itemView.findViewById(R.id.color_view_button);

        }
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_item,parent,false);
        final TasksViewHolder tvh = new TasksViewHolder(v);
        return tvh;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull final TasksViewHolder holder, final int position) {
        final TasksItem currentItem = mTasksList.get(position);
        holder.task_name.setText((String.valueOf(currentItem.getmText1())));
        holder.colorViewButton.setBackgroundResource(setColor(currentItem.getColor()));
        holder.task_date.setText(String.valueOf(currentItem.getmText3()));
        holder.checkBox.setChecked(currentItem.isDone());
        if(currentItem.isDone() == true){
            holder.checkBox.setClickable(false);
        }
        holder.tasksItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(currentItem.isDone());
            }
        });
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doneTasks(currentItem.getId());
            }
        });

        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Usunieto element "+currentItem.getId(), Toast.LENGTH_SHORT).show();
                deleteTasks(currentItem.getId());
                mTasksList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), mTasksList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTasksList.size();
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

    private void deleteTasks(long taskId){
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .deleteTasks(taskId, getAccessToken());

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

    private String getAccessToken(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(LoginActivity.SHARED_PREFS, MODE_PRIVATE);
        return "Bearer " + sharedPreferences.getString(LoginActivity.ACCESS_TOKEN, "");
    }

    private void doneTasks(long taskId){
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .doneTask(taskId, getAccessToken());

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
}
