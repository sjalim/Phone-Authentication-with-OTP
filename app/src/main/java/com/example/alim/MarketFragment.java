package com.example.alim;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MarketFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MarketFragment extends Fragment {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  private FloatingActionButton floatingAddNewItemButton;

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  RecyclerView marketRecyleView;
  ListAdapter_Market marketListAdapter;
  ArrayList<CustomListItem_Market> mData;
  ConstraintLayout rootLayout;
  EditText searchInput;
  CharSequence search = "";


  private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
  private CollectionReference productsOfMarketCollectionRef = firebaseFirestore.collection("products_of_market");




  public MarketFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment MarketFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static MarketFragment newInstance(String param1, String param2) {
    MarketFragment fragment = new MarketFragment();
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
  productsListOfMarketFirestore[] item = new productsListOfMarketFirestore[100];
  int indx =0;
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View contentView =inflater.inflate(R.layout.fragment_market, container, false);

    floatingAddNewItemButton = contentView.findViewById(R.id.floatingActionButton);
    marketRecyleView = contentView.findViewById(R.id.market_rv);
    rootLayout = contentView.findViewById(R.id.root_layout);
    searchInput = contentView.findViewById(R.id.search_input);
    mData = new ArrayList<>();


    searchInput.setBackgroundResource(R.drawable.search_input_style);
    rootLayout.setBackgroundColor(getResources().getColor(R.color.white));


    setUpRecyclerView();
   mData =  marketListAdapter.getmData();
//      filter();
      Log.d("checked","i am here");


    searchInput.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

        search = s;
//        Log.d("checked","search :"+search);
//          setUpRecyclerView(search.toString());


      }

      @Override
      public void afterTextChanged(Editable s) {

      }



    });

      floatingAddNewItemButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Fragment fragment = new AddNewItemFragment();
          FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
          FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

          fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
          fragmentTransaction.replace(R.id.container, fragment);
          fragmentTransaction.addToBackStack(null);
          fragmentTransaction.commit();

        }
      });


    return contentView;
  }

    private void setUpRecyclerView() {

        Query query = productsOfMarketCollectionRef.orderBy("productTitle",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<productsListOfMarketFirestore> options = new FirestoreRecyclerOptions.Builder<productsListOfMarketFirestore>()
                .setQuery(query,productsListOfMarketFirestore.class)
                .build();
        marketListAdapter = new ListAdapter_Market(options);
        marketRecyleView.setHasFixedSize(true);
        marketRecyleView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        marketRecyleView.setAdapter(marketListAdapter);

    }

//    private void setUpRecyclerView(String srch) {
//
//        Query query = productsOfMarketCollectionRef.orderBy("productTitle",Query.Direction.ASCENDING).whereEqualTo("productTitle",srch);
//        FirestoreRecyclerOptions<productsListOfMarketFirestore> options = new FirestoreRecyclerOptions.Builder<productsListOfMarketFirestore>()
//                .setQuery(query,productsListOfMarketFirestore.class)
//                .build();
//        marketListAdapter = new ListAdapter_Market(options);
//        marketRecyleView.setHasFixedSize(true);
//        marketRecyleView.setLayoutManager(new LinearLayoutManager(this.getContext()));
//        marketRecyleView.setAdapter(marketListAdapter);
//
//    }

    private void filter()
    {
        List<CustomListItem_Market> lstFiltered = new ArrayList<>();
        for (CustomListItem_Market row : mData) {

            Log.d("checked",row.getTitle() +" "+row.getFeatured_photos()+ " "+row.getPrice());

        }

    }


    @Override
    public void onStart() {
        super.onStart();
        marketListAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        marketListAdapter.stopListening();
    }
}
