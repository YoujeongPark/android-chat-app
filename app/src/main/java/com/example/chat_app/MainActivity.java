package com.example.chat_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter chatAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<ChatData> chatDataList;
    private String nick = "nick1";

    private EditText editText_chat;
    private Button Button_send;

    // Write a message to the database
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private  DatabaseReference myRef = database.getReference("message");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        myRef.removeValue(); // 기존 데이터 베이스 다 지우기


        Button_send =  findViewById(R.id.Button_send);
        editText_chat = findViewById(R.id.EditText_chat);

        Button_send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String msg = editText_chat.getText().toString(); //msg
                if(msg != null){
                    ChatData chat = new ChatData();
                    chat.setNickName("nick");
                    chat.setMsg(msg);
                    myRef.push().setValue(chat);
                }
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        chatDataList = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatDataList, this,nick );
        recyclerView.setAdapter(chatAdapter);


        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            //snapshot -> 채팅 데이터를 담고 있는 변수
                ChatData chat = snapshot.getValue(ChatData.class);
                ((ChatAdapter)chatAdapter).addChat(chat);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}