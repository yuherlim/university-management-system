/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.*;
import dao.*;
import entity.*;
import boundary.*;
import java.util.Iterator;
import utility.MessageUI;

/**
 *
 * @author Yu
 */
public class ProgrammeManagement {

    private ListInterface<Programme> programmeList = new CircularDoublyLinkedList<>();
    private ProgrammeDAO programmeDAO = new ProgrammeDAO();
    private ProgrammeManagementUI programmeManagementUI = new ProgrammeManagementUI();
    String currentBatchNo = "202301";

    public ProgrammeManagement() {
        programmeList = programmeDAO.retrieveFromFile();
    }

    public void runProgrammeManagement() {
        int choice = 0;
        do {
            choice = programmeManagementUI.getMenuChoice();
            switch (choice) {
                case 0:
                    MessageUI.displayExitMessage();
                    break;
                case 1:
                    addNewProgramme();
                    break;
                case 2:
                    removeProgramme();
                    break;
                case 3:
                    findProgramme();
                    break;
                case 4:
                    ammendProgramme();
                    break;
                case 5:
                    displayProgrammes();
                    break;
                case 6:
                    addTutorialGroupToProgramme();
                    break;
                case 7:
                    removeTutorialGroupFromProgramme();
                    break;
                case 8:
                    displayTutorialGroupForProgramme();
                    break;
                case 9:
                    generateReports();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }

    private void addNewProgramme() {
        char cont;
        char confirmation;
        do {
            Programme newProgramme = programmeManagementUI.inputProgrammeDetails();
            programmeList.add(newProgramme);
            programmeManagementUI.printProgrammeDetails(newProgramme);

            System.out.println("Confirm addition of this programme?");

            confirmation = programmeManagementUI.inputConfirmation();
            if (confirmation == 'Y') {
                programmeDAO.saveToFile(programmeList);
                System.out.println("");
                System.out.println("New programme added successfully.\n");
            } else { // resets the programmeList back to original;
                programmeList = programmeDAO.retrieveFromFile();
            }

            System.out.println("Continue adding programmes?");
            cont = programmeManagementUI.inputConfirmation();
        } while (cont == 'Y');
        System.out.println("\nExiting programme addition...");
    }

    private String getAllProgrammesFromList(ListInterface<Programme> programmeList) {
        String outputStr = "";
        Iterator<Programme> it = programmeList.getIterator();
        while (it.hasNext()) {
            Programme currentProgramme = it.next();
            outputStr += currentProgramme + "\n";
        }
        return outputStr;
    }

    private String getAllProgrammesFromStack(ArrayStack<Programme> undoStackProgramme) {
        String outputStr = "";
        Iterator<Programme> it = undoStackProgramme.getIterator();
        while (it.hasNext()) {
            outputStr += it.next() + "\n";
        }
        return outputStr;
    }

    private void displayProgrammes() {
        if (programmeList.isEmpty()) {
            System.out.println("\nThere are currently no programmes.");
            return;
        }

        viewProgrammeDetails(programmeList);
    }

    private void removeProgramme() {
        if (programmeList.isEmpty()) {
            System.out.println("\nThere are currently no programmes.");
            return;
        }

        ArrayStack<Integer> undoStackPosition = new ArrayStack<>();
        ArrayStack<Programme> undoStackProgramme = new ArrayStack<>();
        ArrayList<String> codeToRemove = new ArrayList<>(); // used to modify course list if there is changes to programme code.
        boolean isRemoved = false;
        int selection;
        do {
            selection = programmeManagementUI.getRemoveProgrammeMenuChoice();
            switch (selection) {
                case 0:
                    if (!isRemoved) {
                        break;
                    }

                    if (getRemovalConfirmation(undoStackProgramme) != 'Y') {
                        programmeList = programmeDAO.retrieveFromFile(); // reset list to original.
                        break;
                    }

                    getCodeFromUndoStack(undoStackProgramme, codeToRemove, undoStackPosition);
                    updateRemovalToFile(codeToRemove);
                    break;
                case 1:
                    if (programmeList.isEmpty()) {
                        System.out.println("\nThere are currently no programmes.");
                        break;
                    }

                    Programme programmeToRemove = getProgrammeToRemove();

                    if (programmeToRemove == null) {
                        programmeManagementUI.nonexistentProductCodeMsg();
                        break;
                    }

                    if (getProgrammeRemovalConfirmation(programmeToRemove) != 'Y') {
                        System.out.println("\nRemoval of this programme cancelled.");
                        break;
                    }

                    isRemoved = removeProgrammeTemporarily(undoStackProgramme, programmeToRemove, undoStackPosition);
                    break;
                case 2:
                    if (undoStackProgramme.isEmpty()) {
                        System.out.println("\nThere is nothing to undo.");
                        break;
                    }

                    if (getUndoConfirmation(undoStackProgramme) != 'Y') {
                        System.out.println("\nUndo cancelled.");
                        break;
                    }

                    isRemoved = undoRemoval(undoStackProgramme, undoStackPosition);
                    break;
            }
        } while (selection != 0);

        System.out.println("\nExiting programme removal...");
    }

    private boolean undoRemoval(ArrayStack<Programme> undoStackProgramme, ArrayStack<Integer> undoStackPosition) {
        while (!(undoStackProgramme.isEmpty())) {
            programmeList.add(undoStackPosition.pop(), undoStackProgramme.pop());
        }
        System.out.println("\nUndo successful.");
        programmeManagementUI.listProgrammes(getAllProgrammesFromList(programmeList));
        return false;
    }

    private char getUndoConfirmation(ArrayStack<Programme> undoStackProgramme) {
        char undoConfirmation;
        programmeManagementUI.listProgrammes(getAllProgrammesFromStack(undoStackProgramme));
        System.out.println("\nAre you sure you want to undo the removal of the above programmes?");
        undoConfirmation = programmeManagementUI.inputConfirmation();
        return undoConfirmation;
    }

    private boolean removeProgrammeTemporarily(ArrayStack<Programme> undoStackProgramme, Programme programmeToRemove, ArrayStack<Integer> undoStackPosition) {
        int removedProgrammePosition = ((CircularDoublyLinkedList) programmeList).locatePosition(programmeToRemove);
        undoStackProgramme.push(programmeToRemove);
        undoStackPosition.push(removedProgrammePosition);
        programmeList.remove(programmeToRemove);
        System.out.println("\nProgramme successfully removed.");
        programmeManagementUI.listProgrammes(getAllProgrammesFromList(programmeList));
        return true;
    }

    private char getProgrammeRemovalConfirmation(Programme programmeToRemove) {
        char removeConfirmation;
        programmeManagementUI.printProgrammeDetails(programmeToRemove);
        System.out.println("\nAre you sure you want to remove the above programme?");
        removeConfirmation = programmeManagementUI.inputConfirmation();
        return removeConfirmation;
    }

    private void getCodeFromUndoStack(ArrayStack<Programme> undoStackProgramme, ArrayList<String> codeToRemove, ArrayStack<Integer> undoStackPosition) {
        // clear the undo stack data.
        while (!(undoStackProgramme.isEmpty())) {
            codeToRemove.add(undoStackProgramme.pop().getCode());
        }
        undoStackPosition.clear();
    }

    private char getRemovalConfirmation(ArrayStack<Programme> undoStackProgramme) {
        char confirmation;
        programmeManagementUI.listProgrammes(getAllProgrammesFromStack(undoStackProgramme));
        System.out.println("\nConfirm the removal of the above programmes? (this action cannot be undone.)");
        confirmation = programmeManagementUI.inputConfirmation();
        return confirmation;
    }

    private void updateRemovalToFile(ArrayList<String> codeToRemove) {
        programmeDAO.saveToFile(programmeList);
        System.out.println("\nThe above programmes has been successfully removed.");
        removeProgrammeFromCourseList(codeToRemove);
    }

    private Programme getProgrammeToRemove() {
        programmeManagementUI.listProgrammes(getAllProgrammesFromList(programmeList));
        System.out.println("\nEnter the programme code of the program to remove.");
        Programme programmeToRemove = searchByProgrammeCode(programmeList);
        return programmeToRemove;
    }
    
    private Programme getProgrammeToAddTutGroup() {
        programmeManagementUI.listProgrammes(getAllProgrammesFromList(programmeList));
        System.out.println("\nEnter the programme code of the program to remove.");
        Programme programmeToAddTutGroup = searchByProgrammeCode(programmeList);
        return programmeToAddTutGroup;
    }

    private void findProgramme() {
        if (programmeList.isEmpty()) {
            System.out.println("\nThere are currently no programmes.");
            return;
        }

        int selection;
        do {
            selection = programmeManagementUI.getFindProgrammeMenuChoice();
            switch (selection) {
                case 0:
                    System.out.println("\nExiting programme searching...");
                    break;
                case 1:
                    findByProgrammeCode();
                    break;
                case 2:
                    findByName();
                    break;
                case 3:
                    findByFaculty();
                    break;
                case 4:
                    findByProgrammeType();
                    break;
            }

        } while (selection != 0);
    }

    private void findByName() {
        String searchQuery = programmeManagementUI.inputSearchQuery();
        searchByStringQuery("name", searchQuery);
    }

    private void findByProgrammeCode() {
        System.out.println("\nEnter the programme code of the program to find.");
        Programme programmeToFind = searchByProgrammeCode(programmeList);
        if (programmeToFind == null) {
            programmeManagementUI.nonexistentProductCodeMsg();
        } else {
            System.out.println("\nSearch results: ");
            programmeManagementUI.printProgrammeDetails(programmeToFind);
        }
    }

    private void viewProgrammeDetails(ListInterface<Programme> programmeListToSearchIn) {
        int selection;
        do {
            programmeManagementUI.listProgrammes(getAllProgrammesFromList(programmeListToSearchIn));
            selection = programmeManagementUI.getViewProgrammeDetailsMenuChoice();
            switch (selection) {
                case 0:
                    System.out.println("Going back...");
                    break;
                case 1:
                    System.out.println("\nEnter the programme code of the program to view.");
                    Programme programmeToView = searchByProgrammeCode(programmeListToSearchIn);

                    if (programmeToView == null) {
                        programmeManagementUI.nonexistentProductCodeMsg();
                        break;
                    }

                    programmeManagementUI.printProgrammeDetails(programmeToView);
                    break;
            }
        } while (selection != 0);
    }

    private Programme searchByProgrammeCode(ListInterface<Programme> programmeListToSearchIn) {
        String code = programmeManagementUI.inputProgrammeCode();
        Programme programmeToSearch = programmeListToSearchIn.getEntry(new Programme(code));
        return programmeToSearch;
    }

    private void ammendProgramme() {
        if (programmeList.isEmpty()) {
            System.out.println("\nThere are currently no programmes.");
            return;
        }

        int selection;
        do {
            selection = programmeManagementUI.getModifyProgrammeDetailsMenuChoice();
            switch (selection) {
                case 0:
                    System.out.println("\nExiting programme ammendment...");
                    break;
                case 1:
                    Programme programmeToModify = getProgrammeToModify();

                    if (programmeToModify == null) {
                        programmeManagementUI.nonexistentProductCodeMsg();
                        break;
                    }

                    performProgrammeModifications(programmeToModify);
                    break;
            }
        } while (selection != 0);
    }

    private void performProgrammeModifications(Programme programmeToModify) {
        String codeBeforeModification;
        boolean codeIsModified = false;
        boolean isModified = false;
        int programmeToModifyPosition = ((CircularDoublyLinkedList) programmeList).locatePosition(programmeToModify);
        int selection;
        codeBeforeModification = programmeToModify.getCode(); // used to modify course list if there is changes to programme code.
        programmeManagementUI.printProgrammeDetails(programmeToModify);
        System.out.println("");
        do {
            selection = programmeManagementUI.getModifyProgrammeMenuChoice();
            switch (selection) {
                case 0:
                    updateModificationsToFile(isModified, codeIsModified, codeBeforeModification, programmeToModify);
                    break;
                case 1:
                    programmeToModify.setCode(programmeManagementUI.inputUniqueProgrammeCode());
                    isModified = true;
                    codeIsModified = true;
                    break;
                case 2:
                    programmeToModify.setName(programmeManagementUI.inputProgrammeName());
                    isModified = true;
                    break;
                case 3:
                    programmeToModify.setFaculty(programmeManagementUI.inputProgrammeFaculty());
                    isModified = true;
                    break;
                case 4:
                    programmeToModify.setProgrammeType(programmeManagementUI.inputProgrammeType());
                    isModified = true;
                    break;
                case 5:
                    programmeToModify.setDescription(programmeManagementUI.inputProgrammeDescription());
                    isModified = true;
                    break;
                case 6:
                    programmeToModify.setDuration(programmeManagementUI.inputProgrammeDuration());
                    isModified = true;
                    break;
                case 7:
                    programmeToModify.setTotalFee(programmeManagementUI.inputProgrammeTotalFees());
                    isModified = true;
                    break;
            }
            programmeManagementUI.printProgrammeDetails(programmeList.getEntry(programmeToModifyPosition));
        } while (selection != 0);
    }

    private Programme getProgrammeToModify() {
        programmeManagementUI.listProgrammes(getAllProgrammesFromList(programmeList));
        System.out.println("\nEnter a programme code to modify programme details.");
        Programme programmeToModify = searchByProgrammeCode(programmeList);
        return programmeToModify;
    }

    private void addTutorialGroupToProgramme() {
        if (programmeList.isEmpty()) {
            System.out.println("\nThere are currently no programmes.");
            return;
        }
        
        int selection;
        do {
            selection = programmeManagementUI.getAddTutorialGroupMenuChoice();
            switch (selection) {
                case 0:
                    System.out.println("\nExiting tutorial group addition...");
                    break;
                case 1:
                    Programme programmeToAddTutGroup = getProgrammeToAddTutGroup();
                    
                    if (programmeToAddTutGroup == null) {
                        programmeManagementUI.nonexistentProductCodeMsg();
                        break;
                    }
                    
                    performTutGroupAddition(programmeToAddTutGroup);
                    break;
            }
        } while (selection != 0);
    }

    

    private void removeTutorialGroupFromProgramme() {
//        if (programmeList.isEmpty()) {
//            System.out.println("\nThere are currently no programmes.");
//            return;
//        }
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void displayTutorialGroupForProgramme() {
//        if (programmeList.isEmpty()) {
//            System.out.println("\nThere are currently no programmes.");
//            return;
//        }
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void generateReports() {
//        if (programmeList.isEmpty()) {
//            System.out.println("\nThere are currently no programmes.");
//            return;
//        }
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static void main(String[] args) {
        ProgrammeManagement programmeManagement = new ProgrammeManagement();
        programmeManagement.runProgrammeManagement();
    }

    private void modifyProgrammeInCourseList(String code, String newCode) {
        CourseDAO courseDAO = new CourseDAO();
        ListInterface<Course> courseList = courseDAO.retrieveFromFile();

        Iterator<Course> it = courseList.getIterator();
        while (it.hasNext()) {
            Course currentCourse = it.next();
            ArrayList<String> courseProgrammeList = currentCourse.getProgrammes();
            if (courseProgrammeList != null) {
                courseProgrammeList.replace(code, newCode);
            }
        }
        courseDAO.saveToFile(courseList);
    }

    private void removeProgrammeFromCourseList(ArrayList<String> codeToRemove) {
        CourseDAO courseDAO = new CourseDAO();
        ListInterface<Course> courseList = courseDAO.retrieveFromFile();

        Iterator<Course> it = courseList.getIterator();
        while (it.hasNext()) {
            Course currentCourse = it.next();
            ArrayList<String> courseProgrammeList = currentCourse.getProgrammes();
            if (courseProgrammeList != null) {
                for (int i = 1; i <= codeToRemove.getNumberOfEntries(); i++) {
                    courseProgrammeList.remove(codeToRemove.getEntry(i));
                }
            }
        }
        courseDAO.saveToFile(courseList);
    }

    private void findByFaculty() {
        String searchQuery = programmeManagementUI.inputSearchQuery();
        searchByStringQuery("faculty", searchQuery);
    }

    // performs search for the query on the column given.
    private void searchByStringQuery(String column, String query) {
        ListInterface<Programme> searchResults = new CircularDoublyLinkedList<>();

        Iterator<Programme> it = programmeList.getIterator();
        while (it.hasNext()) {
            Programme currentProgramme = it.next();
            switch (column) {
                case "name":
                    if (currentProgramme.getName().toUpperCase().contains(query)) {
                        searchResults.add(currentProgramme);
                    }
                    break;
                case "faculty":
                    if (currentProgramme.getFaculty().toUpperCase().contains(query)) {
                        searchResults.add(currentProgramme);
                    }
                    break;
                case "programmeType":
                    if (currentProgramme.getProgrammeType().equalsIgnoreCase(query)) {
                        searchResults.add(currentProgramme);
                    }
                    break;
            }

        }

        System.out.println("\nSearch results: ");
        if (searchResults.isEmpty()) {
            System.out.printf("\nThere are no results found for \"%s\"\n", query);
        } else {
            viewProgrammeDetails(searchResults);
        }

    }

    private void findByProgrammeType() {
        String searchQuery = programmeManagementUI.inputProgrammeType();
        searchByStringQuery("programmeType", searchQuery);
    }

    private void updateModificationsToFile(boolean isModified, boolean codeIsModified, String codeBeforeModification, Programme programmeToModify) {
        char confirmation;
        if (isModified) {
            System.out.println("Confirm the following modification?");
            confirmation = programmeManagementUI.inputConfirmation();
            if (confirmation == 'Y') {
                programmeDAO.saveToFile(programmeList);
                System.out.println("\nProgramme ammendment successful.");
                if (codeIsModified) {
                    modifyProgrammeInCourseList(codeBeforeModification, programmeToModify.getCode());
                }
            } else { // reset list to original.
                programmeList = programmeDAO.retrieveFromFile();
            }
        }
    }

    private void performTutGroupAddition(Programme programmeToAddTutGroup) {
        ArrayList<String> currentTutorialGroupList = programmeToAddTutGroup.get
        
    }
}

/*
RFS
Bachelor of Science (Hons) in Food Science
Faculty of Applied Sciences (FOAS)
This programme applies the pure science subjects, such as chemistry, biochemistry, nutrition, biology and microbiology to the study of the nature, properties and composition of foods.
3
30000
120
 */
