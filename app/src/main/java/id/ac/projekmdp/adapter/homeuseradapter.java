package id.ac.projekmdp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.ac.projekmdp.R;
import id.ac.projekmdp.kelas.Pegawai;

public class homeuseradapter extends RecyclerView.Adapter<homeuseradapter.itemViewHolder>{
    private ArrayList<Pegawai> arrPegawai = new ArrayList<Pegawai>();
    private ArrayList<Pegawai> arrPegawais = new ArrayList<Pegawai>();
    private Context context;
    private String searchText, jenis;

    public homeuseradapter(Context ctx, ArrayList<Pegawai> pegawais, String search, String jenis) {
        this.context = ctx;
        this.arrPegawais = pegawais;
        this.jenis = jenis;
        this.searchText = search;

        //Search & Filter
        if(!this.searchText.equals("") && !jenis.equalsIgnoreCase("all")){
            for(int i=0; i < arrPegawais.size(); i++){
                if(arrPegawais.get(i).getNama().contains(searchText) && arrPegawais.get(i).getJasa().equalsIgnoreCase(jenis)){
                    this.arrPegawai.add(arrPegawais.get(i));
                }
            }
        }
        else if(!this.searchText.equals("")){
            for(int i=0; i < arrPegawais.size(); i++){
                if(arrPegawais.get(i).getNama().contains(searchText)){
                    this.arrPegawai.add(arrPegawais.get(i));
                }
            }
        }
        else if(!jenis.equalsIgnoreCase("all")){
            for(int i=0; i < arrPegawais.size(); i++){
                if(arrPegawais.get(i).getJasa().equalsIgnoreCase(jenis)){
                    this.arrPegawai.add(arrPegawais.get(i));
                }
            }
        }
        else{
            this.arrPegawai = pegawais;
        }




//         //Search
//         if(!search.equalsIgnoreCase("")){
//             for(int i=0; i < arrPegawai.size(); i++){
//                   if(!arrPegawai.get(i).getNama().contains(searchText)){
//                     this.arrPegawai.remove(i);
//                     i--;
//                 }
//             }
//         }
//
//         //Filter
//         if(!jenis.equalsIgnoreCase("all")){
//             for(int i=0; i < arrPegawai.size(); i++){
//                 if(!arrPegawai.get(i).getJasa().equalsIgnoreCase(jenis)){
//                     this.arrPegawai.remove(i);
//                     i--;
//                 }
//             }


        //search
        /*ArrayList<Pegawai> listPegawaiSorted = new ArrayList<>();
        listPegawaiSorted.clear();
        for(Pegawai p: pegawais){
        if(p.getNama().toLowerCase().contains(searchText))
        }*/
    }


    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_home_user, parent, false);
        return new homeuseradapter.itemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {
        final Pegawai p = arrPegawai.get(position);
        holder.txtnama.setText(p.getNama());
        holder.txtjenis.setText(p.getJasa());
        holder.txtharga.setText(p.getHarga()+"");
//         holder.txtNama.setText(p.getNama();
    }

    @Override
    public int getItemCount() {
        return arrPegawai.size();
    }

    public class itemViewHolder extends RecyclerView.ViewHolder {
         TextView txtnama,txtjenis,txtharga;
         Button btntambah;

        public itemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtnama=itemView.findViewById(R.id.textView5);
            txtjenis=itemView.findViewById(R.id.textView6);
            txtharga=itemView.findViewById(R.id.textView7);
            btntambah=itemView.findViewById(R.id.button3);
        }
    }
}
