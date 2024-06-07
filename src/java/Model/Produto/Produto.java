/**
 *
 * @author Pietro Rosolia
 */
package Model.Produto;


public class Produto {
    
    private int id;
    private String nome;
    private double preco;
    private String desc;
    private String imagem;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String img) {
        this.imagem = img;
    }

    @Override
    public String toString() {
        return "Produto{" + "nome=" + nome + ", preco=" + preco + ", descrição=" + desc + '}';
    }
}
