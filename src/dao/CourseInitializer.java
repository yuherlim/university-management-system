/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import adt.ArrayList;
import adt.CircularDoublyLinkedList;
import adt.ListInterface;
import entity.Course;
import java.util.Iterator;

/**
 *
 * @author Sia Yeong Sheng
 */
public class CourseInitializer {

    public void initializeCourseList() {
        CourseDAO courseDAO = new CourseDAO();
        ListInterface<Course> courseList = new CircularDoublyLinkedList<>();
        ArrayList<String> domainList1 = new ArrayList<>();
        ArrayList<String> domainList2 = new ArrayList<>();
        ArrayList<String> domainList3 = new ArrayList<>();
        ArrayList<String> progList1 = new ArrayList<>();
        ArrayList<String> progList2 = new ArrayList<>();
        ArrayList<String> progList3 = new ArrayList<>();
        domainList1.add("Accounting");
        domainList1.add("Add-Math");
        domainList2.add("Biology");
        domainList2.add("Chemistry");
        domainList3.add("Add-Math");
        domainList3.add("Physics");
        progList1.add("RSW");
        progList1.add("RST");
        progList1.add("RDS");
        progList2.add("RMM");
        progList2.add("RSS");
        progList3.add("RSW");
        progList3.add("RST");
        progList3.add("RDS");
        progList3.add("RMM");
        progList3.add("RSS");

        courseList.add(new Course("AACS1234", "Introduction to Computer Systems", domainList3, 3, 95.00, progList1));
        courseList.add(new Course("ABCD3333", "Data Science", domainList3, 2, 100.00, progList3));
        courseList.add(new Course("BACD1234", "Artificial Intelligence", domainList2, 2, 105.00, progList3));
        courseList.add(new Course("BASD1234", "Data Engineering", domainList1, 3, 95.00, progList2));
        courseList.add(new Course("AACS2312", "Human Resource Management ", domainList1, 4, 85.00, progList1));
        courseList.add(new Course("CASD1234", "Principles of Entrepreneurship", domainList2, 2, 95.00, progList1));
        courseList.add(new Course("MPU-1321", "Cocuriculum", domainList3, 2, 111.00, progList2));
        courseList.add(new Course("DASD1234", "Whatever", domainList2, 3, 122.00, progList3));
        courseList.add(new Course("GASD1333", "Penat", domainList1, 4, 111.00, progList1));
        courseList.add(new Course("ASDA1323", "Copy Paste", domainList1, 2, 100.00, progList1));
        courseList.add(new Course("BSAQ1211", "Hello World", domainList2, 3, 95.00, progList2));
        courseList.add(new Course("AABS1215", "Kill Your Merit Scholarship", domainList3, 8, 111.00, progList1));
        courseList.add(new Course("DACA1231", "Hello Morld", domainList3, 3, 120.00, progList2));
        courseList.add(new Course("MPU-3123", "Hello Eorld", domainList1, 4, 125.00, progList1));
        courseList.add(new Course("MPU-1213", "FYP", domainList1, 3, 122.00, progList1));
        courseList.add(new Course("BCSA1234", "Research Method", domainList2, 3, 121.00, progList3));
        courseList.add(new Course("BDAS3251", "CGPA killer", domainList2, 6, 95.00, progList3));
        courseList.add(new Course("BBAA3213", "Please don't zuo 9 me", domainList3, 2, 99.00, progList3));
        courseList.add(new Course("BSAS3124", "Industrial Monkey", domainList1, 2, 95.00, progList1));
        courseList.add(new Course("BAAA1251", "Crazy Frog", domainList2, 3, 155.00, progList2));

        courseDAO.saveToFile(courseList);
    }

    public static void main(String[] args) {

        CourseDAO courseDAO = new CourseDAO();
        CourseInitializer cI = new CourseInitializer();
        cI.initializeCourseList();
        ListInterface<Course> courseList = courseDAO.retrieveFromFile();
        

        Iterator<Course> it = courseList.getIterator();
        while(it.hasNext()) {
            System.out.println(it.next());
        }
      }
}
