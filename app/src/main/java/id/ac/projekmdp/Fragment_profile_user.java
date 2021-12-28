package id.ac.projekmdp;

import android.media.Image;
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

import org.w3c.dom.Text;

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
    public static Fragment_profile_user newInstance(User_page u) {
        Fragment_profile_user fragment = new Fragment_profile_user();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.u=u;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
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
        edtnama=view.findViewById(R.id.editTextTextPersonName);
        edtalamat=view.findViewById(R.id.editTextTextPersonName2);
        edttelepon=view.findViewById(R.id.editTextTextPersonName3);
        edtpassword=view.findViewById(R.id.editTextTextPersonName4);
        btnedit=view.findViewById(R.id.button4);
        btnsave=view.findViewById(R.id.button5);

        txtemail.setText(u.sedang_login.getEmail());
        //txtjeniskelamin.setText(u.sedang_login.);
        edtnama.setText(u.sedang_login.getNama());
        edtalamat.setText(u.sedang_login.getAlamat());
        edttelepon.setText(u.sedang_login.getTelepon());
        edtpassword.setText(u.sedang_login.getPassword());
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
                            Toast.makeText(getContext(), "Update Success", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), "Update Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}