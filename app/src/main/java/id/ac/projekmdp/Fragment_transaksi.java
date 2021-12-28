package id.ac.projekmdp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

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

    Spinner spinnerJasa;
    Button daterangepicker;
    RecyclerView rvTransaksi;
    TextView txtSearch;

    public Fragment_transaksi() {
        // Required empty public constructor
    }

    public static Fragment_transaksi newInstance(User_page u) {
        Fragment_transaksi fragment = new Fragment_transaksi();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.user_page = u;
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

        spinnerJasa = view.findViewById(R.id.spinner3);
        daterangepicker = view.findViewById(R.id.daterangepicker);
        txtSearch = view.findViewById(R.id.editTextTextPersonName5);
        rvTransaksi = view.findViewById(R.id.rvTransaksiUser);

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
                Toast.makeText(getActivity(), ""+materialDatePicker.getSelection(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), ""+"Selected Date is : " + materialDatePicker.getHeaderText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}