package id.ac.projekmdp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.ac.projekmdp.databinding.RvlistpegawaiBinding;
import id.ac.projekmdp.kelas.Pegawai;

public class listPegawaiAdapter extends RecyclerView.Adapter<listPegawaiAdapter.ViewHolder> {

    private ArrayList<Pegawai> listPegawai;
    private OnItemClickCallback onItemCLickCallback;

    public listPegawaiAdapter(ArrayList<Pegawai> listPegawai) {
        this.listPegawai = listPegawai;
    }
    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemCLickCallback = onItemClickCallback;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvlistpegawaiBinding binding = RvlistpegawaiBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pegawai pegawai = listPegawai.get(position);
        holder.bind(pegawai);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemCLickCallback.onItemClicked(pegawai);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPegawai.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final RvlistpegawaiBinding binding;
        public ViewHolder(@NonNull RvlistpegawaiBinding rvlistpegawaiBinding) {
            super(rvlistpegawaiBinding.getRoot());
            this.binding = rvlistpegawaiBinding;
        }

        void bind(Pegawai pegawai){
            binding.tvNamaPegawai.setText(pegawai.getNama());
            binding.tvEmailPegawai.setText(pegawai.getEmail());
            binding.tvJasaPegawai.setText(pegawai.getJasa());
            binding.tvTelpPegawai.setText(pegawai.getTelepon());
        }
    }

    public interface OnItemClickCallback{
        void onItemClicked(Pegawai pegawai);
    }
}
