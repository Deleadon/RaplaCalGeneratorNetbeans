/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbw.RaplaCalGenerator;

import java.text.SimpleDateFormat;
import java.util.*;
import org.json.*;

/**
 *
 * @author schlenker
 */
public class HTMLCleaner {
    private StringBuilder html;
    private ArrayList<Integer> head = new ArrayList<Integer>(); //0->start, 1->end
    private ArrayList<Course> courses = new ArrayList<Course>();
    public HTMLCleaner (StringBuilder nraw) {
        this.html = nraw;
    }
    
    public ArrayList<Course> generateCourses (String location) {
        try {

        head.add(html.indexOf("<head"));
        head.add(html.indexOf("/head") + 6);
        html.delete(head.get(0), head.get(1));
        int index = 0;
        while(html.indexOf("<br>", index) != -1) {  //kill <br>
            int actualBr = html.indexOf("<br>");
            html.delete(actualBr, actualBr +4);
            index = actualBr;
        }
        index=0;
        while(html.indexOf("Ã¤", index) != -1) {  //kill <br>
            int actual = html.indexOf("Ã¤");
            html.replace(actual, actual+2, "ä");
            index = actual+10;
            //System.out.println("Another Ä deleted: " + html.substring(actual - 5, actual +20));
        }
        index=0;
        while(html.indexOf("Ã¼", index) != -1) {  //kill <br>
            int actual = html.indexOf("Ã¼");
            html.replace(actual, actual+2, "ü");
            index = actual+10;
            //System.out.println("Another Ä deleted: " + html.substring(actual - 5, actual +20));
        }
        index=0;
        while(html.indexOf("Ã¶", index) != -1) {  //kill <br>
            int actual = html.indexOf("Ã¶");
            html.replace(actual, actual+2, "ö");
            index = actual+10;
            //System.out.println("Another Ä deleted: " + html.substring(actual - 5, actual +20));
        }
        JSONObject myJSON = XML.toJSONObject(html.toString());
        JSONArray coursesL1 = myJSON.getJSONObject("html").getJSONObject("body").getJSONArray("div").getJSONObject(1).getJSONObject("table").getJSONObject("tbody").getJSONArray("tr");
        JSONObject itemsO = coursesL1.getJSONObject(4).getJSONArray("td").getJSONObject(0);
        //System.out.println(itemsO.keySet());
        for (int i=2;i<coursesL1.length();i++) {
            JSONArray tds = coursesL1.getJSONObject(i).getJSONArray("td");
            for (int j=0;j<tds.length();j++) {
                JSONObject entry = tds.getJSONObject(j);
                Set<String> contents = entry.keySet();
                if (contents.contains("a")) {
                    Course newCourse = new Course();
                    JSONObject actualCourse = entry.getJSONObject("a");
                    
                    JSONArray courseContent = actualCourse.getJSONArray("content");
                    newCourse.setTitle(courseContent.getString(1));
                    String courseTimes = actualCourse.getJSONObject("span").getJSONArray("div").getString(1);
                    String[] splits = courseTimes.split(" ");
                    String day = splits[1].substring(0, 2);
                    String month = splits[1].substring(3, 5);
                    String year = splits[1].substring(6, 8);
                    
                    String[] times = splits[2].split("-");                    
                    String[] startTime = times[0].split(":");
                    String[] endTime = times[1].split(":");
                    int i_day = Integer.parseInt(day);
                    int i_month = Integer.parseInt(month);
                    int i_year = Integer.parseInt(year) + 2000;
                    int i_startHours = Integer.parseInt(startTime[0]);     
                    int i_startMinutes = Integer.parseInt(startTime[1]);   
                    int i_endHours = Integer.parseInt(endTime[0]);   
                    int i_endMinutes = Integer.parseInt(endTime[1]);   
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm");
                    Date startDate = format.parse(i_year + "-"+i_month+"-"+ i_day+ " "+ i_startHours+"-"+i_startMinutes);
                    Date endDate = format.parse(i_year + "-"+i_month+"-"+ i_day+ " "+ i_endHours+"-"+i_endMinutes);
                    newCourse.setStartDate(startDate);
                    newCourse.setEndDate(endDate);
                    courses.add(newCourse);
                }
            }
        }
        return this.courses;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
