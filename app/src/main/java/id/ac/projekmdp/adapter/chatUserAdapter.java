package id.ac.projekmdp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import id.ac.projekmdp.R;
import id.ac.projekmdp.kelas.Dchat;
import id.ac.projekmdp.kelas.Pegawai;
import id.ac.projekmdp.kelas.User;

public class chatUserAdapter extends RecyclerView.Adapter{
    ArrayList<Dchat>datadchat=new ArrayList<>();
    private int dari;
    Context context;
    String link_foto_user;
    public chatUserAdapter(ArrayList<Dchat> datadchat, int dari, String link_foto_user,Context c) {
        this.link_foto_user=link_foto_user;
        this.datadchat = datadchat;
        this.dari=dari;
        this.context=c;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_chat_user, parent, false);
//        return new chatUserAdapter.itemViewHolder(v);
        if(dari==1){
            //halaman user
            if(viewType==1){//user
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_chat_user, parent, false);
                return new chatUserAdapter.itemViewHolder(v);
            }else {//pegawai
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_chat_user_penerima, parent, false);
                return new chatUserAdapter.penerimaViewHolder(v);
            }
        }
        else {
            //halaman pegawai
            if(viewType==1){//pegawai
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_chat_user_penerima, parent, false);
                return new chatUserAdapter.penerimaViewHolder(v);

            }else {//user
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_chat_user, parent, false);
                return new chatUserAdapter.itemViewHolder(v);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Dchat dchat = datadchat.get(position);
        //holder.txtchat.setText(dchat.getChat());
        if(holder.getClass()==itemViewHolder.class){
            itemViewHolder viewHolder=(itemViewHolder) holder;
            viewHolder.txtchat.setText(dchat.getChat());
            //viewHolder.ivprofile
            if(!link_foto_user.equals("")){
                Glide.with(context).load(link_foto_user).into(viewHolder.ivprofile);
            }
        }
        else if (holder.getClass()==penerimaViewHolder.class){
            penerimaViewHolder viewHolder=(penerimaViewHolder) holder;
            viewHolder.txtchat.setText(dchat.getChat());
        }
    }

    @Override
    public int getItemCount() {
        return datadchat.size();
    }

    @Override
    public int getItemViewType(int position) {
//        if (datadchat.get(position).getPengirim()==1){
//
//        }
        return datadchat.get(position).getPengirim();
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
    public class penerimaViewHolder extends RecyclerView.ViewHolder{
        TextView txtchat;
        ImageView ivprofile;
        public penerimaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtchat=itemView.findViewById(R.id.textView25);
            ivprofile=itemView.findViewById(R.id.imageView11);
        }
    }
}
