/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package academico.view;

import java.util.List;

import academico.controller.ProfessorController;
import academico.model.JPAUtil;
import academico.model.NotaAluno;
import academico.model.NotaConsultaDTO;
import jakarta.persistence.EntityManager;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Windows 11
 */public class NotaTableModel extends AbstractTableModel {

    private List<NotaConsultaDTO> lista;
    private String[] colunas = {
        "Aluno", "Turma", "Disciplina", "P1", "P2", "PF", "Média Total"
    };

    public NotaTableModel(List<NotaConsultaDTO> lista) {

        this.lista = lista;

    }


    public Class<?> getColumnClass(int col) {
    return switch (col) {
        case 3 -> Double.class;
        case 4 -> Double.class;
        case 5 -> Double.class;
        case 6 -> Double.class;
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

        NotaConsultaDTO n = lista.get(row);
    
        Double media;
        if (n.getP1() == null || n.getP2() == null){

            media = null;

        }
        else if (n.getPf() == null){
            media = (n.getP1()+n.getP2())/2;} 
        else {
            media = (n.getP1()+n.getP2()+n.getPf())/3;}


        return switch (col) {
            case 0 -> n.getAluno();
            case 1 -> n.getTurma();
            case 2 -> n.getDisciplina();
            case 3 -> n.getP1();   
            case 4 -> n.getP2();
            case 5 -> n.getPf();
            case 6 -> media;
            default -> null;
        };
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col == 3 || col == 4 || col == 5; 
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        if (col == 3) {
            lista.get(row).setP1((Double) value);;
            Double P1 = (Double) value;
            NotaConsultaDTO d = lista.get(row);
            d.setP2(P1);
            
            EntityManager em = JPAUtil.getEntityManager();
            
            ProfessorController professor = new ProfessorController();
            professor.atualizarNota(em, 
                d.getAluno(), 
                d.getTurma(), 
                "P1", 
                P1);

            fireTableCellUpdated(row, col);
        }
        else if (col == 4) {

            lista.get(row).setP2((Double) value);;
            Double P2 = (Double) value;
            NotaConsultaDTO d = lista.get(row);
            d.setP2(P2);
            
            EntityManager em = JPAUtil.getEntityManager();
            
            ProfessorController professor = new ProfessorController();
            professor.atualizarNota(em, 
                d.getAluno(), 
                d.getTurma(), 
                "P2", 
                P2);

            fireTableCellUpdated(row, col);
        }
        else if (col == 5){

            lista.get(row).setPf((Double) value);;
            Double PF = (Double) value;
            NotaConsultaDTO d = lista.get(row);
            d.setP2(PF);
            
            EntityManager em = JPAUtil.getEntityManager();
            
            ProfessorController professor = new ProfessorController();
            professor.atualizarNota(em, 
                d.getAluno(), 
                d.getTurma(), 
                "PF", 
                PF);

            fireTableCellUpdated(row, col);



        }


    }

    public List<NotaConsultaDTO> getLista() {
        return lista;
    }
}