package almoxarifado.controller;

import java.time.LocalDateTime;
import java.util.List;

import almoxarifado.model.Estoque;
import almoxarifado.model.Produto;
import almoxarifado.model.tipoProduto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ProdutoController {


    private EntityManager em;

    // CREATE
    public void criarProduto(String codigo, String nome, tipoProduto tipo, String descricao, String unidade_medida,
                            Integer qt_atual, Integer estoqueId) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Produto produto = new Produto();

        if (codigo != null) produto.setCodigo(codigo);
        if (nome != null) produto.setNome(nome);
        if (tipo != null) produto.setTipo(tipo);
        if (descricao != null) produto.setDescricao(descricao);
        if (unidade_medida != null) produto.setUnidade_medida(unidade_medida);
        if (qt_atual != null) produto.setQt_atual(qt_atual);
        if (estoqueId != null){
            Estoque estoque = new Estoque();

            try {
                estoque = em.find(Estoque.class, estoqueId);
            }
            catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException("Estoque não encontrado!");
            }
            produto.setEstoque(estoque);

        }

        produto.setCreated_at(LocalDateTime.now());
        produto.setDeleted(false);

        em.persist(produto);
        tx.commit();
    }


    // READ
    public List<Produto> consultarProduto(String localEstoque) {

        if (localEstoque != null){

            String jpqlProduto = """
                    SELECT p
                    FROM Produto p
                    JOIN p.estoque e
                    WHERE e.local = :localEstoque AND p.deleted = false
                    """;
            return em.createQuery(jpqlProduto, Produto.class).setParameter("localEstoque", localEstoque).getResultList();

        }
        else {

            String jpqlProduto = """
                    SELECT p
                    FROM Produto p
                    WHERE p.deleted = false
                    """;

            return em.createQuery(jpqlProduto, Produto.class).getResultList();

        }
        
    }




    // UPDATE
    public void atualizarProduto(Integer produtoId, String codigo, String nome, tipoProduto tipo, String descricao, 
        String unidade_medida, Integer qt_atual) {
        if (produtoId == null)
            throw new RuntimeException("Id nulo! Não é possível atualizar.");
        EntityTransaction tx = em.getTransaction();
        try {

            tx.begin();

            Produto produto = new Produto();
            try {
                produto = em.find(Produto.class, produtoId);
            }
            catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException("Produto não encontrado!");
            }

            if (codigo != null) produto.setCodigo(codigo);
            if (nome != null) produto.setNome(nome);
            if (tipo != null) produto.setTipo(tipo);
            if (descricao != null) produto.setDescricao(descricao);
            if (unidade_medida != null) produto.setUnidade_medida(unidade_medida);
            if (qt_atual != null) produto.setQt_atual(qt_atual);

            tx.commit();
        }
        catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }



    // DELETE (soft delete)
    public void deletarProduto(Integer produtoId) {
        Produto produto = em.find(Produto.class, produtoId);

        if (produto == null)
            throw new RuntimeException("Produto não encontrado!");

        em.getTransaction().begin();
        produto.setDeleted(true);
        
        
        em.getTransaction().commit();
    }


    
}
