/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbw.RaplaCalGenerator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author schlenker
 */
public class Interfacer {
    private JTextField startYearField,startMonthField,startDayField,endYearField,
            endMonthField,endDayField,urlField,filenameField;
    private JFrame frame = new JFrame();

        public void initUI() {
        frame.setTitle("Rapla Calendar Generator 1.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BoxLayout boxLayout = new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS);
        frame.setLayout(boxLayout);
        startYearField = new JTextField(5);
        startMonthField = new JTextField(5);
        startDayField = new JTextField(5);
        endYearField = new JTextField(5);
        endMonthField = new JTextField(5);
        endDayField = new JTextField(5);
        
        JPanel startDatePanel = new JPanel();
        JPanel welcomePanel = new JPanel();
        
        welcomePanel.add(new JLabel("Welcome to your personal calendar generator!"));
        startDatePanel.add(new JLabel("Start Date:"));
        startDatePanel.add(new JLabel("  Day:"));
        startDatePanel.add(startDayField);
        startDatePanel.add(new JLabel("  Month:"));
        startDatePanel.add(startMonthField);
        startDatePanel.add(new JLabel("Year:"));
        startDatePanel.add(startYearField);
        
        JPanel endDatePanel = new JPanel();
        endDatePanel.add(new JLabel("End Date:"));
        endDatePanel.add(new JLabel("  Day:"));
        endDatePanel.add(endDayField);
        endDatePanel.add(new JLabel("  Month:"));
        endDatePanel.add(endMonthField);
        endDatePanel.add(new JLabel("Year:"));
        endDatePanel.add(endYearField);
        
        urlField = new JTextField(30);
        JPanel keyPanel = new JPanel();
        keyPanel.add(new JLabel("Enter rapla-url:"));
        keyPanel.add(urlField);
        
        filenameField = new JTextField(10);
        JPanel filePanel = new JPanel();
        filePanel.add(new JLabel("Configure your Filename:"));
        filePanel.add(filenameField);
        
        JButton submitBtn = new JButton("Submit");
        submitBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                createCalendar();
            }
        });
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "See you next time!","Goodbye",JOptionPane.INFORMATION_MESSAGE);
                frame.setVisible(false);
                frame.dispose();
            }
        });
        JButton helpBtn = new JButton("Help");
        helpBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Hi, du siehst so aus als ob du Hilfe bräuchtest?\n"
                        + "Gib einfach das Start und End Datum ein für den Zeitraum für den du die Kalendereinträge möchtest\n"
                        + "Es macht nichts wenn du zu viele Tage angibst!\n"
                        + "Halte dich bitte an das Format: 1 10 2015, das Jahr musst du also ausschreiben ;)\n"
                        + "Bei Rapla-URL musst du einmal auf deinen Rapla-Kalender gehen und dann die URL, \n"
                        + "also die Internetadresse einfach so wie sie oben steht in das Feld kopieren\n"
                        + "z.B. https://rapla.dhbw-stuttgart.de/rapla?key=skWyEHbLgbNZMJRmmAn2OQ&prev=%3C%3C&day=15&month=10&year=2015\n"
                        + "Filename ist ein Feld in dem du eintragen musst wie das Kalender-File später heißen soll\n"
                        + "Das File selbst wird dann dort gespeichert, wo du das Programm gestartet hast\n"
                        + "Ich hoffe damit sind deine Fragen geklärt?\n"
                        + "Wenn du doch noch eine hast oder Verbesserungs-Ideen hast dann schreib mir einfach:\n"
                        + "mail@robin-schlenker.de\n"
                        + "Viel Spaß in der Uni!","Help Central",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        JPanel btnPanel = new JPanel();
        btnPanel.add(submitBtn);
        btnPanel.add(cancelBtn);
        btnPanel.add(helpBtn);
        
        frame.add(welcomePanel);
        frame.add(startDatePanel);
        frame.add(endDatePanel);
        frame.add(keyPanel);
        frame.add(filePanel);
        frame.add(btnPanel);
        frame.setSize(500, 250);
        frame.setVisible(true);
    }
        private void createCalendar() {
        StringBuilder url = new StringBuilder(urlField.getText());
        
        int startDay = Integer.parseInt(startDayField.getText());
        int startMonth = Integer.parseInt(startMonthField.getText());
        int startYear = Integer.parseInt(startYearField.getText());
        int endDay = Integer.parseInt(endDayField.getText());
        int endMonth = Integer.parseInt(endMonthField.getText());
        int endYear = Integer.parseInt(endYearField.getText());
        int[]startDate = {startDay,startMonth,startYear};
        int[]endDate = {endDay,endMonth,endYear};
        String filename = filenameField.getText();
        
        MyHTTPClient client = new MyHTTPClient();
        ArrayList<StringBuilder> calData = client.getEntries(startDate, endDate, getKey(url));
        //ArrayList<StringBuilder> calData = client.getEntries(startDate, endDate, "txB1FOi5xd1wUJBWuX8lJr58RSjvcBHHm4d21IRwE-C07jmu5c6CVIZ1HU6jFYIWhmWyEHbLgbNZMJRmmAn2OQ");
        ArrayList<Course> courses = new ArrayList();
        
        for(StringBuilder content: calData){
            HTMLCleaner cleaner = new HTMLCleaner(content);
            courses.addAll(cleaner.generateCourses("Paulinenstr"));
        }    
   
        CalendarHandler handler = new CalendarHandler();
        handler.addEventList(courses);
        handler.printCalendar();
        try{
        handler.safeCalendar(filename);
        } catch (IOException e) {
            e.printStackTrace();
        } 
        JOptionPane.showMessageDialog(frame, "Thank you for using CalGenerator 1.0","Goodbye",JOptionPane.INFORMATION_MESSAGE);
        frame.setVisible(false);
        frame.dispose();
        }
                
        private String getKey(StringBuilder url) {
            int[] key = {1,2};
            key[0] = url.indexOf("key=") +4;
            key[1] = url.indexOf("&",key[0]);
            System.out.println("key: " + url.substring(key[0], key[1]));
            return url.substring(key[0], key[1]);
        }
    
}
