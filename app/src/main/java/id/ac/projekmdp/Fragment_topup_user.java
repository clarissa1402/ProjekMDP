package id.ac.projekmdp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
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
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.TransactionRequest;
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme;
import com.midtrans.sdk.corekit.models.snap.TransactionResult;
import com.midtrans.sdk.uikit.SdkUIFlowBuilder;

import java.util.ArrayList;
import id.ac.projekmdp.databinding.FragmentTopupUserBinding;
import id.ac.projekmdp.kelas.Pegawai;
import id.ac.projekmdp.kelas.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_topup_user#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_topup_user extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    User_page u;
    private FragmentTopupUserBinding binding;
    User sedang_login;

    public Fragment_topup_user() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_topup_user.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_topup_user newInstance(User_page u, User sedang_login) {
        Fragment_topup_user fragment = new Fragment_topup_user();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.u=u;
        fragment.sedang_login = sedang_login;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_topup_user, container, false);
        binding = FragmentTopupUserBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.uang.setText("IDR "+sedang_login.getSaldo());
    }
    private void makePayment(){
        SdkUIFlowBuilder.init()
                .setClientKey("SB-Mid-client-HVTcgsrWFrzF3AVC") // client_key is mandatory
                .setContext(u.getBaseContext()) // context is mandatory
                .setTransactionFinishedCallback(new TransactionFinishedCallback() {
                    @Override
                    public void onTransactionFinished(TransactionResult result) {
                        // Handle finished transaction here.
                    }
                }) // set transaction finish callback (sdk callback)
                .setMerchantBaseUrl("https://babowemidtrans.herokuapp.com/index.php/") //set merchant url (required)
                .enableLog(true) // enable sdk log (optional)
                .setColorTheme(new CustomColorTheme("#FFE51255", "#B61548", "#FFE51255")) // set theme. it will replace theme on snap theme on MAP ( optional)
                .buildSDK();
    }
    private void clickPay(){
        String TRANSACTION_ID;
        int TOTAL_AMOUNT;
        //TransactionRequest transactionRequest = new TransactionRequest(TRANSACTION_ID, TOTAL_AMOUNT);
        MidtransSDK.getInstance().startPaymentUiFlow(u.getBaseContext() );
    }
}