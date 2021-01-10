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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.pracainzynierska.R;
import com.example.pracainzynierska.adapter.EventsItem;
import com.example.pracainzynierska.model.EventRequest;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class EventDialog extends AppCompatDialogFragment{

    public EditText mEventName, mEventDesc,  mEventDate;
    private EventDialogListener listener;
    private ArrayList<EventsItem> mEventsList;
    SimpleDateFormat formatter = new SimpleDateFormat("MM:dd:yyyy");
    private Button colorViewButton;
    String result;
    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4;
    private ImageView imageView;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_event, null);

        Bundle bundle = getArguments();
        final String imageLink = bundle.getString("date", "");


        mEventsList = new ArrayList<>();

        builder.setView(view).setNegativeButton("Anuluj ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
            }
        })
        .setPositiveButton("Akceptuj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                EventRequest eventRequest = new EventRequest( Long.valueOf(1), mEventName.getText().toString().trim(), result, "2020-12-09 00:00:00");
                listener.addEvent(eventRequest);
                Toast.makeText(getContext(), "Dodano wydarzenie", Toast.LENGTH_SHORT).show();

            }
        });

        builder.setView(view);
        mEventName = view.findViewById(R.id.eventName);
        mEventDesc = view.findViewById(R.id.eventDesc);
        colorViewButton = view.findViewById(R.id.buttonViewColor);
        imageView = view.findViewById(R.id.imageViewEvent);
        radioButton1 = view.findViewById(R.id.radioButtonEvent);
        radioButton2 = view.findViewById(R.id.radioButtonEvent2);
        radioButton3 = view.findViewById(R.id.radioButtonEvent3);
        radioButton4 = view.findViewById(R.id.radioButtonEvent4);

        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("ResourceType") String test = getResources().getString(R.color.blue);
                result=testColor(test);
            }
        });

        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("ResourceType") String test = getResources().getString(R.color.red);
                result=testColor(test);
            }
        });

        radioButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("ResourceType") String test = getResources().getString(R.color.grey);
                result=testColor(test);
            }
        });

        radioButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("ResourceType") String test = getResources().getString(R.color.azure);
                result=testColor(test);
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (EventDialogListener) context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString()+
                    "must implement EventDialogListener");
        }
    }



    public interface EventDialogListener{
        void addEvent(EventRequest eventRequest);
    }

}
