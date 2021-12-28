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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import id.ac.projekmdp.R;
import id.ac.projekmdp.User_page;
import id.ac.projekmdp.kelas.Pegawai;
import id.ac.projekmdp.kelas.Transaksi;

public class transaksiUserAdapter extends RecyclerView.Adapter<transaksiUserAdapter.itemViewHolder>{
    private ArrayList<Pegawai> arrPegawai = new ArrayList<Pegawai>();
    private ArrayList<Transaksi> arrTransaksi = new ArrayList<Transaksi>();
    private ArrayList<Transaksi> arrTransaksiAll = new ArrayList<Transaksi>();
    private Context context;
    int telp = 0;

    public transaksiUserAdapter(Context context, ArrayList<Transaksi> arrTransaksiAll, ArrayList<Pegawai> arrPegawai, int idUser) {
        this.arrPegawai = arrPegawai;
        this.arrTransaksiAll = arrTransaksiAll;
        this.context = context;

        for(int i = arrTransaksiAll.size()-1; i >= 0; i--){
            if(arrTransaksiAll.get(i).getIdUser() == idUser){
                arrTransaksi.add(arrTransaksiAll.get(i));
            }
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

        holder.txtTanggal.setText(t.getTanggal());

        for(int i=0; i<arrPegawai.size(); i++){
            if(arrPegawai.get(i).getNik() == t.getNikPegawai()){
                holder.txtJenis.setText(arrPegawai.get(i).getJasa());
                holder.txtNama.setText(arrPegawai.get(i).getNama());
//                String temp = arrPegawai.get(i).getTelepon().replaceAll("\\D+","");
//                telp = Integer.parseInt(temp);
            }
        }

        holder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + telp));
                context.startActivity(callIntent);
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
        TextView txtJenis,txtNama, txtTanggal, txtHarga;
        Button btnCall, btnChat;
        public itemViewHolder(@NonNull View itemView) {
            super(itemView);

            ivFoto = itemView.findViewById(R.id.imageView7);
            txtJenis = itemView.findViewById(R.id.textView17);
            txtNama = itemView.findViewById(R.id.textView);
            txtTanggal = itemView.findViewById(R.id.textView16);
            txtHarga = itemView.findViewById(R.id.textView18);
            btnCall = itemView.findViewById(R.id.button7);
            btnChat = itemView.findViewById(R.id.button);

        }
    }
}
