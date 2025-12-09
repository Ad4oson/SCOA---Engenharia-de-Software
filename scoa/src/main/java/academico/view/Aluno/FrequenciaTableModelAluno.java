/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package academico.view.Aluno;

import academico.controller.ProfessorController;
import academico.model.FrequenciaAluno;
import academico.model.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Windows 11
 */
public class FrequenciaTableModelAluno extends AbstractTableModel{
    
    
    
    private List<FrequenciaAluno> lista;
    private String[] colunas = {
        "Aluno", "Turma", "Disciplina", "Presente", "Dia"
    };

    public FrequenciaTableModelAluno(List<FrequenciaAluno> lista) {
        this.lista = lista;
    }

    public Class<?> getColumnClass(int col) {
    return switch (col) {
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
        FrequenciaAluno f = lista.get(row);

        return switch (col) {
            case 0 -> f.getAluno().getNome();
            case 1 -> f.getTurma().getNome();
            case 2 -> f.getTurma().getDisciplina().getNome();
            case 3 -> f.isPresente();      // boolean
            case 4 -> f.getData();
            default -> null;
        };
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false; // apenas coluna de frequência editável
    }


    public List<FrequenciaAluno> getLista() {
        return lista;
    }
    
}
