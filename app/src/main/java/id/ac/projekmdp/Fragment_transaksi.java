package id.ac.projekmdp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DatabaseReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import id.ac.projekmdp.adapter.transaksiUserAdapter;
import id.ac.projekmdp.kelas.Pegawai;
import id.ac.projekmdp.kelas.Transaksi;
import id.ac.projekmdp.kelas.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_transaksi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_transaksi extends Fragment {
    private DatabaseReference root;
    User_page user_page;

    ArrayList<Transaksi> dataTransaksi = new ArrayList<Transaksi>();
    ArrayList<Pegawai> dataPegawai = new ArrayList<Pegawai>();
//    String[] jasa = {"All","Cleaning","Painting","Plumbing","Electrical","Repair"};
    String[] status = {"Status","Declined","Ongoing","Pending","Finished"};

//    Spinner spinnerJasa;
    Spinner spinnerStatus;
    Button daterangepicker, btnClearRange;
    RecyclerView rvTransaksi;
    TextView txtSearch;

//    String selectedJasa = "Status";
    String selectedStatus = "Status";
    String searchText = "";
    String startDate = "";
    String endDate = "";

    transaksiUserAdapter adapterTransaksi;

    public Fragment_transaksi() {
        // Required empty public constructor
    }

    public static Fragment_transaksi newInstance(User_page u, ArrayList<Transaksi>dataTransaksi, ArrayList<Pegawai> dataPegawai) {
        Fragment_transaksi fragment = new Fragment_transaksi();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.user_page = u;
        fragment.dataPegawai = dataPegawai;
        fragment.dataTransaksi = dataTransaksi;
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
        return inflater.inflate(R.layout.fragment_transaksi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinnerStatus = view.findViewById(R.id.spinner3);
        daterangepicker = view.findViewById(R.id.daterangepicker);
        txtSearch = view.findViewById(R.id.editTextTextPersonName5);
        rvTransaksi = view.findViewById(R.id.rvTransaksiUser);
        btnClearRange = view.findViewById(R.id.button11);

        //CLEAR RANGE
        btnClearRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDate = "";
                endDate = "";
                loadRV();
            }
        });

        //SET SPINNER
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, status);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);

        //DATE RANGE PICKER
        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.dateRangePicker();
        materialDateBuilder.setTitleText("Select Date");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        daterangepicker.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchText = txtSearch.getText().toString();
                loadRV();
            }
        });

        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedStatus = spinnerStatus.getSelectedItem().toString();
                loadRV();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        rvTransaksi.setHasFixedSize(true);
        rvTransaksi.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadRV();
    }

    public void loadRV(){
        adapterTransaksi = new transaksiUserAdapter(getActivity(), dataTransaksi, dataPegawai, user_page.id, selectedStatus, searchText, startDate, endDate);
        rvTransaksi.setAdapter(adapterTransaksi);
    }
}