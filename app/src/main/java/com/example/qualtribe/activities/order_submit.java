package com.example.qualtribe.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.qualtribe.R;
import com.example.qualtribe.databinding.ActivityOrderSubmitBinding;
import com.example.qualtribe.databinding.DialogProgressSimpleBinding;
import com.example.qualtribe.models.SubmittedOrder;
import com.example.qualtribe.utils.Constants;
import com.example.qualtribe.utils.FileUtils;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tbruyelle.rxpermissions3.RxPermissions;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class order_submit extends AppCompatActivity implements View.OnClickListener {

    final CompositeDisposable compositeDisposable = new CompositeDisposable();
    ImageView message, order, profile;
    ActivityOrderSubmitBinding binding;
    String orderId = "";
    String buyerEmail = "";
    RxPermissions rxPermissions;
    ActivityResultLauncher<Intent> someActivityResultLauncher;
    File selectedFile;
    SubmittedOrder submittedOrder;
    AlertDialog alertDialog;
    DialogProgressSimpleBinding dialogProgressSimpleBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderSubmitBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        rxPermissions = new RxPermissions(this);
        orderId = getIntent().getStringExtra(Constants.KEY_ORDER_ID);
        buyerEmail = getIntent().getStringExtra(Constants.KEY_BUYER_EMAIL);
        submittedOrder = new SubmittedOrder(orderId);
        if (buyerEmail != null) {
            submittedOrder.setBuyerEmail(buyerEmail);
        }

        message = findViewById(R.id.msg_ic);
        message.setOnClickListener(this);

        profile = findViewById(R.id.profile_ic);
        profile.setOnClickListener(this);

        order = findViewById(R.id.order_ic);
        order.setOnClickListener(this);

        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        if (data != null) {
                            Uri uri = data.getData();

                            Log.d(Constants.TAG, "onActivityResult: " + uri);
                            try {
                                Uri correctUri = FileUtils.getFilePathFromUri(order_submit.this, uri);
                                selectedFile = new File(correctUri.getPath());
                                Log.d(Constants.TAG, "onCreate: " + selectedFile.getAbsolutePath());
                                Log.d(Constants.TAG, "onCreate: " + selectedFile.getName());
                                binding.tvFilename.setVisibility(View.VISIBLE);
                                binding.tvFilename.setText("Selected file: " + selectedFile.getName());

                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.d(Constants.TAG, "onCreate: " + e.getMessage());
                            }

                        } else {
                            Log.d(Constants.TAG, "onActivityResult: dara is null");
                        }
                    }
                });

        binding.submitReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String requirements = binding.requirements.getText().toString();
                if (requirements.isEmpty()) {
                    Toast.makeText(order_submit.this, "Requirements cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                submittedOrder.setRequirements(requirements);
                submittedOrder.setStatus("delivered");
                submitOrder();
            }
        });

        binding.btnSelectAttachment.setOnClickListener(v -> {
            getReadPermission();
        });

    }

    private void uploadFile() {


        showAlertDialog();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            StorageReference storageRef = FirebaseStorage
                    .getInstance()
                    .getReference()
                    .child("submitted-orders")
                    .child(user.getUid())
                    .child(selectedFile.getName());

            UploadTask uploadTask = storageRef.putFile(Uri.fromFile(selectedFile));

            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    dialogProgressSimpleBinding.tvPercentage.setText(((int) progress) + "%");
                    Log.d(Constants.TAG, "Upload is " + progress + "% done");
                }
            });

            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return storageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        submittedOrder.setAttachmentUrl(downloadUri.toString());
                        submitOrderNow();
                        Log.d(Constants.TAG, "onComplete: " + downloadUri);
                    } else {
                        Toast.makeText(order_submit.this, "Not successful", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void showAlertDialog() {
        dialogProgressSimpleBinding = DialogProgressSimpleBinding.inflate(getLayoutInflater());
        alertDialog = new MaterialAlertDialogBuilder(this)
                .setView(dialogProgressSimpleBinding.getRoot())
                .setCancelable(false)
                .create();
        alertDialog.show();
    }


    private void submitOrder() {
        if (selectedFile != null) {
            uploadFile();
        } else {
            submitOrderNow();
        }
    }

    private void submitOrderNow() {
        alertDialog.dismiss();
        FirebaseDatabase.getInstance().getReference()
                .child("submitted-orders")
                .push()
                .setValue(submittedOrder);
        startActivity(new Intent(this, Seller_Home.class));
        finish();
        Toast.makeText(this, "Order submitted", Toast.LENGTH_SHORT).show();
    }


    private void getReadPermission() {
        compositeDisposable.add(rxPermissions.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(permission -> {
                    // Denied permission without ask never again
                    // Denied permission with ask never again
                    // Need to go to the settings
                    if (permission.granted) {
                        pickFile();
                    } else
                        showPermissionRequiredDialog(!permission.shouldShowRequestPermissionRationale);
                }, throwable -> {
                    Log.d(Constants.TAG, "getReadPermission: " + throwable.getMessage());
                    Toast.makeText(order_submit.this, "Error:" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }));

    }

    private void pickFile() {


        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(Intent.createChooser(intent, "Choose an attachment"));
    }

    private void showPermissionRequiredDialog(boolean isNeverAskAgainSelected) {
        MaterialAlertDialogBuilder materialAlertDialogBuilder =
                new MaterialAlertDialogBuilder(order_submit.this)
                        .setCancelable(false)
                        .setTitle("Permission required")
                        .setMessage("Read storage permission is required to show pick files")
                        .setNegativeButton("Close", (dialog, which) -> {
                            order_submit.this.finish();
                        });
        if (isNeverAskAgainSelected) {
            materialAlertDialogBuilder.setPositiveButton("Settings", (dialog, which) -> {
                openSettingsIntent();
            });
        } else {
            materialAlertDialogBuilder.setPositiveButton("Grant Permission", (dialog, which) -> {
                getReadPermission();
            });
        }
        materialAlertDialogBuilder.show();
    }

    private void openSettingsIntent() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.msg_ic:
                startActivity(new Intent(this, sellerchat.class));
                break;

            case R.id.order_ic:
                startActivity(new Intent(this, seller_order.class));
                break;


            case R.id.profile_ic:
                startActivity(new Intent(this, sellermenu.class));
                break;


        }

    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}