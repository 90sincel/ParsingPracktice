/**
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
import java.util.NoSuchElementException;

/**
 * Linked data structure composed of appointment objects
 */
public class Schedule {
    //Having head as Package is a potential privacy leak, as accessing head allows programmer to access entire Linked Structure...
    /**
     * COULD LEAD TO PRIVACY LEAK IF MODIFIED, DECLARE PRIVATE AND ONLY ALLOW GETTER METHOD TO PROTECT
     */
    AppointmentNode head;
    private int size;

    /**
     * Default
     */
    public Schedule(){
        this.head = null;
    }

    /**
     * Copy constructor, creates deep copy
     * @param other
     */
    public Schedule(Schedule other){
        if(other == null){
            this.head = null;
        }else{
            AppointmentNode temp = other.head;
            int index = other.size;
            Appointment[] nodes = new Appointment[index];

            for(int i=0; i<index; i++){
                nodes[i] = temp.getAppointment();
                temp = temp.getPointer();
            }

            for(int i=nodes.length-1; i>-1; i--) {
                this.addToStart(new Appointment(nodes[i], ""));
            }
        }
    }

    /**
     * Nested class
     */
    class AppointmentNode{
        private Appointment appointment;//data
        private AppointmentNode pointer;//next

        /**
         * Default
         */
        public AppointmentNode(){
            this.appointment = null;
            setPointer(null);
            size++;
        }

        /**
         * Parameterized counstructor
         * @param appointment appointment object
         * @param next pointer to next appointment node
         */
        public AppointmentNode(Appointment appointment, AppointmentNode next){
            setAppointment(appointment);
            setPointer(next);
        }

        /**
         * Copy constructor , creates deep copy
         * @param toBCloned Appointment node
         */
        public AppointmentNode(AppointmentNode toBCloned)  {
            AppointmentNode cloned = toBCloned.clone();
            this.setAppointment(cloned.getAppointment());
            this.setPointer(null);
        }
        //clone()

        /**
         * Creates deep clone
         * @return AppointmentNode
         */
        public AppointmentNode clone(){//need to address
            AppointmentNode clone = new AppointmentNode(getAppointment().clone("Cloned node"), null );
            return clone;
        }

        /**
         * To string method
         * @return string
         */
        public String toString(){
            String out = getAppointment().toString();
            return out;
        }

        /**
         *
         * @return Appointment
         */
        public Appointment getAppointment() {
            return appointment;
        }

        /**
         *
         * @param appointment appointment
         */
        public void setAppointment(Appointment appointment) {
            this.appointment = appointment;
        }

        /**
         *
         * @return AppointmentNode
         */
        public AppointmentNode getPointer() {
            return pointer;
        }

        /**
         *
         * @param pointer AppointmentNode
         */
        public void setPointer(AppointmentNode pointer) {
            this.pointer = pointer;
        }
    }


    /**
     *
     * @param a Appointment
     */
    public void addToStart(Appointment a){
        if(head == null){
            head = new AppointmentNode(a, null);
        }else{
            AppointmentNode newNode = new AppointmentNode(a, null);
            AppointmentNode temp = head;
            head = newNode;
            newNode.setPointer(temp);
        }
        size++;
    }

    /**
     *
     * @param appointment appointment
     * @param index int
     */
    public void insertAtIndex(Appointment appointment, int index){
        try{
            if(head == null){
                this.addToStart(appointment);
                size++;
            }else if(index < -1 | index > this.size-1){
                //System.out.println("Cannot enter non-existent index!");
                throw new NoSuchElementException("Cannot enter non-existent index!");
            }else if(index == 0){
                this.addToStart(appointment);
                size++;
            }else if(index == size-1){
                AppointmentNode temp = head;
                while(temp.pointer != null){
                    temp = temp.pointer;
                }
                temp.setPointer(new AppointmentNode(appointment, null));
                size++;
            }else{
                AppointmentNode temp = head;
                int counter=0;
                while(counter != index-1){
                    temp = temp.pointer;
                    counter++;
                }
                AppointmentNode after = temp.pointer;
                temp.setPointer(new AppointmentNode(appointment, null));
                temp.getPointer().setPointer(after);
                size++;
            }
        }catch(NullPointerException | NoSuchElementException n){
            System.out.println(n.getMessage());
            System.exit(0);
        } catch(Exception e){
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    /**
     *
     * @param index int
     */
    public void deleteFromIndex(int index){
        try{
            if(head == null){
                System.out.println("No elements!");
            }else if(index < 0 | index > this.size-1){
                //System.out.println("Cannot enter non-existent index!");
                throw new NoSuchElementException("Cannot enter non-existent index!");
            }else if(index == 0){
                deleteFromStart();
            }else if(index == size - 1){
                AppointmentNode temp = head;
                while(temp.getPointer().getPointer() != null)//think get next when you see get pointer
                    temp = temp.getPointer();

                temp.setPointer(null);
                size--;
            }else{
                AppointmentNode temp = head;
                int counter = 0;
                while(counter != index - 1){
                    temp = temp.getPointer();
                    counter++;
                }
                AppointmentNode removed = temp.getPointer();
                temp.setPointer(temp.getPointer().getPointer());
                removed.setPointer(null);
                System.out.println("ID: "+removed.getAppointment().getAppointmentID()+" removed successfully!");
                size--;
            }

        }catch (NoSuchElementException | NullPointerException e){
            System.out.println(e.getMessage());
            System.exit(0);
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * deletes entry at beginning
     */
    public void deleteFromStart(){
        if(head == null){
            System.out.println("No elements!");
        }else{
            AppointmentNode removed = head;
            head = head.getPointer();
            removed.setPointer(null);
            size--;
        }
    }

    /**
     * replaces entry at index
     * @param a appointment
     * @param index int
     */
    public void replaceAtIndex(Appointment a, int index){
        try{
            if(head == null){
                System.out.println("No elements!");
            }else if(index < 0 | index > this.size-1){
                //System.out.println("Cannot enter non-existent index!");
                throw new NoSuchElementException("Cannot enter non-existent index!");
            }else if(index == 0){
                AppointmentNode replaced = head;
                head = new AppointmentNode(a, null);
                head.setPointer(replaced.getPointer());
                replaced.setPointer(null);
            }else if(index == size-1){
                AppointmentNode temp = head;
                while(temp.getPointer().getPointer() != null)
                    temp = temp.getPointer();
                //AppointmentNode replaced = temp.getPointer();
                temp.setPointer(new AppointmentNode(a, null));

            }else{
                AppointmentNode temp = head;
                int counter = 0;
                while(counter != index-1){
                    temp = temp.getPointer();
                    counter++;
                }
                AppointmentNode replaced = temp.getPointer();
                temp.setPointer(new AppointmentNode(a, null));
                temp.getPointer().setPointer(replaced.getPointer());
                replaced.setPointer(null);
            }
        }catch(NoSuchElementException | NullPointerException n){
            System.out.println(n.getMessage());
            System.exit(0);
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    /**
     *
     * @param appointmentID string
     * @return AppointmentNode
     */
    public AppointmentNode find(String appointmentID){
        AppointmentNode temp = head;
        int counter = 1;
        while(temp.getPointer() != null & (!temp.getPointer().getAppointment().getAppointmentID().toLowerCase().equals(appointmentID.toLowerCase()))){
            temp = temp.getPointer();
            counter++;
        }
        if(counter == size) {
            System.out.println("Searched: "+counter+" appointments, nothing was found by that ID.");
            return null;
        }
        else {
            System.out.println("Searched: "+counter+" appointments, ID was located at index: "+ counter);
            return temp.getPointer();
        }
    }

    /**
     *
     * @param appointmentID string
     * @return boolean
     */
    public boolean contains(String appointmentID){
        AppointmentNode temp = head;
        while(temp != null && !temp.getAppointment().getAppointmentID().toLowerCase().equals(appointmentID.toLowerCase()))
            temp = temp.getPointer();


        if(temp == null)
            return false;
        else
            return true;

    }

    /**
     *
     * @param schedule schedule
     * @return boolean
     */
    public boolean equals(Schedule schedule){
        if(size != schedule.size)
            return false;
        else{
            AppointmentNode tempOne = head;
            AppointmentNode tempTwo = schedule.head;
            while(tempOne.getPointer() != null & tempOne.getAppointment().equals(tempTwo.getAppointment())){
                tempOne = tempOne.getPointer();
                tempTwo = tempTwo.getPointer();
            }
            if(tempOne.getPointer() == null){
                return tempOne.getAppointment().equals(tempTwo.getAppointment());
            }else{
                return false;
            }
        }
    }

    /**
     *
     * @return string
     */
    public String toString(){
        String out = "";
        AppointmentNode temp = head;
        while(temp != null){
            out += temp.toString();
            temp = temp.getPointer();
        }
        return out;
    }

    /**
     *
     * @return int
     */
    public int getSize(){
        return this.size;
    }


}

