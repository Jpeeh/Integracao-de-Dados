package trabalhopratico;

/**
 *
 * @author Joao & Miguel
 */
public class Autor {
    private static int sequencia = 0;
    String nome;
    StringBuilder generos, data_nasc;

     public Autor(String nome, StringBuilder data_nasc, StringBuilder generos) {
        this.nome = nome;
        this.data_nasc = data_nasc;
        this.generos = generos;
        sequencia++;
    }
     
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public StringBuilder getGeneros() {
        return generos;
    }

    public int getSequencia() {
        return sequencia;
    }

    public StringBuilder getData_nasc() {
        return data_nasc;
    }
}
    