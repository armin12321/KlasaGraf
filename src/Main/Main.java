package Main;

import java.util.Scanner;
import Graf.Graf;

public class Main {
    public static void main(final String[] args) {
        final Scanner ulaz = new Scanner(System.in);
        final Graf<Integer> testniGraf1 = unesiGrafInteger(ulaz);
        final Graf<String> testniGraf2 = unesiGrafString(ulaz);

        Integer početni1 = 0;
        String početni2 = "";

        System.out.println("Koji je početni čvor grafa sa integerima?");
        početni1 = ulaz.nextInt();

        ulaz.nextLine();

        System.out.println("Koji je početni čvor grafa sa stringovima?");
        početni2 = ulaz.nextLine();

        testniGraf1.Bfs(početni1);
        testniGraf1.Dfs(početni1);
        testniGraf1.Dijkstra(početni1);
        testniGraf1.BellmanFord(početni1);
        if (testniGraf1.jeLiBipartitan(početni1) == true)
            System.out.println("Graf je bipartitan!");
        else
            System.out.println("Graf nije bipartitan!");

        testniGraf2.Bfs(početni2);
        testniGraf2.Dfs(početni2);
        testniGraf2.Dijkstra(početni2);
        testniGraf2.BellmanFord(početni2);
        if (testniGraf2.jeLiBipartitan(početni2) == true)
            System.out.println("Graf je bipartitan!");
        else
            System.out.println("Graf nije bipartitan!");

        ulaz.close();
    }

    // za čvorove čija su imena stringovi
    public static Graf<String> unesiGrafString(final Scanner ulaz) {
        final Graf<String> mojGraf = new Graf<String>();
        String početniČvor = "";
        String krajnjiČvor = "";
        Integer težinaIvice = 0, brojIvica = 0;
        Integer usmjerenGraf = 0;

        System.out.println("Unesi 1 za usmjereni graf, ili drugi broj za neusmjereni graf:");
        usmjerenGraf = ulaz.nextInt();
        ulaz.nextLine();

        if (usmjerenGraf == 1)
            mojGraf.setUsmjereniGraf(true);
        else
            mojGraf.setUsmjereniGraf(false);

        System.out.println("Unesi koliko ivica želiš u tvome grafu?");
        brojIvica = ulaz.nextInt();
        ulaz.nextLine();

        System.out.println("Ivice su oblika (početniČvor, krajnjiČvor, težinaIvice).");

        for (int i = 0; i < brojIvica; i++) {
            System.out.println("Unesi " + (i + 1) + ". ivicu po redu:");
            početniČvor = ulaz.nextLine();
            krajnjiČvor = ulaz.nextLine();
            težinaIvice = ulaz.nextInt();
            ulaz.nextLine();
            mojGraf.dodajIvicu(početniČvor, krajnjiČvor, težinaIvice);
        }

        return mojGraf;
    }

    // za čvorove čija su imena integeri
    public static Graf<Integer> unesiGrafInteger(final Scanner ulaz) {
        final Graf<Integer> mojGraf = new Graf<Integer>();
        Integer početniČvor = 0;
        Integer krajnjiČvor = 0;
        Integer težinaIvice = 0, brojIvica = 0;
        Integer usmjerenGraf = 0;

        System.out.println("Unesi 1 za usmjereni graf,a drugi broj za neusmjereni graf:");
        usmjerenGraf = ulaz.nextInt();
        ulaz.nextLine();

        if (usmjerenGraf == 1) 
            mojGraf.setUsmjereniGraf(true);
        else
            mojGraf.setUsmjereniGraf(false);

        System.out.println("Unesi koliko ivica želiš u tvome grafu?");
        brojIvica = ulaz.nextInt();
        ulaz.nextLine();

        System.out.println("Ivice su oblika (početniČvor, krajnjiČvor, težinaIvice).");

        for (int i = 0; i < brojIvica; i++) {
            System.out.println("Unesi " + (i + 1) + ". ivicu po redu:");
            početniČvor = ulaz.nextInt();
            krajnjiČvor = ulaz.nextInt();
            težinaIvice = ulaz.nextInt();
            ulaz.nextLine();
            mojGraf.dodajIvicu(početniČvor, krajnjiČvor, težinaIvice);
        }

        return mojGraf;
    }
}

