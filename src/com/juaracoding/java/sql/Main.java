package com.juaracoding.java.sql;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/perkuliahan";
	static final String DB_User ="root";
	static final String DB_Password ="";
	
	
	static Connection conn;
	static Statement stat;
	static ResultSet rs;
	
//	static Scanner sc = new Scanner(System.in);
	
	static InputStreamReader data = new InputStreamReader(System.in);
	static BufferedReader br = new BufferedReader(data);
	
	public static void main(String[] args) {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,DB_User,DB_Password);
			
			stat = conn.createStatement();
			
			
			while(!conn.isClosed()) {
				showMenu();
			}
			
			stat.close();
			conn.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void showMenu() {
		System.out.println("===============================");
		System.out.println("1. Masukan Data");
		System.out.println("2. Update Data");
		System.out.println("3. Tampilkan Data");
		System.out.println("4. Delete Data");
		System.out.println("5. Keluar");
		System.out.print("Masukan Pilihan = ");
//		int pil = sc.nextInt();
		try {
			int pil = Integer.parseInt(br.readLine());
			switch (pil) {
			case 1: {
				insertData();
				break;
			}
			case 2: {
				updateData();
				break;
			}
			case 3: {
				showData();
				break;
			}
			case 4: {
				deleteData();
				break;
			}
			case 5: {
				System.exit(0);
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + pil);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void insertData() {
		try {
			System.out.print("Nim = ");
			int nim = Integer.parseInt(br.readLine());
			System.out.print("Nama = ");
			String nama = br.readLine();
			System.out.print("Alamat = ");
			String alamat = br.readLine();
			System.out.print("Umur = ");
			int umur = Integer.parseInt(br.readLine());
			
			String qry = "INSERT INTO `mahasiswa`(`nim`, `nama`, `alamat`, `umur`) VALUES (%d,'%s','%s',%d)";

			qry = String.format(qry, nim,nama,alamat,umur);
			
			stat.execute(qry);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void showData() {
		String qry = "SELECT * FROM mahasiswa";
		try {
			rs = stat.executeQuery(qry);
			System.out.println("======Tampilan Asik======");
			while(rs.next()) {
				int nim = rs.getInt("nim");
				String nama = rs.getString("nama");
				String alamat = rs.getString("alamat");
				int umur = rs.getInt("umur");
				
				System.out.println(String.format("%d %s --> %s (%d)",nim,nama,alamat,umur));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void updateData() {
		try {
			System.out.print("Masukan Nim yang ingin di ubah= ");
			int nim = Integer.parseInt(br.readLine());
			System.out.print("Nama = ");
			String nama = br.readLine();
			System.out.print("Alamat = ");
			String alamat = br.readLine();
			System.out.print("Umur = ");
			int umur = Integer.parseInt(br.readLine());
			
			String qry = "UPDATE `mahasiswa` SET `nama`='%s',`alamat`='%s',`umur`='%d' WHERE nim = %d";

			qry = String.format(qry, nama,alamat,umur,nim);
			
			stat.execute(qry);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void deleteData() {
		try {
			System.out.print("Masukan Nim yang ingin di hapus= ");
			int nim = Integer.parseInt(br.readLine());

			String qry = "DELETE FROM `mahasiswa` WHERE  nim = %d";

			qry = String.format(qry, nim);
			
			stat.execute(qry);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
