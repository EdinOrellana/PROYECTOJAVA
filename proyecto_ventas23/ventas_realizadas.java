package proyecto_ventas23;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ventas_realizadas {

    JPanel ventas_general = new JPanel();
    JTable tabla;
    JScrollPane sp;
    JTextField Factura = new JTextField();
    JTextField nit = new JTextField();
    JTextField Nombre = new JTextField();
    JTextField Fecha = new JTextField();

    private void inicio() {
        ventas_general.setLayout(null);
         
        JLabel l1 = new JLabel("No.Factura");
        l1.setBounds(140, 20, 75, 30);

        JLabel l2 = new JLabel("NIT");
        l2.setBounds(420, 20, 75, 30);

        JLabel l3 = new JLabel("Nombre");
        l3.setBounds(140, 100, 75, 30);

        JLabel l4 = new JLabel("Fecha");
        l4.setBounds(420, 100, 75, 30);

        Factura.setBounds(225, 20, 160, 30);
        nit.setBounds(520, 20, 160, 30);

        Nombre.setBounds(225, 100, 160, 30);
        Fecha.setBounds(520, 100, 160, 30);

        JButton b1 = new JButton("Buscar");
        b1.setBounds(140, 150, 540, 30);
        ventas_general.add(b1);

        ventas_general.add(l1);
        ventas_general.add(l2);
        ventas_general.add(l3);
        ventas_general.add(l4);

        ventas_general.add(Factura);
        ventas_general.add(nit);
        ventas_general.add(Nombre);
        ventas_general.add(Fecha);

        ActionListener verificar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int factura_uso;
                if (Factura.getText().trim().equals("")) {
                    factura_uso = 0;
                } else {

                    factura_uso = Integer.parseInt(Factura.getText().trim());

                }
                int nit_uso;
                if (nit.getText().trim().equals("")) {
                    nit_uso = 0;
                } else {

                    nit_uso = Integer.parseInt(nit.getText().trim());

                }

                String Nombre_uso;
                if (Nombre.getText().trim().equals("")) {
                    Nombre_uso = null;
                } else {

                    Nombre_uso = Nombre.getText().trim();

                }
                String Fecha_uso;
                if (Fecha.getText().trim().equals("")) {
                    Fecha_uso = null;
                } else {

                    Fecha_uso = Fecha.getText().trim();

                }

                VentasDAO vd = new VentasDAO();
                Object resultado [][]= vd.filtro_ventas(factura_uso, nit_uso, Nombre_uso, Fecha_uso);
                if(resultado==null){
                    System.out.println("Entro en la ventas realizadas null");
                    sp.setVisible(false);
                    tabla();
                }else{
                    System.out.println("Entro en la ventas realizadas con datos");
                sp.setVisible(false);
                resultados(resultado);    
                }
            }
        };

        b1.addActionListener(verificar);
    }

    private void tabla() {
        String columnas[] = {"No_Factura", "NIT", "Nombre", "Fecha", "Total"};
        VentasDAO vd = new VentasDAO();
        Object filas[][] = vd.listar_tabla();
        tabla = new JTable(filas, columnas);
        sp = new JScrollPane(tabla);
        sp.setBounds(50, 200, 750, 400);
        ventas_general.add(sp);

    }
 private void resultados(Object result[][]) {
        String columnas[] = {"No_Factura", "NIT", "Nombre", "Fecha", "Total"};
        tabla = new JTable(result, columnas);
        sp = new JScrollPane(tabla);
        sp.setBounds(50, 200, 750, 400);
        ventas_general.add(sp);

    }
    public void ejecutar() {
        inicio();
        tabla();
    }
}
