package id.ac.projekmdp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.ac.projekmdp.R;
import id.ac.projekmdp.kelas.Dchat;
import id.ac.projekmdp.kelas.Pegawai;

public class chatUserAdapter extends RecyclerView.Adapter<chatUserAdapter.itemViewHolder>{
    ArrayList<Dchat>datadchat=new ArrayList<>();

    public chatUserAdapter(ArrayList<Dchat> datadchat) {
        this.datadchat = datadchat;
    }

    @NonNull
    @Override
    public chatUserAdapter.itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_chat_user, parent, false);
        return new chatUserAdapter.itemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull chatUserAdapter.itemViewHolder holder, int position) {
        final Dchat dchat = datadchat.get(position);
        holder.txtchat.setText(dchat.getChat());
        System.out.println(dchat.getChat());
    }

    @Override
    public int getItemCount() {
        return datadchat.size();
    }

    public class itemViewHolder extends RecyclerView.ViewHolder {
        TextView txtchat;
        ImageView ivprofile;
        public itemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtchat=itemView.findViewById(R.id.textView22);
            ivprofile=itemView.findViewById(R.id.imageView9);
        }
    }
}
