package id.ac.projekmdp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import id.ac.projekmdp.databinding.RvlistpegawaiBinding;
import id.ac.projekmdp.databinding.RvlisttransaksiBinding;
import id.ac.projekmdp.kelas.Pegawai;
import id.ac.projekmdp.kelas.Transaksi;
import id.ac.projekmdp.kelas.User;

public class transaksiAdminAdapter extends RecyclerView.Adapter<transaksiAdminAdapter.itemViewHolder> {

    private ArrayList<User> arrUser = new ArrayList<>();
    private ArrayList<Pegawai> arrPegawai = new ArrayList<>();
    private ArrayList<Transaksi> arrTransaksi = new ArrayList<>();
    private ArrayList<Transaksi> arrTransaksiAll = new ArrayList<>();
    private DatabaseReference root;
    int nik_peg = -1;

    public transaksiAdminAdapter(ArrayList<Pegawai> arrPegawai,ArrayList<User> arrUser,ArrayList<Transaksi> arrTransaksiAll, String selectedStatus, String searchText, String startDate, String endDate) {
        this.arrTransaksiAll = arrTransaksiAll;
        this.arrUser = arrUser;
        this.arrPegawai = arrPegawai;

        //FILTER & SEARCH
        for(int i = arrTransaksiAll.size()-1; i >= 0; i--){
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
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvlisttransaksiBinding binding = RvlisttransaksiBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new itemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {
        Transaksi transaksi = arrTransaksi.get(position);
        holder.bind(transaksi);
    }

    @Override
    public int getItemCount() {
        return arrTransaksi.size();
    }

    public class itemViewHolder extends RecyclerView.ViewHolder {
        private final RvlisttransaksiBinding binding;
        public itemViewHolder(@NonNull RvlisttransaksiBinding rvlisttransaksiBinding) {
            super(rvlisttransaksiBinding.getRoot());
            this.binding = rvlisttransaksiBinding;
        }
        void bind(Transaksi transaksi){
            DecimalFormat formatHarga = (DecimalFormat) DecimalFormat.getCurrencyInstance();
            DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
            formatRp.setCurrencySymbol("Rp");
            formatRp.setMonetaryDecimalSeparator(',');
            formatRp.setGroupingSeparator('.');
            formatHarga.setDecimalFormatSymbols(formatRp);
            binding.tvHargaTrans.setText(""+formatHarga.format(new Double(transaksi.getHarga())));
            binding.tvTanggalTrans.setText(transaksi.getTanggal()+"");
            for(int i=0; i<arrPegawai.size(); i++){
                if(arrPegawai.get(i).getNik() == transaksi.getNikPegawai()){
                    binding.tvJasaTrans.setText(arrPegawai.get(i).getJasa());
                    binding.tvNamaPegTrans.setText(arrPegawai.get(i).getNama());
                    nik_peg=arrPegawai.get(i).getNik();
                }
            }
            for(int i=0; i<arrUser.size(); i++){
                if(arrUser.get(i).getId() == transaksi.getIdUser()){
                    binding.tvNamaPelanggan.setText(arrUser.get(i).getNama());
                }
            }
            if(transaksi.getStatus() == 0){
                binding.tvStatus.setText("Declined");
            }
            else if(transaksi.getStatus() == 1){
                binding.tvStatus.setText("Ongoing");
            }
            else if(transaksi.getStatus() == 2){
                binding.tvStatus.setText("Pending");
            }
            else if(transaksi.getStatus() == 3){
                binding.tvStatus.setText("Finished");
            }
        }
    }
}