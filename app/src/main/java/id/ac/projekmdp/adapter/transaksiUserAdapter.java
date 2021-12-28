package id.ac.projekmdp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.ac.projekmdp.kelas.Pegawai;
import id.ac.projekmdp.kelas.Transaksi;

public class transaksiUserAdapter extends RecyclerView.Adapter<transaksiUserAdapter.itemViewHolder>{
    private ArrayList<Pegawai> arrPegawai = new ArrayList<Pegawai>();
    private ArrayList<Transaksi> arrTransaksi = new ArrayList<Transaksi>();
    private ArrayList<Transaksi> arrTransaksiAll = new ArrayList<Transaksi>();
    private Context context;

    public transaksiUserAdapter(ArrayList<Pegawai> arrPegawai, ArrayList<Transaksi> arrTransaksiAll, Context context) {
        this.arrPegawai = arrPegawai;
        this.arrTransaksiAll = arrTransaksiAll;
        this.context = context;


    }

    @NonNull
    @Override
    public transaksiUserAdapter.itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull transaksiUserAdapter.itemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class itemViewHolder extends RecyclerView.ViewHolder {
        public itemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
