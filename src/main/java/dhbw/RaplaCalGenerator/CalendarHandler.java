/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbw.RaplaCalGenerator;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.property.DateStart;
import biweekly.property.Summary;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author schlenker
 */
public class CalendarHandler {
    private ICalendar iCal;
    private ArrayList<Course> courseList;
    int eventCount = 0;
    public CalendarHandler() {
    iCal = new ICalendar();
    courseList = new ArrayList<Course>();
    }

    public void addEvent(Course course) {
        VEvent event = new VEvent();

        Summary summary = event.setSummary(course.getTitle());
        summary.setLanguage("en-us");

        //event.setLocation(course.getLocation());
        event.setDateStart(new DateStart(course.getStartDate()));
        event.setDateEnd(course.getEndDate());
        iCal.addEvent(event);
        eventCount++;
        courseList.add(course);
    }
    public void addEventList(ArrayList<Course> courses) {
        for(int i=0;i<courses.size();i++) {
            addEvent(courses.get(i));
        }
    }
    public ICalendar getCalendar() {
        return iCal;
    }
    public void safeCalendar(String fileName) throws IOException {
        File file = new File(fileName+".ics");
        Biweekly.write(iCal).go(file);
        System.out.println("Added "+ eventCount +" Events to the file: "+ fileName+".ics");
    }
    public void printCalendar() {
        System.out.println("The Calendar contains "+eventCount+" Events:");
        for (Course course: courseList) {
            course.printCourse();
        }
    }
}
