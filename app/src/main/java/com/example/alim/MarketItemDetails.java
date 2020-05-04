package com.example.alim;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;


public class MarketItemDetails extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ViewPager viewPager;
    private TextView textViewProductTitle, textViewProductPrice, textViewProductLoctaion, textViewProductConditionType, textViewProductCategory, textViewProductTime;
    private TextView textViewOwnerNumber, textViewProductDescription, TextViewProductConditionTypeLebel;
    private Context mContext;

    ViewPagerImageAdapterMarketDetails viewPagerImageAdapter;


    public MarketItemDetails() {
        // Required empty public constructor
    }

    public static MarketItemDetails newInstance(String param1, String param2) {
        MarketItemDetails fragment = new MarketItemDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contextView = inflater.inflate(R.layout.fragment_market_item_details, container, false);
        mContext = contextView.getContext();

        final String productRegion;
        final String productArea;
        final String productCategory;
        final String productTitle;
        final String productDescription;
        final String productPrice;
        final String productCondition;


        textViewOwnerNumber = contextView.findViewById(R.id.textView_number);
        textViewProductCategory = contextView.findViewById(R.id.textView_productCategory);
        textViewProductConditionType = contextView.findViewById(R.id.textView_productConditonType);
        textViewProductDescription = contextView.findViewById(R.id.textView_productDescripation);
        textViewProductLoctaion = contextView.findViewById(R.id.textView_productLocation);
        textViewProductPrice = contextView.findViewById(R.id.textView_productPrice);
        textViewProductTime = contextView.findViewById(R.id.textView_productTime);
        textViewProductTitle = contextView.findViewById(R.id.textView_productitle);
        TextViewProductConditionTypeLebel = contextView.findViewById(R.id.textView_condition_type_lebel);
        viewPager = contextView.findViewById(R.id.viewPage_productsImage);

        if (!textViewProductCategory.getText().equals("Instrument")) {
            textViewProductConditionType.setText("Type:");
        } else {
            textViewProductConditionType.setText("Condition:");
        }


        final String productId = getArguments().getString("productId");
        DocumentReference productCollectionRef = FirebaseFirestore.getInstance().document("products_of_market/" + productId);


        productCollectionRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                productsListOfMarketFirestore productsDetails = documentSnapshot.toObject(productsListOfMarketFirestore.class);


                String[] images = new String[]{
                   "user_image1/"+productId+".jpg","user_image2/"+productId+".jpg"
                };


                viewPagerImageAdapter = new ViewPagerImageAdapterMarketDetails(mContext, images);
                viewPager.setAdapter(viewPagerImageAdapter);


//                textViewOwnerNumber.setText(documentSnapshot.getString());
                textViewProductCategory.setText(documentSnapshot.getString("productCategory"));
                textViewProductConditionType.setText(documentSnapshot.getString("productCondition"));
                textViewProductDescription.setText(documentSnapshot.getString("productDescription"));
                textViewProductLoctaion.setText(documentSnapshot.getString("productArea") + ", " + documentSnapshot.getString("productRegion"));

                textViewProductPrice.setText("৳" + documentSnapshot.getString("productPrice"));
//                textViewProductTime.setText(documentSnapshot.getString());
                textViewProductTitle.setText(documentSnapshot.getString("productTitle"));


//                Log.d("checked", productsDetails.getProductTitle());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


        return contextView;
    }

}
