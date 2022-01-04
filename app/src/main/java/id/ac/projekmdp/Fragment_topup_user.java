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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.TransactionRequest;
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme;
import com.midtrans.sdk.corekit.models.BillingAddress;
import com.midtrans.sdk.corekit.models.CustomerDetails;
import com.midtrans.sdk.corekit.models.ItemDetails;
import com.midtrans.sdk.corekit.models.ShippingAddress;
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
        binding = FragmentTopupUserBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.uang.setText("IDR "+sedang_login.getSaldo());
        makePayment();
        binding.btnTopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "btnTopup" , Toast.LENGTH_LONG).show();
                clickPay();
            }
        });
    }
    private void makePayment(){
        SdkUIFlowBuilder.init()
                .setClientKey("SB-Mid-client-HVTcgsrWFrzF3AVC") // client_key is mandatory
                .setContext(getContext()) // context is mandatory
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
        String TRANSACTION_ID = "tes1halohalo";
        int TOTAL_AMOUNT = 1000;
        TransactionRequest transactionRequest = new TransactionRequest(TRANSACTION_ID, TOTAL_AMOUNT);
        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setCustomerIdentifier("budi-6789");
        customerDetails.setPhone("08123456789");
        customerDetails.setFirstName("Budi");
        customerDetails.setLastName("Utomo");
        customerDetails.setEmail("budi@utomo.com");

        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setAddress("Jalan Andalas Gang Sebelah No. 1");
        shippingAddress.setCity("Jakarta");
        shippingAddress.setPostalCode("10220");
        customerDetails.setShippingAddress(shippingAddress);

        BillingAddress billingAddress = new BillingAddress();
        billingAddress.setAddress("Jalan Andalas Gang Sebelah No. 1");
        billingAddress.setCity("Jakarta");
        billingAddress.setPostalCode("10220");
        customerDetails.setBillingAddress(billingAddress);

        transactionRequest.setCustomerDetails(customerDetails);

        String ITEM_ID_1 = "T1";
        int ITEM_PRICE_1 = 20000;
        int ITEM_QUANTITY_1 = 2;
        String ITEM_NAME_1 = "baju";
        ItemDetails itemDetails1 = new ItemDetails(ITEM_ID_1, ITEM_PRICE_1, ITEM_QUANTITY_1, ITEM_NAME_1);

// Create array list and add above item details in it and then set it to transaction request.
        ArrayList<ItemDetails> itemDetailsList = new ArrayList<>();
        itemDetailsList.add(itemDetails1);

// Set item details into the transaction request.
        transactionRequest.setItemDetails(itemDetailsList);
        MidtransSDK.getInstance().setTransactionRequest(transactionRequest);
        MidtransSDK.getInstance().startPaymentUiFlow(getContext());
    }
}