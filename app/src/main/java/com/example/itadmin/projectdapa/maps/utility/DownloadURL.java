package com.example.itadmin.projectdapa.maps.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


class DownloadURL {

    public String readURL(String myUrl1) throws IOException {

        String data1 = "";
        InputStream inputStream1 = null;
        HttpURLConnection urlConnection1 = null;

        try {
            URL url1 = new URL(myUrl1);

            urlConnection1 = (HttpURLConnection) url1.openConnection();
            urlConnection1.connect();
            inputStream1 = urlConnection1.getInputStream();

            BufferedReader br1 = new BufferedReader(new InputStreamReader(inputStream1));
            StringBuilder sb = new StringBuilder();

            String line1;

            while((line1 = br1.readLine()) != null){
                sb.append(line1);
            }

            data1 = sb.toString();

            br1.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();

        }finally {
            inputStream1.close();
            urlConnection1.disconnect();
        }

        return data1;
    }

}
