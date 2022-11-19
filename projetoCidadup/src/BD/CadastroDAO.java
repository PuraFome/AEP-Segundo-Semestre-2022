package BD;

import Classes.Login;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class CadastroDAO {
    public void salvarCadastro(Login l){
        String sqlCadastrocidadao = "INSERT INTO public.cadastrocidadao (email, senha, nome, cpf, rg, data_nascimento, sexo, telefone) values (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstm = null;

        try{
            conn = ConnectionFactory.criarConexao();
            pstm = conn.prepareStatement(sqlCadastrocidadao);

            pstm.setString(1, l.email);
            pstm.setString(2, l.senha);
            pstm.setString(3, l.nome);
            pstm.setString(4, l.cpf);
            pstm.setString(5, l.rg);
            pstm.setString(6, l.data_nascimento);
            pstm.setString(7, l.sexo);
            pstm.setString(8, l.telefone);

            pstm.execute();  

            JOptionPane.showMessageDialog(null, "Cadastro efetuado com sucesso!");
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Erro cadastrar: "+e);
        } finally {
            try {
                if (pstm != null){
                    pstm.close();
                }

                if (conn != null){
                    conn.close();
                }
            } catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Erro cadastrar, encerrar conexão: "+e);
            }
        }

    }

    public boolean autenticaUsuarioBoleano(Login login) throws Exception{
        String sql = "Select * from public.cadastrocidadao where email = ?  and senha = ? ";

        Connection conn = null;
        PreparedStatement pstm = null;
        boolean resp = false;

        try{
            conn = ConnectionFactory.criarConexao();
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, login.email);
            pstm.setString(2, login.senha);

            ResultSet result = pstm.executeQuery();
            if ( result.next() )
                resp = true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "UsuárioDAO: " + e);
        }
        return resp;            
    }   

    public Login autenticaUsuario(Login login) throws Exception{
        String sql = "Select * from public.cadastrocidadao where email = ?  and senha = ? ";

        Connection conn = null;
        PreparedStatement pstm = null;
        Login resp = new Login();

        try{
            conn = ConnectionFactory.criarConexao();
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, login.email);
            pstm.setString(2, login.senha);

            ResultSet result = pstm.executeQuery();
            while ( result.next()){
                resp.nome = result.getString("nome");
                resp.email = result.getString("email");
                resp.senha = result.getString("senha");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "UsuárioDAO: " + e);
        }
        return resp;            
    }        
    
    public Login buscaUsuario(Login login) throws Exception{
        String sql = "Select * from public.cadastrocidadao where email = ? ";

        Connection conn = null;
        PreparedStatement pstm = null;
        Login resp = new Login();

        try{
            conn = ConnectionFactory.criarConexao();
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, login.email);

            ResultSet result = pstm.executeQuery();
            while ( result.next()){
                resp.nome = result.getString("nome");
                resp.data_nascimento = result.getString("data_nascimento");
                resp.senha = result.getString("senha");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "UsuárioDAO: " + e);
        }
        return resp;            
    }     
    
    public void atualizaLogin(Login login){
        String sql = "UPDATE public.cadastrocidadao SET nome = ?, data_nascimento = ?, endereco = ?";

        Connection conn = null;
        PreparedStatement pstm = null;

        try{
            conn = ConnectionFactory.criarConexao();

            if ( login.senha == null  ) 
                sql += " Where login = ? ";
            else 
                sql += ", senha = ? Where login = ? ";                    
            
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, login.nome);
            pstm.setString(2, login.data_nascimento);
            
            if ( login.senha == null ) {
                pstm.setString(4, login.email);            
            } else {
                pstm.setString(4, login.senha);
                pstm.setString(5, login.email);                                                        
            }          
            
            pstm.execute();  
            JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!");
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: "+e);
        } finally {
            try {
                if (pstm != null){
                    pstm.close();
                }

                if (conn != null){
                    conn.close();
                }
            } catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Erro atualizar, encerrar conexão: "+e);
            }
        }    
    }
    
    public void deleteLogin(Login l){
        String sql = "DELETE FROM public.cadastrocidadao WHERE login = ?";
        
        Connection  conn = null;
        PreparedStatement pstm = null;
      
        try {
            conn = ConnectionFactory.criarConexao();
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, l.email);
            
            pstm.execute();
           
            JOptionPane.showMessageDialog(null, "Usuário excluido com sucesso !!");
        }  catch (Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao deletar: "+e);
        } finally {
            try {
                if (pstm != null){
                    pstm.close();
                }

                if (conn != null){
                    conn.close();
                }
            } catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Erro deletar, encerrar conexão: "+e);
            }
        }
    }    
}
