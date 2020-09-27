package Graf;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;

public class Graf<Tip> {
    private HashMap<Tip, HashMap<Tip, Integer>> mapa = new HashMap<Tip, HashMap<Tip, Integer>>();   
    private boolean obaSmjeraDefault = true;

    //razne vrste konstruktora//:

    //prazan konstruktor.
    public Graf() {

    }

    //konstruiranje grafa samo sa čvorovima, bez ivica.
    public Graf(List<Tip> čvorovi) {
        for (int i = 0;i < čvorovi.size(); i++) 
            dodajČvor(čvorovi.get(i));              
    }

    //konstruktor kopije:
    public Graf(Graf<Tip> _graf) {
        obaSmjeraDefault = _graf.jeLiUsmjereniGraf();

        for (Tip trenutniČvor : _graf.mapa.keySet()) {      
            this.dodajČvor(trenutniČvor);
            for (Tip komšija : _graf.mapa.get(trenutniČvor).keySet())   
                dodajIvicu(trenutniČvor,  komšija, _graf.mapa.get(trenutniČvor).get(komšija), false);
        }           
    }


    //funkcije za rad sa podacima//:

    public void setUsmjereniGraf(boolean vrijednost) {
        obaSmjeraDefault = !vrijednost;
    }

    public boolean jeLiUsmjereniGraf() {
        return obaSmjeraDefault;
    }

    public void dodajČvor(Tip _čvor) {
        if (!imaČvor(_čvor))
            mapa.put(_čvor, new HashMap<Tip, Integer>());
    }

    public void dodajIvicu(Tip odakle, Tip dokle, Integer težinaIvice, boolean obaSmjera) {
        if (!imaČvor(odakle))
            dodajČvor(odakle);

        if (!imaČvor(dokle))
            dodajČvor(dokle);

        if (!imaIvicu(odakle, dokle))
            mapa.get(odakle).put(dokle, težinaIvice);

        if (obaSmjera && !imaIvicu(dokle, odakle))
            mapa.get(dokle).put(odakle, težinaIvice);   
    }

    public void dodajIvicu(Tip odakle, Tip dokle) {
        dodajIvicu(odakle, dokle, 0, obaSmjeraDefault);
    }

    public Integer getTežina(Tip početak, Tip kraj) {
        return mapa.get(početak).get(kraj);
    }

    public void promijeniTežinuIvice(Tip početak, Tip kraj, Integer novaVrijednostTežine) {
        if (!this.imaIvicu(početak, kraj)) {
            System.out.println("Ivica ne postoji, pravim novu...");
            dodajIvicu(početak, kraj, novaVrijednostTežine, obaSmjeraDefault);
            return;
        }           
        mapa.get(početak).replace(kraj, novaVrijednostTežine);

        if (obaSmjeraDefault)
            mapa.get(kraj).replace(početak, novaVrijednostTežine);
    }

    public void dodajIvicu(Tip odakle, Tip dokle, Integer težinaIvice) {
        //za defaultnu vrijednost varijable obaSmjera:
        dodajIvicu(odakle, dokle, težinaIvice, obaSmjeraDefault);
    }

    public int getBrojČvorova() {
        return mapa.keySet().size();
    }

    public int getBrojIvica(boolean obaSmjera) {
        int broj = 0;

        for (Tip mojTip : mapa.keySet()) 
            broj += mapa.get(mojTip).size();

        if (obaSmjera)
            broj /= 2;

        return broj;
    }

    public int getBrojIvica() {
        //sa podrazumijevanom obaSmjera u true:
        return getBrojIvica(obaSmjeraDefault);
    }

    public void SadržajGrafa() { //ispiši sve što graf sadrži
        System.out.println("Podaci:'čvor:komšija1,težinaPovezanosti | komšija2,težinaPovezanosti ...");
        for (Tip čvor : mapa.keySet()) {
            System.out.print(čvor + " :");
            for (Tip komšija : mapa.get(čvor).keySet()) 
                System.out.print(komšija + ", " + mapa.get(čvor).get(komšija) + "|");
            System.out.println();
        }
    }

    public boolean imaČvor(Tip čvor) {
        if (mapa.containsKey(čvor))
            return true;

        return false;
    }

    public boolean imaIvicu(Tip početak, Tip kraj) {
        if (mapa.get(početak).containsKey(kraj))
            return true;
            
        return false;
    }

    public Queue<Tip> Bfs(Tip početni) { // ispiši redoslijed posjete čvorova pomoću Dfs algoritma
        System.out.println("****Breadth First Search****");

        Tip trenutniČvor = početni; //neka vrijednost za početni.
        Queue<Tip> queue = new LinkedList<Tip>();
        HashMap<Tip, Boolean> posjećen = new HashMap<Tip, Boolean>();
        Queue<Tip> rezultat = new LinkedList<Tip>();

        for (Tip mojTip : mapa.keySet())
            posjećen.put(mojTip, false);

        queue.add(početni);
        posjećen.replace(početni, true);

        System.out.println("Redoslijed posjete čvorova pomoću BFS :");

        while (!queue.isEmpty() ) {
            trenutniČvor = queue.remove();
            rezultat.add(trenutniČvor);                     

            System.out.print(trenutniČvor + " | ");

            for (Tip komšija : mapa.get(trenutniČvor).keySet()) {
                if (!posjećen.get(komšija)) {
                    queue.add(komšija);
                    posjećen.replace(komšija, true);                    
                }               
            }
        }
        System.out.println();

        return rezultat;
    }

    public Queue<Tip> Dfs(Tip početni) { // ispiši redoslijed posjete čvorova pomoću Bfs algoritma
        System.out.println("****Depth First Search****");

        Tip trenutniČvor = početni;
        Stack<Tip> stack = new Stack<Tip>();
        Queue<Tip> rezultat = new LinkedList<Tip>();
        HashMap<Tip, Boolean> posjećen = new HashMap<Tip, Boolean>();

        for (Tip mojTip : mapa.keySet())
            posjećen.put(mojTip, false);

        stack.push(početni);
        posjećen.replace(početni, true);

        System.out.println("Redoslijed posjete čvorova pomoću DFS :");

        while (!stack.isEmpty()) {
            trenutniČvor = stack.pop();
            rezultat.add(trenutniČvor);                     

            System.out.print(trenutniČvor + " | ");

            for (Tip komšija : mapa.get(trenutniČvor).keySet()) {
                if (!posjećen.get(komšija)) {
                    stack.push(komšija);
                    posjećen.replace(komšija, true);
                }                                   
            }                                               
        }
        System.out.println();

        return rezultat;
    }

    private Tip minimalnaUdaljenost(HashMap<Tip, Integer> r, HashMap<Tip, Boolean> a) {
        Tip najmanji = null;
        Integer najmanjaVrijednost = Integer.MAX_VALUE;

        for (Tip i : r.keySet()) {
                if (r.get(i) <= najmanjaVrijednost && !a.get(i)) {
                    najmanjaVrijednost = r.get(i);
                    najmanji = i;               
                }                       
        }

        return najmanji;        
    }

    public HashMap<Tip, Integer> Dijkstra(Tip početak) {
        System.out.println("****Dijkstra****");
        //varijable potrebne za rad algoritma:      
        Tip početni = početak;
        HashMap<Tip, Boolean> posjećen = new HashMap<Tip, Boolean>();
        HashMap<Tip, Integer> rezultat = new HashMap<Tip, Integer>();       

        //inicijalizacija varijabli potrebnih za rad.
        for (Tip trenutniČvor : mapa.keySet()) {
            posjećen.put(trenutniČvor, false);
            rezultat.put(trenutniČvor, Integer.MAX_VALUE);
        }   
        rezultat.replace(početak, 0); 

        while (posjećen.containsValue(false)) { 
            početak = minimalnaUdaljenost(rezultat, posjećen);

            posjećen.replace(početak, true);

            for (Tip komšija : mapa.get(početak).keySet())                              
                if (getTežina(početak, komšija) + rezultat.get(početak) <= rezultat.get(komšija)) 
                        rezultat.replace(komšija, getTežina(početak, komšija) + rezultat.get(početak));

        }

        System.out.println("Najkraće udaljenosti od čvora : " + početni + " su :");
        for (Tip mojTip : mapa.keySet()) 
            System.out.println(mojTip + " : " + rezultat.get(mojTip));

        System.out.println();

        return rezultat;
    }

    public HashMap<Tip, Integer> BellmanFord(Tip početni) {
        System.out.println("****Bellman Ford****");
        //ako želimo da algoritam radi i sa negativnim ivicama, onda graf mora biti usmjeren
        //jer bi inace negativna dvostruka ivica predstavljala ciklus...

        HashMap<Tip, Integer> rezultat = new HashMap<Tip, Integer>();

        for (Tip trenutniČvor : mapa.keySet())          
                rezultat.put(trenutniČvor, Integer.MAX_VALUE);

        rezultat.replace(početni, 0);   

        for (int i = 0; i < this.getBrojČvorova() - 1; i++) {
            for (Tip trenutniČvor : mapa.keySet()) {                
                if (rezultat.get(trenutniČvor) == Integer.MAX_VALUE)
                        continue;
                for (Tip komšija : mapa.get(trenutniČvor).keySet())                     
                    if (rezultat.get(komšija) > rezultat.get(trenutniČvor) 
                                    + this.getTežina(trenutniČvor, komšija))                        
                            rezultat.replace(komšija, rezultat.get(trenutniČvor)
                                    + this.getTežina(trenutniČvor, komšija));                                                       
            }
        }

        //provjera da li postoji negativni ciklus:

        for (Tip trenutniČvor : mapa.keySet()) {
            for (Tip komšija : mapa.get(trenutniČvor).keySet()) 
                if (rezultat.get(komšija) > 
                    rezultat.get(trenutniČvor) + this.getTežina(trenutniČvor, komšija)) {
                        //javljam negativni ciklus u grafu.
                        System.out.println("Vaš graf sadrži negativni ciklus.");
                        System.out.println("Prema tome, nemoguće naći udaljenosti...");
                        return null;
                    }
        }

        System.out.println("Najkraće udaljenosti od čvora : " + početni + " su :");
        for (Tip mojTip : mapa.keySet()) 
            System.out.println(mojTip + " : " + rezultat.get(mojTip));

        return rezultat;
    }

    public boolean jeLiBipartitan(Tip početni) {
        //ovaj algoritam radi samo za povezane grafove
        //ali ipak ga je lagano modifikovati da proradi i sa 
        //nepovezanim grafovima...

        Tip trenutniČvor = početni;
        HashMap<Tip, Integer> boja = new HashMap<Tip, Integer>();
        Queue<Tip> queue= new LinkedList<Tip>();
        
        //postavi sve boje na neobojeno  -1:
        for (Tip a : mapa.keySet())
            boja.put(a, -1);

        queue.add(početni);
        boja.replace(početni, 0);

        while (!queue.isEmpty()) {
            trenutniČvor = queue.remove();

            for (Tip komšija : mapa.get(trenutniČvor).keySet()) {
                if (boja.get(komšija) == -1) { //ako nije posjećen
                    boja.replace(komšija, 1 - boja.get(trenutniČvor));
                    queue.add(komšija);
                } else if (boja.get(komšija) == boja.get(trenutniČvor)) {
                    return false;
                }
            }
        }
        return true;
    }

}