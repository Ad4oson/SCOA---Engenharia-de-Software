/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package academico.view.Aluno;

import academico.controller.AlunoController;
import academico.model.Feedback;
import academico.model.JPAUtil;
import academico.model.Requisicao;
import academico.model.TipoFeedback;
import academico.model.TipoRequisicao;
import jakarta.persistence.EntityManager;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Windows 11
 */
public class RequisicaoTableModel extends AbstractTableModel {
    
     
    private List<Requisicao> lista;
    private String[] colunas = {
        "Id","Requisicao", "Tipo", "Deletar"
    };

    public RequisicaoTableModel(List<Requisicao> lista) {
        this.lista = lista;
    }

    @Override
    public Class<?> getColumnClass(int col) {
    return switch (col) {
        case 0 -> int.class;
        case 2 -> TipoRequisicao.class;
        case 3 -> Boolean.class;
        default -> String.class;
    };
    }

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int col) {
        return colunas[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Requisicao r = lista.get(row);

        return switch (col) {
            case 0 -> r.getId();
            case 1 -> r.getTexto();
            case 2 -> r.getTipo();
            case 3 -> r.isDeleted(); //boolean
            default -> null;
        };
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col != 0; // apenas coluna de frequência editável
    }

    @Override
    public void setValueAt(Object value, int row, int col) {

        EntityManager em = JPAUtil.getEntityManager();
        AlunoController aluno = new AlunoController();
        Requisicao r = lista.get(row);

        
        switch (col) {
        
            case 1 -> {
                lista.get(row).setTexto((String) value);
                String texto = (String) value;
                
                r.setTexto(texto);

                aluno.atualizarRequisicao(
                em,
                r.getId(),
                r.getTexto(),
                r.getTipo()
                 );

                fireTableCellUpdated(row, col);
            }
                
            case 2 -> {
                lista.get(row).setTipo((TipoRequisicao) value);
                TipoRequisicao tipo = (TipoRequisicao) value;
                r.setTipo(tipo);


                aluno.atualizarRequisicao(
                em,
                r.getId(),
                r.getTexto(),
                r.getTipo()
                 );

                fireTableCellUpdated(row, col);
                
            }  
            case 3 -> {
                
                lista.get(row).setDeleted((Boolean) value);
                Boolean deleted = (Boolean) value;
                r.setDeleted(deleted);


                aluno.atualizarRequisicao(
                em,
                r.getId(),
                r.getTexto(),
                r.getTipo()
                 );

                fireTableCellUpdated(row, col);
            }
        
                
        }
 
            

    }

    public List<Requisicao> getLista() {
        return lista;
    }
    
    
    
    
    
}
