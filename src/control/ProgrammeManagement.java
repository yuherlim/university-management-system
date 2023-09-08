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
        int selection;
        do {
            selection = programmeManagementUI.getAddProgrammeMenuChoice();
            switch (selection) {
                case 0:
                    System.out.println("\nExiting programme addition...");
                    break;
                case 1:
                    performProgrammeAddition();
                    break;
            }
        } while (selection != 0);
    }

    private void performProgrammeAddition() {
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

    private String getAllTutGroupAndProgrammesFromStack(ArrayStack<Programme> undoStackProgramme, ArrayStack<String> undoStackTutGroup) {
        String outputStr = "";
        Iterator<Programme> itProgramme = undoStackProgramme.getIterator();
        Iterator<String> itTutGroup = undoStackTutGroup.getIterator();
        while (itProgramme.hasNext()) {
            Programme currentProgramme = itProgramme.next();
            outputStr += String.format("%-4s %-80s %-35s", currentProgramme.getCode(), currentProgramme.getName(), itTutGroup.next()) + "\n";
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
        ArrayList<String> codeToRemove = new ArrayList<>(); // used to modify course list and tutorial group list if there is changes to programme code.
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
                        programmeManagementUI.nothingToUndoMsg();
                        break;
                    }

                    if (getUndoConfirmation(undoStackProgramme) != 'Y') {
                        programmeManagementUI.undoCancelledMsg();
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

    private boolean undoTutGroupRemoval(ArrayStack<Programme> undoStackProgramme, ArrayStack<String> undoStackTutGroup) {
        while (!(undoStackProgramme.isEmpty())) {
            undoStackProgramme.pop().getTutorialGroups().add(undoStackTutGroup.pop());
        }
        System.out.println("\nUndo successful.");
        programmeManagementUI.listTutGroupsForProgrammes(getTutGroupsForAllProgrammes(programmeList));
        return false;
    }

    private char getUndoConfirmation(ArrayStack<Programme> undoStackProgramme) {
        char undoConfirmation;
        programmeManagementUI.listProgrammes(getAllProgrammesFromStack(undoStackProgramme));
        System.out.println("\nAre you sure you want to undo the removal of the above programmes?");
        undoConfirmation = programmeManagementUI.inputConfirmation();
        return undoConfirmation;
    }

    private char getUndoTutGroupRemovalConfirmation(ArrayStack<Programme> undoStackProgramme, ArrayStack<String> undoStackTutGroup) {
        char undoConfirmation;
        programmeManagementUI.listTutGroupsForProgrammes(getAllTutGroupAndProgrammesFromStack(undoStackProgramme, undoStackTutGroup));
        System.out.println("\nAre you sure you want to undo the removal of the above tutorial groups from their respective programmes?");
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

    private boolean removeProgrammeTutGroupTemporarily(ArrayStack<Programme> undoStackProgramme, Programme programmeToRemoveTutGroup, ArrayStack<String> undoStackTutGroup) {
        undoStackProgramme.push(programmeToRemoveTutGroup);
        undoStackTutGroup.push(programmeToRemoveTutGroup.getTutorialGroups().getLast());
        programmeToRemoveTutGroup.getTutorialGroups().removeLast();
        System.out.println("\nLatest tutorial group successfully removed from programme.");
        programmeManagementUI.listTutGroupsForProgrammes(getTutGroupsForOneProgramme(programmeToRemoveTutGroup));
        return true;
    }

    private char getProgrammeRemovalConfirmation(Programme programmeToRemove) {
        char removeConfirmation;
        programmeManagementUI.printProgrammeDetails(programmeToRemove);
        System.out.println("\nAre you sure you want to remove the above programme?");
        removeConfirmation = programmeManagementUI.inputConfirmation();
        return removeConfirmation;
    }

    private char getTutGroupRemovalConfirmation(Programme programmeToRemoveTutGroup) {
        char removeConfirmation;
        programmeManagementUI.listTutGroupsForProgrammes(getTutGroupsForOneProgramme(programmeToRemoveTutGroup));
        System.out.println("\nAre you sure you want to remove a tutorial group for the above programme?");
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

    private void getTutGroupCodeFromUndoStack(ArrayStack<Programme> undoStackProgramme, ArrayList<String> tutGroupCodeToRemove, ArrayStack<String> undoStackTutGroup) {
        // clear the undo stack data.
        while (!(undoStackProgramme.isEmpty())) {
            String currentTutGroupCode = currentBatchNo + undoStackProgramme.pop().getCode() + undoStackTutGroup.pop();
            tutGroupCodeToRemove.add(currentTutGroupCode);
        }
    }

    private char getRemovalConfirmation(ArrayStack<Programme> undoStackProgramme) {
        char confirmation;
        programmeManagementUI.listProgrammes(getAllProgrammesFromStack(undoStackProgramme));
        System.out.println("\nConfirm the removal of the above programmes? (this action cannot be undone.)");
        confirmation = programmeManagementUI.inputConfirmation();
        return confirmation;
    }

    private char getTutGroupRemovalConfirmation(ArrayStack<Programme> undoStackProgramme, ArrayStack<String> undoStackTutGroup) {
        char confirmation;
        programmeManagementUI.listTutGroupsForProgrammes(getAllTutGroupAndProgrammesFromStack(undoStackProgramme, undoStackTutGroup));
        System.out.println("\nConfirm the removal of the above tutorial groups from their respective programmes? (this action cannot be undone.)");
        confirmation = programmeManagementUI.inputConfirmation();
        return confirmation;
    }

    private void updateRemovalToFile(ArrayList<String> codeToRemove) {
        programmeDAO.saveToFile(programmeList);
        System.out.println("\nThe above programmes has been successfully removed.");
        removeProgrammeFromCourseList(codeToRemove);
        removeProgrammeFromTutGroup(codeToRemove);
    }

    private void updateTutGroupRemovalToFile(ArrayList<String> tutGroupCodeToRemove) {
        programmeDAO.saveToFile(programmeList);
        System.out.println("\nThe above tutorial groups has been successfully removed from their respective programmes.");
        removeTutGroupFromTutGroupList(tutGroupCodeToRemove);
    }

    private Programme getProgrammeToRemove() {
        programmeManagementUI.listProgrammes(getAllProgrammesFromList(programmeList));
        System.out.println("\nEnter the programme code of the program to remove.");
        Programme programmeToRemove = searchByProgrammeCode(programmeList);
        return programmeToRemove;
    }

    private Programme getProgrammeToRemoveTutGroup() {
        programmeManagementUI.listTutGroupsForProgrammes(getTutGroupsForAllProgrammes(programmeList));
        System.out.println("\nEnter the programme code of the program to remove a tutorial group.");
        Programme programmeToRemoveTutGroup = searchByProgrammeCode(programmeList);
        return programmeToRemoveTutGroup;
    }

    private Programme getProgrammeToAddTutGroup() {
        programmeManagementUI.listTutGroupsForProgrammes(getTutGroupsForAllProgrammes(programmeList));
        System.out.println("\nEnter the programme code of the program to add tutorial groups.");
        Programme programmeToAddTutGroup = searchByProgrammeCode(programmeList);
        return programmeToAddTutGroup;
    }

    private Programme getProgrammeToViewTutGroup() {
        System.out.println("\nEnter the programme code of the program that you want to view all tutorial groups.");
        Programme programmeToViewTutGroup = searchByProgrammeCode(programmeList);
        return programmeToViewTutGroup;
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
        codeBeforeModification = programmeToModify.getCode(); // used to modify course list and tutorial group list if there is changes to programme code.
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
        if (programmeList.isEmpty()) {
            System.out.println("\nThere are currently no programmes.");
            return;
        }

        ArrayStack<Programme> undoStackProgramme = new ArrayStack<>();
        ArrayStack<String> undoStackTutGroup = new ArrayStack<>();
        ArrayList<String> tutGroupCodeToRemove = new ArrayList<>(); // used to modify tutorial group list if there is removal of tut groups from programmes.
        boolean isRemoved = false;
        int selection;
        do {
            selection = programmeManagementUI.getRemoveTutGroupMenuChoice();
            switch (selection) {
                case 0:
                    if (!isRemoved) {
                        break;
                    }

                    if (getTutGroupRemovalConfirmation(undoStackProgramme, undoStackTutGroup) != 'Y') {
                        programmeList = programmeDAO.retrieveFromFile(); // reset list to original.
                        break;
                    }

                    getTutGroupCodeFromUndoStack(undoStackProgramme, tutGroupCodeToRemove, undoStackTutGroup);
                    updateTutGroupRemovalToFile(tutGroupCodeToRemove);
                    break;
                case 1:
                    Programme programmeToRemoveTutGroup = getProgrammeToRemoveTutGroup();

                    if (programmeToRemoveTutGroup == null) {
                        programmeManagementUI.nonexistentProductCodeMsg();
                        break;
                    }

                    ArrayList<String> tutGroupList = programmeToRemoveTutGroup.getTutorialGroups();

                    if (tutGroupList == null || tutGroupList.isEmpty()) {
                        System.out.println("\nThere are currently no tutorial groups for this programme.");
                        break;
                    }

                    if (getTutGroupRemovalConfirmation(programmeToRemoveTutGroup) != 'Y') {
                        System.out.println("\nRemoval of tutorial group from this programme cancelled.");
                        break;
                    }

                    isRemoved = removeProgrammeTutGroupTemporarily(undoStackProgramme, programmeToRemoveTutGroup, undoStackTutGroup);
                    break;
                case 2:
                    if (undoStackProgramme.isEmpty()) {
                        programmeManagementUI.nothingToUndoMsg();
                        break;
                    }

                    if (getUndoTutGroupRemovalConfirmation(undoStackProgramme, undoStackTutGroup) != 'Y') {
                        programmeManagementUI.undoCancelledMsg();
                        break;
                    }

                    isRemoved = undoTutGroupRemoval(undoStackProgramme, undoStackTutGroup);
                    break;
            }
        } while (selection != 0);

        System.out.println("\nExiting removal of tutorial group from programme...");
    }

    private void displayTutorialGroupForProgramme() {
        if (programmeList.isEmpty()) {
            System.out.println("\nThere are currently no programmes.");
            return;
        }

        int selection;
        do {
            selection = programmeManagementUI.getDisplayTutorialGroupMenuChoice();
            switch (selection) {
                case 0:
                    System.out.println("\nExiting tutorial group display...");
                    break;
                case 1:
                    Programme programmeToViewTutGroup = getProgrammeToViewTutGroup();

                    if (programmeToViewTutGroup == null) {
                        programmeManagementUI.nonexistentProductCodeMsg();
                        break;
                    }

                    programmeManagementUI.listTutGroupsForProgrammes(getTutGroupsForOneProgramme(programmeToViewTutGroup));
                    break;
                case 2:
                    programmeManagementUI.listTutGroupsForProgrammes(getTutGroupsForAllProgrammes(programmeList));
                    break;
            }
        } while (selection != 0);

        programmeManagementUI.listTutGroupsForProgrammes(getTutGroupsForAllProgrammes(programmeList));
    }

    private String getTutGroupsForAllProgrammes(ListInterface<Programme> programmeList) {
        String outputStr = "";
        Iterator<Programme> it = programmeList.getIterator();
        while (it.hasNext()) {
            Programme currentProgramme = it.next();
            outputStr += getTutGroupsForOneProgramme(currentProgramme);
        }
        return outputStr;
    }

    private String getTutGroupsForOneProgramme(Programme programmeToViewTutGroup) {
        String outputStr = "";
        String groupStr = "";
        ArrayList<String> currentProgrammeTutGroupList = programmeToViewTutGroup.getTutorialGroups();

        if (currentProgrammeTutGroupList != null && !currentProgrammeTutGroupList.isEmpty()) {
            groupStr += createTutorialGroupStr(currentProgrammeTutGroupList);
        } else {
            groupStr += "none";
        }

        outputStr += String.format("%-4s %-80s %-35s\n", programmeToViewTutGroup.getCode(), programmeToViewTutGroup.getName(), groupStr);

        return outputStr;
    }

    private void generateReports() {
        if (programmeList.isEmpty()) {
            System.out.println("\nThere are currently no programmes.");
            return;
        }

        ListInterface<Programme> assignedTutGroupProgrammes;
        ListInterface<Programme> unassignedTutGroupProgrammes;
        int selection;
        do {
            selection = programmeManagementUI.getGenerateReportMenuChoice();
            assignedTutGroupProgrammes = new ArrayList<>();
            unassignedTutGroupProgrammes = new ArrayList<>();
            switch (selection) {
                case 0:
                    System.out.println("\nExiting report generation...");
                    break;
                case 1:
                    programmeManagementUI.generateTotalFeeReport(getSortedProgrammeListByTotalFee());
                    break;
                case 2:
                    programmeManagementUI.generateProgrammeTypeReport(getSortedProgrammeListByProgrammeType());
                    break;
                case 3:
                    getAssignedAndUnassignedProgrammes(assignedTutGroupProgrammes, unassignedTutGroupProgrammes);
                    programmeManagementUI.generateTutGroupAssignmentReport(assignedTutGroupProgrammes, unassignedTutGroupProgrammes);
                    break;
            }

        } while (selection != 0);
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

    private void modifyProgrammeInTutGroupList(String code, String newCode) {
        TutorialGroupDAO tutGroupDAO = new TutorialGroupDAO();
        ListInterface<TutorialGroup> tutGroupList = tutGroupDAO.retrieveFromFile();

        Iterator<TutorialGroup> it = tutGroupList.getIterator();
        while (it.hasNext()) {
            TutorialGroup currentTutGroup = it.next();
            if (currentTutGroup.getBatch().equals(currentBatchNo) && currentTutGroup.getProgramme().equals(code)) {
                String newTutGroupID = currentBatchNo + newCode + currentTutGroup.getGroup();
                currentTutGroup.setId(newTutGroupID);
                currentTutGroup.setProgramme(newCode);
            }
        }
        tutGroupDAO.saveToFile(tutGroupList);
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

    private void removeProgrammeFromTutGroup(ArrayList<String> codeToRemove) {
        TutorialGroupDAO tutGroupDAO = new TutorialGroupDAO();
        ListInterface<TutorialGroup> tutGroupList = tutGroupDAO.retrieveFromFile();

        Iterator<TutorialGroup> it = tutGroupList.getIterator();
        while (it.hasNext()) {
            TutorialGroup currentTutGroup = it.next();
            for (int i = 1; i <= codeToRemove.getNumberOfEntries(); i++) {
                if (currentTutGroup.getBatch().equals(currentBatchNo) && currentTutGroup.getProgramme().equals(codeToRemove.getEntry(i))) {
                    tutGroupList.remove(currentTutGroup);
                }
            }
        }
        tutGroupDAO.saveToFile(tutGroupList);
    }

    private void removeTutGroupFromTutGroupList(ArrayList<String> tutGroupCodeToRemove) {
        TutorialGroupDAO tutGroupDAO = new TutorialGroupDAO();
        ListInterface<TutorialGroup> tutGroupList = tutGroupDAO.retrieveFromFile();

        for (int i = 1; i <= tutGroupCodeToRemove.getNumberOfEntries(); i++) {
            tutGroupList.remove(new TutorialGroup(tutGroupCodeToRemove.getEntry(i)));
        }

        tutGroupDAO.saveToFile(tutGroupList);
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
                    modifyProgrammeInTutGroupList(codeBeforeModification, programmeToModify.getCode());
                }
            } else { // reset list to original.
                programmeList = programmeDAO.retrieveFromFile();
            }
        }
    }

    private void performTutGroupAddition(Programme programmeToAddTutGroup) {
        ArrayList<String> currentTutorialGroupList = programmeToAddTutGroup.getTutorialGroups();
        String outputStr = "";
        String latestGroup;

        char confirmation;
        do {

            if (currentTutorialGroupList != null && !currentTutorialGroupList.isEmpty()) {
                latestGroup = "G" + ((Integer.parseInt(currentTutorialGroupList.getLast().substring(1))) + 1);
                outputStr = createTutorialGroupStr(currentTutorialGroupList);
            } else {
                currentTutorialGroupList = new ArrayList<>();
                latestGroup = "G1";
                outputStr = "none";
            }

            System.out.println("\n" + programmeToAddTutGroup.getCode() + " current tutorial groups: " + outputStr + "\n");

            confirmation = getAddTutGroupConfirmation();

            if (confirmation != 'Y') {
                System.out.println("\nStopped adding tutorial groups...");
                break;
            }

            addTutGroupToProgrammeList(currentTutorialGroupList, latestGroup, programmeToAddTutGroup);
        } while (confirmation == 'Y');

    }

    private void addTutGroupToProgrammeList(ArrayList<String> currentTutorialGroupList, String latestGroup, Programme programmeToAddTutGroup) {
        currentTutorialGroupList.add(latestGroup);
        programmeToAddTutGroup.setTutorialGroups(currentTutorialGroupList);
        updateProgrammeAndTutGroupFile(programmeToAddTutGroup);
        System.out.println("\nTutorial " + latestGroup + " addition successful.");
    }

    public static String createTutorialGroupStr(ArrayList<String> currentTutorialGroupList) {
        String outputStr = "";
        for (int i = 1; i <= currentTutorialGroupList.getNumberOfEntries(); i++) {
            outputStr += currentTutorialGroupList.getEntry(i) + ", ";
            if (i == currentTutorialGroupList.getNumberOfEntries()) {
                outputStr += "\b\b";
            }
        }
        return outputStr;
    }

    private char getAddTutGroupConfirmation() {
        System.out.println("Do you want to add a new tutorial group?");
        return programmeManagementUI.inputConfirmation();
    }

    private void updateProgrammeAndTutGroupFile(Programme programmeToAddTutGroup) {
        programmeDAO.saveToFile(programmeList);
        TutorialGroupDAO tutGroupDAO = new TutorialGroupDAO();
        ListInterface<TutorialGroup> tutGroupList = tutGroupDAO.retrieveFromFile();
        String tutGroupID = currentBatchNo + programmeToAddTutGroup.getCode() + programmeToAddTutGroup.getTutorialGroups().getLast();
        tutGroupList.add(new TutorialGroup(tutGroupID, programmeToAddTutGroup.getTutorialGroups().getLast(), programmeToAddTutGroup.getCode(), currentBatchNo));
        tutGroupDAO.saveToFile(tutGroupList);
    }

    //The total fee of each programme is sorted through descending order.
    private ListInterface<Programme> getSortedProgrammeListByTotalFee() {
        ListInterface<Programme> sortedList = convertToArrayList(programmeList); // the list that will be sorted.

        // Insertion sort, descending order
        for (int i = 2; i <= sortedList.getNumberOfEntries(); i++) {
            int j = i;
            while (j > 1 && sortedList.getEntry(j - 1).getTotalFee() < sortedList.getEntry(j).getTotalFee()) {
                swapProgrammePosition(sortedList, j, j - 1);
                j--;
            }
        }

        return sortedList;
    }

    private ListInterface<Programme> getSortedProgrammeListByProgrammeType() {
        ListInterface<Programme> sortedList = convertToArrayList(programmeList); // the list that will be sorted.

        // Insertion sort, ascending order
        for (int i = 2; i <= sortedList.getNumberOfEntries(); i++) {
            int j = i;
            while (j > 1 && sortedList.getEntry(j - 1).getProgrammeType().compareTo(sortedList.getEntry(j).getProgrammeType()) > 0) {
                swapProgrammePosition(sortedList, j, j - 1);
                j--;
            }
        }

        return sortedList;
    }

    private void getAssignedAndUnassignedProgrammes(ListInterface<Programme> assignedTutGroupProgrammes, ListInterface<Programme> unassignedTutGroupProgrammes) {
        Iterator<Programme> it = programmeList.getIterator();
        while (it.hasNext()) {
            Programme currentProgramme = it.next();
            ArrayList<String> currentProgTutGroups = currentProgramme.getTutorialGroups();
            if (currentProgTutGroups == null || currentProgTutGroups.isEmpty()) {
                unassignedTutGroupProgrammes.add(currentProgramme);
            } else {
                assignedTutGroupProgrammes.add(currentProgramme);
            }
        }
    }

    public ListInterface<Programme> convertToArrayList(ListInterface<Programme> programmeList) {
        ListInterface<Programme> arrayList = new ArrayList<>();
        Iterator<Programme> it = programmeList.getIterator();
        while (it.hasNext()) {
            Programme currentProgramme = it.next();
            arrayList.add(currentProgramme);
        }
        return arrayList;
    }

    private void swapProgrammePosition(ListInterface<Programme> sortedList, int firstProgrammePosition, int secondProgrammePosition) {
        Programme temp = sortedList.getEntry(firstProgrammePosition);
        sortedList.replace(firstProgrammePosition, sortedList.getEntry(secondProgrammePosition));
        sortedList.replace(secondProgrammePosition, temp);
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
