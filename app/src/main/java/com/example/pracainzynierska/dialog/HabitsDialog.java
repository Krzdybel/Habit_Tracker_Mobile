package com.example.pracainzynierska.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pracainzynierska.R;
import com.example.pracainzynierska.adapter.HabitsAdapter;
import com.example.pracainzynierska.adapter.HabitsItem;
import com.example.pracainzynierska.model.HabitRequest;

import java.util.ArrayList;

public class HabitsDialog extends AppCompatDialogFragment {

    private TextView mHabitsName;
    private RadioButton radioButtonColor1, radioButtonColor2, radioButtonColor3, radioButtonColor4;
    private RadioButton radioButtonImage1, radioButtonImage2, radioButtonImage3, radioButtonImage4;
    Context context;
    private RecyclerView.Adapter mAdapter;
    private ImageView imageView;
    private HabitsDialogListener listener;
    private ArrayList<HabitsItem> mHabitsList;
    String result,resultImage;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle saveInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_habits, null);


        builder.setView(view).setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        })
                .setPositiveButton("Akceptuj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(mHabitsName.getText().toString().trim().isEmpty()){
                            Toast.makeText(getContext(), "Uzupełnij nazwę", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(), "Dodano nawyk "+resultImage, Toast.LENGTH_SHORT).show();
                            HabitRequest habitRequest = new HabitRequest(mHabitsName.getText().toString().trim(),result, Long.valueOf(1), resultImage);
                            listener.addHabit(habitRequest);
                        }
                    }
                });




        mHabitsList = new ArrayList<>();
        mAdapter = new HabitsAdapter(context, mHabitsList);
        mHabitsName = view.findViewById(R.id.habitsName);
        imageView = view.findViewById(R.id.imageView1);
        radioButtonColor1 = view.findViewById(R.id.radioButtonColor);
        radioButtonColor2 = view.findViewById(R.id.radioButtonColor2);
        radioButtonColor3 = view.findViewById(R.id.radioButtonColor3);
        radioButtonColor4 = view.findViewById(R.id.radioButtonColor4);

        radioButtonImage1 = view.findViewById(R.id.radioButtonImage);
        radioButtonImage2 = view.findViewById(R.id.radioButtonImage2);
        radioButtonImage3 = view.findViewById(R.id.radioButtonImage3);
        radioButtonImage4 = view.findViewById(R.id.radioButtonImage4);

        radioButtonColor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("ResourceType") String test = getResources().getString(R.color.blue);
                result=testColor(test);
            }
        });

        radioButtonColor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("ResourceType") String test = getResources().getString(R.color.red);
                result=testColor(test);
            }
        });

        radioButtonColor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("ResourceType") String test = getResources().getString(R.color.grey);
                result=testColor(test);
            }
        });

        radioButtonColor4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("ResourceType") String test = getResources().getString(R.color.azure);
                result=testColor(test);
            }
        });

        radioButtonImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("ResourceType") String test1 = getResources().getString(R.drawable.ic_directions_bike_black_24dp);
                resultImage=testImage(test1);
            }
        });

        radioButtonImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("ResourceType") String test1 = getResources().getString(R.drawable.ic_book_black_24dp);
                resultImage=testImage(test1);
            }
        });

        radioButtonImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("ResourceType") String test1 = getResources().getString(R.drawable.ic_accessibility_black_24dp);
                resultImage=testImage(test1);
            }
        });

        radioButtonImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("ResourceType") String test1 = getResources().getString(R.drawable.ic_attach_money_black_24dp);
                resultImage=testImage(test1);
            }
        });


        return builder.create();
    }

    private String testColor(String hexColor){
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

    @SuppressLint("ResourceType")
    private String testImage(String imageResource){
        if(imageResource.equals(getResources().getString(R.drawable.ic_book_black_24dp))){
            return "faBook";
        }
        if(imageResource.equals(getResources().getString(R.drawable.ic_attach_money_black_24dp))){
            return "faMoneyBillAlt";
        }
        if(imageResource.equals(getResources().getString(R.drawable.ic_accessibility_black_24dp))){
            return "faDumbbell";
        }
        if(imageResource.equals(getResources().getString(R.drawable.ic_directions_bike_black_24dp))){
            return "faBiking";
        }

        return "error";
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (HabitsDialog.HabitsDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement HabitsDialogListener");
        }

    }

    public interface HabitsDialogListener {
        void addHabit(HabitRequest habitRequest);
    }
}
