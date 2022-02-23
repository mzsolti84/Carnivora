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

        List<Integer> lista = List.of(103, 104, 105, 106, 107, 108, 109, 110, 111, 133);

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
