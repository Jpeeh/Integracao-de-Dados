package trabalhopratico;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class trabalhopratico {

    static ArrayList<Autor> procuraAutor(String nome) throws FileNotFoundException, IOException {
        ArrayList<Autor> lista = new ArrayList();
        String link = "https://pt.wikipedia.org/wiki/";

        HttpRequestFunctions.httpRequest1(link, nome, "autores.html");

        String er_nome = "<h1 id=\"firstHeading\" class=\"firstHeading\" lang=\"pt\">([^<]+)</h1>";
        String er_datanasc = "<a href=\"/wiki/([^>]+)\" class=\"mw-redirect\" title=\"([^>]+)\">([^<]+)</a> de <a href=\"/wiki/([^>]+)\" title=\"([^>]+)\">([^<]+)</a>";
        String er_nac = "<a href=\"/wiki/([^>]+)\" title=\"([^>]+)\">([^<]+)</a>";
        String er_generos = "<td style=\"vertical-align: top; text-align: left; padding:4px 4px 4px 0;\"><a href=\"/wiki/([^<]+)\" title=\"([^>]+)\">([^<]+)</a>, <a href=\"/wiki/([^<]+)\" title=\"([^>]+)\">([^<]+)</a></td>";
       
                
        Scanner ler;
        ler = new Scanner(new FileInputStream("autores.html"));  //PARA LER DO FICHEIRO HTML ONDE ESTÃO OS DADOS

        Pattern p1 = Pattern.compile(er_nome);
        Pattern p2 = Pattern.compile(er_datanasc);
        Pattern p3 = Pattern.compile(er_generos);
        Matcher m1, m2, m3;
        
        String linha;
        String nome1;
        StringBuilder data = new StringBuilder();  //ano de nascimeto 
        StringBuilder generos = new StringBuilder();  //para agregar todos os géneros (podem existir vários)

        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            m1 = p1.matcher(linha);
            m2 = p2.matcher(linha);
            m3 = p3.matcher(linha);
            while (m1.find() && m2.find() && m3.find()) {
                    nome1 = m1.group(1);
                    data.append(m2.group(3)).append(" de ").append(m2.group(6));
                    generos.append(m3.group(3)).append("\n").append(m3.group(6));
                    System.out.println("Nome: "+nome1+" Data: "+data+" Generos: "+generos);
                    Autor aut = new Autor(nome1,data,generos);
                    lista.add(aut);
            }
        }
        return lista;
    }

    public static void main(String[] args) throws IOException {
        ArrayList<Autor> res = new ArrayList();
        System.out.println("Autor a pesquisa: ");
        Scanner ler = new Scanner(System.in);  //PARA LER DA CONSOLA
        String linha;
        linha = ler.nextLine();

        res = procuraAutor(linha);

        System.out.println("Listagem de " + res.size() + " produtos encontrados");
        for (int i = 0; i < res.size(); i++) {
            System.out.println("Nome: " + res.get(i).getNome());
        }
    }
}
