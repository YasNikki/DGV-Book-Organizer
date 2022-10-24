
package main;

public class Main {

    public static void main(String[] args) {
        
        controller.Conexao_DB.carregaDriver();
        new view.splash_DGV_GUI().setVisible(true);
        
    }
    
}
