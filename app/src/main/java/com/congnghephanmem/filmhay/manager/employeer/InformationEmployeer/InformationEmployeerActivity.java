package com.congnghephanmem.filmhay.manager.employeer.InformationEmployeer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.congnghephanmem.filmhay.DatePickerFragment;
import com.congnghephanmem.filmhay.Model.GetData;
import com.congnghephanmem.filmhay.Model.History;
import com.congnghephanmem.filmhay.Model.User;
import com.congnghephanmem.filmhay.R;
import com.congnghephanmem.filmhay.SignUp.SignUpActivity;
import com.congnghephanmem.filmhay.manager.employeer.ManagerEmployerActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnFocusChange;

public class InformationEmployeerActivity extends AppCompatActivity  {

    @BindView(R.id.Edit_email_employeer)
    EditText editEmail;
    @BindView(R.id.Edit_phone_employeer)
    EditText editPhone;
    @BindView(R.id.Edit_name_employeer)
    EditText editName;
    @BindView(R.id.Edit_date_employeer)
    EditText editNgaySinh;
    @BindView(R.id.btn_update_employeer)
    Button btn_update;
    @BindView(R.id.employeer_gt)
    RadioGroup radioGroup;
    @BindView(R.id.img_user_employeer)
    ImageView img;
    @BindView(R.id.btn_delete_employeer) Button btnDelete;
    DatabaseReference mData;
    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CHOOSE_PHOTO = 321;
    private FirebaseAuth mAuth;
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_employeer);

        ButterKnife.bind(this);

        editNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(editNgaySinh);
            }
        });
        //

        // add user
        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance().getReference();

        registerForContextMenu(img);
        //load data khi ấn vào một intent
        loadIntent();

        returnEmployeerManager();
    }

    private void returnEmployeerManager() {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar_employeer);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thông tin nhân viên");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void loadIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        if (bundle != null){
            String method = bundle.getString("method");
            if (method.equals("update")){
                editEmail.setEnabled(false);
                editPhone.setEnabled(false);
                User user = (User) bundle.getSerializable("user");
                editPhone.setText(user.getPhone());
                editEmail.setText(user.getEmail());
                editName.setText(user.getName());
                editNgaySinh.setText(user.getBirthday());
                String gt = user.getGender();
                RadioButton radioButton = (RadioButton) findViewById(R.id.employeer_nu);
                radioButton.setChecked(true);
                if (gt.equals("Nam")){
                    RadioButton radioButto = (RadioButton) findViewById(R.id.employeer_nam);
                    radioButto.setChecked(true);
                }
                Picasso.get().load(user.getAvatar()).into(img);
                //update dữ liệu
                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateData();
                    }
                });

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mData.child("User").child(editPhone.getText().toString()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(InformationEmployeerActivity.this, "Xóa dữ liệu thành công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(InformationEmployeerActivity.this, ManagerEmployerActivity.class));
                                finish();
                            }
                        });
                    }
                });
            }
        }else {
            btn_update.setText("Thêm nhân viên");
            btnDelete.setVisibility(View.GONE);
            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addMember();
                }
            });
        }
    }

    //update dữ liệu
    private void updateData() {
        int selected = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButtonGioiTinh = (RadioButton) findViewById(selected);
        String gt = radioButtonGioiTinh.getText().toString();
        Calendar calendar = Calendar.getInstance();
        final StorageReference storageReference = firebaseStorage.getReferenceFromUrl("gs://appfilm-b6b73.appspot.com/");
        StorageReference mountainRef = storageReference.child("Image_NhanVien").child("image_employeer" + calendar.getTimeInMillis() + ".png");
        // Get the data from an ImageView as bytes
        img.setDrawingCacheEnabled(true);
        img.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                if (taskSnapshot.getMetadata() != null){
                    if (taskSnapshot.getMetadata().getReference() != null){
                        Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imageUrl = uri.toString();
                                User user = new User(editEmail.getText().toString(), editPhone.getText().toString(),editName.getText().toString(),gt,editNgaySinh.getText().toString(),"employeer", imageUrl);
                                mData.child("User").child(user.getPhone()).setValue(user, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        if(error == null){
                                            Toast.makeText(InformationEmployeerActivity.this, "Update dữ liệu thành công", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(InformationEmployeerActivity.this, ManagerEmployerActivity.class));
                                            finish();
                                        }
                                    }
                                });
                            }
                        });
                    }
                }
            }
        });
    }

    public int check = 0;
    private void checkTrungPhone(){
        mData.child("User").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {
                User user = snapshot.getValue(User.class);
                if (editPhone.getText().toString().equals(user.getPhone())){
                    check = 1;
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addMember() {
        if (editEmail.getText().toString().isEmpty()){
            editEmail.setError("Email chưa được nhập");
            return;
        }
        if (editPhone.getText().toString().isEmpty()){
            editPhone.setError("Số điện thoại không được bỏ trống");
            return;
        }
        if (editPhone.getText().toString().length() != 10){
            editPhone.setError("Số điện thoại phải đủ 10 số");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(editEmail.getText().toString()).matches()){
            editEmail.setError("Không đúng đinh dạng email");
            return;
        }
        if (editName.getText().toString().isEmpty()){
            editName.setError("Tên không được bỏ trống");
            return;
        }
        String mail = editEmail.getText().toString();
        String pass = "123321";
        checkTrungPhone();
        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                switch (check){
                    case 1:
                        Toast.makeText(InformationEmployeerActivity.this, "Số điện thoại này đã được sử dụng", Toast.LENGTH_SHORT).show();
                        check = 0;
                        break;
                    case 0:
                        addUser(mail, pass);
                        break;
                }
            }
        }.start();

    }


    private void addUser(String email, String pass) {
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user1 = mAuth.getCurrentUser();
                            InsertData();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(InformationEmployeerActivity.this, "Email đã được sử dụng", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void InsertData(){
        int selected = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButtonGioiTinh = (RadioButton) findViewById(selected);
        String gt = radioButtonGioiTinh.getText().toString();
        String mail = editEmail.getText().toString();
        String pass = "123321";
        Calendar calendar = Calendar.getInstance();
        final StorageReference storageReference = firebaseStorage.getReferenceFromUrl("gs://appfilm-b6b73.appspot.com/");
        StorageReference mountainRef = storageReference.child("Image_NhanVien").child("image_employeer" + calendar.getTimeInMillis() + ".png");
        // Get the data from an ImageView as bytes
        img.setDrawingCacheEnabled(true);
        img.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                if (taskSnapshot.getMetadata() != null){
                    if (taskSnapshot.getMetadata().getReference() != null){
                        Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imageUrl = uri.toString();
                                User user = new User(mail, editPhone.getText().toString(),editName.getText().toString(),gt,editNgaySinh.getText().toString(),"employeer", imageUrl);


                                mData.child("User").child(editPhone.getText().toString()).setValue(user, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        if (error == null){
                                            Toast.makeText(InformationEmployeerActivity.this, "Thêm nhân viên thành công", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(InformationEmployeerActivity.this, ManagerEmployerActivity.class));
                                            finish();
                                        }
                                    }
                                });
                            }
                        });
                    }
                }
            }
        });
    }



    private void showDateDialog(EditText editNgaySinh) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = ((view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            //chuyển dữ liệu ngày sang string
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            editNgaySinh.setText(simpleDateFormat.format(calendar.getTime()));
        });
        new DatePickerDialog(InformationEmployeerActivity.this, dateSetListener, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)).show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context, menu);
        menu.setHeaderTitle("Photo");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.context_menu_take_photo:
                ActivityCompat.requestPermissions(InformationEmployeerActivity.this, new String[]{
                        Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
                break;
            case R.id.context_menu_choose_photo:
                intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
                break;

        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == REQUEST_CHOOSE_PHOTO){
                try {
                    Uri imageUri = data.getData();
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    img.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_CODE_CAMERA) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                img.setImageBitmap(bitmap);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_CAMERA && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CODE_CAMERA);
        }else {
            Toast.makeText(InformationEmployeerActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}