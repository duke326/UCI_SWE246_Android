package com.example.exercise5;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment implements  View.OnClickListener{
    int taskId;
    Button deleteButton;
    Button todoButton;
    Button doingButton;
    Button doneButton;
    View view;
    TextView task;
    TextView status;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            taskId=savedInstanceState.getInt("taskId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_deatil, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        view=getView();
        bindView();
        if(view!=null){
            bindView();
            Log.d("this",""+MainActivity.todoItems.size());
            Log.d("this",""+taskId);
            task.setText(MainActivity.todoItems.get(taskId));

            status.setText(MainActivity.status.get(taskId));
        }
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("taskId",taskId);

    }
    private void bindView() {
        task=view.findViewById(R.id.task);
        status=view.findViewById(R.id.status);
        deleteButton=view.findViewById(R.id.deleteButton);
        todoButton=view.findViewById(R.id.todoButton);
        doingButton=view.findViewById(R.id.doingButton);
        doneButton=view.findViewById(R.id.doneButton);
        todoButton.setOnClickListener(this);
        doneButton.setOnClickListener(this);
        doingButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.todoButton:
                status.setText("ToDo");
                MainActivity.status.set(taskId,"ToDo");
                break;
            case R.id.doingButton:
                status.setText("Doing");
                MainActivity.status.set(taskId,"Doing");
                break;
            case R.id.doneButton:
                status.setText("Done");
                MainActivity.status.set(taskId,"Done");
                break;
            case R.id.deleteButton:
                MainActivity.todoItems.remove(taskId);
                MainActivity.aa.notifyDataSetChanged();
                MainActivity.status.remove(taskId);
                break;
        }
    }
}