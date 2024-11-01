/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import adt.*;
import boundary.*;
import entity.*;
import dao.*;
import java.util.Iterator;
import java.util.Scanner;
import utility.MessageUI;

/**
 *
 * @author Sia Yeong Sheng
 */
public class CourseManagement {

    private ListInterface<Course> courseList = new CircularDoublyLinkedList<>();
    private ListInterface<Programme> progList = new CircularDoublyLinkedList<>();
    private ProgrammeDAO progDAO = new ProgrammeDAO();
    private CourseDAO courseDAO = new CourseDAO();
    private CourseManagementUI courseUI = new CourseManagementUI();
    private ListInterface<String> programmes = new ArrayList<>();
    private ListInterface<String> domainKnowledges = new ArrayList<>();
    private CourseInputValidator validator = new CourseInputValidator();
    Scanner scan = new Scanner(System.in);

    public CourseManagement() {
        courseList = courseDAO.retrieveFromFile();
        progList = progDAO.retrieveFromFile();
        //set the programmes string array based on the number of programme available in programme class 
        for (int i = 1; i <= progList.getNumberOfEntries(); i++) {
         
            programmes.add(progList.getEntry(i).getCode());
        }
        
        //assume that it will be only 5, and is fixed domain knowledge requirements
        domainKnowledges.add("Accounting");
        domainKnowledges.add("Add.Math");
        domainKnowledges.add("Biology");
        domainKnowledges.add("Chemistry");
        domainKnowledges.add("Physics");
        
    }

    //main functions
    public void addNewCourse() {
        String code, name;
        int creditHR;
        double feePerCH;
        courseUI.displayAddCourseMsg();
        code = courseUI.inputCourseCode(courseList);
        name = courseUI.inputCourseName();

        ListInterface<String> inputDomains = inputDomainLoop(domainKnowledges);

        creditHR = courseUI.inputCreditHour();
        feePerCH = courseUI.inputFeePerCreditHour();

        ListInterface<String> inputProgList = programmeInputList(programmes);

        Course course = new Course(code, name, (ArrayList<String>) inputDomains, creditHR, feePerCH, (ArrayList<String>) inputProgList);
        //Not using annoymous object in add due to the need to display the new course added
        courseList.add(course);
        courseDAO.saveToFile(courseList);
        System.out.println(course + "successfully added.\n");
        addCourseInProgramme(course, progList);
        progDAO.saveToFile(progList);

    }

    public void removeCourse() {
        StackInterface<Integer> undoStackPosition = new ArrayStack();
        StackInterface<Course> undoStackCourse = new ArrayStack();
        Course course;
        int selection;
        do {          
            courseUI.displayAllCourse(courseList);
            selection = courseUI.deleteCourseMenuSelection();
            int entryAt;

            switch (selection) {
                case 1:
                    entryAt = courseUI.deleteByNo(courseList);
                    if (entryAt >= 1 && entryAt <= courseList.getNumberOfEntries()) {
                        undoStackPosition.push(entryAt);
                        undoStackCourse.push(courseList.getEntry(entryAt));
                        courseList.remove(entryAt);
                        MessageUI.courseDeleteMsg();
                    }
                    break;

                case 2:
                    ListInterface<Course> temp = courseUI.filterCourseByProgramme(courseList);
                    course = courseUI.deleteFilteredListByNo(temp);
                    if (course != null) {
                        undoStackPosition.push(((CircularDoublyLinkedList) (courseList)).locatePosition(course));
                        undoStackCourse.push(course);
                        courseList.remove(course);
                        MessageUI.courseDeleteMsg();
                    }
                    break;

                case 3:
                    course = courseUI.searchCourseByCode(courseList);
                    if (course != null) {
                        undoStackPosition.push(((CircularDoublyLinkedList) (courseList)).locatePosition(course));
                        undoStackCourse.push(course);
                        courseList.remove(course);
                        MessageUI.courseDeleteMsg();
                    }
                    break;

                case 4:
                    if (!undoStackCourse.isEmpty()) {
                        char undo = courseUI.undo(undoStackCourse);
                        if (undo == 'Y') {
                            courseList.add(undoStackPosition.pop(), undoStackCourse.pop());
                            MessageUI.courseUndoDeleteMsg();
                        } else {
                            System.out.println("Exiting undo");
                        }
                    } else {
                        System.out.println("Nothing to undo");
                        MessageUI.pause();
                    }
                    break;

                case 0:
                    if (!undoStackCourse.isEmpty()) {
                        char confirmation = courseUI.exitConfirmationForDelete();
                        if (confirmation == 'Y') {
                            while (!undoStackCourse.isEmpty()) {
                                removeACourseFromProgrammes(undoStackCourse.pop(), progList, progDAO);
                            }
                            undoStackPosition.clear();
                            MessageUI.displayExit();
                            MessageUI.savingIntoFile();
                            courseDAO.saveToFile(courseList);
                        } else {
                            selection = -1;
                        }
                    }
                    break;

            }
        } while (selection != 0);

        //current available cost
        //option to remove specific course by number
        //option to remove specific course by ID
        //check if the ID does exist
        //return fail to delete / delete success
        //write the update into file
    }

    public void modifyCourse() {
        courseUI.displayModifyCourseMenuMsg();
        courseUI.displayAllCourse(courseList);
        Course target = courseUI.searchCourseByCode(courseList);

        if (target != null) {
            int selection = -1;
            do {
                selection = courseUI.getModifyMenuSelection(target);
                switch (selection) {
                    case 1:
                        modifyCourseName(courseList, target);
                        break;

                    case 2:
                        modifyCourseDomainList(domainKnowledges, target);
                        break;

                    case 3:
                        modifyCourseCH(courseList, target);
                        break;

                    case 4:
                        modifyCourseFeePCH(courseList, target);
                        break;

                    case 5:
                        modifyCourseProgList(target);
                        break;

                    case 0:
                        MessageUI.displayExit();
                        MessageUI.savingIntoFile();
                        progDAO.saveToFile(progList);
                        courseDAO.saveToFile(courseList);
                        break;
                }
            } while (selection != 0);
        }
    }

    public void displayCourse() {
        int selection = -1;
        do {
            selection = courseUI.getDisplayCourseMenuSelection();
            switch (selection) {
                case 1:
                    courseUI.displayAllCourse(courseList);
                    break;
                case 2:

                    courseUI.displayCourseByProgramme(courseList);
                    break;
                case 3:
                    courseUI.displayACourse(courseList);
                    break;

                case 0:
                    MessageUI.displayExit();
                    break;

                default:
                    MessageUI.displayInvalidChoiceMessage();
                    break;
            }
        } while (selection != 0);
    }

    public void getReport() {
        int selection = -1;
        ListInterface<Course> sortedList;
        do {
            selection = courseUI.getReportMenuChoice();
            switch (selection) {
                case 1:
                    courseUI.report(courseList, programmes);
                    MessageUI.pause();
                    break;

                case 2:
                    sortedList = sortByCodeOrCH(courseList, selection);
                    courseUI.report((ArrayList) sortedList, programmes);
                    MessageUI.pause();
                    break;

                case 3:
                    sortedList = sortByCodeOrCH(courseList, selection);
                    courseUI.report((ArrayList) sortedList, programmes);
                    MessageUI.pause();
                    break;

                case 0:
                    MessageUI.displayExitMessage();
                    break;

                default:
                    MessageUI.displayInvalidChoiceMessage();
                    break;
            }

        } while (selection != 0);     
    }

    public static void main(String[] args) {
        CourseManagement courseManagement = new CourseManagement();
        CourseManagementUI courseManagementUI = new CourseManagementUI();
        int selection = -1;
        do {
//            System.out.println(courseManagement.progList.getFirst().getCourses());
//            System.out.println(courseManagement.progList.getLast());
//            System.out.println(courseManagement.progList.getLast().getCourses());

            selection = courseManagementUI.getCourseMainMenuChoice();
            switch (selection) {
                case 1:
                    courseManagement.addNewCourse();
                    break;

                case 2:
                    courseManagement.modifyCourse();
                    break;

                case 3:
                    courseManagement.removeCourse();
                    break;

                case 4:
                    courseManagement.displayCourse();
                    break;

                case 5:
                    courseManagement.getReport();
                    break;

                case 0:
                    MessageUI.displayExitMessage();
                    break;

                default:
                    MessageUI.displayInvalidChoiceMessage();
                    break;
            }

        } while (selection != 0);

    }

    



//support control functions 
    // add
    private ListInterface<String> inputDomainLoop(ListInterface<String> domainList) {
        int domainSelection;
        ListInterface<String> domains = new ArrayList<>();
        do {
            domainSelection = courseUI.inputDomain();
            boolean notDuplicated = true;
            if (domainSelection >= 1 && domainSelection <= domainList.getNumberOfEntries()) {
                if (domains.getNumberOfEntries() > 0) {
                    notDuplicated = validator.checkExistInList(domains, domainList.getEntry(domainSelection));
                }

            } else if (domainSelection == 0) {
                System.out.println("Exiting domain input\n");
            } else {
                MessageUI.displayInvalidChoiceMessage();
            }

            if ((domainSelection >= 1 && domainSelection <= domainList.getNumberOfEntries()) && notDuplicated) {
                domains.add(domainList.getEntry(domainSelection));
                System.out.println(domainList.getEntry(domainSelection) + " successfully added.\n");
            } else if (!notDuplicated) {
                System.out.println("The domain already in the list.\n");
            }

        } while (domainSelection != 0);

        return domains;
    }

    private ListInterface<String> programmeInputList(ListInterface<String> programmes) {
        int programmeSelection;
        ListInterface<String> result = new ArrayList<>();
        do {
            programmeSelection = courseUI.inputProgramme(programmes, 'I');
            boolean notDuplicated = true;
            if (programmeSelection >= 1 && programmeSelection <= programmes.getNumberOfEntries()) {
                if (result.getNumberOfEntries() > 0) {
                    notDuplicated = validator.checkExistInList(result, programmes.getEntry(programmeSelection));
                }

            } else if (programmeSelection == 0) {
                if (result.getNumberOfEntries() > 0) {
                    System.out.println("Exiting programme input\n");
                } else {
                    System.out.println("Programme list cannot be empty.");
                    programmeSelection = -1;
                }
            } else {
                MessageUI.displayInvalidChoiceMessage();
            }

            if ((programmeSelection >= 1 && programmeSelection <= programmes.getNumberOfEntries()) && notDuplicated) {
                result.add(programmes.getEntry(programmeSelection));
                System.out.println(programmes.getEntry(programmeSelection) + " successfully added.");
            } else if (!notDuplicated) {
                System.out.println("The programme already in the list.\n");
            }

        } while (programmeSelection != 0);

        return result;
    }

    //modify
    private void modifyCourseName(ListInterface<Course> courseList, Course course) {
        
        int targetPos = ((CircularDoublyLinkedList) courseList).locatePosition(course);
        String beforeName = course.getCourseName();
        course.setCourseName(courseUI.inputCourseName());
        String afterName = course.getCourseName();
        courseList.replace(targetPos, course);
        System.out.println("\nThe course name has been changed from \"" +beforeName+ "\" to \"" + afterName+"\".");
        MessageUI.courseModificationMsg();
    }

    private void modifyCourseCH(ListInterface<Course> courseList, Course course) {

        int targetPos = ((CircularDoublyLinkedList) courseList).locatePosition(course);
        int beforeCH = course.getCreditHR();
        course.setCreditHR(courseUI.inputCreditHour());
        int afterCH = course.getCreditHR();
        courseList.replace(targetPos, course);
        System.out.println("\nThe course credit hour has been changed from " +beforeCH+ " to " + afterCH);
        MessageUI.courseModificationMsg();
    }

    private void modifyCourseFeePCH(ListInterface<Course> courseList, Course course) {

        int targetPos = ((CircularDoublyLinkedList) courseList).locatePosition(course);
        double beforeCourseFeePCH = course.getFeePCH();
        course.setFeePCH(courseUI.inputFeePerCreditHour());
        double afterCourseFeePCH = course.getFeePCH();
        courseList.replace(targetPos, course);
        System.out.println("\nThe course fee per credit hour has been changed from " +beforeCourseFeePCH+ " to " + afterCourseFeePCH);
        MessageUI.courseModificationMsg();
    }

    private void modifyCourseDomainList(ListInterface<String> domainList, Course course) {

        int selection = -1;

        do {
            MessageUI.courseTopDivider();
            System.out.println("Modifying " + course.getCourseCode() + " domain knowledge list");
            MessageUI.courseBtmDivider();
            selection = courseUI.addOrDelDLorPL();
            switch(selection){
                case 0:
                    MessageUI.displayExit();
                    break;
                    
                case 1:
                    modifyCourseDomainListSuppFunc(selection, course, domainList);
                    break;
                    
                case 2:
                    modifyCourseDomainListSuppFunc(selection, course, domainList);
                    break;
                    
                default:
                    MessageUI.displayInvalidChoiceMessage();
                    break;
            }       
        } while (selection != 0);
    }
    
    private void modifyCourseDomainListSuppFunc(int selection, Course course, ListInterface<String> domainList){
                System.out.println("\nCurrent domain knowledge required for the course: ");
                System.out.println(course.getRequiredDomainKnowledge());             
                ListInterface<String> inputDomainList = course.getRequiredDomainKnowledge();
                ListInterface<String> updateDomainList = listForModfication(inputDomainList);
                int noOfEntry = courseUI.inputDomain(); //user input will return int value to access predifined domain list;
                boolean notExist = true, modification = false;
                if (noOfEntry >= 1 && noOfEntry <= domainList.getNumberOfEntries()) {
                    notExist = validator.checkExistInList(inputDomainList, domainList.getEntry(noOfEntry));

                    if (selection == 1) {
                        if (notExist) {
                            updateDomainList.add(domainList.getEntry(noOfEntry));
                            System.out.println(domainList.getEntry(noOfEntry) + " has been added.");
                            modification = true;                              
                        } else {
                            System.out.println("Existing domain");
                        }

                    } else if (selection == 2) {
                        if (!notExist) {
                            updateDomainList.remove(domainList.getEntry(noOfEntry));
                            System.out.println(domainList.getEntry(noOfEntry) + " has been removed.");
                            modification = true;
                        } else {
                            System.out.println("Domain not in the list");
                        }
                    }
                } else if (noOfEntry == 0) {
                    System.out.println("\nExiting domain list modification.");
                } else {
                    System.out.println("\nInvalid selection.");
                }
                if (modification) {
                    course.setRequiredDomainKnowledge((ArrayList<String>) updateDomainList);                 
                    MessageUI.courseModificationMsg();
                }
    }

    private void modifyCourseProgList(Course course) {

        int selection = -1;

        do {
            MessageUI.courseTopDivider();
            System.out.println("Modifying " + course.getCourseCode() + " programme list");
            MessageUI.courseBtmDivider();
            selection = courseUI.addOrDelDLorPL();
            switch (selection) {
                case 0:
                    MessageUI.displayExit();
                    break;

                case 1:
                    modifyCourseProgListSuppFunc(selection, course);
                    break;

                case 2:
                    modifyCourseProgListSuppFunc(selection, course);
                    break;

                default:
                    MessageUI.displayInvalidChoiceMessage();
                    break;

            }
        } while (selection != 0);

    }

    private void modifyCourseProgListSuppFunc(int selection, Course course) {    
        ListInterface<String> inputProgList = course.getProgrammes();
        ListInterface<String> updateProgList = listForModfication(inputProgList);
        boolean notExist = true, modification = false;
        System.out.println("\nCurrent programme list: ");
        System.out.println(inputProgList);
              
        if (selection == 1) {
            int noOfEntry = courseUI.inputProgramme(programmes, 'I'); 
            if (noOfEntry >= 1 && noOfEntry <= programmes.getNumberOfEntries()) {
                notExist = validator.checkExistInList(inputProgList, programmes.getEntry(noOfEntry));
                if (notExist) {               
                    updateProgList.add(programmes.getEntry(noOfEntry));           
                    System.out.println(programmes.getEntry(noOfEntry) + " has been added.");
                    modification = true;
                } else {
                    System.out.println("Existing programme");
                }
            } else if (noOfEntry == 0) {
                System.out.println("\nExiting programme list modification.");
            } else {
                System.out.println("\nInvalid selection.");
            }

        } else {
            int noOfEntry = courseUI.inputProgramme(programmes, 'D'); //user input will return int value to access predifined programmes list;
            if (noOfEntry >= 1 && noOfEntry < programmes.getNumberOfEntries()) {
                notExist = validator.checkExistInList(inputProgList, programmes.getEntry(noOfEntry));
                if (!notExist) {
                    updateProgList.remove(programmes.getEntry(noOfEntry));
                    System.out.println(programmes.getEntry(noOfEntry) + " has been removed.");
                    modification = true;
                } else {
                    System.out.println("Programme not in the list");

                }
            } else if (noOfEntry == 0) {
                System.out.println("\nExiting programme list modification.");
            } else {
                System.out.println("\nInvalid selection.");
            }

        }
        if (modification) {
            course.setProgrammes((ArrayList<String>) updateProgList);           
            modifyCourseInProgramme(course, progList, selection);
            MessageUI.courseModificationMsg();
        }
    }

    private ListInterface<String> listForModfication(ListInterface<String> originalList){
        //need to create a new list otherwise the reference address of list will cause the entire
        //course who share the same list to be changed. NECSSARY BECAUSE OF THE PREDEFINED DATA IN THE
        //CourseInitializer class.
        ArrayList<String> modifyingList = new ArrayList<>();
        for(int i=1;i<=originalList.getNumberOfEntries();i++){
            modifyingList.add(originalList.getEntry(i));
        }
        
        return modifyingList;
    }
    
    //use to modify course list in the programme list
    private void modifyCourseInProgramme(Course course, ListInterface<Programme> progList, int addOrDelete) {
        if (addOrDelete == 1) {
            addCourseInProgramme(course, progList);
        } else {           
            removeCourseInProgramme(course, progList);
        }
    }

    private void addCourseInProgramme(Course course, ListInterface<Programme> progList) {
        Iterator<Programme> it = progList.getIterator();
        while (it.hasNext()) {
            Programme currentProgramme = it.next();
            String currentProgrammeCode = currentProgramme.getCode();
            if (currentProgramme.getCourses() != null) {
                for (int i = 1; i <= course.getProgrammes().getNumberOfEntries(); i++) {
                    if (course.getProgrammes().getEntry(i).equals(currentProgrammeCode) && !currentProgramme.getCourses().contains(course.getCourseCode())) {
                        currentProgramme.getCourses().add(course.getCourseCode());
                        System.out.println("Course " + course.getCourseCode() + " has been added into " + currentProgrammeCode + " course list.");
                    }
                }
            } else {
                ArrayList<String> newList = new ArrayList<>();
                newList.add(course.getCourseCode());
                currentProgramme.setCourses(newList);
            }
        }
    }

    private void removeCourseInProgramme(Course course, ListInterface<Programme> progList) {
        Iterator<Programme> it = progList.getIterator();
        while (it.hasNext()) {
            Programme currentProgramme = it.next();
            ArrayList<String> courseListInProgramme = currentProgramme.getCourses();
            if (courseListInProgramme != null) {
                for (int i = 1; i <= courseListInProgramme.getNumberOfEntries(); i++) {
                    if (courseListInProgramme.contains(course.getCourseCode()) && !course.getProgrammes().contains(currentProgramme.getCode())) {
                        //if the couselist in the curernt programme does cotains the course code and
                        //if the current course doesn;t cotains the current programme code in programme list
                        courseListInProgramme.remove(course.getCourseCode());
                        currentProgramme.setCourses(courseListInProgramme);
                        System.out.println("Course " + course.getCourseCode() + " has been removed from " + currentProgramme.getCode() + " course list due to the modification.\n");                        
                    }
                }
            }
        }
    }

    //Delete course
    private void removeACourseFromProgrammes(Course course, ListInterface<Programme> progList, ProgrammeDAO progDAO) {
        Iterator<Programme> it = progList.getIterator();
        while (it.hasNext()) {
            Programme currentProgramme = it.next();
            if (currentProgramme == null) {
                ArrayList<String> courseListInProgramme = currentProgramme.getCourses();

                if (courseListInProgramme.contains(course.getCourseCode())) {
                    courseListInProgramme.remove(course.getCourseCode());
                    currentProgramme.setCourses(courseListInProgramme);
                }
            }
        }

        progDAO.saveToFile(progList);
    }

    private ListInterface<Course> sortByCodeOrCH(ListInterface<Course> courseList, int selection) {
//        ListInterface<Course> sorted = convertToArrayList(courseList);
//        for (int i = 1; i < sorted.getNumberOfEntries(); i++) {
//            for (int j = i + 1; j <= sorted.getNumberOfEntries(); j++) {
//                if (selection == 2) {
//                    if (sorted.getEntry(i).getCourseCode().compareTo(sorted.getEntry(j).getCourseCode()) > 0) {
//                        swap(sorted, i, j);
//                    }
//                } else {
//                    if (sorted.getEntry(i).getCreditHR() > sorted.getEntry(j).getCreditHR()) {
//                        swap(sorted, i, j);
//                    }
//                }
//            }
//        }
        ListInterface<Course> sorted = convertToArrayList(courseList);

        if (selection == 2) {
            for (int i = 1; i <= sorted.getNumberOfEntries(); i++) {
                int j = i;
                String currentCourseCode = sorted.getEntry(j).getCourseCode();    
                
                while (j > 1 && currentCourseCode.compareTo(sorted.getEntry(j-1).getCourseCode()) < 0) {
                    //if current course code is smaller than previous course code, swap their position
                    swap(sorted, j);
                    j--;
                }
            }
        } else {
            for (int i = 1; i <= sorted.getNumberOfEntries(); i++) {
                int j = i;
                int currentCourseCH = sorted.getEntry(j).getCreditHR();
                
                while (j > 1 && currentCourseCH < sorted.getEntry(j-1).getCreditHR()) {
                    //if current course credit hour is lower than previous course hour, swap their position
                    swap(sorted, j);
                    j--;
                }
            }
        }

        return sorted;
    }
    
//    private void swap(ListInterface<Course> sorted,int i,int j){
//        Course temp = sorted.getEntry(i);
//        sorted.replace(i, sorted.getEntry(j));
//        sorted.replace(j, temp);
    private void swap(ListInterface<Course> sorted,int j) {
        Course temp = sorted.getEntry(j);
        sorted.replace(j, sorted.getEntry(j - 1));
        sorted.replace(j-1, temp);
   
    }

    private ListInterface<Course> convertToArrayList(ListInterface<Course> courseList) {
        ListInterface<Course> presort = new ArrayList<>();
        Iterator<Course> it = courseList.getIterator();
        while (it.hasNext()) {
            Course course = it.next();
            presort.add(course);
        }
        return presort;
    }
}
