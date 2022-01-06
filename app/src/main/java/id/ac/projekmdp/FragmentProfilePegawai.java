package id.ac.projekmdp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import id.ac.projekmdp.databinding.FragmentProfilePegawaiBinding;
import id.ac.projekmdp.kelas.Pegawai;
import id.ac.projekmdp.kelas.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProfilePegawai#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProfilePegawai extends Fragment {


    FragmentProfilePegawaiBinding binding;
    DatabaseReference root;
    Pegawai pegawai;
    ArrayList<Pegawai> datapegawai = new ArrayList<>();
    public FragmentProfilePegawai() {
        // Required empty public constructor
    }

    public static FragmentProfilePegawai newInstance(Pegawai pegawai) {
        FragmentProfilePegawai fragment = new FragmentProfilePegawai();
        Bundle args = new Bundle();
        fragment.pegawai = pegawai;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfilePegawaiBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        root = FirebaseDatabase.getInstance().getReference();

        binding.tvNikPegProfile.setText(pegawai.getNik()+"");
        binding.tvEmailPegProfile.setText(pegawai.getEmail());
        binding.tvSaldoPegProfile.setText("Saldo : "+pegawai.getSaldo()+"");
        binding.textView28.setText(pegawai.getJasa());
        binding.etHargaPegProfile.setText(pegawai.getHarga()+"");
        binding.etDeskripsiPegProfile.setText(pegawai.getDeskripsi());
        binding.etAlamatPegProfile.setText(pegawai.getAlamat());
        binding.etTelpPegProfile.setText(pegawai.getTelepon());
        binding.etNama.setText(pegawai.getNama());
        binding.etPasswordProfilePeg.setText(pegawai.getPassword());
        if(pegawai.getUrl().equals("")){

        }else{
            Glide.with(getContext()).load(pegawai.getUrl()).into(binding.imageView13);
        }
        binding.etAlamatPegProfile.setEnabled(false);
        binding.etDeskripsiPegProfile.setEnabled(false);
        binding.etNama.setEnabled(false);
        binding.etTelpPegProfile.setEnabled(false);
        binding.etPasswordProfilePeg.setEnabled(false);
        binding.etHargaPegProfile.setEnabled(false);
        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!binding.etNama.isEnabled()){
                    binding.etAlamatPegProfile.setEnabled(true);
                    binding.etDeskripsiPegProfile.setEnabled(true);
                    binding.etNama.setEnabled(true);
                    binding.etTelpPegProfile.setEnabled(true);
                    binding.etPasswordProfilePeg.setEnabled(true);
                    binding.etHargaPegProfile.setEnabled(true);
                }
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.child("Pegawai").orderByChild("nik").equalTo(pegawai.getNik()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot childSnapshot: snapshot.getChildren()){
                            String key = childSnapshot.getKey();
                            root.child("Pegawai").child(key).child("nama").setValue(binding.etNama.getText().toString());
                            root.child("Pegawai").child(key).child("telepon").setValue(binding.etTelpPegProfile.getText().toString());
                            root.child("Pegawai").child(key).child("alamat").setValue(binding.etAlamatPegProfile.getText().toString());
                            root.child("Pegawai").child(key).child("deskripsi").setValue(binding.etDeskripsiPegProfile.getText().toString());
                            root.child("Pegawai").child(key).child("harga").setValue(Integer.parseInt(binding.etHargaPegProfile.getText().toString()));
                            root.child("Pegawai").child(key).child("password").setValue(binding.etPasswordProfilePeg.getText().toString());
                        }
                        Toast.makeText(getContext(),"Edited",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                upload();
            }
        });
        binding.imageView13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_image();
            }
        });
    }
    public void upload(){
        binding.imageView13.setDrawingCacheEnabled(true);
        binding.imageView13.buildDrawingCache();
        Bitmap bitmap=((BitmapDrawable) binding.imageView13.getDrawable()).getBitmap();
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] data=baos.toByteArray();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference("images").child("P"+pegawai.getNik()+".jpeg");
        UploadTask uploadTask=reference.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if(taskSnapshot.getMetadata().getReference()!=null){
                    if (taskSnapshot.getMetadata().getReference()!=null){
                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if(task.getResult()!=null){
                                    saveData(task.getResult().toString());
                                    //saveData("U"+u.id+".jpeg");
                                    Toast.makeText(getActivity(), "Berhasil", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(getActivity(), "Gagal", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else {
                        Toast.makeText(getActivity(), "Gagal", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getActivity(), "Gagal", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void saveData(String link_url){

        root.child("Pegawai").orderByChild("nik").equalTo(pegawai.getNik()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                    String key = childSnapshot.getKey();
                    root.child("Pegawai").child(key).child("url").setValue(link_url);
                }
                Toast.makeText(getContext(), "Update Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Update Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==20&&data!=null&&resultCode== Activity.RESULT_OK){
            final Uri path=data.getData();
            Thread thread=new Thread(()->{
                try {
                    InputStream inputStream= getActivity().getContentResolver().openInputStream(path);
                    Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                    binding.imageView13.post(()->{
                        binding.imageView13.setImageBitmap(bitmap);
                    });
                }catch (IOException e){
                    e.printStackTrace();
                }
            });
            thread.start();
        }
        if(requestCode==10 && resultCode== Activity.RESULT_OK){
            final Bundle extras=data.getExtras();
            Thread thread=new Thread(()->{
                Bitmap bitmap=(Bitmap) extras.get("data");
                binding.imageView13.post(()->{
                    binding.imageView13.setImageBitmap(bitmap);
                });

            });
            thread.start();
        }
    }


    void select_image(){
        final CharSequence[] items={"Take Photo","Choose Photo From Library","Cancel"};
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.app_name));
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setItems(items,((dialogInterface, i) -> {
            if(items[i].equals("Take Photo")){
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,10);
            }
            else if(items[i].equals("Choose Photo From Library")){
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Select Image"),20);
            }
            else if(items[i].equals("Cancel")){
                dialogInterface.dismiss();
            }
        }));
        builder.show();
    }
}