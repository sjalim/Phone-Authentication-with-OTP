package com.example.alim;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddNewItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewItemFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Spinner spinnerRegion, spinnerArea, spinnerCatagory;
    RadioButton radioButton;
    RadioGroup radioGroup;
    EditText editTextTitle, editTextDescription, editTextPrice;
    Button buttonSubmit, buttonPreview;
    String region[], dhaka[], sylhet[],category[];
    int pos;
    String userId;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private Bitmap compressed1,compressed2;
    private Uri imageUri1 = null,imageUri = null;
    private ProgressBar progressBar;
    private ImageView imageView1,imageView2;
    private StorageReference storageReference;
    private DocumentReference databaseReferenceId;
    boolean ch= false ,ch1 =false;

    int IMAGE_PICKER_SELECT = 0;
    int calledChoose ;

    int productId ;

    private String productIdString;





    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddNewItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddNewItemFragment newInstance(String param1, String param2) {
        AddNewItemFragment fragment = new AddNewItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AddNewItemFragment() {
        // Required empty public constructor
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
        // Inflate the layout for this fragment

        final View contentView = inflater.inflate(R.layout.fragment_add_new_item, container, false);

        spinnerArea = contentView.findViewById(R.id.spinner_area);
        spinnerRegion = contentView.findViewById(R.id.spinner_region);
        spinnerCatagory = contentView.findViewById(R.id.spinner_category);
        radioGroup = contentView.findViewById(R.id.radio_group);

        buttonPreview = contentView.findViewById(R.id.button_preview);
        buttonSubmit = contentView.findViewById(R.id.button_submit);
        imageView1 = contentView.findViewById(R.id.imageView_demopic1);
        imageView2 = contentView.findViewById(R.id.imageView_demopic2);

        editTextTitle = contentView.findViewById(R.id.editText_title);
        editTextPrice = contentView.findViewById(R.id.editText_price);
        editTextDescription  = contentView.findViewById(R.id.editText_description);


        firebaseAuth = FirebaseAuth.getInstance();

        userId = firebaseAuth.getCurrentUser().getUid();

        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();


        databaseReferenceId = firebaseFirestore.document("productId/currectId");

        databaseReferenceId.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                productId = Integer.parseInt(documentSnapshot.getString("id"));

                productIdString =  Integer.toString(productId);
                Log.d("checked",productIdString);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("checked","Id collection open failed");
            }
        });


        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        {
                            if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                                Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_LONG).show();
                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                            } else {
                                choseImage();
                                calledChoose =1;
                            }
                        } else {
                            choseImage();

                            calledChoose =1;
                        }

            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                    } else {
                        choseImage();

                        calledChoose =2;
                    }
                } else {
                    choseImage();

                    calledChoose =2;
                }

            }
        });



        region = getResources().getStringArray(R.array.region);
        dhaka = getResources().getStringArray(R.array.Dhaka_area);
        sylhet = getResources().getStringArray(R.array.sylhet_area);
        category = getResources().getStringArray(R.array.category);


        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, region);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerRegion.setAdapter(adapter);

        spinnerRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),region[position], Toast.LENGTH_SHORT);
                pos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        if(pos ==0)
        {
            ArrayAdapter adapter1 = new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,dhaka);

            adapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spinnerArea.setAdapter(adapter1);

        }
        else
        {
            ArrayAdapter adapter1 = new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,sylhet);

            adapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spinnerArea.setAdapter(adapter1);


        }

        ArrayAdapter adapter1 = new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,category);

        adapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerCatagory.setAdapter(adapter1);


        buttonSubmit.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick (View v){
                    final String productRegion = spinnerRegion.getSelectedItem().toString();
                    final String productArea = spinnerArea.getSelectedItem().toString();
                    final String productCategory = spinnerCatagory.getSelectedItem().toString();
                    final String productTitle = editTextTitle.getText().toString();
                    final String productDescription = editTextDescription.getText().toString();
                    final String productPrice = editTextPrice.getText().toString();
                    int seleted = radioGroup.getCheckedRadioButtonId();
                    radioButton = contentView.findViewById(seleted);
                   final String productCondition  =  radioButton.getText().toString();


                    if(!TextUtils.isEmpty(productArea) && !TextUtils.isEmpty(productRegion)&& !TextUtils.isEmpty(productCategory)&& !TextUtils.isEmpty(productCondition) && !TextUtils.isEmpty(productDescription)&&!TextUtils.isEmpty(productPrice) &&!TextUtils.isEmpty(productTitle))
                    {
                        File imageFile1 = new File(imageUri.getPath());
                        File imageFile2 = new File(imageUri1.getPath());

                        Log.d("checked","ok at compress");
                        try {
                            compressed1 = new Compressor(getContext())
                                    .setMaxHeight(140)
                                    .setMaxWidth(140)
                                    .setQuality(50)
                                    .compressToBitmap(imageFile1);

                            compressed2 = new Compressor(getContext())
                                    .setMaxHeight(140)
                                    .setMaxWidth(140)
                                    .setQuality(50)
                                    .compressToBitmap(imageFile2);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    else
                    {
                        Toast.makeText(getContext(),"Input Invalid",Toast.LENGTH_SHORT);
                        Log.d("error","Add new item invalid");

                    }

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    compressed1.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] thumbData1 = byteArrayOutputStream.toByteArray();

                    UploadTask image_path = storageReference.child("user_image1").child(productIdString+".jpg").putBytes(thumbData1);
                    image_path.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(getContext(),"pic 1 is selected",Toast.LENGTH_SHORT);
                                Log.d("checked","image1 done");




                                ch = true;

                            }
                            else
                            {
                                Toast.makeText(getContext(),"problem with pic 1",Toast.LENGTH_SHORT);
                                Log.d("error","error with first pic upload");

                            }
                        }
                    });
                    ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
                    compressed2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream1);
                    byte[] thumbData2 = byteArrayOutputStream1.toByteArray();

                    UploadTask image_path2 = storageReference.child("user_image2").child(productIdString+".jpg").putBytes(thumbData2);
                    image_path2.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(getContext(),"pic 2 is selected",Toast.LENGTH_SHORT).show();
                                ch1 = true;
                                Log.d("checked","image2 done");
                                storeData(productRegion , productArea, productCategory ,productTitle,productDescription,productPrice,productCondition);

                            }
                            else
                            {
                                Toast.makeText(getContext(),"problem with pic 2",Toast.LENGTH_SHORT).show();
                                Log.d("error","error with second pic upload");
                            }
                        }
                    });
//                    if(ch && ch1)
//                    {
//                        Log.d("checked","store called");
//                        storeData(productRegion,productArea,productCategory,productTitle,productDescription,productPrice,productCondition);
//                    }
//                    else
//                    {
//                        Log.d("checked","error store called " +ch +" "+ch1);
//
//                    }


                }
            });


        return contentView;
        }

    private void storeData(String productRegion, String productArea, String productCategory, String productTitle, String productDescription, String productPrice, String productCondition) {

        Map<String,String> productData = new HashMap<>();
        productData.put("productOwner",userId);
        productData.put("productId",productIdString);
        productData.put("productRegion",productRegion);
        productData.put("productArea",productArea);
        productData.put("productCategory",productCategory);
        productData.put("productTitle",productTitle);
        productData.put("productDescription",productDescription);
        productData.put("productPrice",productPrice);
        productData.put("productCondition",productCondition);
        productData.put("productImage1",imageUri.toString());
        productData.put("productImage2",imageUri1.toString());
        firebaseFirestore.collection("products_of_market").document(productIdString).set(productData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                    Toast.makeText(getContext(),"product is uploaded successfully",Toast.LENGTH_SHORT).show();

                }

        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "(FIRESTORE Error) : " + e.toString(), Toast.LENGTH_LONG).show();
            }
        });

        productId++;

        productIdString = Integer.toString(productId);
                Map<String,String> mp = new HashMap<>();
                mp.put("id",productIdString);
        databaseReferenceId.set(mp);



    }

    private void choseImage() {
//        CropImage.activity()
//                .setGuidelines(CropImageView.Guidelines.ON)
//                .setAspectRatio(1, 1)
//                .start(getActivity());
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, IMAGE_PICKER_SELECT);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_PICKER_SELECT
                && resultCode == Activity.RESULT_OK) {
            String path = getPathFromCameraData(data, this.getActivity());
            Log.i("PICTURE", "Path: " + path);
            if (path != null) {

                if(calledChoose==1)

                {
                    imageUri = Uri.fromFile(new File(path));
                    imageView1.setImageURI(imageUri);
                }
                else if(calledChoose == 2)
                {
                    imageUri1 = Uri.fromFile(new File(path));
                    imageView2.setImageURI(imageUri1);

                }
                else
                {
                    Log.d("checker","onActivity result error");
                }
            }
        }

    }
    public static String getPathFromCameraData(Intent data, Context context) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }




}
