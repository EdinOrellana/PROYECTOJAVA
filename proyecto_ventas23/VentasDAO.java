package proyecto_ventas23;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VentasDAO {

    PreparedStatement ps;
    ResultSet rs;
    Connection con;
    proyecto_ventas23.Conexion conectar = new proyecto_ventas23.Conexion();
    Object datos[][];

    public void insertar(int no_factura, int nit, String nombre, String fecha, double total) {

        String sql = "insert into compras(No_Factura,NIT,Nombre,Fecha,Total) values (?,?,?,?,?)";
        try {
            con = conectar.Conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, no_factura);
            ps.setInt(2, nit);
            ps.setString(3, nombre);
            ps.setString(4, fecha);
            ps.setDouble(5, total);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public int filtro() {
        String sql = "select * from compras;";
        int x = 1;
        try {

            con = conectar.Conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                x++;
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return x;

    }

    public Object[][] listar_tabla() {
        String instruccion = "select * from compras";
        try {
            int x = 0;
            con = conectar.Conectar();
            ps = con.prepareStatement(instruccion);
            rs = ps.executeQuery();
            while (rs.next()) {
                x++;
            }

            datos = new Object[x][5];
            x = 0;
            con = conectar.Conectar();
            ps = con.prepareStatement(instruccion);
            rs = ps.executeQuery();
            while (rs.next()) {
                datos[x][0] = rs.getInt(1);
                datos[x][1] = rs.getInt(2);
                datos[x][2] = rs.getString(3);
                datos[x][3] = rs.getDate(4);
                datos[x][4] = rs.getInt(5);
                x++;
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return datos;

    }

    public Object[][] filtro_ventas(int factura, int nit, String Nombre, String Fecha) {

        String sql = "select * from compras where No_Factura ='" + factura + "' or NIT ='" + nit + "' or Nombre = '" + Nombre + "' or Fecha = '" + Fecha + "'; ";

        try {

            con = conectar.Conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            int x = 0;

            while (rs.next()) {
                x++;
            }
            if(x<=0){
                return null;
            }else{
                con = conectar.Conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            datos = new Object[x][5];
            x = 0;
            System.out.println("pasara?");
            while (rs.next()) {
                datos[x][0] = rs.getInt(1);
                datos[x][1] = rs.getInt(2);
                datos[x][2] = rs.getString(3);
                datos[x][3] = rs.getDate(4);
                datos[x][4] = rs.getInt(5);
                x++;
                System.out.println("entro al while");
            }
            System.out.println("paso el while"); 
            return datos;
            }
         
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

}
