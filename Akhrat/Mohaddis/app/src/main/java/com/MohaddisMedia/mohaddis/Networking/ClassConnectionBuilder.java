package com.MohaddisMedia.mohaddis.Networking;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ClassConnectionBuilder  {
    public static int totalSize = 0;
    public static int sizeDownloaded = 0;
    ClassConnectionBuilder()
    {

    }
    public static String getResponces(URL url,int timeout)  {
        String responce = "";
        try {

            HttpURLConnection httpURLConnectio  = (HttpURLConnection)url.openConnection();

            httpURLConnectio.setRequestMethod("GET");
            httpURLConnectio.setConnectTimeout(timeout);

            totalSize = httpURLConnectio.getContentLength();
            int rcode = httpURLConnectio.getResponseCode();
            if(rcode == 200 || rcode == 203)
            {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnectio.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                for(String str;(str=bufferedReader.readLine())!= null;)
                {
                    stringBuilder = stringBuilder.append(str + "\n");
                    sizeDownloaded +=str.length();

                }
                responce = stringBuilder.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  responce;

    }
}


