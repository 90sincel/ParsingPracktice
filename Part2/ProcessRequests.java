/**
 * GENERAL DESCRIPTION:
 * The goal of this code is to effectively process a file that represents a schedule for a clinic that contains some repeats
 * into a linked dynamic data structure that will allow the use to adjust and compare entries into this schedule.
 * I did this by implementing the described methods as I saw fit, i parsed the schedule file directly into an arraylist then removed duplicates,
 * reversed the order then placed the appointments temporaly from earliest to lates. When processing the requests i relied heavily on
 * arraylists as well as these data structures are better for comparing elements, finally a loop to compare requests with the current
 * schedule is used, relying on the doctor's names and times as constraints.
 * @author ThomasHegarty
 * ID: 40155703
 * COMP249
 * Assignment #4
 * @version 1.1
 * DUE: December 6th, 2021
 * Assumes perfect user
 */
// -----------------------------------------------------
// Assignment 4
// Question: 2 // Written by: Thomas Hegarty ID: 40155703
// -----------------------------------------------------
package Part2;
import jdk.swing.interop.SwingInterOpUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

//Static method for cloned Appointment
//Static method for cloned Schedule

/*
Paths:
-   src/Schedule.txt
-   /Users/trevorhegarty/my_code/comp249/assignment_4_updated/assignment_4/src/Schedule.txt

-   src/Requests.txt
-   /Users/trevorhegarty/my_code/comp249/assignment_4_updated/assignment_4/src/Requests.txt
 */

/**
 * Main process class
 */
public class ProcessRequests {
    //Removes duplicate appointments, but the algorithm is generic...

    /**
     * Generic method that removes duplicate entries
     * @param before arraylist
     * @param <T> generic type of arraylist
     * @return original arraylist with removed duplicate entries
     */
    public static <T> ArrayList<T> DuplicateRemover(ArrayList<T> before){
        ArrayList<T> after = new ArrayList<>();

        for(T thing: before){

            if(!after.contains(thing))
                after.add(thing);

        }
        return after;
    }

    /**
     * Converts our Linked data structure to arraylist
     * @param sched Liked list of Appointment objects
     * @return arraylist
     */
    public static ArrayList<Appointment> LinkToArray(Schedule sched){
        ArrayList<Appointment> output = new ArrayList<>();

        Schedule.AppointmentNode temp = sched.head;
        while(temp.getPointer() != null){
            output.add(temp.getAppointment());
            temp = temp.getPointer();
        }
        output.add(temp.getAppointment());

        return output;
    }

    /**
     * Reverses arraylist
     * @param before arraylist
     * @return arraylist
     */
    public static ArrayList<Appointment> reverser(ArrayList<Part2.Appointment> before){
        ArrayList<Appointment> after = new ArrayList<>();
        Appointment[] unordered = new Appointment[before.size()];

        for(int i=0; i<before.size(); i++)
            unordered[i] = before.get(i);

        for(int i = unordered.length-1; i>-1; i--)
            after.add(unordered[i]);

        return after;
    }

    /**
     * takes schedule object and fills with appointments form Schedule.txt
     * @param sched schedule object
     */
    public static void ScheduleCreator(Schedule sched){
        Scanner input = null;

        try{
            input = new Scanner(new FileReader("src/Schedule.txt"));
            ArrayList<String> docnames = new ArrayList<>();
            while(input.hasNextLine()) {
                String a = input.nextLine();
                if (a.equals(""))
                    break;
                else
                    docnames.add(a);
            }
            //System.out.println(docnames);
            String[] idAndName = new String[2];
            String[] startArray = new String[2];
            String[] endArray = new String[2];
            double start, end;
            ArrayList<Appointment> preProc = new ArrayList<>();
            while(input.hasNextLine()){
                idAndName = input.nextLine().split(" ");
                startArray = input.nextLine().split(" ");
                endArray = input.nextLine().split(" ");
                start = Double.parseDouble(startArray[1]);
                end = Double.parseDouble(endArray[1]);
                //sched.addToStart(new Appointment(idAndName[0], idAndName[1], start, end));
                preProc.add(new Appointment(idAndName[0], idAndName[1], start, end));
                if(input.hasNextLine())
                    input.nextLine();
            }
            //Removes duplicates from the parsed arraylist of appointments
            ArrayList<Appointment> processed = DuplicateRemover(preProc);
            //Orders schedule from lastest to earlies...
            ArrayList<Appointment> sequential = reverser(processed);
            /*
            processed.forEach(Appointment -> System.out.println(Appointment.toString()));
            System.out.println("Start reversed: ");
            sequential.forEach(Appointment -> System.out.println(Appointment.toString()));
            */


            for(Appointment thing: sequential)
                sched.addToStart(thing);


            input.close();

        }catch(IOException e){
            System.out.println(e.getMessage());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Processes request.txt for availability within passed schedule
     * @param sched schedule object
     * @param fileInput File
     */
    public static void RequestProcessor(Schedule sched, File fileInput) {
        /*
        Does similar parsing as the above method,
        - parses requests and compares S/E times
        -   if S/E is good it will print can fit message with approprate docs
        -   if S/E is good with specific docs
        -   if S/E is not gewd....
         */
        ArrayList<Appointment> reqs = new ArrayList<>();
        ArrayList<Appointment> schedArray = LinkToArray(sched);
        ArrayList<String> docsPre = new ArrayList<>();
        ArrayList<String> docs = new ArrayList<>();

        for(Appointment thing: schedArray)
            docsPre.add(thing.getDoctorName().strip());

        docs = DuplicateRemover(docsPre);

        Scanner fileIn = null;
        try {
            fileIn = new Scanner(new FileReader(fileInput.getPath()));
            while (fileIn.hasNextLine()) {
                String[] inputsies = new String[3];
                inputsies = fileIn.nextLine().split(" ");
                reqs.add(new Appointment(inputsies[0]
                        , "", Double.parseDouble(inputsies[1])
                        , Double.parseDouble(inputsies[2])));
            }

            for(Appointment thing: reqs){

                String returnsies = "";
                ArrayList<String> unavailableDocs = new ArrayList<>();
                for(Appointment scheduled: schedArray){
                    double diff = scheduled.getEndTime() - scheduled.getStartTime();
                    if(thing.getStartTime() == scheduled.getStartTime()+diff)
                        unavailableDocs.add(scheduled.getDoctorName().strip());
                    else if(thing.getStartTime() == scheduled.getStartTime() | thing.getEndTime() == scheduled.getEndTime())
                        unavailableDocs.add(scheduled.getDoctorName().strip());
                }

                //System.out.println(unavailableDocs.size());

                if(unavailableDocs.size() <= docs.size()-2){
                    returnsies += "Patient can book appointment " + thing.getAppointmentID()
                            + " from "+thing.getStartTime()+" to "+thing.getEndTime()
                            +" as nothing is scheduled during this time for multiple doctors.";
                }else if(unavailableDocs.size() == docs.size()){
                    returnsies += "Patient cannot book appointment " + thing.getAppointmentID()
                            + " from "+thing.getStartTime()+" to "+thing.getEndTime()
                            +" as no doctor is available at this time.";
                }else{
                    String soloDoc = "";
                    for(String name: docs){
                        if(!unavailableDocs.contains(name))
                            soloDoc += name;
                    }
                    returnsies += "Patient can book appointment " + thing.getAppointmentID()
                            + " from "+thing.getStartTime()+" to "+thing.getEndTime()
                            + " only with Dr. "+soloDoc+" as other doctors are not available at this time.";
                }
                System.out.println(returnsies);
                //System.out.println(thing.getAppointmentID()+"\n"+unavailableDocs);
            }



            fileIn.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    /*
    Paths:
    -   src/Requests.txt
     */

    /**
     * Main method
     * @param args arguments
     */
    public static void main(String[] args){

        System.out.println("---------------\t\tWELCOME TO THE PROGRAM I HAD TO WRITE FOR A GRADE\t\t---------------" +
                "\n---------------\t\t\t\tWRITEN BY: Thomas of Hegarty\t\t\t\t\t---------------\n");

        Schedule schedOne = new Schedule();
        ScheduleCreator(schedOne);
        //Copy constructor, creates deep copy
        Schedule schedTwo = new Schedule(schedOne);

        System.out.println("Schedule one and two are equal: "+schedTwo.equals(schedOne)+"\n");

        Scanner userIn = new Scanner(System.in);
        System.out.println("Please enter the file name of the requests you wish to process: ");
        File requests = new File(userIn.nextLine());

        RequestProcessor(schedOne, requests);

        System.out.println("Current schedule: ");
        System.out.println(schedOne.toString());
        System.out.println("\nPlease enter one of the above id's to find said appointment: ");
        String answer = userIn.nextLine();
        System.out.println("Contains "+answer+"? "+schedOne.contains(answer));
        Schedule.AppointmentNode byID = schedOne.find(answer);
        System.out.println(byID.toString());
        System.out.println("\nPerforming a bunch of stuff on Schedules and Appointments");

        //Deep clone
        //Definite privacy leak as accessing head allows programmer to access the entire Linked Data set
        Appointment cloned = schedTwo.head.getAppointment().clone("M10");
        System.out.println("Cloned appointment:\n"+cloned.toString());

        System.out.println("Enter yes if you want to crash the program! ");
        String yesOrNo = userIn.nextLine().toLowerCase();
        if(yesOrNo.equals("yes")){
            schedOne.deleteFromIndex(30000);
        }else{
            System.out.println("\t\tSize before insertion: "+schedTwo.getSize());
            System.out.println("\t\tInserting appointment at index 4:");
            schedTwo.insertAtIndex(new Appointment("M40", "Dr. Cheese", 12.00, 12.30),4);
            System.out.println("\t\tSize after insertion: "+schedTwo.getSize());
            Schedule.AppointmentNode inserted = schedTwo.find("M40");
            System.out.println("Inserted appointment:\n"+inserted.toString()+"\n");

            System.out.println("\t\tReplacing inserted appointment: ");
            schedTwo.replaceAtIndex(new Appointment("M60", "Dr. Queso", 21.00, 22.00), 4);
            Schedule.AppointmentNode replaced = schedTwo.find("m60");
            System.out.println("Replaced inserted node with:\n"+replaced.toString()+"\n");


            System.out.println("\t\tDeleting inserted appointment:");
            schedTwo.deleteFromIndex(4);
            System.out.println("Size after deletion: "+schedTwo.getSize());
            System.out.println("After deletion:\n"+schedTwo.toString()+"\n");

            System.out.println("\n\t\t\tTHANKS FOR USING THIS THING!!!\n");
        }



        userIn.close();

    }
}

