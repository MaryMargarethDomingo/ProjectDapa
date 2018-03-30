package com.example.itadmin.projectdapa.survival;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.example.itadmin.projectdapa.MainActivity;
import com.example.itadmin.projectdapa.R;

import java.util.ArrayList;

public class SurvivalCheckBoxFragment extends Fragment {
    private MyCustomAdapter dataAdapter = null;
    private String[] items = {"Non-perishable food", "Drinking Water", "Medicines", "First aid kit",
            "Clothes", "Flashlights", "Portable radio", "Batteries", "Lighter/matches", "Sanitation items",
            "Extra Cash", "Whistle", "Important documents and ID's", "Family emergency contact information",
            "Emergency blanket", "Map of the area", "Multi-tool"};
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.survival_checklist, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        displayListView();
        checkButtonClick();

    }

    private void displayListView(){
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = preferences.edit();
        ArrayList<DisasterBean> disasterList = new ArrayList<DisasterBean>();

        for(int i=0; i<items.length; i++){
            if(!preferences.contains(items[i])){
                disasterList.add(new DisasterBean(items[i],false));

                editor.putBoolean(items[i], false);
                editor.apply();
            }else{
                disasterList.add(new DisasterBean(items[i], preferences.getBoolean(items[i], false)));
            }
        }

        dataAdapter = new MyCustomAdapter(getActivity(),R.layout.survival_info, disasterList);
        ListView listView = getView().findViewById(R.id.listView1);
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                DisasterBean disaster = (DisasterBean) parent.getItemAtPosition(position);
                Toast.makeText(getContext(),"Clicked on Row: " + disaster.getName(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private class MyCustomAdapter extends ArrayAdapter<DisasterBean>{

        private ArrayList<DisasterBean> stateList;

        public MyCustomAdapter(Context context, int textViewResourceId, ArrayList<DisasterBean> stateList){
            super(context, textViewResourceId, stateList);
            this.stateList = new ArrayList<DisasterBean>();
            this.stateList.addAll(stateList);
        }

        private class ViewHolder{

            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            ViewHolder holder = null;

            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null){

                LayoutInflater vi = (LayoutInflater) MainActivity.contextOfApplication.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.survival_info, null);

                holder = new ViewHolder();
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);

                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener(){

                    public void onClick(View v){
                        CheckBox cb = (CheckBox) v;
                        DisasterBean disaster = (DisasterBean) cb.getTag();

                        Toast.makeText(getContext(), "Clicked on Checkbox: " + cb.getText() ,Toast.LENGTH_LONG).show();
                        disaster.setSelected(cb.isChecked());

                        if(preferences.getBoolean(disaster.getName(), true)) {
                            editor.putBoolean(disaster.getName(), false);
                            editor.apply();
                        }else{
                            editor.putBoolean(disaster.getName(), true);
                            editor.apply();
                        }
                    }
                });

            }
            else{
                holder = (ViewHolder) convertView.getTag();
            }

            DisasterBean disaster = stateList.get(position);

            holder.name.setText(disaster.getName());
            holder.name.setChecked(disaster.isSelected());

            holder.name.setTag(disaster);

            return convertView;
        }

    }

    private void checkButtonClick() {

        Button myButton = getView().findViewById(R.id.findSelected);

        myButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

                ArrayList<DisasterBean> stateList = dataAdapter.stateList;

                for(int i=0;i<stateList.size();i++){
                    DisasterBean disaster = stateList.get(i);

                    if(disaster.isSelected()){
                        responseText.append("\n" + disaster.getName());
                    }
                }

                Toast.makeText(getContext(),responseText, Toast.LENGTH_LONG).show();
            }
        });
    }



}

