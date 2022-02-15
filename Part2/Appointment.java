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

/**
 * Interface to enforce the use of isOnSameTime
 */
interface Bookable{
    String isOnSameTime(Appointment A);
}

/*
-   Here Appointment will form our linkedList of Appointment nodes
-   Will provide the functionality to add/insert/delete nodes within itself
-   The node counter could use recurion with break case being Node.next == null, not really necessary as you can just use a while loop
 */

/**
 * Appointment object
 */
public class Appointment implements Bookable{
    String appointmentID, doctorName;
    double startTime, endTime;

    /**
     * Default constructor
     */
    public Appointment(){
        setAppointmentID("");
        setDoctorName("");
        setStartTime(0.0);
        setEndTime(0.0);
    }

    /**
     *
     * @param id string
     * @param name string
     * @param start double
     * @param end double
     */
    public Appointment(String id, String name, Double start, Double end){
        setAppointmentID(id);
        setDoctorName(name);
        setStartTime(start);
        setEndTime(end);
    }

    /**
     * Copy constructor, deep copy
     * @param other appointment
     * @param id string
     */
    public Appointment(Appointment other, String id){
        Appointment deep = other.clone(id);
        this.setAppointmentID(id);
        this.setDoctorName(deep.getDoctorName());
        this.setStartTime(deep.getStartTime());
        this.setEndTime(deep.getEndTime());
    }

    /**
     * Creates deep copy
     * @param id string
     * @return appointment
     */
    public Appointment clone(String id){//DEEP COPY

        Appointment clone = new Appointment(id, this.getDoctorName(), this.getStartTime(), this.getEndTime());
        return clone;

    }

    /**
     *
     * @param other object
     * @return boolean
     */
    public boolean equals(Object other){
        if(other == null)
            return false;
        else if(this.getClass() != other.getClass())
            return false;
        else{
            Appointment a = (Appointment) other;
            return a.getDoctorName().equals(this.getDoctorName())
                    & a.getStartTime() == this.getStartTime()
                    & a.getEndTime() == this.getEndTime();
        }
    }

    /**
     *
     * @return string
     */
    public String toString(){
        String out = "ID: "+getAppointmentID()+" Dr. "+getDoctorName()+"\nStart: "+getStartTime()+"\nFinish: "+ getEndTime()+"\n\n";
        return out;
    }

    /**
     *
     * @param A Appointment
     * @return string
     */
    public String isOnSameTime(Appointment A){
        String whatIsUp = "";
        double diff = A.getEndTime() - A.getStartTime();
        if(this.getStartTime() == A.getStartTime())
            whatIsUp += "Same time";
        else if(this.getEndTime() == A.getStartTime() || this.getStartTime() == A.getEndTime())
            whatIsUp += "Some overlap";
        else if(A.getStartTime()+diff == this.getEndTime())
            whatIsUp += "Some overlap";
        else
            whatIsUp += "Different time";

        return whatIsUp;
    }

    /**
     *
     * @return string
     */
    public String getAppointmentID() {
        return appointmentID;
    }

    /**
     *
     * @param appointmentID string
     */
    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     *
     * @return string
     */
    public String getDoctorName() {
        return doctorName;
    }

    /**
     *
     * @param doctorName string
     */
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    /**
     *
     * @return double
     */
    public double getStartTime() {
        return startTime;
    }

    /**
     *
     * @param startTime double
     */
    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    /**
     *
     * @return double
     */
    public double getEndTime() {
        return endTime;
    }

    /**
     *
     * @param endTime double
     */
    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }
}

