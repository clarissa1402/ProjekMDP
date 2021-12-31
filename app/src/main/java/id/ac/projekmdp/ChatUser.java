package id.ac.projekmdp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import id.ac.projekmdp.adapter.chatUserAdapter;
import id.ac.projekmdp.kelas.Dchat;
import id.ac.projekmdp.kelas.Hchat;
import id.ac.projekmdp.kelas.User;

public class ChatUser extends AppCompatActivity {
    RecyclerView rv;
    ImageView ivback,ivsend;
    EditText edtchat;
    chatUserAdapter userchatadapter;
    ArrayList<Hchat>datahchat=new ArrayList<>();
    ArrayList<Dchat>datadchat=new ArrayList<>();
    //ArrayList<Hchat>datahchatfiltered=new ArrayList<>();
    ArrayList<Dchat>datadchatfiltered=new ArrayList<>();
    DatabaseReference root;
    int nik_peg,id_user,id_hc,dari;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        root= FirebaseDatabase.getInstance().getReference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_user);
        rv=findViewById(R.id.rvhomechatuser);
        ivback=findViewById(R.id.imageView8);
        ivsend=findViewById(R.id.imageView10);
        edtchat=findViewById(R.id.editTextTextPersonName2);
        nik_peg=getIntent().getIntExtra("nik_peg",0);
        id_user=getIntent().getIntExtra("id_user",0);
        dari=getIntent().getIntExtra("dari",0);
        System.out.println(nik_peg+" "+id_user+" "+dari);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(ChatUser.this));
        load_data();


        ivsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id_hc=0;
                if (cek_id_hchat()==-1){
                    //insert hchat
                    root.child("Hchat").push().setValue(new Hchat(datahchat.size(),id_user,nik_peg)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getBaseContext(),"Hchat Success",Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getBaseContext(),"Hchat Failed",Toast.LENGTH_SHORT).show();
                        }
                    });
                    //untuk dchat

                    //
                    if (datahchat.size()==0){
                        id_hc=0;
                    }
                    else{
                        id_hc=cek_id_hchat();
                    }
                    load_data();
                }
                else{
                    //search hchat
                    id_hc=cek_id_hchat();
                }
                root.child("Dchat").push().setValue(new Dchat(datadchat.size(),id_hc,dari,edtchat.getText().toString())).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        load_data();
                        Toast.makeText(getBaseContext(),"Success",Toast.LENGTH_SHORT).show();
                        edtchat.setText("");
//                        filter();
//                        userchatadapter = new chatUserAdapter(datadchatfiltered);
//                        rv.setAdapter(userchatadapter);
//                        userchatadapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getBaseContext(),"Failed",Toast.LENGTH_SHORT).show();
                    }
                });
                //untuk recycler
            }
        });
//        filter();
//        rv.setHasFixedSize(true);
//        rv.setLayoutManager(new LinearLayoutManager(ChatUser.this));
//        userchatadapter = new chatUserAdapter(datadchatfiltered);
//        rv.setAdapter(userchatadapter);
//        userchatadapter.notifyDataSetChanged();
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    void load_data(){
        if (datadchat.size()!=0){
            datadchat.clear();
        }
        if (datahchat.size()!=0){
            datahchat.clear();
        }
        root.child("Hchat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    datahchat.add(new Hchat(
                        Integer.parseInt(String.valueOf(dataSnapshot.child("id").getValue())),
                        Integer.parseInt(String.valueOf(dataSnapshot.child("user_id").getValue())),
                        Integer.parseInt(String.valueOf(dataSnapshot.child("pegawai_nik").getValue()))
                    ));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getBaseContext(), error +"", Toast.LENGTH_LONG).show();
            }
        });
        root.child("Dchat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    datadchat.add(new Dchat(
                        Integer.parseInt(String.valueOf(dataSnapshot.child("id").getValue())),
                        Integer.parseInt(String.valueOf(dataSnapshot.child("id_hchat").getValue())),
                        Integer.parseInt(String.valueOf(dataSnapshot.child("pengirim").getValue())),
                        String.valueOf(dataSnapshot.child("chat").getValue())
                    ));
                }
                //System.out.println(datadchat.size()+"a");
                filter();

                userchatadapter = new chatUserAdapter(datadchatfiltered,dari);
                rv.setAdapter(userchatadapter);
                rv.scrollToPosition(datadchatfiltered.size()-1);
                userchatadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getBaseContext(), error +"", Toast.LENGTH_LONG).show();
            }
        });
//        root.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                for (DataSnapshot dataSnapshot : snapshot.child("Hchat").getChildren()){
//                    datahchat.add(new Hchat(
//                        Integer.parseInt(String.valueOf(dataSnapshot.child("id").getValue())),
//                        Integer.parseInt(String.valueOf(dataSnapshot.child("user_id").getValue())),
//                        Integer.parseInt(String.valueOf(dataSnapshot.child("pegawai_nik").getValue()))
//                    ));
//                }
//                for (DataSnapshot dataSnapshot : snapshot.child("Dchat").getChildren()){
//                    datadchat.add(new Dchat(
//                        Integer.parseInt(String.valueOf(dataSnapshot.child("id").getValue())),
//                        Integer.parseInt(String.valueOf(dataSnapshot.child("id_hchat").getValue())),
//                        Integer.parseInt(String.valueOf(dataSnapshot.child("pengirim").getValue())),
//                        String.valueOf(dataSnapshot.child("chat").getValue())
//                    ));
//                }
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                for (DataSnapshot dataSnapshot : snapshot.child("Hchat").getChildren()){
//                    datahchat.add(new Hchat(
//                            Integer.parseInt(String.valueOf(dataSnapshot.child("id").getValue())),
//                            Integer.parseInt(String.valueOf(dataSnapshot.child("user_id").getValue())),
//                            Integer.parseInt(String.valueOf(dataSnapshot.child("pegawai_nik").getValue()))
//                    ));
//                }
//                for (DataSnapshot dataSnapshot : snapshot.child("Dchat").getChildren()){
//                    datadchat.add(new Dchat(
//                            Integer.parseInt(String.valueOf(dataSnapshot.child("id").getValue())),
//                            Integer.parseInt(String.valueOf(dataSnapshot.child("id_hchat").getValue())),
//                            Integer.parseInt(String.valueOf(dataSnapshot.child("pengirim").getValue())),
//                            String.valueOf(dataSnapshot.child("chat").getValue())
//                    ));
//                }
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }
    public int cek_id_hchat(){
        int id_hchat=-1;
        for (int i = 0; i < datahchat.size(); i++) {
            if(datahchat.get(i).getUser_id()==id_user&&datahchat.get(i).getPegawai_nik()==nik_peg
            ){
                id_hchat=datahchat.get(i).getId();
            }
        }
        return id_hchat;
    }
    public void filter(){

        if (datadchatfiltered.size()!=0){
            datadchatfiltered.clear();
        }
        int id_hchat=cek_id_hchat();
        if(id_hchat!=-1){
            //ada hchatnya
            for (int i = 0; i < datadchat.size(); i++) {
                if(datadchat.get(i).getId_hchat()==id_hchat){
                    datadchatfiltered.add(datadchat.get(i));
                }
            }
        }
    }
}