package trabalhopratico;

/**
 *
 * @author Joao & Miguel
 */
public class Autor {
    int sequencia = 0;
    String nome, data_nasc, data_morte, nacionalidade, generos, premios, link_foto;

     public Autor(String nome, String data_nasc, String data_morte, String nacionalidade, String generos, String premios, String link_foto) {
        this.nome = nome;
        this.data_nasc = data_nasc;
        this.data_morte = data_morte;
        this.nacionalidade = nacionalidade;
        this.generos = generos;
        this.premios = premios;
        this.generos = generos;
        this.link_foto = link_foto;
        sequencia = sequencia + 1;
    }

    public String getData_morte() {
        return data_morte;
    }

    public void setData_morte(String data_morte) {
        this.data_morte = data_morte;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getPremios() {
        return premios;
    }

    public void setPremios(String premios) {
        this.premios = premios;
    }

    public String getLink_foto() {
        return link_foto;
    }

    public void setLink_foto(String link_foto) {
        this.link_foto = link_foto;
    }
     
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGeneros() {
        return generos;
    }

    public int getSequencia() {
        return sequencia;
    }

    public String getData_nasc() {
        return data_nasc;
    }
    
    public void setData_nasc(String data_nasc) {
        this.data_nasc = data_nasc;
    }
}
    