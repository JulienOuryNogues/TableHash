import java.io.*;

/**
 * Created by Julien on 11/9/2015.
 */



public class Map {
    /**
     * Created by Julien on 11/9/2015.
     */
    //voici ma liste chainee
    private class ListeChaine {
        private String key;
        private int value;
        private ListeChaine next;
        private int len;


        private ListeChaine() {
            this.len=0;
        }

        private ListeChaine(String key, int value){
            this.key=key;
            this.value=value;
            this.next=null;
            this.len=1;
        }

        private ListeChaine(String key, int value, ListeChaine next){
            this.key=key;
            this.value=value;
            this.next=next;
            this.len=next.len +1;
        }

        private boolean containsKey(String key){
            if (this.is_empty()){
                return false;
            }else if (this.key.equals(key)) {
                return true;
            }else
                return (this.next).containsKey(key);
        }

        //dans une liste qui contient deja key
        private int getValue(String key){
            if (this.key.equals(key)){
                return this.value;
            }
            return (this.next).getValue(key);
        }

        // dans une liste qui contient deja key
        private void delKey(String key){
            if(this.next.key.equals(key)){
                this.next=this.next.next;
                this.len-=1;
                return;
            }
            this.next.delKey(key);
        }

        private void print(){
            if(this.len==0){
                return;
            }
            System.out.print(key + ":"+ value+" ");
            this.next.print();
        }


        private boolean is_empty(){
            return this.len==0;
        }

    }


    // equivalent d'un dictionnaire
    private int N=10; // taille
    private ListeChaine[] table=new ListeChaine[N];
    private int limit = 10; //Nombre de collisions limites


    private int hash(String key){
        return hash_bon(key);
    }

    // plante des qu'on extend la table !!
    private int hash_mauvais(String key){
        int res=0;
        for (int i = 0; i < key.length(); i++) {
            res +=key.charAt(i);
        }
        return res;
    }


    private int hash_moyen (String key){
        int hash = 7;
        for (int i = 0; i < key.length(); i++) {
            hash = hash*31 + key.charAt(i);
        }
        return hash;
    }

    private int hash_bon (String key){
        return (key.hashCode());
    }




    // dans le cas ou la table de hashage n'est plus assez grande (depassage de la limite)
    private void extend (){
        int NN =N;
        if (N<400){
            N*=2; // on double
        }else{
            N+=N/10; // a partir d'un certain nombre, j'augmente de 10%
        }
        ListeChaine[] table_ancienne = table;
        table = new ListeChaine[N];
        for (int i=0; i<N;i++){
            table[i]=new ListeChaine();
        }
        for (int i=0;i<NN;i++){
            ListeChaine temp = table_ancienne[i];
            while(!(temp.is_empty())){
                put(temp.key,temp.value);
                temp=temp.next;
            }
        }
    }


    public Map(){
        for (int i=0; i<N;i++){
            table[i]=new ListeChaine();
        }
    }


    public boolean containsKey(String key){
        int hash_reduit = hash(key) % N;
        return table[hash_reduit].containsKey(key);
    }

    public void put(String key, int value){
        if (key.equals("")){
            System.out.println("Erreur : la cle demandee est vide");
            return;
        }
        int hash_reduit = hash(key) % N;
        //des fois, hashcode renvoie des nombres négatifs...
        if (hash_reduit<0){
            hash_reduit+=N;
        }
        // verifier si la key est deja utilisee
        ListeChaine place = table[hash_reduit];
        if (place.containsKey(key)){
            System.out.println("Attention, la clé est deja utilisée");
            return;
        }
        table[hash_reduit]= new ListeChaine(key,value,place);
        // si la limite ets depassee, on augmente la taille
        if (table[hash_reduit].len>limit){
            extend();
        }
    }


    public int get(String key){
        int hash_reduit = hash(key) % N;
        ListeChaine place= table[hash_reduit];
        if (key.equals("")){
            System.out.println("Attention la cle demandee est vide");
            return (-1);
        }
        if (!place.containsKey(key)){
            System.out.println("Attention, la cle "+key+" n'existe pas");
            return (-1);
        }
        return place.getValue(key);
    }


    public void del(String key){
        int hash_reduit = hash(key) % N;
        // verifier si la key est deja utilisee
        ListeChaine place = table[hash_reduit];
        if (place.containsKey(key)){
            System.out.println("Attention, la clé "+key+" n'existe pas");
            return;
        }
        place.delKey(key);
    }


    public void print(){
        for(int i=0;i<N;i++){
            System.out.print("Case "+i+" : { ");
            table[i].print();
            System.out.println("}");
        }
    }


    //telecharge le dictionnaire francais

    public void load(){
        DataInputStream dis;
        try {
            dis = new DataInputStream(
                    new BufferedInputStream(
                            new FileInputStream(
                                    new File("lexiqueFR.txt"))));

            String line=dis.readLine();
            while (line != null) {
                System.out.println(line);
                put(line,(int) (Math.random()*100));
                line=dis.readLine();
            }
            dis.close();
        } catch (FileNotFoundException e) {
            System.out.println("Il n'y a rien a charger ! ");
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }

    }

    }



