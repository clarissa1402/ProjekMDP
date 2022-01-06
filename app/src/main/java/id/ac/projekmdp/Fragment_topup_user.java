package id.ac.projekmdp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.TransactionRequest;
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme;
import com.midtrans.sdk.corekit.models.BankType;
import com.midtrans.sdk.corekit.models.BillingAddress;
import com.midtrans.sdk.corekit.models.CustomerDetails;
import com.midtrans.sdk.corekit.models.ItemDetails;
import com.midtrans.sdk.corekit.models.ShippingAddress;
import com.midtrans.sdk.corekit.models.snap.CreditCard;
import com.midtrans.sdk.corekit.models.snap.TransactionResult;
import com.midtrans.sdk.uikit.SdkUIFlowBuilder;

import java.util.ArrayList;
import id.ac.projekmdp.databinding.FragmentTopupUserBinding;
import id.ac.projekmdp.kelas.Pegawai;
import id.ac.projekmdp.kelas.Topup;
import id.ac.projekmdp.kelas.Transaksi;
import id.ac.projekmdp.kelas.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_topup_user#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_topup_user extends Fragment implements TransactionFinishedCallback{

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
    DatabaseReference root;
    ArrayList<Transaksi>dataTransaksi = new ArrayList<>();
    ArrayList<Topup>topupList = new ArrayList<>();
    int TotalTopup;

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
    public static Fragment_topup_user newInstance(User_page u, User sedang_login, ArrayList<Transaksi>dataTransaksi) {
        Fragment_topup_user fragment = new Fragment_topup_user();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.u=u;
        fragment.sedang_login = sedang_login;
        fragment.dataTransaksi = dataTransaksi;
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
        binding = FragmentTopupUserBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    Context context;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        root= FirebaseDatabase.getInstance().getReference();
        load_data();
        binding.uang.setText("IDR "+sedang_login.getSaldo());
        binding.btnTopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = view.getContext();
                if(Integer.parseInt(binding.etUang.getText().toString())<50000 ){
                    Toast.makeText(context,"Top up minim IDR 50.000",Toast.LENGTH_SHORT).show();
                }
                else{
                    root.child("Topup").push().setValue(new Topup(topupList.size()+1, Integer.parseInt(binding.etUang.getText().toString()))).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(context,"Top Up",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();
                        }
                    });
                    try {
                        TotalTopup = Integer.parseInt(binding.etUang.getText().toString());
                    }catch (Exception e){
                        Toast.makeText(context, e+"", Toast.LENGTH_SHORT).show();
                    }
                    clickPay();
                }
                //Toast.makeText(view.getContext(), "btnTopup" , Toast.LENGTH_LONG).show();
            }
        });
    }
    String Ckey = "SB-Mid-client-HVTcgsrWFrzF3AVC";
    private void clickPay(){
        SdkUIFlowBuilder.init()
                .setClientKey(Ckey) // client_key is mandatory
                .setContext(context) // context is mandatory
                .setTransactionFinishedCallback(new TransactionFinishedCallback() {
                    @Override
                    public void onTransactionFinished(TransactionResult result) {
                        // Handle finished transaction here.
                        updateSaldo();
                        binding.etUang.setText("");
                    }
                }) // set transaction finish callback (sdk callback)
                .setMerchantBaseUrl("https://babowemidtrans.herokuapp.com/index.php/") //set merchant url (required)
                .enableLog(true) // enable sdk log (optional)
                .setColorTheme(new CustomColorTheme("#FFE51255", "#B61548", "#FFE51255")) // set theme. it will replace theme on snap theme on MAP ( optional)
                .setLanguage("id") //`en` for English and `id` for Bahasa
                .buildSDK();

        String TRANSACTION_ID = (topupList.size()+1)+"Topup";
        int TOTAL_AMOUNT = Integer.parseInt(binding.etUang.getText().toString());
        TransactionRequest transactionRequest = new TransactionRequest(TRANSACTION_ID, TOTAL_AMOUNT);
        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setPhone(sedang_login.getTelepon().toString());
        customerDetails.setFirstName(sedang_login.getNama().toString());

        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setAddress(sedang_login.getAlamat().toString());
        customerDetails.setShippingAddress(shippingAddress);

        BillingAddress billingAddress = new BillingAddress();
        billingAddress.setAddress(sedang_login.getAlamat().toString());
        customerDetails.setBillingAddress(billingAddress);

        transactionRequest.setCustomerDetails(customerDetails);

        String ITEM_ID_1 = "T1";
        int ITEM_PRICE_1 = Integer.parseInt(binding.etUang.getText().toString());
        int ITEM_QUANTITY_1 = 1;
        String ITEM_NAME_1 = "Top up";
        ItemDetails itemDetails1 = new ItemDetails(ITEM_ID_1, ITEM_PRICE_1, ITEM_QUANTITY_1, ITEM_NAME_1);

// Create array list and add above item details in it and then set it to transaction request.
        ArrayList<ItemDetails> itemDetailsList = new ArrayList<>();
        itemDetailsList.add(itemDetails1);

// Set item details into the transaction request.
        transactionRequest.setItemDetails(itemDetailsList);
        CreditCard creditCardOptions = new CreditCard();
// Set to true if you want to save card to Snap
        creditCardOptions.setSaveCard(false);
// Set bank name when using MIGS channel
        creditCardOptions.setBank(BankType.BCA);
// Set MIGS channel (ONLY for BCA, BRI and Maybank Acquiring bank)
        creditCardOptions.setChannel(CreditCard.MIGS);
// Set Credit Card Options
        transactionRequest.setCreditCard(creditCardOptions);
// Set transaction request into SDK instance
        MidtransSDK.getInstance().setTransactionRequest(transactionRequest);
//...
        MidtransSDK.getInstance().startPaymentUiFlow(context);
    }

    public void load_data() {
        root.child("Topup").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    topupList.add(new Topup(
                            Integer.parseInt(String.valueOf(dataSnapshot.child("id").getValue())),
                            Integer.parseInt(String.valueOf(dataSnapshot.child("saldo").getValue()))
                    ));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, error + "", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onTransactionFinished(TransactionResult result) {
        if (result.getResponse() != null) {
            switch (result.getStatus()) {
                case TransactionResult.STATUS_SUCCESS:
                    Toast.makeText(context, "Transaction Finished. ID: " + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
                    updateSaldo();
                    break;
                case TransactionResult.STATUS_PENDING:
                    Toast.makeText(context, "Transaction Pending. ID: " + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
                    break;
                case TransactionResult.STATUS_FAILED:
                    Toast.makeText(context, "Transaction Failed. ID: " + result.getResponse().getTransactionId() + ". Message: " + result.getResponse().getStatusMessage(), Toast.LENGTH_LONG).show();
                    break;
            }
            result.getResponse().getValidationMessages();
        } else if (result.isTransactionCanceled()) {
            Toast.makeText(context, "Transaction Canceled", Toast.LENGTH_LONG).show();
        } else {
            if (result.getStatus().equalsIgnoreCase(TransactionResult.STATUS_INVALID)) {
                Toast.makeText(context, "Transaction Invalid", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Transaction Finished with failure.", Toast.LENGTH_LONG).show();
            }
        }
    }
    void updateSaldo(){
        int temp = Integer.parseInt(binding.uang.getText().toString().substring(4));
        int total = temp + TotalTopup;
        binding.uang.setText("IDR "+total);
        root.child("Users").orderByChild("id").equalTo(sedang_login.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                    String key = childSnapshot.getKey();
                    root.child("Users").child(key).child("saldo").setValue(total);
                    //Toast.makeText(getContext(),String.valueOf(childSnapshot.child("id").getValue()) , Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getContext(), "Topup Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Topup Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}