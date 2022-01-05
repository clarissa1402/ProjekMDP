package id.ac.projekmdp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.ac.projekmdp.databinding.RvlistpegawaiBinding;
import id.ac.projekmdp.kelas.Pegawai;

public class listPegawaiAdapter extends RecyclerView.Adapter<listPegawaiAdapter.itemViewHolder> {

    private ArrayList<Pegawai> listPegawai = new ArrayList<>();
    private ArrayList<Pegawai> listPegawaiSorted = new ArrayList<>();
    private Context context;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    private OnItemClickCallback onItemClickCallback;
    private String searchText, jenis;

    public listPegawaiAdapter(ArrayList<Pegawai> pegawais, String search, String jenis) {
        this.listPegawaiSorted = pegawais;
        this.jenis = jenis;
        this.searchText = search;


        //Search & Filter
        if(!this.searchText.equals("") && !jenis.equalsIgnoreCase("all")){
            for(int i=0; i < listPegawaiSorted.size(); i++){
                if(listPegawaiSorted.get(i).getNama().contains(searchText) && listPegawaiSorted.get(i).getJasa().equalsIgnoreCase(jenis)){
                    this.listPegawai.add(listPegawaiSorted.get(i));
                }
            }
        }
        else if(!this.searchText.equals("")){
            for(int i=0; i < listPegawaiSorted.size(); i++){
                if(listPegawaiSorted.get(i).getNama().contains(searchText)){
                    this.listPegawai.add(listPegawaiSorted.get(i));
                }
            }
        }
        else if(!jenis.equalsIgnoreCase("all")){
            for(int i=0; i < listPegawaiSorted.size(); i++){
                if(listPegawaiSorted.get(i).getJasa().equalsIgnoreCase(jenis)){
                    this.listPegawai.add(listPegawaiSorted.get(i));
                }
            }
        }
        else{
            this.listPegawai = pegawais;
        }
    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvlistpegawaiBinding binding = RvlistpegawaiBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new itemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {
        Pegawai pegawai = listPegawai.get(position);
        holder.bind(pegawai);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(pegawai);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPegawai.size();
    }

    public class itemViewHolder extends RecyclerView.ViewHolder {
        private final RvlistpegawaiBinding binding;
        public itemViewHolder(@NonNull RvlistpegawaiBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        void bind(Pegawai pegawai){
            binding.tvTelpPegawai.setText(pegawai.getTelepon());
            binding.tvJasaPegawai.setText(pegawai.getJasa());
            binding.tvEmailPegawai.setText(pegawai.getEmail());
            binding.tvNamaPegawai.setText(pegawai.getNama());
        }
    }
    public interface OnItemClickCallback{
        void onItemClicked(Pegawai pegawai);
    }
}
