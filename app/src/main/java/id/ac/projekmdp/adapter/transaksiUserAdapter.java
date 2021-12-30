package id.ac.projekmdp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import id.ac.projekmdp.R;
import id.ac.projekmdp.User_page;
import id.ac.projekmdp.kelas.Pegawai;
import id.ac.projekmdp.kelas.Transaksi;
import id.ac.projekmdp.kelas.User;

public class transaksiUserAdapter extends RecyclerView.Adapter<transaksiUserAdapter.itemViewHolder>{
    private ArrayList<Pegawai> arrPegawai = new ArrayList<Pegawai>();
    private ArrayList<Transaksi> arrTransaksi = new ArrayList<Transaksi>();
    private ArrayList<Transaksi> arrTransaksiAll = new ArrayList<Transaksi>();
    private Context context;
    private DatabaseReference root;
    String telp = "";
    int nik_peg=-1;
    int idTrans = 0;
    int saldoPegawai = 0;

    public transaksiUserAdapter(Context context, ArrayList<Transaksi> arrTransaksiAll, ArrayList<Pegawai> arrPegawai, int idUser, String selectedStatus, String searchText, String startDate, String endDate) {
        this.arrPegawai = arrPegawai;
        this.arrTransaksiAll = arrTransaksiAll;
        this.context = context;

        //FILTER & SEARCH
        for(int i = arrTransaksiAll.size()-1; i >= 0; i--){
            if(arrTransaksiAll.get(i).getIdUser() == idUser){
                Transaksi trans = arrTransaksiAll.get(i);
                if(!selectedStatus.equals("Status") && !searchText.equals("") && (!startDate.equals("") && !endDate.equals(""))){
                    if(cekStatus(selectedStatus,trans.getStatus()) && cekNama(trans.getNikPegawai(),searchText) && cekTanggal(trans.getTanggal(),startDate,endDate)){
                        arrTransaksi.add(arrTransaksiAll.get(i));
                    }
                }
                else if(!selectedStatus.equals("Status") && !searchText.equals("")){
                    if(cekStatus(selectedStatus,trans.getStatus()) && cekNama(trans.getNikPegawai(),searchText)){
                        arrTransaksi.add(arrTransaksiAll.get(i));
                    }
                }
                else if(!searchText.equals("") && !startDate.equals("") && !endDate.equals("")){
                    if(cekNama(trans.getNikPegawai(),searchText) && cekTanggal(trans.getTanggal(),startDate,endDate)){
                        arrTransaksi.add(arrTransaksiAll.get(i));
                    }
                }
                else if(!selectedStatus.equals("Status") && !startDate.equals("") && !endDate.equals("")){
                    if(cekStatus(selectedStatus,trans.getStatus()) && cekTanggal(trans.getTanggal(),startDate,endDate)){
                        arrTransaksi.add(arrTransaksiAll.get(i));
                    }
                }
                else if(!selectedStatus.equals("Status")){
                    if(cekStatus(selectedStatus,trans.getStatus())){
                        arrTransaksi.add(arrTransaksiAll.get(i));
                    }
                }
                else if(!searchText.equals("")){
                    if(cekNama(trans.getNikPegawai(),searchText)){
                        arrTransaksi.add(arrTransaksiAll.get(i));
                    }
                }
                else if((!startDate.equals("") && !endDate.equals(""))){
                    if(cekTanggal(trans.getTanggal(),startDate,endDate)){
                        arrTransaksi.add(arrTransaksiAll.get(i));
                    }
                }
                else{
                    arrTransaksi.add(arrTransaksiAll.get(i));
                }
            }
        }
    }

    public boolean cekStatus(String status, int stat){
        if(status.equalsIgnoreCase("Declined") && stat == 0){
            return true;
        }
        else if(status.equalsIgnoreCase("Ongoing") && stat == 1){
            return true;
        }
        else if(status.equalsIgnoreCase("Pending") && stat == 2){
            return true;
        }
        else if(status.equalsIgnoreCase("Finished") && stat == 3){
            return true;
        }
        else{
            return false;
        }
    }

//    public boolean cekJasa(int nik, String jasa){
//        for(int i = 0; i < arrPegawai.size(); i++){
//            if(arrPegawai.get(i).getNik() == nik && arrPegawai.get(i).getJasa().equalsIgnoreCase(jasa)){
//                return true;
//            }
//        }
//        return false;
//    }

    public boolean cekNama(int nik, String nama){
        for(int i = 0; i < arrPegawai.size(); i++){
            if(arrPegawai.get(i).getNik() == nik && arrPegawai.get(i).getNama().toLowerCase().contains(nama.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    public boolean cekTanggal(String tglTrans, String start, String end){
        try {
            Date tglTransD = new SimpleDateFormat("dd/MM/yyyy").parse(tglTrans);
            Date startD = new SimpleDateFormat("dd/MM/yyyy").parse(start);
            Date endD = new SimpleDateFormat("dd/MM/yyyy").parse(end);

            if((tglTransD.before(endD) || tglTrans.equals(end)) && (tglTransD.after(startD) || tglTrans.equals(start))){
                return true;
            }
            else{
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    @NonNull
    @Override
    public transaksiUserAdapter.itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_transaksi_user, parent, false);
        return new transaksiUserAdapter.itemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull transaksiUserAdapter.itemViewHolder holder, int position) {
        final Transaksi t = arrTransaksi.get(position);

        DecimalFormat formatHarga = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        formatHarga.setDecimalFormatSymbols(formatRp);
        holder.txtHarga.setText(""+formatHarga.format(new Double(t.getHarga())));

        holder.btnFinish.setEnabled(false);
        holder.btnFinish.setVisibility(View.INVISIBLE);
        holder.txtTanggal.setText(t.getTanggal());

        for(int i=0; i<arrPegawai.size(); i++){
            if(arrPegawai.get(i).getNik() == t.getNikPegawai()){
                holder.txtJenis.setText(arrPegawai.get(i).getJasa());
                holder.txtNama.setText(arrPegawai.get(i).getNama());

                telp = arrPegawai.get(i).getTelepon().replaceAll("\\D+","");
                nik_peg=arrPegawai.get(i).getNik();
                saldoPegawai = arrPegawai.get(i).getSaldo();
            }
        }

        if(t.getStatus() == 0){
            holder.txtStatus.setText("Declined");
        }
        else if(t.getStatus() == 1){
            holder.txtStatus.setText("Ongoing");
            holder.btnFinish.setEnabled(true);
            holder.btnFinish.setVisibility(View.VISIBLE);
            holder.btnFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //FINISH TRANSAKSI
                    finishTransaksi(t.getId(), t.getNikPegawai(), saldoPegawai, t.getHarga());
                }
            });
        }
        else if(t.getStatus() == 2){
            holder.txtStatus.setText("Pending");
        }
        else if(t.getStatus() == 3){
            holder.txtStatus.setText("Finished");
        }

        holder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User_page user_page = (User_page) context;
                user_page.call(telp);
            }
        });

        holder.btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User_page user_page = (User_page) context;
                user_page.chat(nik_peg);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrTransaksi.size();
    }

    public class itemViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView txtJenis,txtNama, txtTanggal, txtHarga, txtStatus;
        Button btnCall, btnChat, btnFinish;
        public itemViewHolder(@NonNull View itemView) {
            super(itemView);

            ivFoto = itemView.findViewById(R.id.imageView7);
            txtJenis = itemView.findViewById(R.id.textView17);
            txtNama = itemView.findViewById(R.id.textView);
            txtTanggal = itemView.findViewById(R.id.textView16);
            txtHarga = itemView.findViewById(R.id.textView18);
            txtStatus = itemView.findViewById(R.id.textView19);
            btnCall = itemView.findViewById(R.id.button7);
            btnChat = itemView.findViewById(R.id.button);
            btnFinish = itemView.findViewById(R.id.button8);

        }
    }

    private void finishTransaksi(int idTrans, int nikPegawai, int saldopeg, int harga){
        root= FirebaseDatabase.getInstance().getReference();
        root.child("Transaksi").orderByChild("id").equalTo(idTrans).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                    String key = childSnapshot.getKey();
                    root.child("Transaksi").child(key).child("status").setValue(3);
                }

                //Success finish trans
                User_page user_page = (User_page)context;
                Toast.makeText(user_page, "Transaction finished", Toast.LENGTH_SHORT).show();
                user_page.loadTransaksi();
                user_page.gototransaksi();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                User_page user_page = (User_page)context;
                Toast.makeText(user_page, "Oops.. Try again later", Toast.LENGTH_SHORT).show();
            }
        });

        root.child("Pegawai").orderByChild("nik").equalTo(nikPegawai).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                    String key = childSnapshot.getKey();
                    int newSaldo = saldopeg + harga;
                    root.child("Pegawai").child(key).child("saldo").setValue(newSaldo);

                    System.out.println(saldopeg + " + " + harga + " = " + newSaldo);
                }

                //Success finish trans
                User_page user_page = (User_page)context;
                user_page.loadPegawai();
                user_page.gototransaksi();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                User_page user_page = (User_page)context;
                Toast.makeText(user_page, "Oops.. Try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
