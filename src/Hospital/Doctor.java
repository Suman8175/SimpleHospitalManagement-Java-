package Hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Doctor {
    private final Connection connection;

    public Doctor(Connection connection) {
        this.connection = connection;
    }

    public void viewDoctor(){
        String query="SELECT * FROM Doctor";
        boolean dataFound=false;
        try{
        PreparedStatement statement=connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("+-----------------+----------------+-----------------+------------------+");
            System.out.println("| Doctor_Id       | DoctorName     | DoctorAge       | DoctorGender     |");
            System.out.println("+-----------------+----------------+-----------------+------------------+");
            while (resultSet.next()){
            int id= resultSet.getInt("DoctorId");
            String name=resultSet.getString("DoctorName");
            int age=resultSet.getInt("DoctorAge");
            String gender=resultSet.getString("DoctorGender");
                System.out.printf("|%-17s|%-17s|%-17s|%-17s|\n",id,name,age,gender);
                System.out.println();
                dataFound=true;
            }
            if (!dataFound){
                System.out.println("\n\n");
                System.out.println("------------------No records available!!-------------------------------");
            }
        }
        catch (Exception e){

            System.out.println("Error occurred when showing!!!");
        }
    }

    public boolean getDoctorById(int id){
        String query="Select * from Doctor where DoctorId=?";
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
