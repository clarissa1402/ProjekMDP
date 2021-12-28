package id.ac.projekmdp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtnama.isEnabled()){
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

    }
    public void load_data(){
        root= FirebaseDatabase.getInstance().getReference();
        root.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    datauser.add(new User(Integer.parseInt(String.valueOf(dataSnapshot.child("id").getValue())),  String.valueOf(dataSnapshot.child("email").getValue()),String.valueOf(dataSnapshot.child("nama").getValue()),String.valueOf(dataSnapshot.child("telepon").getValue()),String.valueOf(dataSnapshot.child("alamat").getValue()),String.valueOf(dataSnapshot.child("password").getValue())));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        for (int i = 0; i < datauser.size(); i++) {
            if(datauser.get(i).getId()==u.id){
                txtemail.setText(datauser.get(i).getEmail());
//        //txtjeniskelamin.setText(u.sedang_login.);
                edtnama.setText(datauser.get(i).getNama());
                edtalamat.setText(datauser.get(i).getAlamat());
                edttelepon.setText(datauser.get(i).getTelepon());
                edtpassword.setText(datauser.get(i).getPassword());
            }
        }
    }
}