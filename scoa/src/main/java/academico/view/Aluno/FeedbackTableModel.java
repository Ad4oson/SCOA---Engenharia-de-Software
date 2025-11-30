/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package academico.view.Aluno;

import academico.controller.AlunoController;
import academico.model.Feedback;
import academico.model.JPAUtil;
import academico.model.TipoFeedback;
import jakarta.persistence.EntityManager;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Windows 11
 */
public class FeedbackTableModel extends AbstractTableModel {
  
    
    private List<Feedback> lista;
    private String[] colunas = {
        "Id","Feedback", "Tipo", "Deletar"
    };

    public FeedbackTableModel(List<Feedback> lista) {
        this.lista = lista;
    }

    @Override
    public Class<?> getColumnClass(int col) {
    return switch (col) {
        case 0 -> int.class;
        case 2 -> TipoFeedback.class;
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
        Feedback f = lista.get(row);

        return switch (col) {
            case 0 -> f.getId();
            case 1 -> f.getTexto();
            case 2 -> f.getTipoFeedback();
            case 3 -> f.isDeleted(); //boolean
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
        Feedback f = lista.get(row);

        
        switch (col) {
        
            case 1 -> {
                lista.get(row).setTexto((String) value);
                String texto = (String) value;
                
                f.setTexto(texto);

                aluno.atualizarFeedback(
                em,
                f.getId(),
                f.getTexto(),
                f.getTipoFeedback(),
                f.isDeleted()
                 );

                fireTableCellUpdated(row, col);
            }
                
            case 2 -> {
                lista.get(row).setTipoFeedback((TipoFeedback) value);
                TipoFeedback tipo = (TipoFeedback) value;
                f.setTipoFeedback(tipo);


                aluno.atualizarFeedback(
                em,
                f.getId(),
                f.getTexto(),
                f.getTipoFeedback(),
                f.isDeleted()
                 );

                fireTableCellUpdated(row, col);
                
            }  
            case 3 -> {
                
                lista.get(row).setDeleted((Boolean) value);
                Boolean deleted = (Boolean) value;
                f.setDeleted(deleted);


                aluno.atualizarFeedback(
                em,
                f.getId(),
                f.getTexto(),
                f.getTipoFeedback(),
                f.isDeleted()
                 );

                fireTableCellUpdated(row, col);
            }
        
                
        }
 
            

    }

    public List<Feedback> getLista() {
        return lista;
    }
    
    
    
}
