package com.example.qualtribe.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qualtribe.R;
import com.example.qualtribe.databinding.ActivityModificationBinding;
import com.example.qualtribe.models.Order;
import com.example.qualtribe.models.SubmittedOrder;
import com.example.qualtribe.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class Modification extends AppCompatActivity implements View.OnClickListener {

    public static final int progressType = 0;
    ImageView home, message, search, ivOrder, profile, sub_img;
    TextView desc, tvPrice, tvReq, status, newModification;
    Button approve, Modbutton, down;
//    String orderID, req, price, Mod, url;
    String url = "";
    String uid;
    int count = 0;
//    SubmittedOrder myOrder = new SubmittedOrder();
    ProgressDialog mProgressDialog;
    Button button;
    ImageView imageView;
    ActivityModificationBinding binding;
    private ProgressDialog progressDialog;

    SubmittedOrder submittedOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityModificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        uid = FirebaseAuth.getInstance().getUid();

        submittedOrder = (SubmittedOrder) getIntent().getSerializableExtra(Constants.KEY_SUBMITTED_ORDER);

        desc = findViewById(R.id.submitted_order_desc);
        sub_img = findViewById(R.id.submitted_order_img);
        tvPrice = findViewById(R.id.tvPrice);
        tvReq = findViewById(R.id.tvRequirement);
        status = findViewById(R.id.status);
        newModification = findViewById(R.id.newModification);
        down = findViewById(R.id.download);

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = submittedOrder.getAttachmentUrl();
                new DownloadFromURL().execute(url);
            }
        });



        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myReference = firebaseDatabase.getReference("submitted-orders");

//        myReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot seller : snapshot.getChildren()) {
//                    SubmittedOrder o = seller.getValue(SubmittedOrder.class);
//                    Log.i("MASLA", "onDataChange: " + o.getOrderId());
//                    Log.i("MASLA", "onDataChange: " + orderID);
//
//
//                    if (o.getOrderId().equals(orderID)) {
//                        myOrder = o;
//                        Log.i("ORDER", "onDataChange: " + o.getBuyerEmail());
//                        Log.i("ORDER", "onDataChange: " + myOrder.getBuyerEmail());
//                        url = myOrder.getAttachmentUrl();
//                        renderData();
//                    }
//
//                }
//
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });



//        Modbutton.setOnClickListener(v -> {
//            Mod = newModification.getText().toString();
//
//
//            DatabaseReference myRef4 = firebaseDatabase.getReference("submitted-orders");
//
//            myRef4.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    for (DataSnapshot donor : snapshot.getChildren()) {
//                        SubmittedOrder d = donor.getValue(SubmittedOrder.class);
//
//                        if (d.getOrderId().equals(orderID) && count == 0) {
//                            String key = donor.getKey();
//                            count = count + 1;
//                            myRef4.child(key).removeValue();
//                            SubmittedOrder dd = new SubmittedOrder(d.getRequirements(), d.getOrderId(), d.getBuyerEmail(), d.getAttachmentUrl(), "revision", Mod, uid);
//                            myRef4.push().setValue(dd);
//                            Intent myIntent = new Intent(Modification.this, Contract.class);
//                            startActivity(myIntent);
//                            finish();
//                            break;
//                        }
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//
//
//        });


        approve = findViewById(R.id.approve);
        approve.setOnClickListener(this);

        binding.revision.setOnClickListener(this);

        home = findViewById(R.id.home_ic);
        home.setOnClickListener(this);

        message = findViewById(R.id.msg_ic);
        message.setOnClickListener(this);

        search = findViewById(R.id.search_ic);
        search.setOnClickListener(this);


        profile = findViewById(R.id.profile_ic);
        profile.setOnClickListener(this);

        ivOrder = findViewById(R.id.order_ic);
        ivOrder.setOnClickListener(this);


    }

    private void renderData() {
        desc.setText(submittedOrder.getRequirements());
        String imageUri = submittedOrder.getAttachmentUrl();
        Picasso.with(Modification.this).load(imageUri).into(sub_img);

        tvPrice.setText(submittedOrder.getPrice());
        tvReq.setText(submittedOrder.getRequirements());
        status.setText(submittedOrder.getStatus());


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.revision:
                sendRevision();
                startActivity(new Intent(this, Homepage.class));
                finish();
                break;

            case R.id.approve:
                startActivity(new Intent(this, Review.class));
                break;

            case R.id.home_ic:
                startActivity(new Intent(this, Homepage.class));
                break;

            case R.id.msg_ic:
                startActivity(new Intent(this, chat.class));
                break;

            case R.id.search_ic:
                startActivity(new Intent(this, loginSearch.class));
                break;

            case R.id.order_ic:
                startActivity(new Intent(this, Contract.class));
                break;

            case R.id.profile_ic:
                startActivity(new Intent(this, loginMenu.class));
                finish();

        }
    }

    private void sendRevision() {

    }

    //progress dialog
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progressType: // we set this to 0
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("File is Downloading. Please wait...");
                progressDialog.setIndeterminate(false);
                progressDialog.setMax(100);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setCancelable(true);
                progressDialog.show();
                return progressDialog;
            default:
                return null;
        }
    }

    class DownloadFromURL extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progressType);
        }

        @Override
        protected String doInBackground(String... fileUrl) {
            int count;
            try {
                URL url = new URL(fileUrl[0]);
                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();
                // show progress bar 0-100%
                int fileLength = urlConnection.getContentLength();
                InputStream inputStream = new BufferedInputStream(url.openStream(), 8192);
                OutputStream outputStream = new FileOutputStream(Environment.getExternalStorageDirectory().getPath());

                byte[] data = new byte[1024];
                long total = 0;
                while ((count = inputStream.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / fileLength));
                    outputStream.write(data, 0, count);
                }
                // flushing output
                outputStream.flush();
                // closing streams
                outputStream.close();
                inputStream.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
            return null;
        }

        // progress bar Updating

        protected void onProgressUpdate(String... progress) {
            // progress percentage
            progressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String file_url) {
        }
    }
}