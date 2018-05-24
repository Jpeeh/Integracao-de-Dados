package trabalhopratico;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;

//PROCURA_NOME FEITO!
//PROCURA_DATANASC FEITO!
//PROCURA_GENERO FEITO!
//PROCURA_NACIONALIDADE FEITO! (PRECISA DE SER CORRIGIDO)
public class trabalhopratico {

    public static String procuraNome(String nome) throws FileNotFoundException, IOException {
        String link = "https://pt.wikipedia.org/wiki/";
        HttpRequestFunctions.httpRequest1(link, nome, "autores.html");

        String er_nome = "<h1 id=\"firstHeading\" class=\"firstHeading\" lang=\"pt\">([^<]+)</h1>";
        String er_nac = "<a href=\"/wiki/([^>]+)\" title=\"([^>]+)\">([^<]+)</a>";

        Scanner ler;
        ler = new Scanner(new FileInputStream("autores.html"));  //PARA LER DO FICHEIRO HTML ONDE ESTÃO OS DADOS

        Pattern p1 = Pattern.compile(er_nome);
        //Pattern p3 = Pattern.compile(er_generos);
        Matcher m;
        String linha;

        //StringBuilder generos = new StringBuilder();  //para agregar todos os géneros (podem existir vários) 
        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            m = p1.matcher(linha);
            if (m.find()) {
                ler.close();
                return m.group(1);
            }
        }
        ler.close();
        return null;
    }

    public static StringBuilder procuraDataNasc(String nome) throws IOException {
        String er = "<a href=\"/wiki/([^>]+)\" class=\"mw-redirect\" title=\"([^>]+)\">([^<]+)</a> de <a href=\"/wiki/([^>]+)\" title=\"([^>]+)\">([^<]+)</a>";
        String link = "https://pt.wikipedia.org/wiki/";
        HttpRequestFunctions.httpRequest1(link, nome, "autores.html");

        Scanner ler;
        ler = new Scanner(new FileInputStream("autores.html"));  //PARA LER DO FICHEIRO HTML ONDE ESTÃO OS DADOS

        Pattern p = Pattern.compile(er);
        StringBuilder data = new StringBuilder();  //ano de nascimento 

        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            Matcher m = p.matcher(linha);
            if (m.find()) {
                data.append(m.group(3)).append(" de ").append(m.group(6));
            }
        }
        ler.close();
        return data;
    }

    public static StringBuilder procuraGeneros(String nome) throws FileNotFoundException, IOException {
        String er = "<td style=\"vertical-align: top; text-align: left; padding:4px 4px 4px 0;\"><a href=\"/wiki/([^<]+)\" title=\"([^>]+)\">([^<]+)</a>, <a href=\"/wiki/([^<]+)\" title=\"([^>]+)\">([^<]+)</a></td>";
        String link = "https://pt.wikipedia.org/wiki/";
        HttpRequestFunctions.httpRequest1(link, nome, "autores.html");

        Scanner ler;
        ler = new Scanner(new FileInputStream("autores.html"));

        Pattern p = Pattern.compile(er);
        StringBuilder generos = new StringBuilder();  //ano de nascimento 

        String linha;
        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            Matcher m = p.matcher(linha);
            if (m.find()) {
                generos.append(m.group(3)).append(" , ").append(m.group(6));
            }
        }
        ler.close();
        return generos;
    }

    public static String procuraNacionalidade(String nome) throws FileNotFoundException, IOException {
        //ESTÁ A RETORNAR SEMPRE O VALOR NULL, CORRIGIR (PROVAVELMENTE A EXPRESSAO REGULAR ESTÁ ERRADA)!!!

        String er = "<td style=\"vertical-align: top; text-align: left; padding:4px\">(?:<a href=\"/wiki/[^\"]*\" title=\"[^\"]*\">(?:[^<]+</a>, )?<img alt=\"[^\"]*\" src=\"[^\"]+\" width=\"[^\"]+\" height=\"[^\"]+\" class=\"[^\"]+\" (?:srcset=\"[^\"]+\" )?data-file-width=\"[^\"]+\" data-file-height=\"[^\"]+\" />(?:</a>)?(?:&#160;)?[^<]+<a href=\"/wiki/[^\"]+\" title=\"[^\"]+\">([^<]+)</a>)?(?:(?:<img alt=\"[^\"]*\" src=\"[^\"]*\" width=\"[^\"]*\" height=\"[^\"]*\" class=\"[^\"]*\" srcset=\"[^\"]*\" data-file-width=\"[^\"]*\" data-file-height=\"[^\"]*\" />(?:&#160;)?)?(?:<a href=\"/wiki/[^\"]*\" title=\"[^\"]*\">)?([^<]+)(?:</a>)?)?</td>|<td style=\"vertical-align: top; text-align: left; padding:4px\"><a href=\"/wiki/[^\"]*\" title=\"[^\"]*\"><img alt=\"[^\"]*\" src=\"[^\"]*\" width=\"[^\"]*\" height=\"[^\"]*\" class=\"[^\"]*\" srcset=\"[^\"]*\" data-file-width=\"[^\"]*\" data-file-height=\"[^\"]*\" /></a> ([^<]+)</td>|<td style=\"vertical-align: top; text-align: left; padding:4px\"><a href=\"/wiki/[^\"]*\" title=\"[^\"]*\"><img alt=\"[^\"]*\" src=\"[^\"]*\" width=\"[^\"]*\" height=\"[^\"]*\" class=\"[^\"]*\" srcset=\"[^\"]*\" data-file-width=\"[^\"]*\" data-file-height=\"[^\"]*\" /></a> <a href=\"/wiki/[^\"]*\" title=\"[^\"]*\">([^<]+)</a>,<td />";
        String link = "https://pt.wikipedia.org/wiki/";
        HttpRequestFunctions.httpRequest1(link, nome, "autores.html");

        Pattern p = Pattern.compile(er);
        Matcher m;
        Scanner ler;
        ler = new Scanner(new FileInputStream("autores.html"));
        String nacionalidade = null;
        String linha;

        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            m = p.matcher(linha);
            if (m.find()) {
                nacionalidade = m.group(26);
            }
        }
        ler.close();
        return nacionalidade;
    }

    public static String procura_DataMorte(String nome) throws IOException {
        String er = "<td scope=\"row\" style=\"vertical-align: top; text-align: left; font-weight:bold; padding:4px 4px 4px 0;\">Morte</td>";
        String er1 = "<a href=\"/wiki/([^<]+)\" title=\"([^<]+)\">([^<]+)</a> de <a href=\"/wiki/([^<]+)\" title=\"([^<]+)\">([^<]+)</a>";
        String link = "https://pt.wikipedia.org/wiki/";
        HttpRequestFunctions.httpRequest1(link, nome, "autores.html");

        Pattern p = Pattern.compile(er);
        Pattern p2 = Pattern.compile(er1);
        Scanner ler;
        ler = new Scanner(new FileInputStream("autores.html"));
        String morte;

        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            Matcher m = p.matcher(linha);
            if (m.find()) {
                while (ler.hasNextLine()) {
                    linha = ler.nextLine();
                    Matcher m2 = p2.matcher(linha);
                    if(m2.find()){
                        morte = m2.group(3) + "de" + m2.group(6);
                        ler.close();
                        return morte;
                    }
                }
            }
        }
        ler.close();
        return null;
    }

    public static Document criaElemento(Autor aux, Document doc) {
        Element raiz;
        if (doc == null) {
            raiz = new Element("autores"); //cria <catalogo>...</catalogo>
            doc = new Document(raiz);
        } else {
            raiz = doc.getRootElement();
        }
        Element autor = new Element("autor");
        String id = String.valueOf(aux.getSequencia());
        Attribute a = new Attribute("id", id);
        autor.setAttribute(a);

        Element nome = new Element("nome").addContent(aux.getNome());
        autor.addContent(nome);

        String data = String.valueOf(aux.getData_nasc());
        Element data_nasc = new Element("data_nascimento").addContent(data);
        autor.addContent(data_nasc);

        String genero = String.valueOf(aux.getGeneros());
        Element generos = new Element("generos").addContent(genero);
        autor.addContent(generos);

        raiz.addContent(autor);

        return doc;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Autor a pesquisar: ");
        Scanner ler = new Scanner(System.in);  //PARA LER DA CONSOLA
        String linha;
        linha = ler.nextLine();
        String aux = procura_DataMorte(linha);
        System.out.println(aux);

        /*
         System.out.println("Nome: " + aux.getNome() + " Data: " + aux.getData_nasc() + " Generos: " + aux.getGeneros());
         Document doc = XMLJDomFunctions.lerDocumentoXML("autores.xml");
         doc = criaElemento(aux, doc);
         XMLJDomFunctions.escreverDocumentoParaFicheiro(doc, "autores.xml"); */
    }
}
