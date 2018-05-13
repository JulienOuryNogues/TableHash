/**
 * Created by Julien on 11/9/2015.
 */
public class Main {

    public static void main(String args[]){
        Map dico = new Map();
        /*
        dico.put("ac",10);
        dico.put("ca",2);
        dico.put("aba",3);
        dico.put("aaaa",42);
        dico.put("acaaa",0);
        dico.put("caaaa",0);
        dico.put("aacaa",0);
        dico.put("aaaca",0);
        dico.put("aaaac",0);
        dico.print();
        System.out.print("voici la cle ac : "+dico.get("ac")); */
        dico.load();
        dico.print();

    }



}
