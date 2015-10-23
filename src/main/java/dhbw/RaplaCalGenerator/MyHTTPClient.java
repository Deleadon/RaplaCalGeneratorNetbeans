/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbw.RaplaCalGenerator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author schlenker
 */
public class MyHTTPClient {
    public  ArrayList<StringBuilder> getEntries(int[]startDate,int[]endDate,String key) {  //dates int[] day;month;year
        Boolean iterator = true;
        ArrayList<StringBuilder> returnStrings = new ArrayList<StringBuilder>();
        int[]activeDate = startDate;
        int weekCount = 0;
        while (iterator) {
            weekCount++;
            StringBuilder raw = getCalendarData("https://rapla.dhbw-stuttgart.de/rapla?key="+key+"&prev=<<&day="+activeDate[0]+"&month="+activeDate[1]+"&year="+activeDate[2], false);
            returnStrings.add(raw);
            //iterator = !(endDate[0]<=activeDate[0]&& endDate[1]<=activeDate[1]&&endDate[2]<=activeDate[2]);
            iterator = endReached(activeDate, endDate);
            System.out.println("Week: "+ weekCount);
            activeDate = nextDate(activeDate);
        }
        System.out.println("Downloaded "+weekCount+" weeks");
        return returnStrings;
    }
    private Boolean endReached(int[]activeDate, int[]endDate){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
        Date active = format.parse(activeDate[2]+"-"+activeDate[1]+"-"+activeDate[0]);
        Date end = format.parse(endDate[2]+"-"+endDate[1]+"-"+endDate[0]);
        return active.compareTo(end)<=0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    private int[] nextDate(int[] prev) {
        if (prev[0]<23) {
            prev[0]+=7;
        } else {
            prev[0]=(prev[0]+7)-30;
            if (prev[1] < 11){
                prev[1]++;
            }  else {
                prev[2]++;
                prev[1]=1;
            }      
        }
        return prev;
    }
    public static StringBuilder getCalendarData(String myURL, Boolean print) {
               try {
        URL rapla = new URL(myURL);
        URLConnection yc = rapla.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                yc.getInputStream()));
        String inputLine;
        StringBuilder html = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            if (print) System.out.println(inputLine);
            html.append(inputLine); 
        }
        in.close(); 
        return html;
        } catch (Exception e) {
            e.printStackTrace();
        }
     return null;
    }
}
