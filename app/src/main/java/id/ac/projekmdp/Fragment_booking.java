package id.ac.projekmdp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.Locale;

import id.ac.projekmdp.kelas.Pegawai;
import id.ac.projekmdp.kelas.Transaksi;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_booking#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_booking extends Fragment {

    private DatabaseReference root;
    User_page user_page;
    int nik;

    ArrayList<Transaksi> dataTransaksi = new ArrayList<Transaksi>();
    ArrayList<Pegawai> dataPegawai = new ArrayList<Pegawai>();
    Pegawai selected;
    String selectedDate = "";

    TextView txtJasa, txtNama, txtTelepon, txtDeskripsi, txtHarga;
    ImageView foto;
    Button btnBook;
    CalendarView tanggal;

    public Fragment_booking() {
        // Required empty public constructor
    }

    public static Fragment_booking newInstance(User_page u, int nik) {
        Fragment_booking fragment = new Fragment_booking();
        Bundle args = new Bundle();
        fragment.user_page = u;
        fragment.nik = nik;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        root= FirebaseDatabase.getInstance().getReference();

        btnBook = view.findViewById(R.id.button6);
        txtJasa = view.findViewById(R.id.textView10);
        txtNama = view.findViewById(R.id.textView11);
        txtTelepon = view.findViewById(R.id.textView12);
        txtDeskripsi = view.findViewById(R.id.textView13);
        txtHarga = view.findViewById(R.id.textView15);
        foto = view.findViewById(R.id.imageView6);
        tanggal = view.findViewById(R.id.calendarView);

        loadPegawai();
        loadTransaksi();

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedDate.equals("")){
                    Toast.makeText(user_page, "Choose booking date", Toast.LENGTH_SHORT).show();
                }
                else{
                    //CEK TANGGAL & GET NEW TRANS ID
                    int newID = 0;
                    boolean cek = true;

                    String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                    try {
                        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(selectedDate);
                        Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(currentDate);

                        if(date1.before(date2)){
                            cek = false;
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                        cek = false;
                    }

                    for(int i = 0; i < dataTransaksi.size(); i++){
                        if(dataTransaksi.get(i).getNikPegawai() == nik && dataTransaksi.get(i).getTanggal().equals(selectedDate)){
                            cek = false;
                        }

                        if(dataTransaksi.get(i).getId() > newID){
                            newID = dataTransaksi.get(i).getId();
                        }
                    }
                    if(cek){
                        user_page.set_sedang_login();
                        //CEK SALDO
                        if(user_page.sedang_login.getSaldo() - selected.getHarga() >= 0){
                            insertTransaksi(newID);
                        }
                        else{
                            Toast.makeText(user_page, "Not enough saldo", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(user_page, "Invalid Date", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tanggal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                i1++;
                selectedDate = i2 +"/"+ i1 +"/"+ i;
            }
        });
    }

    private void loadPegawai(){
        root.child("Pegawai").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    dataPegawai.add(new Pegawai(
                            Integer.parseInt(String.valueOf(dataSnapshot.child("nik").getValue())),
                            String.valueOf(dataSnapshot.child("email").getValue()),
                            String.valueOf(dataSnapshot.child("nama").getValue()),
                            String.valueOf(dataSnapshot.child("telepon").getValue()),
                            String.valueOf(dataSnapshot.child("alamat").getValue()),
                            String.valueOf(dataSnapshot.child("password").getValue()),
                            String.valueOf(dataSnapshot.child("jasa").getValue()),
                            String.valueOf(dataSnapshot.child("deskripsi").getValue()),
                            Integer.parseInt(String.valueOf(dataSnapshot.child("harga").getValue())),
                            Integer.parseInt(String.valueOf(dataSnapshot.child("saldo").getValue()))
                    ));
                }

                //Get Selected Pegawai
                for(int i = 0; i < dataPegawai.size(); i++){
                    if(dataPegawai.get(i).getNik() == nik){
                        selected = dataPegawai.get(i);
                    }
                }

                //Set TextView
                txtJasa.setText(selected.getJasa());
                txtNama.setText(selected.getNama());
                txtTelepon.setText(selected.getTelepon());
                txtDeskripsi.setText(selected.getDeskripsi());

                //format harga
                DecimalFormat formatHarga = (DecimalFormat) DecimalFormat.getCurrencyInstance();
                DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
                formatRp.setCurrencySymbol("Rp");
                formatRp.setMonetaryDecimalSeparator(',');
                formatRp.setGroupingSeparator('.');
                formatHarga.setDecimalFormatSymbols(formatRp);
                txtHarga.setText(""+formatHarga.format(new Double(selected.getHarga())));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadTransaksi(){
        root.child("Transaksi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    dataTransaksi.add(new Transaksi(
                            Integer.parseInt(String.valueOf(dataSnapshot.child("id").getValue())),
                            Integer.parseInt(String.valueOf(dataSnapshot.child("idUser").getValue())),
                            Integer.parseInt(String.valueOf(dataSnapshot.child("nikPegawai").getValue())),
                            String.valueOf(dataSnapshot.child("tanggal").getValue()),
                            Integer.parseInt(String.valueOf(dataSnapshot.child("harga").getValue())),
                            Integer.parseInt(String.valueOf(dataSnapshot.child("status").getValue()))
                    ));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void insertTransaksi(int newID){
        newID++;
        root.child("Transaksi").push().setValue(new Transaksi(newID,user_page.id,nik,selectedDate,selected.getHarga(),2)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                updateSaldo();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(user_page, "Transaction Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateSaldo(){
        root.child("Users").orderByChild("id").equalTo(user_page.id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                    String key = childSnapshot.getKey();
                    user_page.set_sedang_login();
                    int saldo = user_page.sedang_login.getSaldo() - selected.getHarga();
                    root.child("Users").child(key).child("saldo").setValue(saldo);
                    user_page.load_data();
                    user_page.set_sedang_login();
                }

                //Transaction Success
                Toast.makeText(user_page, "Transaction Success", Toast.LENGTH_SHORT).show();
                user_page.loadTransaksi();
                user_page.gotohome();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Update Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}