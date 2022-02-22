package hu.progmatic.carnivora.kepkezeles.ment;

import java.io.FileOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class KepMent {

    public static void kepMentes() {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        String query = "select * from kep " +
                "where id = 68";

        try{
            //get connection
            conn = JDBCUtil.getConnection();

            //create preparedStatement
            preparedStatement =
                    conn.prepareStatement(query);

            //execute query
            ResultSet resultSet =
                    preparedStatement.executeQuery();
            resultSet.next();

            Blob clob = resultSet.getBlob(3);
            byte[] byteArr =
                    clob.getBytes(1,(int)clob.length());

            FileOutputStream fileOutputStream =
                    new FileOutputStream("C:\\savedImage.jpg");
            fileOutputStream.write(byteArr);

            System.out.println("Image retrieved successfully.");

            //close connection
            fileOutputStream.close();
            preparedStatement.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
