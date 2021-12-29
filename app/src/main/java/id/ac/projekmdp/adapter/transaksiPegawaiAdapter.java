package id.ac.projekmdp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import id.ac.projekmdp.Pegawai_page;
import id.ac.projekmdp.R;
import id.ac.projekmdp.kelas.Pegawai;
import id.ac.projekmdp.kelas.Transaksi;
import id.ac.projekmdp.kelas.User;

public class transaksiPegawaiAdapter extends RecyclerView.Adapter<transaksiPegawaiAdapter.itemViewHolder>{
    private ArrayList<User> arrUser = new ArrayList<User>();
    private ArrayList<Transaksi> arrTransaksi = new ArrayList<Transaksi>();
    private ArrayList<Transaksi> arrTransaksiAll = new ArrayList<Transaksi>();
    private Context context;
    private DatabaseReference root;
    String telp = "";

    public transaksiPegawaiAdapter(Context context, ArrayList<Transaksi> arrTransaksiAll, ArrayList<User> arrUser, int nik, String selectedStatus, String searchText, String startDate, String endDate) {
        this.arrUser = arrUser;
        this.arrTransaksiAll = arrTransaksiAll;
        this.context = context;

        //FILTER & SEARCH
        for(int i = arrTransaksiAll.size()-1; i >= 0; i--){
            if(arrTransaksiAll.get(i).getNikPegawai() == nik){
                Transaksi trans = arrTransaksiAll.get(i);
                if(!selectedStatus.equalsIgnoreCase("Status") && !searchText.equals("") && (!startDate.equals("") && !endDate.equals(""))){
                    if(cekStatus(selectedStatus,trans.getStatus()) && cekNama(trans.getIdUser(),searchText) && cekTanggal(trans.getTanggal(),startDate,endDate)){
                        arrTransaksi.add(arrTransaksiAll.get(i));
                    }
                }
                else if(!selectedStatus.equalsIgnoreCase("Status") && !searchText.equals("")){
                    if(cekStatus(selectedStatus,trans.getStatus()) && cekNama(trans.getIdUser(),searchText)){
                        arrTransaksi.add(arrTransaksiAll.get(i));
                    }
                }
                else if(!searchText.equals("") && !startDate.equals("") && !endDate.equals("")){
                    if(cekNama(trans.getIdUser(),searchText) && cekTanggal(trans.getTanggal(),startDate,endDate)){
                        arrTransaksi.add(arrTransaksiAll.get(i));
                    }
                }
                else if(!selectedStatus.equalsIgnoreCase("Status") && !startDate.equals("") && !endDate.equals("")){
                    if(cekStatus(selectedStatus,trans.getStatus()) && cekTanggal(trans.getTanggal(),startDate,endDate)){
                        arrTransaksi.add(arrTransaksiAll.get(i));
                    }
                }
                else if(!selectedStatus.equalsIgnoreCase("Status")){
                    if(cekStatus(selectedStatus,trans.getStatus())){
                        arrTransaksi.add(arrTransaksiAll.get(i));
                    }
                }
                else if(!searchText.equals("")){
                    if(cekNama(trans.getIdUser(),searchText)){
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

    public boolean cekNama(int id, String nama){
        for(int i = 0; i < arrUser.size(); i++){
            if(arrUser.get(i).getId() == id && arrUser.get(i).getNama().toLowerCase().contains(nama.toLowerCase())){
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
    public transaksiPegawaiAdapter.itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_transaksi_pegawai, parent, false);
        return new transaksiPegawaiAdapter.itemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull transaksiPegawaiAdapter.itemViewHolder holder, int position) {
        final Transaksi t = arrTransaksi.get(position);

        holder.btnAccept.setEnabled(false);
        holder.btnAccept.setVisibility(View.INVISIBLE);

        holder.btnDecline.setEnabled(false);
        holder.btnDecline.setVisibility(View.INVISIBLE);

        holder.txtTanggal.setText(t.getTanggal());
        if(t.getStatus() == 0){
            holder.txtStatus.setText("Declined");
        }
        else if(t.getStatus() == 1){
            holder.txtStatus.setText("Ongoing");
        }
        else if(t.getStatus() == 2){
            holder.txtStatus.setText("Pending");

            holder.btnAccept.setEnabled(true);
            holder.btnAccept.setVisibility(View.VISIBLE);

            holder.btnDecline.setEnabled(true);
            holder.btnDecline.setVisibility(View.VISIBLE);

            holder.btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateStatusTransaksi(t.getId(),1);
                }
            });

            holder.btnDecline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(true);
                    builder.setTitle("Decline confirmation");
                    builder.setMessage("Decline this order?");
                    builder.setPositiveButton("Decline",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    updateStatusTransaksi(t.getId(),0);
                                }
                            });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }
        else if(t.getStatus() == 3){
            holder.txtStatus.setText("Finished");
        }

        for(int i=0; i<arrUser.size(); i++){
            if(arrUser.get(i).getId() == t.getIdUser()){
                holder.txtNama.setText(arrUser.get(i).getNama());
                telp = arrUser.get(i).getTelepon().replaceAll("\\D+","");
            }
        }

        holder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pegawai_page pegawai_page = (Pegawai_page) context;
                pegawai_page.call(telp);
            }
        });

        holder.btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrTransaksi.size();
    }

    public class itemViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView txtNama, txtTanggal, txtAlamat, txtStatus;
        Button btnCall, btnChat, btnAccept, btnDecline;
        public itemViewHolder(@NonNull View itemView) {
            super(itemView);

            ivFoto = itemView.findViewById(R.id.imageView107);
            txtNama = itemView.findViewById(R.id.textView100);
            txtTanggal = itemView.findViewById(R.id.textView116);
            txtAlamat = itemView.findViewById(R.id.textView20);
            txtStatus = itemView.findViewById(R.id.textView119);
            btnCall = itemView.findViewById(R.id.button107);
            btnChat = itemView.findViewById(R.id.button100);
            btnAccept = itemView.findViewById(R.id.button108);
            btnDecline = itemView.findViewById(R.id.button9);

        }
    }

    private void updateStatusTransaksi(int idTrans, int status){
        root= FirebaseDatabase.getInstance().getReference();
        root.child("Transaksi").orderByChild("id").equalTo(idTrans).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                    String key = childSnapshot.getKey();
                    root.child("Transaksi").child(key).child("status").setValue(status);
                }

                Pegawai_page pegawai_page = (Pegawai_page)context;
                if(status == 1){
                    Toast.makeText(pegawai_page, "Transaction accepted", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(pegawai_page, "Transaction declined", Toast.LENGTH_SHORT).show();
                }

                pegawai_page.loadTransaksi();
                pegawai_page.gototransaksi();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Pegawai_page pegawai_page = (Pegawai_page) context;
                Toast.makeText(pegawai_page, "Oops.. Try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
