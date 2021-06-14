package com.example.musiclyrics2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> listData;
    private List<User> listTemp;
    private DatabaseReference mDataBase;
    private String USER_KEY = "User";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        init();
        getDataFromDB();
        setOnClickItem();
    }
    private void init() {
    listView = findViewById(R.id.listView);
    listData = new ArrayList<>();
    listTemp = new ArrayList<>();
    adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listData);
    listView.setAdapter(adapter);
        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
    }
    private void getDataFromDB() {
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Проверяем на наличие информации в ключе
                if(listData.size() > 0)listData.clear();
                if(listTemp.size() > 0)listTemp.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    //Проверка бд на то, что она не пустая
                    assert user!= null;
                    listData.add(user.name);
                    //Для передачи всех данных
                    listTemp.add(user);
                }
                //Оповестить адаптер о том, что изменения
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDataBase.addValueEventListener(vListener);
    }

    //Добавляем слушателя нажатия на listView
    private void setOnClickItem() {
        listView.setOnItemClickListener((parent, view, position, id) -> {
            //Вытаскиваем по позиции всю информацию, которую содержит user (в данном случае песни)
            User user = listTemp.get(position);
            //Передача на ShowActivity
            Intent i = new Intent(MenuActivity.this,ShowActivity.class);
            i.putExtra("user_name",user.name);
            i.putExtra("user_sec_name",user.sec_name);
            startActivity(i);
        });
    }
}