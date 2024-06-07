/**
 *
 * @author Pietro Rosolia
 */
package Model.Carrinho;

import Model.Produto.Produto;


public class Carrinho extends Produto{
    private int qnt;

    public int getQnt() {
        return qnt;
    }

    public void setQnt(int qnt) {
        this.qnt = qnt;
    }
}
