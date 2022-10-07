

package conexaoSGBD;

import Encapsulamento.CadastroForm;
import Encapsulamento.ModificarPergunta;
import InterfacesGraficas.ModQuestionario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author gabri
 */
public class ConectSGBD {

    private String url;
    private String user;
    private String password;
    private Connection con;
   
   public  ConectSGBD() {
        url = "jdbc:postgresql://localhost:5432/ProjetoInt-2A";
        user = "postgres";
        password = "gabriel123";
                    
        try {
            Class.forName("org.postgresql.Driver");
            
             con = DriverManager.getConnection(url, user, password);
            JOptionPane.showMessageDialog(null, "Conexão realizada com sucesso!! ");
                   } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Conexao deu errado " + e.getMessage());
            e.printStackTrace();
        }
    }
 
   
    public int executeSql(String sql) {
        try {
           
            Statement statement = con.createStatement();
            int res = statement.executeUpdate(sql);
            con.close();
             JOptionPane.showMessageDialog(null, "Criação de tabela realizada com sucesso!! ");
            return res;
            
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    public ArrayList<CadastroForm>  executeQueryUsuario(){
        
        String sql = "SELECT * FROM CriarQuestionario ;";
        ArrayList <CadastroForm> cadastros = new ArrayList();
        
        try{
           
             Statement statement = con.createStatement();
            ResultSet res = statement.executeQuery(sql);
            if (res.next()) {
               
                SimpleDateFormat formatodata = new SimpleDateFormat("dd/M/yyyy");
               // String nome = res.getString("Nome");
                String corPele = res.getNString("corpele");
                int id = res.getInt("idUsuario");
                Date data = res.getDate("nascimento");
                String nascimento = formatodata.format(data);
                
              CadastroForm item = new CadastroForm();
               item.setId(Integer.parseInt(res.getString("idUsuario")));
               //item.setNome(nome);
               item.setCorPele(corPele);
               item.setNascimento(nascimento);
               
              cadastros.add(item);
              
                System.out.println(item);  
                
            return cadastros;
            
            } else {
                System.out.println("Item nao encontrado");
            
            }
    
          con.close();
           
        }
        catch (Exception e) {
            e.printStackTrace();
            
        }
     return null;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
    
 public void InserirPergunta(Connection conn ,int idPergunta , String enunciado , String  altA , String altB ,String altC , String altD ) {
        
     String sql = "INSERT INTO CriarQuestionario VALUES ('"+idPergunta+"','"+enunciado+"','"+altA+"','"+altB+"','"+altC+"','"+altD+"');"; 
      
        try {
            Statement start = conn.createStatement();
            start.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Inserção do enunciado no BD realizado com sucesso!! ");
            
        } catch (SQLException ex) {
            Logger.getLogger(ConectSGBD.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Inserção deu errado " + ex.getMessage());
        }       
   }
   
public void CadastrarPessoa (Connection conn ,int idusuario , String nome , String corPele , String nascimento) {

    String sql = "INSERT INTO public.cadastroUsuario  VALUES ('"+idusuario+"','"+nome+"','"+corPele+"','"+nascimento+"')";
     try {
            Statement start = conn.createStatement();
            start.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Inserção de usuario no BD realizado com sucesso!! ");
            
        } catch (SQLException ex) {
            Logger.getLogger(ConectSGBD.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Inserção deu errado " + ex.getMessage());
        }
    

} 

 public ArrayList<ModQuestionario>  executeQueryPergunta(){
        
        ArrayList <ModQuestionario> cadastroP = new ArrayList();
        String sql = "SELECT * FROM CriarQuestionario";
        try{
            
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet res = statement.executeQuery(sql);
            if (res.next()) {
               
                String Enunciado = res.getString("Enunciado");
                String altA = res.getString("alternativaA");
                String altB = res.getString("alternativaB");
                String altC = res.getString("alternativaC");
                String altD = res.getString("alternativaD");
                int id = res.getInt("idPergunta");
                ;
                
              ModQuestionario items = new ModQuestionario(Enunciado, altA, altB, altC, altD, id);              
              cadastroP.add(items);
              
             return cadastroP;
            
            } else {
                System.out.println("Item nao encontrado");
            
            }
    
          con.close();
           
        }
        catch (Exception e) {
            e.printStackTrace();
        }
     return null;
    } 


}