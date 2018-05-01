package com.example.itadmin.projectdapa.session.controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.TextView;

import com.example.itadmin.projectdapa.R;
import com.example.itadmin.projectdapa.maps.controller.MapsFragment;

import java.util.Calendar;
import java.util.Date;

public class SavedContactsFragment extends Fragment {

    public  final int PICK_CONTACT = 123;

    private Button addContacts;
    private Button sendSMS;

    private TextView contact1;
    private TextView contact2;
    private TextView contact3;
    private TextView contact4;
    private TextView contact5;

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

        addContacts = view.findViewById(R.id.btnAddContacts);
        sendSMS = view.findViewById(R.id.btnSendSMS);

        contact1 = view.findViewById(R.id.contact1);
        contact2 = view.findViewById(R.id.contact2);
        contact3 = view.findViewById(R.id.contact3);
        contact4 = view.findViewById(R.id.contact4);
        contact5 = view.findViewById(R.id.contact5);

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

        Date dateAndTime = Calendar.getInstance().getTime();

        message = "SOS! I need help!\n" +
                "Last recorded location: \n" +
                "Latitude: " + MapsFragment.latitude + "\n" +
                "Longitude: " + MapsFragment.longitude + "\n" +
                "Time: " + dateAndTime + "\n" +
                "\n\n-Sent from Project DAPA";

        recipient1 = contact1.getText().subSequence(contact1.getText().length() - 16, contact1.getText().length()).toString()
                .replace(" ", "").replaceAll("[()]]", "");

        recipient2 = contact1.getText().subSequence(contact1.getText().length() - 16, contact1.getText().length()).toString()
                .replace(" ", "").replaceAll("[()]]", "");

        recipient3 = contact1.getText().subSequence(contact1.getText().length() - 16, contact1.getText().length()).toString()
                .replace(" ", "").replaceAll("[()]]", "");

        recipient4 = contact1.getText().subSequence(contact1.getText().length() - 16, contact1.getText().length()).toString()
                .replace(" ", "").replaceAll("[()]]", "");

        recipient5 = contact1.getText().subSequence(contact1.getText().length() - 16, contact1.getText().length()).toString()
                .replace(" ", "").replaceAll("[()]]", "");

        sendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SmsManager.getDefault().sendTextMessage(contact1.getText().subSequence(contact1.getText().length() - 11, contact1.getText().length()).toString(),
                        null, message, null, null);

                SmsManager.getDefault().sendTextMessage(recipient1, null, message, null, null);
                SmsManager.getDefault().sendTextMessage(recipient2, null, message, null, null);
                SmsManager.getDefault().sendTextMessage(recipient3, null, message, null, null);
                SmsManager.getDefault().sendTextMessage(recipient5, null, message, null, null);

                Log.d("SMS!!!", message);
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

                        if(contact1.getText().toString().matches("No contact")){
                            contact1.setText(name + ": " + cNumber);

                        }else if(contact2.getText().toString().matches("No contact")){
                            contact2.setText(name + ": " + cNumber);

                        }else if(contact3.getText().toString().matches("No contact")){
                            contact3.setText(name + ": " + cNumber);

                        }else if(contact4.getText().toString().matches("No contact")){
                            contact4.setText(name + ": " + cNumber);

                        }else if(contact5.getText().toString().matches("No contact")) {
                            contact5.setText(name + ": " + cNumber);

                        }

                        break;
                    }
                }
        }
    }

}