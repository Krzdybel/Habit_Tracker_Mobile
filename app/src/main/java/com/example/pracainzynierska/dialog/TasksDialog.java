package com.example.pracainzynierska.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pracainzynierska.LoginActivity;
import com.example.pracainzynierska.R;
import com.example.pracainzynierska.adapter.TasksAdapter;
import com.example.pracainzynierska.adapter.TasksItem;
import com.example.pracainzynierska.model.CurrentUserResponse;
import com.example.pracainzynierska.model.TaskRequest;
import com.example.pracainzynierska.retrofit.RetrofitClient;
import com.example.pracainzynierska.ui.TasksActivity;
import com.muddzdev.styleabletoastlibrary.StyleableToast;


import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class TasksDialog extends AppCompatDialogFragment {

    private TextView mTasksName, mTasksDate;
    private TasksDialogListener listener;
    private ArrayList<TasksItem> mTasksList;
    private RecyclerView.Adapter mAdapter;
    private RadioButton radioButton, radioButton1, radioButton2, radioButton3, radioButton4;
    private RadioGroup radioGroup;
    Context context;
    String result;
    CurrentUserResponse currentUserResponse = new CurrentUserResponse();


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_tasks, null);

        mTasksList = new ArrayList<>();
        mAdapter = new TasksAdapter(context, mTasksList);
        mTasksName = view.findViewById(R.id.tasksName);
        mTasksDate = view.findViewById(R.id.tasksDate);
        radioGroup = view.findViewById(R.id.radio_group);
        radioButton1 = view.findViewById(R.id.radioButton);
        radioButton2 = view.findViewById(R.id.radioButton2);
        radioButton3 = view.findViewById(R.id.radioButton3);
        radioButton4 = view.findViewById(R.id.radioButton4);

        results();

        builder.setView(view).setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

            }
        })
                .setPositiveButton("Akceptuj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        TaskRequest taskRequest = new TaskRequest(mTasksName.getText().toString().trim(), result , Long.valueOf(1), mTasksDate.getText().toString().trim()); //"2020-12-09 00:00:00");
                        listener.addTask(taskRequest);
                        Toast.makeText(getContext(), "WYBRANY KOLOR TO: "+result , Toast.LENGTH_SHORT).show();
                    }
                });

        return builder.create();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (TasksDialog.TasksDialogListener) context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString()+
                    "must implement TaskDialogListener");
        }
    }


    private String test(String hexColor){
        if(hexColor.equals("#ffadd8e6")){
            return "lightBlue";
        }

        if(hexColor.equals("#fffa8072")){
            return "salmon";
        }

        if(hexColor.equals("#ffcccccc")){
            return "lightGrey";
        }

        if(hexColor.equals("#ff90ee90")){
            return "lightGreen";
        }

        return "error";
    }

    private void results(){
        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("ResourceType") String test = getResources().getString(R.color.blue);
                StyleableToast.makeText(getContext(), "" + test(test), R.style.blueToast).show();
                result=test(test);
            }
        });

        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("ResourceType") String test = getResources().getString(R.color.red);
                StyleableToast.makeText(getContext(), "" + test(test), R.style.redToast).show();
                result=test(test);
            }
        });

        radioButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("ResourceType") String test = getResources().getString(R.color.grey);
                StyleableToast.makeText(getContext(), "" + test(test), R.style.greyToast).show();
                result=test(test);
            }
        });

        radioButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("ResourceType") String test = getResources().getString(R.color.azure);
                StyleableToast.makeText(getContext(), "" + test(test), R.style.azureToast).show();
                result=test(test);
            }
        });
    }


    public interface TasksDialogListener{
        void addTask(TaskRequest taskRequest);
    }
}
