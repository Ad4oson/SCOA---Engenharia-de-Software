/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package academico.view.Professor;

import academico.controller.ProfessorController;
import academico.model.FrequenciaAluno;
import academico.model.JPAUtil;
import academico.model.PautaDeAula;
import jakarta.persistence.EntityManager;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Windows 11
 */
public class PautaTableModel extends AbstractTableModel{
    
    
    private List<PautaDeAula> lista;
    private String[] colunas = {
        "Conteúdo", "Atividades", "Observações", "Turma", "Deletado"
    };

    public PautaTableModel(List<PautaDeAula> lista) {
        this.lista = lista;
    }


    

    public Class<?> getColumnClass(int col) {
    return switch (col) {
        
        case 4 -> Boolean.class;
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
        PautaDeAula p = lista.get(row);

        return switch (col) {
            case 0 -> p.getConteudo();
            case 1 -> p.getAtividades();
            case 2 -> p.getObservacoes();
            case 3 -> p.getTurma().getNome(); 
            case 4 -> p.isDeleted();
            default -> null;
        };
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col != 3; 
    }

  
    @Override
    public void setValueAt(Object value, int row, int col) {
        if (col == 0) {
            lista.get(row).setConteudo((String) value);
            String conteudo = (String) value;
            PautaDeAula p = lista.get(row);
            p.setConteudo(conteudo);
            
            EntityManager em = JPAUtil.getEntityManager();
            
            ProfessorController professor = new ProfessorController();
            professor.atualizarPauta(
            em,
            p.getId(),
            p.getConteudo(),
            p.getAtividades(),
            p.getObservacoes(),
            p.isDeleted()
            );
            
            fireTableCellUpdated(row, col);
        }
        else if (col == 1){

            lista.get(row).setAtividades((String) value);
            String conteudo = (String) value;
            PautaDeAula p = lista.get(row);
            p.setConteudo(conteudo);
            
            EntityManager em = JPAUtil.getEntityManager();
            
            ProfessorController professor = new ProfessorController();
            professor.atualizarPauta(
            em,
            p.getId(),
            p.getConteudo(),
            p.getAtividades(),
            p.getObservacoes(),
            p.isDeleted()
            );
            
            fireTableCellUpdated(row, col);


        }
        else if (col == 2){

            lista.get(row).setConteudo((String) value);
            String conteudo = (String) value;
            PautaDeAula p = lista.get(row);
            p.setConteudo(conteudo);
            
            EntityManager em = JPAUtil.getEntityManager();
            
            ProfessorController professor = new ProfessorController();
            professor.atualizarPauta(
            em,
            p.getId(),
            p.getConteudo(),
            p.getAtividades(),
            p.getObservacoes(),
            p.isDeleted()
            );
            
            fireTableCellUpdated(row, col);
        }
        else if (col == 4)  {

            lista.get(row).setDeleted((Boolean) value);
            Boolean deleted = (Boolean) value;
            PautaDeAula p = lista.get(row);
            p.setDeleted(deleted);
            
            EntityManager em = JPAUtil.getEntityManager();
            
            ProfessorController professor = new ProfessorController();
            professor.atualizarPauta(
            em,
            p.getId(),
            p.getConteudo(),
            p.getAtividades(),
            p.getObservacoes(),
            p.isDeleted()
             );
            
            fireTableCellUpdated(row, col);



        }  
    }

    public List<PautaDeAula> getLista() {
        return lista;
    }
    
    
    
    
}
