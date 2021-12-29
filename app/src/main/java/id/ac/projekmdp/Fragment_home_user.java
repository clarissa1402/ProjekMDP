package id.ac.projekmdp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import id.ac.projekmdp.adapter.homeuseradapter;
import id.ac.projekmdp.databinding.FragmentHomeUserBinding;
import id.ac.projekmdp.kelas.Pegawai;
import id.ac.projekmdp.kelas.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_home_user#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_home_user extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DatabaseReference root;
    private FragmentHomeUserBinding binding;

    User_page u;
    ArrayList<User>datauser=new ArrayList<>();
    ArrayList<Pegawai>datapegawai=new ArrayList<>();
    ArrayList<String>jenis=new ArrayList<>();
    homeuseradapter homeuseradapter;
    String search="",jenis_dipilh="";
    RecyclerView rv;
    //String jenis[];
    // TODO: Rename and change types of parameters
    public Fragment_home_user() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Fragment_home_user.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_home_user newInstance(User_page u1, ArrayList<User> datauser, ArrayList<Pegawai> datapegawai) {
        Fragment_home_user fragment = new Fragment_home_user();
        Bundle args = new Bundle();
        fragment.u=u1;
        fragment.datauser = datauser;
        fragment.datapegawai = datapegawai;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home_user, container, false);
        binding = FragmentHomeUserBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        load_data();
        set_jenis();
        System.out.println("SIZE: "+datauser.size()+"");
        for (int i = 0; i < datauser.size(); i++) {
            if(datauser.get(i).getId()==u.id){
                System.out.println("SALDO: "+datauser.get(i).getSaldo()+"");
                binding.textView4.setText("Rp "+datauser.get(i).getSaldo());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, jenis);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spjenis = (Spinner) view.findViewById(R.id.spinner2);
        spjenis.setAdapter(adapter);

        rv=view.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        homeuseradapter = new homeuseradapter(u, datapegawai, search, "All");
        rv.setAdapter(homeuseradapter);
        homeuseradapter.notifyDataSetChanged();

        spjenis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                jenis_dipilh=spjenis.getSelectedItem().toString();
                set_recycle();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.edtsearchhomeUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //if(!binding.edtsearchhomeUser.getText().toString().equals("")){
                    search=binding.edtsearchhomeUser.getText().toString();
                    set_recycle();
                //}
            }
        });

    }
    public void load_data(){
        root= FirebaseDatabase.getInstance().getReference();
        root.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    datauser.add(new User(Integer.parseInt(String.valueOf(dataSnapshot.child("id").getValue())),  String.valueOf(dataSnapshot.child("email").getValue()),String.valueOf(dataSnapshot.child("nama").getValue()),String.valueOf(dataSnapshot.child("telepon").getValue()),String.valueOf(dataSnapshot.child("alamat").getValue()),String.valueOf(dataSnapshot.child("password").getValue()), Integer.parseInt(String.valueOf(dataSnapshot.child("saldo").getValue()))));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
    public void set_jenis(){
        jenis.add("all");
        jenis.add("Cleaning");
        jenis.add("Painting");
        jenis.add("Plumbing");
        jenis.add("Electrical");
        jenis.add("Repair");
    }
    public void  set_recycle(){
        homeuseradapter = new homeuseradapter(u, datapegawai, search, jenis_dipilh);
        binding.rv.setAdapter(homeuseradapter);
        homeuseradapter.notifyDataSetChanged();
    }
}