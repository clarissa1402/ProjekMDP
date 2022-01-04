package id.ac.projekmdp.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import id.ac.projekmdp.adapter.listPegawaiAdapter;
import id.ac.projekmdp.databinding.FragmentMasterBinding;
import id.ac.projekmdp.kelas.Pegawai;

public class MasterFragment extends Fragment {

    private static final String ARG_PARAM_MENU = "param-menu";

    private String menu;
    private FragmentMasterBinding binding;
    private listPegawaiAdapter adapter;
    ArrayList<Pegawai> datapegawai=new ArrayList<>();
    String search="",jenis_dipilh="";


    public static MasterFragment newInstance(String menu,ArrayList<Pegawai> datapegawai) {
        MasterFragment fragment = new MasterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_MENU, menu);
        fragment.datapegawai = datapegawai;
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
        //load_data();

        //Toast.makeText(getContext(),datapegawai.size()+"",Toast.LENGTH_SHORT).show();
        //System.out.println("SIZE: "+datapegawai.size()+"");
        binding.rvAdminMaster.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvAdminMaster.setHasFixedSize(true);

        adapter = new listPegawaiAdapter(datapegawai,search,"All");
        binding.rvAdminMaster.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        binding.spinnerJenis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                jenis_dipilh = binding.spinnerJenis.getSelectedItem().toString();
                setRecycle();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                search = binding.etSearch.getText().toString();
                setRecycle();
            }
        });
        adapter.setOnItemClickCallback(new listPegawaiAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Pegawai pegawai) {
                Intent toUpdate = new Intent(getContext(),AddEditPegawai.class);
                toUpdate.putExtra("detail", (Parcelable) pegawai);
                startActivity(toUpdate);
            }
        });
    }

    public void setRecycle(){
        adapter = new listPegawaiAdapter(datapegawai,search,jenis_dipilh);
        binding.rvAdminMaster.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}