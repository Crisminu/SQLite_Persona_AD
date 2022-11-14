package es.cristina;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Scanner;


public class jdbcSQLite {
    public static void main(String args[]){


        boolean acceso =true;
        int opcion = 0;

        Scanner sc = new Scanner(System.in);
        //Crear un objeto static Connection con valor nulo
        Connection conn = null;

        try {
            //Hace la conexión con el archivo de la base de datos y será la que pasemos como parámetro para cada método
            conn= DriverManager.getConnection("jdbc:sqlite:src/main/java/es/cristina/persona.db");
            System.out.println("Conectado a la base de datos");
            //Menú para los distintos métodos
            while(acceso){
                System.out.println("Seleccione una opción");
                System.out.println("1. Consultar Datos");
                System.out.println("2. Insertar registros en tabla Persona");
                System.out.println("3. Salir");
                opcion = sc.nextInt();
                switch (opcion) {
                    case 1 -> consultarRegistro(conn);
                    case 2 -> insertarRegistro(conn);
                    case 3 -> acceso = false;
                    default -> {
                        System.out.println("Inserte una opción valida");
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void consultarRegistro(Connection conn) {
        try {
            //String SQL con sintaxis SQLite3
            String sql = "SELECT * FROM Persona";
            //Objeto para ejecutar una sentencia de SQL
            Statement statement = conn.createStatement();
            //Método para ejecutar sql y que nos devuelva un objeto ResultSet
            statement.executeQuery(sql);
            //Representa la información de la base de datos
            ResultSet result = statement.executeQuery(sql);

            System.out.println("Consultas de la tabla Persona");
            while(result.next()){
                //Coge cada uno de los valore del resultset
                String id = String.valueOf(result.getInt("Id"));
                String nombre = result.getString("Nombre");
                String apellido1 = result.getString("Apellido1");
                String apellido2 = result.getString("Apellido2");
                String tlf = String.valueOf(result.getInt("Tlf"));
                String dir = result.getString("Dir");
                System.out.println("ID: " + id + "\n" + "Nombre: " + nombre + "\n" + "Primer Apellido: " + apellido1 + "\n" + "Segundo Apellido: " + apellido2 + "\n" + "Teléfono: " + tlf + "\n" + "Dirección: " + dir + "\n" + "*********************" );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void insertarRegistro(Connection conn) throws SQLException, IOException {
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
        int tlf;
        String nombre, apellido1, apellido2, dir;
        System.out.println("Insertar datos");
        int max = 0;
        Statement statement = conn.createStatement();
        String sql = "SELECT MAX(Id) FROM Persona";
        ResultSet rs = statement.executeQuery(sql);
        max= rs.getInt("max(Id)");
        max+=1;
        System.out.println("Nombre");
        nombre = teclado.readLine();
        System.out.println("Apellido 1");
        apellido1 = teclado.readLine();
        System.out.println("Apellido 2");
        apellido2 = teclado.readLine();
        System.out.println("Teléfono");
        tlf = Integer.parseInt(teclado.readLine());
        System.out.println("Dirección");
        dir = teclado.readLine();
        Statement stmt= conn.createStatement();
        //insertamos una nueva playlist
        String sqlFinal= "INSERT INTO Persona VALUES(";
        sqlFinal=sqlFinal + max + ",'" + nombre + "', '" + apellido1 +"', '" + apellido2 + "', " + tlf + ",'" + dir + "');";
        System.out.println(sqlFinal);
        stmt.executeUpdate(sqlFinal);
    }
}
