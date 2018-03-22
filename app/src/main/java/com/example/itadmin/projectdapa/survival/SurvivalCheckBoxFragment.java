package com.example.itadmin.projectdapa.survival;

import android.content.Context;
import android.os.Bundle;
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
    MyCustomAdapter dataAdapter = null;

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
        ArrayList<DisasterBean> disasterList = new ArrayList<DisasterBean>();

        DisasterBean disaster = new DisasterBean("Non-perishable food",false);
        disasterList.add(disaster);

        disaster = new DisasterBean("Drinking Water",false);
        disasterList.add(disaster);

        disaster = new DisasterBean("Medicines",false);
        disasterList.add(disaster);

        disaster = new DisasterBean("First aid kit",false);
        disasterList.add(disaster);

        disaster = new DisasterBean("Clothes",false);
        disasterList.add(disaster);

        disaster = new DisasterBean("Flashlights",false);
        disasterList.add(disaster);

        disaster = new DisasterBean("Portable radio",false);
        disasterList.add(disaster);

        disaster = new DisasterBean("Batteries",false);
        disasterList.add(disaster);

        disaster = new DisasterBean("Lighter/matches",false);
        disasterList.add(disaster);

        disaster = new DisasterBean("Sanitation items",false);
        disasterList.add(disaster);

        disaster = new DisasterBean("Extra Cash",false);
        disasterList.add(disaster);

        disaster = new DisasterBean("Whistle",false);
        disasterList.add(disaster);

        disaster = new DisasterBean("Important documents and ID's",false);
        disasterList.add(disaster);

        disaster = new DisasterBean("Family emergency contact information",false);
        disasterList.add(disaster);

        disaster = new DisasterBean("Emergency blanket",false);
        disasterList.add(disaster);

        disaster = new DisasterBean("Map of the area",false);
        disasterList.add(disaster);

        disaster = new DisasterBean("Multi-tool",false);
        disasterList.add(disaster);

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

