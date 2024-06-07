/**
 *
 * @author Pietro Rosolia
 */
package Model.Pedido;

import Model.Produto.Produto;


public class Pedido extends Produto{
    private int id;
    private String cliente;
    private int qnt;
    private String data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getQnt() {
        return qnt;
    }

    public void setQnt(int qnt) {
        this.qnt = qnt;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Pedido{" + "id=" + id + ", cliente=" + cliente + ", qnt=" + qnt + ", data=" + data + '}';
    }
}
