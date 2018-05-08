package trabalhopratico;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class trabalhopratico {

    static Autor procuraAutor(String nome) throws FileNotFoundException, IOException {
        Autor aux = null;
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
        String nome1 = null;
        StringBuilder data = new StringBuilder();  //ano de nascimeto 
        StringBuilder generos = new StringBuilder();  //para agregar todos os géneros (podem existir vários)

        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            m1 = p1.matcher(linha);
            m2 = p2.matcher(linha);
            m3 = p3.matcher(linha);
            if (m1.find()) {
                nome1 = m1.group(1);
            } else if (m2.find()) {
                data.append(m2.group(3)).append(" de ").append(m2.group(6));
            } else if (m3.find()) {
                generos.append(m3.group(3)).append(" , ").append(m3.group(6));
            }
        }
        aux = new Autor(nome1, data, generos);
        return aux;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Autor a pesquisa: ");
        Scanner ler = new Scanner(System.in);  //PARA LER DA CONSOLA
        String linha;
        linha = ler.nextLine();
        Autor aux = procuraAutor(linha);

        System.out.println("Nome: " + aux.getNome() + " Data: " + aux.getData_nasc() + " Generos: " + aux.getGeneros());
    }
}
