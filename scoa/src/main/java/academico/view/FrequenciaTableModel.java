package academico.view;

import academico.controller.ProfessorController;
import java.util.List;
import academico.model.FrequenciaAluno;
import academico.model.JPAUtil;
import jakarta.persistence.EntityManager;

import javax.swing.table.AbstractTableModel;

public class FrequenciaTableModel extends AbstractTableModel {

    private List<FrequenciaAluno> lista;
    private String[] colunas = {
        "Aluno", "Turma", "Disciplina", "Presente", "Dia"
    };

    public FrequenciaTableModel(List<FrequenciaAluno> lista) {
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
        return col == 3; // apenas coluna de frequência editável
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        if (col == 3) {
            lista.get(row).setPresente((Boolean) value);
            Boolean presente = (Boolean) value;
            FrequenciaAluno f = lista.get(row);
            f.setPresente(presente);
            
            EntityManager em = JPAUtil.getEntityManager();
            
            ProfessorController professor = new ProfessorController();
            professor.atualizarFrequencia(
            em,
            f.getId(),
            f.getJustificativa(),
            presente,
            f.getData()
             );
            
            fireTableCellUpdated(row, col);
        }
    }

    public List<FrequenciaAluno> getLista() {
        return lista;
    }
}
