package trabalhopratico;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;

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

    public static String procuraDataNasc(String nome) throws IOException {
        String er = "<td scope=\"row\" style=\"vertical-align: top; text-align: left; font-weight:bold; padding:4px 4px 4px 0;\">Nascimento</td>";
        String er1 = "<td style=\"vertical-align: top; text-align: left; padding:4px 4px 4px 0;\">(<span style=\"white-space:nowrap;\">)?<a href=\"/wiki/[^#]*#Nascimentos\" (class=\"[^\"]*\")? title=\"[^\"]*\">([^<]+)</a> de <a href=\"/wiki/[^\"]*\" title=\"[^\"]*\">([^<]+)</a>[^<]+</span><br />";
        //STRING ER1 PRECISA DE SER CORRIGIDA, SÓ ESTÁ A DAR VALORES CORRCETOS PARA O AUTOR 'PABLO AUSTER' !!

        String link = "https://pt.wikipedia.org/wiki/";
        HttpRequestFunctions.httpRequest1(link, nome, "autores.html");

        Scanner ler;
        ler = new Scanner(new FileInputStream("autores.html"));  //PARA LER DO FICHEIRO HTML ONDE ESTÃO OS DADOS

        Pattern p = Pattern.compile(er);
        Pattern p1 = Pattern.compile(er1);
        String data;

        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            Matcher m = p.matcher(linha);
            if (m.find()) {
                while (ler.hasNextLine()) {
                    linha = ler.nextLine();
                    Matcher m2 = p1.matcher(linha);
                    if (m2.find()) {
                        ler.close();
                        if (m2.group(1) == null) {//se não existir a tag 'span' na expressão regular er1, (não está a assumir!!)
                            data = m2.group(2) + " de " + m2.group(3);
                            return data;
                        } else if (m2.group(2) == null) {
                            data = m2.group(3) + " de " + m2.group(4);//se não existir o atributo 'class' na expressão regular er1
                            return data;
                        } else {
                            data = m2.group(3) + " de " + m2.group(4);
                            return data;
                        }
                    }
                }
            }
        }
        ler.close();
        return null;
    }

    public static String procuraGeneros(String nome) throws FileNotFoundException, IOException { //SÓ FAZ 1 GÉNERO
        String er = "<td scope=\"row\" style=\"vertical-align: top; text-align: left; font-weight:bold; padding:4px 4px 4px 0;\"><a href=\"/wiki/[^\"]*\" title=\"[^\"]*\">([^<]+)</a></td>";
        String er1 = "<td style=\"vertical-align: top; text-align: left; padding:4px 4px 4px 0;\"><a href=\"/wiki/[^\"]*\" title=\"[^\"]*\">(Romance histórico|Drama|Conto|Romance|Crônica|Fábula|Suspense|Thriller psicológico|Romance policial|Diálogo|Literatura gótica|Erotismo|Fantasia|Terror|Literatura gótica)</a>";
        String link = "https://pt.wikipedia.org/wiki/";
        HttpRequestFunctions.httpRequest1(link, nome, "autores.html");

        Scanner ler;
        ler = new Scanner(new FileInputStream("autores.html"));

        Pattern p = Pattern.compile(er);
        Pattern p1 = Pattern.compile(er1);
        String generos;

        String linha;
        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            Matcher m = p.matcher(linha);
            if (m.find()) {
                linha = ler.nextLine();
                Matcher m1 = p1.matcher(linha);
                if (m1.find()) {
                    ler.close();
                    generos = m1.group(1);
                    return generos;
                }
            }
        }
        ler.close();
        return null;
    }

    public static String procura_Nacionalidade(String nome) throws FileNotFoundException, IOException {  //FEITO!!
        String er = "<td scope=\"row\" style=\"vertical-align: top; text-align: left; font-weight:bold; padding:4px 4px 4px 0;\">Nacionalidade</td>";
        String er1 = "<td style=\"vertical-align: top; text-align: left; padding:4px 4px 4px 0;\"><a href=\"/wiki/[^\"]*\" title=\"[^\"]*\">([^<]+)</a></td>";

        String link = "https://pt.wikipedia.org/wiki/";
        HttpRequestFunctions.httpRequest1(link, nome, "autores.html");

        Pattern p = Pattern.compile(er);
        Pattern p1 = Pattern.compile(er1);

        Scanner ler;
        ler = new Scanner(new FileInputStream("autores.html"));
        String nacionalidade;
        String linha;

        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            Matcher m = p.matcher(linha);
            if (m.find()) {
                while (ler.hasNextLine()) {
                    linha = ler.nextLine();
                    Matcher m2 = p1.matcher(linha);
                    if (m2.find()) {
                        ler.close();
                        nacionalidade = m2.group(1);
                        nacionalidade = HttpRequestFunctions.capitalize(nacionalidade); //mete a 1ª letra da palavra capitalizada
                        return nacionalidade;
                    }
                }
            }
        }
        ler.close();
        return null;
    }

    public static String procura_DataMorte(String nome) throws IOException { //FEITO!!
        String er = "<td scope=\"row\" style=\"vertical-align: top; text-align: left; font-weight:bold; padding:4px 4px 4px 0;\">Morte</td>";
        String er1 = "<td style=\"vertical-align: top; text-align: left; padding:4px 4px 4px 0;\"><span style=\"white-space:nowrap;\"><a href=\"/wiki/[^\"]*\" title=\"[^\"]*\">([^<]+)</a> de <a href=\"/wiki/[^\"]*\" title=\"[^\"]*\">([^<]+)</a>";
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
                    if (m2.find()) {
                        morte = m2.group(1) + " de " + m2.group(2);
                        ler.close();
                        return morte;
                    } else {
                        ler.close();
                        return "ainda em actividade";
                    }
                }
            }
        }
        ler.close();
        return null;
    }

    public static String procuraPremios(String nome) throws IOException {  //SÓ ENCONTRA O 1º PRÉMIO DE CADA AUTOR
        //ER1 PARA VER SE O AUTOR TEM PRÉMIOS OU NÃO 
        String er1 = "<td scope=\"row\" style=\"vertical-align: top; text-align: left; font-weight:bold; padding:4px 4px 4px 0;\">Pr[êé]mios</td>";

        String er2 = "<td style=\"vertical-align: top; text-align: left; padding:4px 4px 4px 0;\"><a href=\"/wiki/[^\"]*\" title=\"[^\"]*\">([^<]+)</a> ([^<]+)<br />";
        String link = "https://pt.wikipedia.org/wiki/";
        HttpRequestFunctions.httpRequest1(link, nome, "autores.html");

        Pattern p = Pattern.compile(er1);
        Pattern p1 = Pattern.compile(er2);

        Scanner ler;
        ler = new Scanner(new FileInputStream("autores.html"));
        String linha;
        String premios;

        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            Matcher m = p.matcher(linha);
            if (m.find()) {  //SE ENCONTRAR PRÉMIOS
                linha = ler.nextLine();
                Matcher m1 = p1.matcher(linha);
                if (m1.find()) {
                    ler.close();
                    premios = m1.group(1);
                    return premios;
                }
            }
        }
        return null;
    }

    public static String procuralink_foto(String nome) throws IOException {
        String link = "https://pt.wikipedia.org/wiki/";
        String er = "<div class=\"floatnone\"><a href=\"/wiki/[^\"]*\" class=\"image\" title=\"[^\"]*\"><img alt=\"[^\"]*\" src=\"([^\"]*)\" width=\"[^\"]*\" height=\"[^\"]*\" srcset=\"[^\"]*\" data-file-width=\"[^\"]*\" data-file-height=\"[^\"]*\" /></a></div>";
        HttpRequestFunctions.httpRequest1(link, nome, "autores.html");

        Scanner ler;
        ler = new Scanner(new FileInputStream("autores.html"));

        Pattern p = Pattern.compile(er);
        String res;
        String linha;

        while (ler.hasNextLine()) {
            linha = ler.nextLine();
            Matcher m = p.matcher(linha);
            if (m.find()) {
                ler.close();
                res = m.group(1);
                return res;
            }
        }
        ler.close();
        return null;
    }

    public static void adicionaAutor(Autor aux) {
        Element raiz;
        Document doc = XMLJDomFunctions.lerDocumentoXML("autores.xml");

        if (doc == null) {
            raiz = new Element("Autores"); //cria <catalogo>...</catalogo>
            doc = new Document(raiz);
        } else {
            raiz = doc.getRootElement();
        }

        Element autor = new Element("Autor");
        String id = String.valueOf(aux.getSequencia());
        Attribute a = new Attribute("id", id);
        autor.setAttribute(a);

        Element nome = new Element("Nome").addContent(aux.getNome());
        autor.addContent(nome);

        Element data_n = new Element("Data_Nascimento").addContent(aux.getData_nasc());
        autor.addContent(data_n);

        Element data_m = new Element("Data_Falecimento").addContent(aux.getData_morte());
        autor.addContent(data_m);

        Element nacionalidade = new Element("Nacionalidade").addContent(aux.getNacionalidade());
        autor.addContent(nacionalidade);

        Element generos = new Element("Generos").addContent(aux.getGeneros());
        autor.addContent(generos);

        Element link_foto = new Element("Link_Foto").addContent(aux.getLink_foto());
        autor.addContent(link_foto);

        raiz.addContent(autor);
        XMLJDomFunctions.escreverDocumentoParaFicheiro(doc, "autores.xml");
    }

    public static void leituraWiki(String linha) throws IOException {
        String enome, d_nasc, d_morte, nac, gen, prem, local_nasc, link_foto;
        enome = procuraNome(linha);
        d_nasc = procuraDataNasc(linha);
        d_morte = procura_DataMorte(linha);
        nac = procura_Nacionalidade(linha);
        gen = procuraGeneros(linha);
        prem = procuraPremios(linha);
        //local_nasc = procura_localNasc(linha);
        link_foto = procuralink_foto(linha);

        Autor aux = new Autor(enome, d_nasc, d_morte, nac, gen, prem, link_foto);

        System.out.println(aux.getNome() + aux.getData_nasc() + aux.getData_morte() + aux.getNacionalidade() + aux.getGeneros());
        adicionaAutor(aux);
    }

    public static String procuraLinkWook(String nome) throws IOException {
        String er = "<a class=\"title-lnk\" href=\"([^\"]+)\"#productPageSectionComments>|<a href=\"(/livro/[^\"]+)\">";
        String link = "https://www.wook.pt/pesquisa/";
        if (Objects.equals(nome, "Roberto Bolaño")) {
            nome = "Roberto Bolano";
        }

        if (Objects.equals(nome, "Paul Auster") || Objects.equals(nome, "Oscar wilde")) {
            HttpRequestFunctions2.httpRequest1(link, nome, "obras.html");
        } else {
            HttpRequestFunctions2.httpRequest1(link, nome, "obras.html");
        }

        Scanner ler = new Scanner(new FileInputStream("obras.html"));
        Pattern p1 = Pattern.compile(er);

        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            Matcher m = p1.matcher(linha);
            if (m.find()) {
                ler.close();
                if (m.group(2) != null) {
                    return m.group(2);
                } else {
                    return m.group(1);
                }
            }
        }
        return null;
    }

    public static String procuraISBN(String nome) throws FileNotFoundException, IOException {
        String link = "https://www.wook.pt" + nome;
        String pesquisa = "";
        String er = "<span id=\"productPageSectionDetails-collapseDetalhes-content-isbn\" class=\"col-xs-12\">ISBN: <span itemprop=\"isbn\" class=\"info\">([^<]+)</span></span>";
        HttpRequestFunctions2.httpRequest1(link, pesquisa, "obras.html");
        Scanner ler = new Scanner(new FileInputStream("obras.html"));
        Pattern p = Pattern.compile(er);
        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            Matcher m = p.matcher(linha);
            if (m.find()) {
                ler.close();
                return m.group(1);
            }
        }
        return null;
    }

    public static String procuraCod_Autor(String nome) throws IOException {  //RETORNA O VALOR NUMÉRICO QUE ESTÁ NO LINK DA PÁGINA DO AUTOR NO WOOK
        String link = "https://www.wook.pt/pesquisa/";
        String er = "de <a href=\"/autor/[^/]+/([^\"]+)\">[^<]+</a>(?:[^<])*</div>";

        HttpRequestFunctions2.httpRequest1(link, nome, "obras.html");

        Scanner ler = new Scanner(new FileInputStream("obras.html"));
        Pattern p1 = Pattern.compile(er);

        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            Matcher m = p1.matcher(linha);
            if (m.find()) {
                ler.close();
                return m.group(1);
            }
        }
        return null;
    }

    public static String procuraPreco(String res) throws IOException {
        String link = "https://www.wook.pt" + res;
        String pesquisa = "";
        String er = "(?:\\s)*<div itemprop=\"price\" class=\"current\" id=\"productPageRightSectionTop-saleAction-price-current\">(?: )?([^<]+)<(?:span|meta) itemprop=\"priceCurrency\" content=\"EUR\">(?:€</span>)?</div>";
        HttpRequestFunctions.httpRequest1(link, pesquisa, "obras.html");
        Scanner ler = new Scanner(new FileInputStream("obras.html"));
        Pattern p = Pattern.compile(er);
        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            Matcher m = p.matcher(linha);
            if (m.find()) {
                ler.close();
                return m.group(1);
            }
        }
        return null;
    }

    public static String procuraAno(String res) throws IOException {
        String link = "https://www.wook.pt" + res;
        String pesquisa = ""; //assumindo que o nome = pesquisa
        String er = "<span id=\"productPageSectionDetails-collapseDetalhes-content-year\" class=\"col-xs-12\">Edição ou reimpressão: <span itemprop=\"datePublished\" class=\"info\">[0-9\\s-]*([^<]{4})</span></span>";
        HttpRequestFunctions2.httpRequest1(link, pesquisa, "obras.html");
        Scanner ler = new Scanner(new FileInputStream("obras.html"));
        Pattern p1 = Pattern.compile(er);
        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            Matcher m = p1.matcher(linha);
            if (m.find()) {
                ler.close();
                return m.group(1);
            }
        }
        return null;
    }

    public static String procuraTitulo(String res) throws IOException {
        String link = "https://www.wook.pt" + res;
        String pesquisa = "";
        String er = "<h1 itemprop=\"name\" id=\"productPageRightSectionTop-title-h1\">([^<]+)</h1>";
        HttpRequestFunctions.httpRequest1(link, pesquisa, "obras.html");
        Scanner ler = new Scanner(new FileInputStream("obras.html"));
        Pattern p1 = Pattern.compile(er);
        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            Matcher m = p1.matcher(linha);
            if (m.find()) {
                ler.close();
                return m.group(1);
            }
        }
        return null;
    }

    public static String procuraEditor(String res) throws IOException {
        String link = "https://www.wook.pt" + res;
        String pesquisa = "";
        String er = "<span itemprop=\"[^\"]*\" itemscope itemtype=\"[^\"]*\" class=\"[^\"]*\">Editor: <span itemprop=\"[^\"]*\" class=\"[^\"]*\">([^<]+)</span></span>";
        HttpRequestFunctions.httpRequest1(link, pesquisa, "obras.html");
        Scanner ler = new Scanner(new FileInputStream("obras.html"));
        Pattern p = Pattern.compile(er);
        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            Matcher m = p.matcher(linha);
            if (m.find()) {
                ler.close();
                return m.group(1);
            }
        }
        return null;
    }

    public static void adicionaObra(Obra aux) {
        Element raiz;
        Document doc = XMLJDomFunctions.lerDocumentoXML("obras.xml");

        if (doc == null) {
            raiz = new Element("Obras"); //cria <catalogo>...</catalogo>
            doc = new Document(raiz);
        } else {
            raiz = doc.getRootElement();
        }

        Element obra = new Element("Livro");

        Element cod_autor = new Element("Codigo_Autor").addContent(aux.getCod());
        Element autor = new Element("Autor").addContent(aux.getAutor());
        Element titulo = new Element("Titulo").addContent(aux.getTitulo());
        Element isbn = new Element("ISBN").addContent(aux.getIsbn());
        Element ano = new Element("Ano").addContent(aux.getAno());
        Element preco = new Element("Preco").addContent(aux.getPreco());
        Element editor = new Element("Editor").addContent(aux.getEditor());
        obra.addContent(cod_autor);
        obra.addContent(autor);
        obra.addContent(titulo);
        obra.addContent(isbn);
        obra.addContent(ano);
        obra.addContent(preco);
        obra.addContent(editor);
        raiz.addContent(obra);
        
        XMLJDomFunctions.escreverDocumentoParaFicheiro(doc, "obras.xml");
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Autor a pesquisar: ");
        Scanner ler = new Scanner(System.in);  //PARA LER DA CONSOLA
        String linha;
        linha = ler.nextLine();
        //leituraWiki(linha);

        String res = procuraLinkWook(linha);  //devolve um link para um livro
        System.out.println(res);
        
        String aux = procuraISBN(res);
        System.out.println(aux);
        
        String cod_autor = procuraCod_Autor(linha);
        System.out.println(cod_autor);
        
        String preco = procuraPreco(res);
        System.out.println(preco);
        
        String ano = procuraAno(res);
        System.out.println(ano);
        
        String titulo = procuraTitulo(res);
        System.out.println(titulo);
        
        String editor = procuraEditor(res);
        System.out.println(editor);
        
        Obra aux1 = new Obra(cod_autor, linha, titulo, aux, ano, editor, preco);
        adicionaObra(aux1);
    }

}
