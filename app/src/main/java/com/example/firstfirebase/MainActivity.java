package com.example.firstfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
EditText et1, et2;
Button insert, retrieve;
ListView lv;
ArrayList<String> al;
ArrayAdapter<String> ad;
FirebaseDatabase db;
DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = findViewById(R.id.editText2);
        et2 = findViewById(R.id.editText);
        insert = findViewById(R.id.button);
        retrieve = findViewById(R.id.button2);
        db = FirebaseDatabase.getInstance();
        dbref = db.getReference("Users");
        lv = findViewById(R.id.listview1);


        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et1.getText().toString();
                String pwd = et2.getText().toString();

                User usr = new User();
                usr.setName(name);
                usr.setPwd(pwd);

                dbref.child(name).setValue(usr);
                Toast.makeText(getApplicationContext(),"Stored Succesfully",Toast.LENGTH_LONG).show();

            }
        });



        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                al=new ArrayList<String>();
                for(DataSnapshot i:dataSnapshot.getChildren()){
                    User user = i.getValue(User.class);
                    /*String name = user.getName();
                    String pwd = user.getPwd();
                    Toast.makeText(getApplicationContext(),"User name is:" +name+"Password is: "+pwd,Toast.LENGTH_LONG).show();
                    */
                    al.add(user.getName()+" "+user.getPwd());
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ad = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,al);
                lv.setAdapter(ad);


            }
        });


    }
}
