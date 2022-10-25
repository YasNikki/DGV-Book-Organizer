
package model;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import view.*;

public class database_funcoes_DAO {
    
    static String url = "jdbc:mysql://localhost/dgv_livros";
    static String username = "root";
    static String password = "";
    
    static String nome_professor;
    static String nome_chave;
    static String nome_item;
    
    // CADASTRAR NO BANCO DE DADOS (1)
    
    public static void updateRegistroLivros() throws SQLException{
        String SQL = "select * from registrolivros";
        
        DefaultTableModel model = (DefaultTableModel) Livros_DGV_GUI.registros.getModel();
        model.setRowCount(0);
        
        Connection con = (Connection) DriverManager.getConnection(url, username, password);
        
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            
            while(rs.next()){
                String nome = rs.getString("nome");
                String serie = rs.getString("serie");
                String livro = rs.getString("livro");
                String data_inicio = rs.getString("data_inicio");
                String data_final = rs.getString("data_final");
                String pendencia = rs.getString("pendencia");
                
                String tBD[] = {nome, serie, livro, data_inicio, data_final, pendencia};
                model.addRow(tBD);
                
            }
        }catch (Exception e){
        }
    }
    
    public static void registraLivro(){
        
        String prof = Livros_DGV_GUI.aluneCBX.getText();
        String serie = Livros_DGV_GUI.serieCBX.getText();
        String livro = Livros_DGV_GUI.livroCBX.getText();
        String entrega = Livros_DGV_GUI.dataCBX.getText();
        
        Date dataDiaAtual = new Date();
        String dia = new SimpleDateFormat("dd/MM/yyyy").format(dataDiaAtual);
        
        controller.Conexao_DB.carregaDriver();

        Connection con = null;
        
        try{
            con = (Connection) DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(database_funcoes_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String SQL = "insert into registrolivros(nome,serie,livro,data_inicio,data_final,pendencia) values(?,?,?,?,?,?)";
        
        try{
            PreparedStatement insert = (PreparedStatement) con.prepareStatement(SQL);
            insert.setString(1, prof);
            insert.setString(2, serie);
            insert.setString(3, livro);
            insert.setString(4, dia);
            insert.setString(5, entrega);
            insert.setString(6, "Pendente");
            
            insert.execute();
            
        }catch (Exception ex) {
            Logger.getLogger(database_funcoes_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            updateRegistroLivros();
        } catch (SQLException ex) {
            Logger.getLogger(database_funcoes_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Livros_DGV_GUI.aluneCBX.setText("");
        Livros_DGV_GUI.serieCBX.setText("");
        Livros_DGV_GUI.livroCBX.setText("");
        Livros_DGV_GUI.dataCBX.setText("");
        
    }
    
    public static void verificaLivro(){
        
        controller.Conexao_DB.carregaDriver();

        Connection con = null;
        
        try{
            con = (Connection) DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(database_funcoes_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DefaultTableModel model = (DefaultTableModel) Livros_DGV_GUI.registros.getModel();
        int row = Livros_DGV_GUI.registros.getSelectedRow();
        String alune = Livros_DGV_GUI.registros.getModel().getValueAt(row, 0).toString();
        String serie = Livros_DGV_GUI.registros.getModel().getValueAt(row, 1).toString();
        String livro = Livros_DGV_GUI.registros.getModel().getValueAt(row, 2).toString();
        String data1 = Livros_DGV_GUI.registros.getModel().getValueAt(row, 3).toString();
        String data2 = Livros_DGV_GUI.registros.getModel().getValueAt(row, 4).toString();
        
        String SQL = "update registrolivros set pendencia='Entregue' where nome='"+alune+"' and serie='"+serie+"'"
                + " and livro='"+livro+"' and data_inicio='"+data1+"' and data_final='"+data2+"'";
        
        
        try{
            PreparedStatement insert = (PreparedStatement) con.prepareStatement(SQL);
            insert.executeUpdate();
            
        }catch (Exception ex) {
            Logger.getLogger(database_funcoes_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            updateRegistroLivros();
        } catch (SQLException ex) {
            Logger.getLogger(database_funcoes_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void desregistraLivros(){
        
        controller.Conexao_DB.carregaDriver();

        Connection con = null;
        
        try{
            con = (Connection) DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(database_funcoes_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DefaultTableModel model = (DefaultTableModel) Livros_DGV_GUI.registros.getModel();
        int row = Livros_DGV_GUI.registros.getSelectedRow();
        String alune = Livros_DGV_GUI.registros.getModel().getValueAt(row, 0).toString();
        String serie = Livros_DGV_GUI.registros.getModel().getValueAt(row, 1).toString();
        String livro = Livros_DGV_GUI.registros.getModel().getValueAt(row, 2).toString();
        String data1 = Livros_DGV_GUI.registros.getModel().getValueAt(row, 3).toString();
        String data2 = Livros_DGV_GUI.registros.getModel().getValueAt(row, 4).toString();
        
        String SQL = "delete from registrolivros where nome='"+alune+"' and serie='"+serie+"'"
                + " and livro='"+livro+"' and data_inicio='"+data1+"' and data_final='"+data2+"'";
        
        
        try{
            PreparedStatement insert = (PreparedStatement) con.prepareStatement(SQL);
            insert.execute();
            
        }catch (Exception ex) {
            Logger.getLogger(database_funcoes_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            updateRegistroLivros();
        } catch (SQLException ex) {
            Logger.getLogger(database_funcoes_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
