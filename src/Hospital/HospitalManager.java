package Hospital;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class HospitalManager {

    private static Doctor doctor;
    private static Patient patient;
    private static Appointment appointment;

     static Connection connection=null;
     static Scanner scanner=new Scanner(System.in);
    private  static  final  String url = "jdbc:sqlserver://localhost;database=hospital;username=sa;password=password;encrypt=true;trustServerCertificate=true;";


    public static void choice(){
        while (true){
            System.out.println("1.Add Patient");
            System.out.println("2.View Patient");
            System.out.println("3.View Doctor");
            System.out.println("4.Book an Appointment");
            System.out.println("5.Exit");
            System.out.println();
            System.out.println("Enter your choice");
            int choice=scanner.nextInt();
            scanner.nextLine();
            switch (choice){
                case 1://Add Patient
                    patient.addPatient();
                    break;
                case 2://View Patient
                    patient.viewPatient();
                    break;
                case 3://View Doctor
                    doctor.viewDoctor();
                    break;
                case 4://Book appointment
                    appointment.bookAppointment();
                    break;
                case 5://Exit
                    return;
                default:
                    System.out.println("Please choose valid choices");
                    break;
            }
        }

    }



    public static void main(String[] args) {
        try {
            connection = DriverManager.getConnection(url);
            patient=new Patient(connection,scanner);
            doctor=new Doctor(connection);
            appointment=new Appointment(connection,scanner,doctor,patient);
            choice();

        } catch (Exception e) {
            System.out.println("Cannot connect to server now!!");
        }

    }
}
