
package controller;

import javax.swing.JOptionPane;

public class Conexao_DB {
    
    public static  void carregaDriver(){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

        } catch (Exception ex) { 
            JOptionPane.showMessageDialog(null, "O carregamento do banco de dados não foi possível.", "ERROR A01", 0);
            System.exit(0);
        }
    }
    
}
