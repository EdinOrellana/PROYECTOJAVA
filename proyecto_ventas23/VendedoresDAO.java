
package proyecto_ventas23;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class VendedoresDAO {
    
    PreparedStatement ps;
    ResultSet rs;
    Connection con;
    proyecto_ventas23.Conexion conectar = new proyecto_ventas23.Conexion();

    public int filtro(String nombre, String contra ){
    
    String sql = "select * from vendedor where nombre = "+"'"+nombre+"'"+" and password = "+"'"+contra+"'"+";";
        try {
            con = conectar.Conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if (rs.next()) {                
                return rs.getInt(7);               
            }
            
        } catch (Exception e) {
           
        }
    
        return -1;
    }
    
    
    
     
    Object datos[][];

    public Object[][] listar_tabla() {
        String instruccion = "select * from vendedores";

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
                datos[x][1] = rs.getString(2);
                datos[x][2] = rs.getInt(3);
                datos[x][3] = rs.getInt(4);
                datos[x][4] = rs.getString(5);
                x++;
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return datos;
    }

    public void crear(int codigo, String nombre, int caja, int ventas, String genero) {
        String sql = "insert into vendedores(codigo, nombre, caja, ventas, genero) values (?,?,?,?,?)";
        try {
            con = conectar.Conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, codigo);
            ps.setString(2, nombre);
            ps.setInt(3, caja);
            ps.setInt(4, ventas);
            ps.setString(5, genero);
            ps.executeUpdate();

        } catch (Exception e) {
        }
    }

    public void modificar(int codigo, String nombre, int caja, int ventas, String genero) {
        String sql = "update vendedores set nombre='" + nombre + "', caja= '" + caja + "', ventas = '" + ventas + "', genero ='" + genero + "' where codigo='" + codigo + "'";

        try {
            con = conectar.Conectar();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void pdf() throws FileNotFoundException, DocumentException {

        FileOutputStream gen = new FileOutputStream("Reporte-Vendedores.pdf");
        Document documento = new Document();

        PdfWriter.getInstance(documento, gen);
        documento.open();

        Paragraph parrafo = new Paragraph("Reporte de Vendedores Base de datos");
        parrafo.setAlignment(1);
        documento.add(parrafo);
        documento.add(new Paragraph("\n"));

        String sql = "select * from vendedores";
        try {
            con = conectar.Conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                documento.add(new Paragraph("Código: " + rs.getInt(1)));
                documento.add(new Paragraph("Nombre: " + rs.getString(2)));
                documento.add(new Paragraph("Caja: " + rs.getInt(3)));
                documento.add(new Paragraph("Ventas: " + rs.getInt(4)));
                documento.add(new Paragraph("Genero: " + rs.getString(5)));
                documento.add(new Paragraph("\n\n"));
            }
        } catch (DocumentException | SQLException e) {
            System.out.println(e);
        }

        documento.close();
        JOptionPane.showMessageDialog(null, "El archivo se creo correctamente");
        try {
            File vendedores_doc = new File("Reporte-Vendedores.pdf");
            Desktop.getDesktop().open(vendedores_doc);
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public void eliminar(int x) {
        String sql = "delete from vendedores where codigo =" + x;
        try {

            con = conectar.Conectar();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("");
        }
    }

}