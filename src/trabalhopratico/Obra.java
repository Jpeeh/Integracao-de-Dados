/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhopratico;

/**
 *
 * @author Joao
 */
public class Obra {
    private String cod;
    private String autor;
    private String titulo;
    private String isbn;
    private String ano;
    private String editor;
    private String preco;
    
    public Obra(String cod, String autor, String titulo, String isbn, String ano, String editor, String preco)
    {
        this.cod = cod;
        this.autor = autor;
        this.titulo = titulo;
        this.isbn = isbn;
        this.ano = ano;
        this.editor = editor;
        this.preco = preco;
    }
    
    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

}
