package hu.progmatic.carnivora.kepkezeles.ment;

import java.io.FileOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class KepMent {

    public static void kepMentes() {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        List<Integer> lista = List.of(67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80,
                81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94,
                101, 102, 133, 134);

        for (Integer list : lista) {
            String query = "select * from kep " +
                    "where id = " + list;

            try {
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
                String name = resultSet.getString(4);
                byte[] byteArr =
                        clob.getBytes(1, (int) clob.length());

                FileOutputStream fileOutputStream =
                        new FileOutputStream(name + ".jpg");
                fileOutputStream.write(byteArr);

                System.out.println("Image retrieved successfully. " + name);

                //close connection
                fileOutputStream.close();
                preparedStatement.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
