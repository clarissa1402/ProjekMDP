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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import id.ac.projekmdp.kelas.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_profile_user#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_profile_user extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText edtnama,edtalamat,edttelepon,edtpassword;
    TextView txtemail,txtjeniskelamin;
    Button btnedit,btnsave;
    ImageView img;
    User_page u;
    DatabaseReference root;
    ArrayList<User>datauser=new ArrayList<>();
    // TODO: Rename and change types of parameters

    public Fragment_profile_user() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Fragment_profile_user.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_profile_user newInstance(User_page u, ArrayList<User>datauser) {
        Fragment_profile_user fragment = new Fragment_profile_user();
        Bundle args = new Bundle();
        fragment.u=u;
        fragment.setArguments(args);
        args.putParcelableArrayList(ARG_PARAM1, datauser);
//        args.putString(ARG_PARAM2, param2);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            datauser = getArguments().getParcelableArrayList(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        root= FirebaseDatabase.getInstance().getReference();
        txtemail=view.findViewById(R.id.textView8);
        txtjeniskelamin=view.findViewById(R.id.textView9);
        img=view.findViewById(R.id.imageView5);
        edtnama=view.findViewById(R.id.etNama1);
        edtalamat=view.findViewById(R.id.etAlamat1);
        edttelepon=view.findViewById(R.id.etTelp1);
        edtpassword=view.findViewById(R.id.etPass1);
        btnedit=view.findViewById(R.id.button4);
        btnsave=view.findViewById(R.id.button5);
        load_data();
        //System.out.println(u.id+"");
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtnama.isEnabled()){
                    //load_data();
                    edtnama.setEnabled(true);
                    edtalamat.setEnabled(true);
                    edttelepon.setEnabled(true);
                    edtpassword.setEnabled(true);
                }
            }
        });
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.child("Users").orderByChild("id").equalTo(u.id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                            String key = childSnapshot.getKey();
                            root.child("Users").child(key).child("nama").setValue(edtnama.getText().toString());
                            root.child("Users").child(key).child("alamat").setValue(edtalamat.getText().toString());
                            root.child("Users").child(key).child("telepon").setValue(edttelepon.getText().toString());
                            root.child("Users").child(key).child("password").setValue(edtpassword.getText().toString());
                            //Toast.makeText(getContext(),String.valueOf(childSnapshot.child("id").getValue()) , Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(getContext(), "Update Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), "Update Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_image();
                upload();
            }
        });
    }

    public void upload(){
        img.setDrawingCacheEnabled(true);
        img.buildDrawingCache();
        Bitmap bitmap=((BitmapDrawable) img.getDrawable()).getBitmap();
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] data=baos.toByteArray();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference("images").child("U"+u.id+".jpeg");
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

    void saveData(String link){

        root.child("Users").orderByChild("id").equalTo(u.id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                    String key = childSnapshot.getKey();
//                    root.child("Users").child(key).child("nama").setValue(edtnama.getText().toString());
//                    root.child("Users").child(key).child("alamat").setValue(edtalamat.getText().toString());
//                    root.child("Users").child(key).child("telepon").setValue(edttelepon.getText().toString());
//                    root.child("Users").child(key).child("password").setValue(edtpassword.getText().toString());
                    root.child("Users").child(key).child("url").setValue(link);
                    //Toast.makeText(getContext(),String.valueOf(childSnapshot.child("id").getValue()) , Toast.LENGTH_SHORT).show();
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
        if(requestCode==20&&data!=null&&resultCode==Activity.RESULT_OK){
            final Uri path=data.getData();
            Thread thread=new Thread(()->{
                try {
                    InputStream inputStream= getActivity().getContentResolver().openInputStream(path);
                    Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                    img.post(()->{
                       img.setImageBitmap(bitmap);
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
               img.post(()->{
                   img.setImageBitmap(bitmap);
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
    public void load_data(){
//        root= FirebaseDatabase.getInstance().getReference();
        root.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    datauser.add(new User(Integer.parseInt(String.valueOf(dataSnapshot.child("id").getValue())),  String.valueOf(dataSnapshot.child("email").getValue()),String.valueOf(dataSnapshot.child("nama").getValue()),String.valueOf(dataSnapshot.child("telepon").getValue()),String.valueOf(dataSnapshot.child("alamat").getValue()),String.valueOf(dataSnapshot.child("password").getValue()),String.valueOf(dataSnapshot.child("jenis_kelamin").getValue())));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        for (int i = 0; i < datauser.size(); i++) {
            if(datauser.get(i).getId()==u.id){
                txtemail.setText(datauser.get(i).getEmail());
                txtjeniskelamin.setText(datauser.get(i).getJenis_kelamin());
                edtnama.setText(datauser.get(i).getNama());
                edtalamat.setText(datauser.get(i).getAlamat());
                edttelepon.setText(datauser.get(i).getTelepon());
                edtpassword.setText(datauser.get(i).getPassword());
            }
        }
//            txtemail.setText(u.sedang_login.getEmail());
//            txtjeniskelamin.setText(u.sedang_login.getJenis_kelamin());
//            edtnama.setText(u.sedang_login.getNama());
//            edtalamat.setText(u.sedang_login.getAlamat());
//            edttelepon.setText(u.sedang_login.getTelepon());
//            edtpassword.setText(u.sedang_login.getPassword());
    }
}