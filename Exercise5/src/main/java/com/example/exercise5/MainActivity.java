package com.example.exercise5;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements NewItemFragment.OnNewItemAddedListener, ToDoListFragment.ToDoListFragmentListener{
    static  ArrayAdapter<String> aa;
    static  ArrayList<String> todoItems;
    static  ArrayList<String> status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        todoItems=new ArrayList<String>();
        status=new ArrayList<>();
        if(savedInstanceState!=null){
            todoItems=savedInstanceState.getStringArrayList("toDOList");
            status=savedInstanceState.getStringArrayList("statusList");
        }
        setContentView(R.layout.activity_main);
        ToDoListFragment todoListFragment=(ToDoListFragment)getSupportFragmentManager().findFragmentById(R.id.TodoListFragment);

        aa=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,todoItems);


        todoListFragment.setListAdapter(aa);

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("toDOList",todoItems);
        outState.putStringArrayList("statusList",status);
    }

    @Override
    public void onNewItemAdded(String newItem) {
        // TODO Auto-generated method stub
        todoItems.add(newItem);
        status.add("To Do");
        aa.notifyDataSetChanged();

    }

    @Override
    public void itemOnclick(long id) {
        View FragmentContainer = findViewById(R.id.detailFragmentContainer);
        if(FragmentContainer!=null){
            //start code
            DetailFragment detailFragment=new DetailFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            detailFragment.setTaskId((int)id);
            ft.replace(R.id.detailFragmentContainer, detailFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();

        }
        else {
            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA, (int) id);
            startActivity(intent);
        }
    }
}