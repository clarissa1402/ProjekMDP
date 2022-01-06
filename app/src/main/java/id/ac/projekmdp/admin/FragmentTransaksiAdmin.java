package id.ac.projekmdp.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DatabaseReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import id.ac.projekmdp.R;
import id.ac.projekmdp.adapter.transaksiAdminAdapter;
import id.ac.projekmdp.adapter.transaksiUserAdapter;
import id.ac.projekmdp.databinding.FragmentMasterBinding;
import id.ac.projekmdp.databinding.FragmentTransaksiAdminBinding;
import id.ac.projekmdp.kelas.Pegawai;
import id.ac.projekmdp.kelas.Transaksi;
import id.ac.projekmdp.kelas.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTransaksiAdmin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTransaksiAdmin extends Fragment {
    private DatabaseReference root;
    private FragmentTransaksiAdminBinding binding;
    ArrayList<Transaksi> dataTransaksi = new ArrayList<>();
    ArrayList<Pegawai> dataPegawai = new ArrayList<>();
    ArrayList<User> dataUser = new ArrayList<>();
    String selectedStatus = "Status";
    String searchText = "";
    String startDate = "";
    String endDate = "";

    transaksiAdminAdapter adapter;

    public FragmentTransaksiAdmin() {
        // Required empty public constructor
    }

    public static FragmentTransaksiAdmin newInstance(ArrayList<Transaksi>dataTransaksi, ArrayList<Pegawai> dataPegawai, ArrayList<User> dataUser) {
        FragmentTransaksiAdmin fragment = new FragmentTransaksiAdmin();
        Bundle args = new Bundle();
        fragment.dataPegawai = dataPegawai;
        fragment.dataTransaksi = dataTransaksi;
        fragment.dataUser = dataUser;
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
        binding = FragmentTransaksiAdminBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.rvListTransaksi.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvListTransaksi.setHasFixedSize(true);

        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.dateRangePicker();
        materialDateBuilder.setTitleText("Select Date");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
        binding.button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDate="";
                endDate="";
                loadRV();
            }
        });
        binding.daterangepicker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getActivity().getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                String range = materialDatePicker.getSelection().toString().replaceAll("\\D+","");
                String awal = range.substring(0,10);
                String akhir = range.substring(13,23);

                Date startD = new Date(Long.parseLong(awal) * 1000L);
                Date endD = new Date(Long.parseLong(akhir) * 1000L);

                DateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
                startDate = newFormat.format(startD);
                endDate = newFormat.format(endD);

                loadRV();
            }
        });

        binding.etSearchTrans.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchText = binding.etSearchTrans.getText().toString();
                loadRV();
            }
        });

        binding.spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedStatus = binding.spinner4.getSelectedItem().toString();
                loadRV();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        loadRV();
    }
    public void loadRV(){
        adapter = new transaksiAdminAdapter(dataPegawai,dataUser,dataTransaksi, selectedStatus, searchText, startDate, endDate);
        binding.rvListTransaksi.setAdapter(adapter);
    }
}