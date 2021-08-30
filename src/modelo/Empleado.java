package modelo;

import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Empleado extends Persona {

    private int id;
    private String codigo;
    private int idPuesto;
    Conexion nuevaConexion;

    public Empleado() {

    }

    public Empleado(int id, String codigo, String nombres, String apellidos,
            String direccion, String telefono, String fechaNacimiento) {
        super(nombres, apellidos, direccion, telefono, fechaNacimiento);
        this.id = id;
        this.codigo = codigo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(int idPuesto) {
        this.idPuesto = idPuesto;
    }

    public DefaultTableModel leer() {
        DefaultTableModel tabla = new DefaultTableModel();
        try {
            String query;
            nuevaConexion = new Conexion();
            nuevaConexion.abrirConexion();
            //query = "SELECT id_empleado as id, codigo, nombres, apellidos, direccion, telefono, fecha_nacimiento FROM empleados;";
            query = "SELECT * FROM empleados e INNER JOIN puesto p ON e.id_puesto = p.id_puesto;";
            ResultSet consulta = nuevaConexion.conexionBD.createStatement().executeQuery(query);
            String encabezado[] = {"id", "Codigo", "Nombres", "Apellidos", 
                "Direccion", "Telefono", "Nacimiento", "id_puesto", "Puesto"};
            tabla.setColumnIdentifiers(encabezado);

            String datos[] = new String[9];
            while (consulta.next()) {
                datos[0] = consulta.getString("id_empleado");
                datos[1] = consulta.getString("codigo");
                datos[2] = consulta.getString("nombres");
                datos[3] = consulta.getString("apellidos");
                datos[4] = consulta.getString("direccion");
                datos[5] = consulta.getString("telefono");
                datos[6] = consulta.getString("fecha_nacimiento");
                datos[7] = consulta.getString("id_puesto");
                datos[8] = consulta.getString("puesto");
                tabla.addRow(datos);
            }

            nuevaConexion.cerrarConexion();

        } catch (SQLException ex) {
            System.out.println("Error..." + ex.getMessage());
        }
        return tabla;
    }

    @Override
    public void agregar() {
        try {
            PreparedStatement parametro;
            String query;
            nuevaConexion = new Conexion();
            nuevaConexion.abrirConexion();
            query = "INSERT INTO empleados(codigo, nombres, apellidos, direccion,"
                    + "telefono, fecha_nacimiento, id_puesto) VALUES(?,?,?,?,?,?,?);";
            parametro = (PreparedStatement) nuevaConexion.conexionBD.prepareStatement(query);
            parametro.setString(1, getCodigo());
            parametro.setString(2, getNombres());
            parametro.setString(3, getApellidos());
            parametro.setString(4, getDireccion());
            parametro.setString(5, getTelefono());
            parametro.setString(6, getFechaNacimiento());
            parametro.setInt(7, getIdPuesto());
            int exec = parametro.executeUpdate();
            nuevaConexion.cerrarConexion();
            JOptionPane.showMessageDialog(null, Integer.toString(exec) 
                    + " Registros Ingresados", "Mensaje", JOptionPane.INFORMATION_MESSAGE);

        } catch (HeadlessException | SQLException ex) {
            System.out.println("Error..." + ex.getMessage());
        }
    }

    @Override
    public void modificar() {
        //modificar a la tabla clientes
        try {
            PreparedStatement parametro;
            String query;
            nuevaConexion = new Conexion();
            nuevaConexion.abrirConexion();
            query = "UPDATE empleados SET codigo = ?,nombres = ?,apellidos = ?,"
                    + "direccion = ?,telefono = ?,fecha_nacimiento = ?, id_puesto = ? WHERE id_empleado = ?;";
            parametro = (PreparedStatement) nuevaConexion.conexionBD.prepareStatement(query);
            parametro.setString(1, getCodigo());
            parametro.setString(2, getNombres());
            parametro.setString(3, getApellidos());
            parametro.setString(4, getDireccion());
            parametro.setString(5, getTelefono());
            parametro.setString(6, getFechaNacimiento());

            parametro.setInt(7, getIdPuesto());
            parametro.setInt(8, getId());
            int exec = parametro.executeUpdate();
            nuevaConexion.cerrarConexion();

            JOptionPane.showMessageDialog(null, Integer.toString(exec) 
                    + " Registros Modificados", "Mensaje", JOptionPane.INFORMATION_MESSAGE);

        } catch (HeadlessException | SQLException ex) {
            System.out.println("Error" + ex.getMessage());
        }

    }

    public void eliminar() {
        //eliminar a la tabla clientes
        try {
            PreparedStatement parametro;
            String query;
            nuevaConexion = new Conexion();
            nuevaConexion.abrirConexion();
            query = "delete from empleados WHERE id_empleado = ?;";
            parametro = (PreparedStatement) nuevaConexion.conexionBD.prepareStatement(query);
            parametro.setInt(1, getId());
            int exec = parametro.executeUpdate();
            nuevaConexion.cerrarConexion();

            JOptionPane.showMessageDialog(null, Integer.toString(exec)
                    + " Registros Eliminado", "Mensaje", JOptionPane.INFORMATION_MESSAGE);

        } catch (HeadlessException | SQLException ex) {
            System.out.println("Error" + ex.getMessage());
        }
    }

}
