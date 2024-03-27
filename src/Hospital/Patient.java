package Hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Scanner;

public class Patient {
   private final Connection connection;
   private final Scanner scanner;

    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }


    public void viewPatient(){
        //Query=SELECT * FROM Patient
        String query="Select * from Patient";
        boolean dataFound=false;
        try(  PreparedStatement preparedStatement=connection.prepareStatement(query);
              ResultSet resultSet = preparedStatement.executeQuery()) {
            System.out.println("+-----------------+-----------------+-----------------+-----------------+");
            System.out.println("| Patient_Id      | Patient_Name    | Patient_Age     | Patient_Gender  |");
            System.out.println("+-----------------+-----------------+-----------------+-----------------+");



                while (resultSet.next()) {
                    int patientId = resultSet.getInt("PatientId");
                    String patientName = resultSet.getString("PatientName");
                    int patientAge = resultSet.getInt("PatientAge");
                    String patientGender = resultSet.getString("PatientGender");
                    System.out.printf("| %-16s| %-16s| %-16s| %-16s|\n", patientId, patientName, patientAge, patientGender);
                    dataFound=true;
                }
                if (!dataFound){
                System.out.println("\n\n");
                System.out.println("------------------No records available!!-------------------------------");
                }

        }
        catch (Exception e){
            System.out.println("Error has occurred!!");
        }
    }


    public  void addPatient(){
        String query="INSERT INTO Patient(PatientName,PatientAge,PatientGender) values (?,?,?)";
        System.out.println("Enter Name of Patient to Add in database");
        String name=scanner.nextLine();
        System.out.println("Enter age of patient");
        int age=scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter gender of patient");
        String gender=scanner.nextLine();
        try(PreparedStatement preparedStatement=connection.prepareStatement(query)){
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);
          int result=  preparedStatement.executeUpdate();
          if (result>0){
              System.out.println("Patient added successfully to database");
          }
          else {
              System.out.println("Failed to add patient.Please try later!!");
          }
        }
        catch (Exception e){
            System.out.println("Failed to make connection with database");
        }
    }


    public boolean getPatientById(int id){
        String query="Select * from Patient where PatientId=?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
        catch (Exception e){
            System.out.println("Error!!");
        }
    return  false;
    }
}
