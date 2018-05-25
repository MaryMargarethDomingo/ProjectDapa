package com.example.itadmin.projectdapa.session.controller;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itadmin.projectdapa.R;
import com.example.itadmin.projectdapa.maps.controller.MapsFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SavedContactsFragment extends Fragment {

    public  final int PICK_CONTACT = 123;

    private ImageButton addContacts;
    private Button sendSMS;

    private ImageButton btnDeleteContact1;
    private ImageButton btnDeleteContact2;
    private ImageButton btnDeleteContact3;
    private ImageButton btnDeleteContact4;
    private ImageButton btnDeleteContact5;


    private TextView contact1;
    private TextView contact2;
    private TextView contact3;
    private TextView contact4;
    private TextView contact5;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    String cNumber;
    String message = "";

    String recipient1;
    String recipient2;
    String recipient3;
    String recipient4;
    String recipient5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_saved_contacts, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = sharedPreferences.edit();

        addContacts = view.findViewById(R.id.btnAddContacts);
        sendSMS = view.findViewById(R.id.btnSendSMS);

        btnDeleteContact1 = view.findViewById(R.id.btnDeleteContact1);
        btnDeleteContact2 = view.findViewById(R.id.btnDeleteContact2);
        btnDeleteContact3 = view.findViewById(R.id.btnDeleteContact3);
        btnDeleteContact4 = view.findViewById(R.id.btnDeleteContact4);
        btnDeleteContact5 = view.findViewById(R.id.btnDeleteContact5);

        contact1 = view.findViewById(R.id.contact1);
        contact2 = view.findViewById(R.id.contact2);
        contact3 = view.findViewById(R.id.contact3);
        contact4 = view.findViewById(R.id.contact4);
        contact5 = view.findViewById(R.id.contact5);

        contact1.setText(sharedPreferences.getString("contact1", "No Contact"));
        contact2.setText(sharedPreferences.getString("contact2", "No Contact"));
        contact3.setText(sharedPreferences.getString("contact3", "No Contact"));
        contact4.setText(sharedPreferences.getString("contact4", "No Contact"));
        contact5.setText(sharedPreferences.getString("contact5", "No Contact"));

        return view;
    }

    @Override
    public void onStart(){
        super.onStart();

        if(ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_CONTACTS}, 1);
        }

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, 1);

        }

        addContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
            }
        });

        btnDeleteContact1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contact1.setText("No Contact");
                editor.putString("contact1", "No Contact");
                Toast.makeText(getActivity(), "Contact deleted", Toast.LENGTH_LONG).show();
            }
        });

        btnDeleteContact2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contact2.setText("No Contact");
                editor.putString("contact2", "No Contact");
                Toast.makeText(getActivity(), "Contact deleted", Toast.LENGTH_LONG).show();
            }
        });

        btnDeleteContact3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contact3.setText("No Contact");
                editor.putString("contact3", "No Contact");
                Toast.makeText(getActivity(), "Contact deleted", Toast.LENGTH_LONG).show();
            }
        });

        btnDeleteContact4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contact4.setText("No Contact");
                editor.putString("contact4", "No Contact");
                Toast.makeText(getActivity(), "Contact deleted", Toast.LENGTH_LONG).show();
            }
        });

        btnDeleteContact5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contact5.setText("No Contact");
                editor.putString("contact5", "No Contact");
                Toast.makeText(getActivity(), "Contact deleted", Toast.LENGTH_LONG).show();
            }
        });

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E yyyy-MM-dd ',' HH:mm a");

        message = "SOS! " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName() + "I need help!\n" +
                "Last known location: \n" +
                "http://maps.google.com/maps?q=" + MapsFragment.latitude + "," + MapsFragment.longitude + "\n" +
                //"Time: " + dateFormat.format(date) + "\n" +
                "\n -Sent from Project DAPA";

        sendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!contact1.getText().toString().equals("No Contact")){
                    recipient1 = contact1.getText().toString().replaceAll("[^0-9]", "");
                    SmsManager.getDefault().sendTextMessage(recipient1, null, message, null, null);
                    Toast.makeText(getContext(), "SOS message sent", Toast.LENGTH_LONG).show();
                    Log.d("SMS1", recipient1 + " = " + message);
                }

                if(!contact2.getText().toString().equals("No Contact")){
                    recipient2 = contact2.getText().toString().replaceAll("[^0-9]", "");
                    SmsManager.getDefault().sendTextMessage(recipient2, null, message, null, null);
                    Toast.makeText(getContext(), "SOS message sent", Toast.LENGTH_LONG).show();
                    Log.d("SMS1", recipient2 + " = " + message);
                }

                if(!contact3.getText().toString().equals("No Contact")){
                    recipient3 = contact3.getText().toString().replaceAll("[^0-9]", "");
                    SmsManager.getDefault().sendTextMessage(recipient3, null, message, null, null);
                    Toast.makeText(getContext(), "SOS message sent", Toast.LENGTH_LONG).show();
                    Log.d("SMS1", recipient3 + " = " + message);
                }

                if(!contact4.getText().toString().equals("No Contact")){
                    recipient4 = contact4.getText().toString().replaceAll("[^0-9]", "");
                    SmsManager.getDefault().sendTextMessage(recipient4, null, message, null, null);
                    Toast.makeText(getContext(), "SOS message sent", Toast.LENGTH_LONG).show();
                    Log.d("SMS1", recipient4 + " = " + message);
                }

                if(!contact5.getText().toString().equals("No Contact")){
                    recipient5 = contact5.getText().toString().replaceAll("[^0-9]", "");
                    SmsManager.getDefault().sendTextMessage(recipient5, null, message, null, null);
                    Toast.makeText(getContext(), "SOS message sent", Toast.LENGTH_LONG).show();
                    Log.d("SMS1", recipient5 + " = " + message);
                }

                if(recipient1.equals("") && recipient2.equals("") && recipient3.equals("") &&
                        recipient4.equals("") && recipient5.equals("")){
                    Toast.makeText(getContext(), "No Contacts available, add contacts first", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == getActivity().RESULT_OK) {

                    Uri contactData = data.getData();
                    Cursor c = getActivity().getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {


                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getActivity().getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                    null, null);
                            phones.moveToFirst();
                            cNumber = phones.getString(phones.getColumnIndex("data1"));
                        }

                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                        if(contact1.getText().toString().matches("No Contact")){
                            editor.putString("contact1", name + ": \n" + cNumber);
                            contact1.setText(name + ": \n" + cNumber);

                        }else if(contact2.getText().toString().matches("No Contact")){
                            editor.putString("contact2", name + ": \n" + cNumber);
                            contact2.setText(name + ": \n" + cNumber);

                        }else if(contact3.getText().toString().matches("No Contact")){
                            editor.putString("contact3", name + ": \n" + cNumber);
                            contact3.setText(name + ": \n" + cNumber);

                        }else if(contact4.getText().toString().matches("No Contact")){
                            editor.putString("contact4", name + ": \n" + cNumber);
                            contact4.setText(name + ": \n" + cNumber);

                        }else if(contact5.getText().toString().matches("No Contact")) {
                            editor.putString("contact5", name + ": \n" + cNumber);
                            contact5.setText(name + ": \n" + cNumber);

                        }

                        break;
                    }
                }
        }
    }

}