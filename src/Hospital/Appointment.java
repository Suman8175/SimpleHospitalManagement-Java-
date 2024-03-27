package Hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Appointment {
    private final Connection connection;
    private final Scanner scanner;

    private final Doctor doctor;
    private final Patient patient;

    public Appointment(Connection connection, Scanner scanner, Doctor doctor, Patient patient) {
        this.connection = connection;
        this.scanner = scanner;
        this.doctor = doctor;
        this.patient = patient;
    }


    public boolean checkAvailability(int id,String date){
        String query="SELECT  count(*) as Appointment from Appointment where DoctorId=? and AppointmentDate=?";
            int count=5;
           boolean checker=false;
        try ( PreparedStatement preparedStatement=connection.prepareStatement(query))
        {
            preparedStatement.setInt(1,id);
            preparedStatement.setString(2,date);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
              count=resultSet.getInt("Appointment");
            }
            if (count==0){
                checker=true;
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return checker;
    }


    public  void bookAppointment(){

        System.out.println("Enter doctor Id");
        int doctorId=scanner.nextInt();
        System.out.println("Enter patient Id");
        int patientId=scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter appointment date in YYYY-MM-DD");
        String date=scanner.nextLine();

        if (patient.getPatientById(patientId) && doctor.getDoctorById(doctorId) && checkAvailability(doctorId,date)){
          try{
                  String queryForInsert="Insert into Appointment values (?,?,?)";
                  PreparedStatement pre= connection.prepareStatement(queryForInsert);
                  pre.setInt(1,patientId);
                  pre.setInt(2,doctorId);
                  pre.setString(3,date);
                  int rs=pre.executeUpdate();
                  if (rs>0){
                      System.out.println("Inserted successfully");
                  }

          }
          catch (Exception e){
              System.out.println("Error");
          }
        }
        else {
            System.out.println("Any one is missing!! Or doctor is busy on that date");
        }

    }

}
