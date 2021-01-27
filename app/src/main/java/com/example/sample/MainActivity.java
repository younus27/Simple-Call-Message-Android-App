package com.example.sample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    EditText etNumber;
    EditText etMessage;
    ImageButton btCall;
    ImageButton btMsg;

    public static final String TAG = "MYJ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumber = findViewById(R.id.et_number);
        etMessage = findViewById(R.id.et_msg);
        btCall = findViewById(R.id.bt_call);
        btMsg = findViewById(R.id.bt_msg);

        btCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = etNumber.getText().toString();
                if (phone.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter Number!", Toast.LENGTH_SHORT).show();
                } else {
                    String s = "tel:" + phone;
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(s));

                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE},1);
                    }
                    else
                    {
                        startActivity(intent);
                    }
                }




//                ContentResolver cr = mContext.getContentResolver(); //Activity/Application android.content.Context
//                Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
//                if(cursor.moveToFirst())
//                {
//                    ArrayList<String> alContacts = new ArrayList<String>();
//                    do
//                    {
//                        String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
//
//                        if(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
//                        {
//                            Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",new String[]{ id }, null);
//                            while (pCur.moveToNext())
//                            {
//                                String contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                                alContacts.add(contactNumber);
//                                break;
//                            }
//                            pCur.close();
//                        }
//
//                    } while (cursor.moveToNext()) ;
//                }












            }
        });

        btMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = etNumber.getText().toString();
                String message = etMessage.getText().toString();

                if (phone.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter Number!", Toast.LENGTH_SHORT).show();
                } else {
                    if (message.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please Message!", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS},1);
                        }
                        else
                        {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(phone,null,message,null,null);
                            Toast.makeText(getApplicationContext(),  "Message Sent", Toast.LENGTH_SHORT).show();

                        }
                    }
                }

            }
        });

        }






    private void getContactList() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i(TAG, "Name: " + name);
                        Log.i(TAG, "Phone Number: " + phoneNo);
                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }


    }


}