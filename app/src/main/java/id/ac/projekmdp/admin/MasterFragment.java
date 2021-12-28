package id.ac.projekmdp.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import id.ac.projekmdp.R;
import id.ac.projekmdp.adapter.homeuseradapter;
import id.ac.projekmdp.adapter.listPegawaiAdapter;
import id.ac.projekmdp.databinding.FragmentMasterBinding;
import id.ac.projekmdp.kelas.Pegawai;
import id.ac.projekmdp.kelas.User;

public class MasterFragment extends Fragment {

    private static final String ARG_PARAM_MENU = "param-menu";
    private DatabaseReference root;
    private  String menu;

    private FragmentMasterBinding binding;
    private listPegawaiAdapter adapter;
    ArrayList<Pegawai> datapegawai=new ArrayList<>();
    String search="",jenis_dipilh="";

    public MasterFragment() {
        // Required empty public constructor
    }

    public static MasterFragment newInstance(String menu) {
        MasterFragment fragment = new MasterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_MENU, menu);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            menu = getArguments().getString(ARG_PARAM_MENU);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_master, container, false);
        binding = FragmentMasterBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        load_data();
        setUpRecyclerView(datapegawai);
        adapter.notifyDataSetChanged();
        Toast.makeText(getContext(),datapegawai.size()+"",Toast.LENGTH_SHORT).show();

    }
    void setUpRecyclerView(ArrayList<Pegawai> listpegawai){
        binding.rvAdminMaster.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvAdminMaster.setHasFixedSize(true);

        adapter = new listPegawaiAdapter(listpegawai);
        binding.rvAdminMaster.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setOnItemClickCallback(new listPegawaiAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Pegawai pegawai) {
                Intent toUpdate = new Intent(getContext(),AddEditPegawai.class);
                toUpdate.putExtra("detail", (Parcelable) pegawai);
                startActivity(toUpdate);
            }
        });
    }
    public void load_data(){
        root= FirebaseDatabase.getInstance().getReference();
        root.child("Pegawai").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    datapegawai.add(new Pegawai(
                            Integer.parseInt(String.valueOf(dataSnapshot.child("nik").getValue())),
                            String.valueOf(dataSnapshot.child("email").getValue()),
                            String.valueOf(dataSnapshot.child("nama").getValue()),
                            String.valueOf(dataSnapshot.child("telepon").getValue()),
                            String.valueOf(dataSnapshot.child("alamat").getValue()),
                            String.valueOf(dataSnapshot.child("password").getValue()),
                            String.valueOf(dataSnapshot.child("jasa").getValue()),
                            String.valueOf(dataSnapshot.child("deskripsi").getValue())
                    ));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}