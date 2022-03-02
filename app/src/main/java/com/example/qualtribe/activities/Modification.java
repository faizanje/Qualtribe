package com.example.qualtribe.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qualtribe.R;
import com.example.qualtribe.models.SubmittedOrder;
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

    ImageView home, message, search, order, profile , sub_img;
    TextView desc, tvPrice, tvReq, status, newModification;
    Button approve, Modbutton, down;
    String orderID, req, price, Mod, url;
    int count = 0;
    SubmittedOrder myOrder = new SubmittedOrder();

    ProgressDialog mProgressDialog;
    Button button;
    private ProgressDialog progressDialog;
    ImageView imageView;
    public static final int progressType = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification);
        Intent intent = getIntent();
        orderID = intent.getStringExtra("orderID");
        price = intent.getStringExtra("price");
        req = intent.getStringExtra("req");

        desc = findViewById(R.id.submitted_order_desc);
        sub_img = findViewById(R.id.submitted_order_img);
        tvPrice = findViewById(R.id.tvPrice);
        tvReq = findViewById(R.id.tvRequirement);
        status = findViewById(R.id.status);
        newModification = findViewById(R.id.newModification);
        Modbutton = findViewById(R.id.Modbutton);
        down = findViewById(R.id.download);

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = myOrder.getAttachmentUrl();
                new DownloadFromURL().execute(url);
            }
        });


        Log.i("ORDERID", "onCreate: " + orderID);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myReference = firebaseDatabase.getReference("submitted-orders");

        myReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot seller: snapshot.getChildren()){
                    SubmittedOrder o = seller.getValue(SubmittedOrder.class);
                    Log.i("MASLA", "onDataChange: " + o.getOrderId());
                    Log.i("MASLA", "onDataChange: " + orderID);


                    if (o.getOrderId().toString().equals(orderID.toString())){
                        myOrder = o;
                        Log.i("ORDER", "onDataChange: " + o.getBuyerEmail());
                        Log.i("ORDER", "onDataChange: " + myOrder.getBuyerEmail());
                        url = myOrder.getAttachmentUrl();
                        renderData();
                    }

                }

            }




            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Modbutton.setOnClickListener(v -> {
            Mod = newModification.getText().toString();


            DatabaseReference myRef4 = firebaseDatabase.getReference("submitted-orders");

            myRef4.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot donor: snapshot.getChildren()) {
                        SubmittedOrder d = donor.getValue(SubmittedOrder.class);

                        if (d.getOrderId().equals(orderID) && count == 0){
                            String key = donor.getKey();
                            count = count + 1;
                            myRef4.child(key).removeValue();
                            SubmittedOrder dd = new SubmittedOrder(d.getRequirements(), d.getOrderId(), d.getBuyerEmail(), d.getAttachmentUrl(),"revision", Mod);
                            myRef4.push().setValue(dd);
                            Intent myIntent = new Intent(Modification.this, Contract.class);
                            startActivity(myIntent);
                            finish();
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });




        });


        approve = findViewById(R.id.approve);
        approve.setOnClickListener(this);

        home = findViewById(R.id.home_ic);
        home.setOnClickListener(this);

        message = findViewById(R.id.msg_ic);
        message.setOnClickListener(this);

        search = findViewById(R.id.search_ic);
        search.setOnClickListener(this);


        profile = findViewById(R.id.profile_ic);
        profile.setOnClickListener(this);

        order = findViewById(R.id.order_ic);
        order.setOnClickListener(this);
    }

    private void renderData() {
        desc.setText(myOrder.getRequirements());
        String imageUri = myOrder.getAttachmentUrl();
        Picasso.with(Modification.this).load(imageUri).into(sub_img);

        tvPrice.setText(price);
        tvReq.setText(req);
        status.setText(myOrder.getStatus());


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

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
                OutputStream outputStream = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() );

                byte data[] = new byte[1024];
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
}