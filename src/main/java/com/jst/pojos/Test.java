package com.jst.pojos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "jdbc:oracle:thin:@localhost:1521:ORCL";
		try {
			System.out.println("Connecting to the database test...");
			Connection conn  = DriverManager.getConnection(url, "\"c##jst.jluctamar\"", "password");
            ResultSet rs;
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Users");
            while ( rs.next() ) {
                String lastName = rs.getString("last_name");
                System.out.println(rs);
            }
            conn.close();
			System.out.println("Success");
		}catch(Exception ex) {
			System.out.println("Uhh ohh");
			ex.printStackTrace();
		}
	}

}
