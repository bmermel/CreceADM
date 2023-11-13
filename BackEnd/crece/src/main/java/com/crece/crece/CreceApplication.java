package com.crece.crece;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.sql.*;

@SpringBootApplication
@EntityScan(basePackages = "com.crece.crece.model")
public class CreceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreceApplication.class, args);

	// Configuración de la conexión
		String url = "jdbc:mysql://localhost:3306/creceDB";
		String usuario = "root";
		String contrasena = "admin";

		// Establecer la conexión
		try (Connection conexion = DriverManager.getConnection(url, usuario, contrasena)) {
			if (conexion != null) {
				System.out.println("Conexión exitosa");
			Statement st = conexion.createStatement();
			st.executeUpdate("INSERT INTO EDIFICIOS (id, nombre,direccion, razonSocial, cuit) VALUES(60, 'leito','savio','leito sa',1241);");
			st.executeUpdate("INSERT INTO USUARIOS (id, nombre, apellido,email, rol, edificios_id) VALUES (1, 'BRIAN','MERMEL','ASDA@GMAIL.COM', 'INQUILINO', 6);");

				// Puedes realizar operaciones en la base de datos aquí
			}
		} catch (SQLException e) {
			System.err.println("Error de conexión: " + e.getMessage());
		}
	}

}




