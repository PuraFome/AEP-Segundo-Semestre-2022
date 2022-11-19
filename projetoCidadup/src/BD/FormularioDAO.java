package BD;

import Classes.Formulario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class FormularioDAO {
    public void salvarFormulario(Formulario l){
        String sqlFormulario = "INSERT INTO public.formulario (id_cidade, id_regiao, id_areainvestimento, id_instituicao, sugestao) values (1, ?::int8, ?::int8, ?::int8, ?)";

        Connection conn = null;
        PreparedStatement pstm = null;

        try{
            conn = ConnectionFactory.criarConexao();
            pstm = conn.prepareStatement(sqlFormulario);

            pstm.setString(1, l.regiao);
            pstm.setString(2, l.areainvestimento);
            pstm.setString(3, l.instituicao);
            pstm.setString(4, l.sugestao);

            
            pstm.execute();  

            JOptionPane.showMessageDialog(null, "Formulario Cadastrado com sucesso!");
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

    
}
