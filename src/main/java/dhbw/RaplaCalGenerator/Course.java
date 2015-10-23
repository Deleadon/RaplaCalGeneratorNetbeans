
package dhbw.RaplaCalGenerator;

import java.util.Date;
import java.util.UUID;

/**
 *
 * @author schlenker
 */
public class Course {
    private Date startDate, endDate;
    private String title, location, prof, course;
    UUID courseID;
    public Course (){
        this.title = "Title not set";
        this.location = "Location not set";
        this.prof = "Prof not set";
        this.course = "Course not set";
        courseID = UUID.randomUUID();
    }
    public Course (String n_location){
        this.title = "Title not set";
        this.location = n_location;
        this.prof = "Prof not set";
        this.course = "Course not set";
        courseID = UUID.randomUUID();
    }
    public Course (Date start, Date end, String n_title, String n_prof, String n_course){
        this.startDate = start;
        this.endDate = end;
        this.title = n_title;
        this.prof = n_prof;
        this.course = n_course;
        courseID = UUID.randomUUID();    
    }

    public void printCourse() {
        System.out.println("-------------------------------\n"+title+"\n"+startDate.toString() + "-" + endDate.toString()+"\n"
                            +location+"\n" + prof + "\n"+course);
    }
    public UUID getCourseID() {
        return courseID;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public void setCourse(String course) {
        this.course = course;
    }
    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getProf() {
        return prof;
    }

    public String getCourse() {
        return course;
    }
}
