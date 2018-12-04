import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class gameOfLife {

    static int nfil = 0, ncol = 0, vid1 = 0, vid2 = 0, res = 0;
    static Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {

        String variant = "";
        settings(variant, entrada);
        char[][] mapNow = new char[nfil][ncol], newMap = new char[nfil][ncol];
        int pob = 0, gen = 1;
        setMaps(mapNow, newMap);
        mapsettings(entrada, mapNow, pob, gen);
        while (true) {
            print(mapNow, pob, gen);
            for (int fil = 1; fil < nfil - 1; fil++) {
                for (int col = 1; col < ncol - 1; col++) {
                    newMap[fil][col] = doa(mapNow, fil, col);
                }
            }
            gen++;
            refresh(mapNow, newMap);
            TimeUnit.MILLISECONDS.sleep(400);
            pob = 0;
        }
    }

    public static void settings(String variant, Scanner entrada) {

        System.out.println("¿Que variante quieres utilizar?  (Formato -> AA/B)");
        boolean work = false;
        while (work == false) {
            try {
                variant = entrada.nextLine();
                if (variant.charAt(2) == '/') {
                    vid1 = Character.getNumericValue(variant.charAt(0));
                    vid2 = Character.getNumericValue(variant.charAt(1));
                    res = Character.getNumericValue(variant.charAt(3));
                    if (vid1 > 8 || vid1 < 1 || vid2 > 8 || vid2 < 1 || res > 8 || res < 1) {
                        work = true;
                        settings(variant, entrada);
                    } else {
                        work = true;
                    }
                }
            } catch (Exception e) {
                System.out.println("No se ha podido realizar la operación, vuelve a introducir los datos.");
            }
        }
        System.out.println("¿Que medida deseas que tenga el mapa? (Formato -> Filas / Columnas)");
        work = false;
        while (work == false) {
            try {
                String mida = entrada.nextLine();
                String[] midaA = mida.split("/");
                nfil = Integer.valueOf(midaA[0]) + 2;
                ncol = Integer.valueOf(midaA[1]) + 2;
                work = true;
            } catch (Exception e) {
                System.out.println("No se ha podido realizar la operación, vuelve a introducir los datos.");
            }
        }
    }

    public static void mapsettings(Scanner entrada, char mapNow[][], int pob, int gen) {

        System.out.println("¿De que manera quieres colocar las células?(Manual o Automático)");
        boolean work = false;
        while (work == false) {
            try {
                String mode = entrada.nextLine().toLowerCase();
                if (mode.equals("manual")) {
                    manual(entrada, mapNow, pob, gen);
                } else if (mode.equals("automático") | mode.equals("automatico")) {
                    auto(entrada, mapNow, pob, gen);
                } else {
                    work = true;
                    mapsettings(entrada, mapNow, pob, gen);
                }
                work = true;
            } catch (Exception e) {
                System.out.println("No se ha podido realizar la operación, vuelve a introducir los datos.");
            }
        }

    }

    public static void setMaps(char mapNow[][], char newMap[][]) {

        for (int fil = 0; fil < nfil; fil++) {
            for (int col = 0; col < ncol; col++) {
                mapNow[fil][col] = '·';
                newMap[fil][col] = '·';
            }
        }
    }

    public static void print(char mapNow[][], int pob, int gen) {

        for (int fil = 1; fil < nfil - 1; fil++) {
            for (int col = 1; col < ncol - 1; col++) {
                if (mapNow[fil][col] == 'O') {
                    pob++;
                }
                System.out.print(mapNow[fil][col] + " ");
            }
            System.out.println();
        }
        System.out.println("Población: " + pob + "    |    " + "Generación: " + gen);
    }

    public static char doa(char mapNow[][], int fil, int col) throws InterruptedException {

        int alive = 0;
        if (mapNow[fil][col - 1] == 'O') {alive++;}
        if (mapNow[fil][col + 1] == 'O') {alive++;}
        if (mapNow[fil - 1][col] == 'O') {alive++;}
        if (mapNow[fil + 1][col] == 'O') {alive++;}
        if (mapNow[fil - 1][col - 1] == 'O') {alive++;}
        if (mapNow[fil + 1][col - 1] == 'O') {alive++;}
        if (mapNow[fil - 1][col + 1] == 'O') {alive++;}
        if (mapNow[fil + 1][col + 1] == 'O') {alive++;}
        if (mapNow[fil][col] == 'O' && (alive == vid1 || alive == vid2)) {
            return 'O';
        } else if (mapNow[fil][col] == '·' && (alive == res)) {
            return 'O';
        } else {
            return '·';
        }
    }

    public static void refresh(char mapNow[][], char newMap[][]) {

        for (int fil = 1; fil < nfil - 1; fil++) {
            for (int col = 1; col < ncol - 1; col++) {
                mapNow[fil][col] = newMap[fil][col];
                newMap[fil][col] = '·';
            }
        }
    }

    public static void manual(Scanner entrada, char mapNow[][], int pob, int gen) {

        System.out.println("¿Cuantas células quieres colocar?");
        int nCel = entrada.nextInt();
        entrada.nextLine();
        for (int cont = 0; cont < nCel; cont++) {
            boolean work = false;
            while (work == false) {
                try {
                    System.out.println("¿Donde quieres colocar tu célula? (Formato -> Fila / Columna)");
                    String pos = entrada.nextLine();
                    String posA[] = pos.split("/");
                    int posf = Integer.valueOf(posA[0]);
                    int posc = Integer.valueOf(posA[1]);
                    if ((posf > 0 && posf < nfil) && (posc > 0 && posc < ncol)) {
                        mapNow[posf][posc] = 'O';
                        print(mapNow, pob, gen);
                        work = true;
                    } else {
                        System.out.println("No se ha podido realizar la operación, vuelve a introducir los datos.");
                        work = true;
                        cont--;
                    }
                    work = true;
                } catch (Exception e) {
                    System.out.println("No se ha podido realizar la operación, vuelve a introducir los datos.");
                }
            }
        }
    }

    public static void auto(Scanner entrada, char mapNow[][], int pob, int gen) {

        System.out.println("¿Cuantos grupos quieres crear?");
        boolean work = false;
        while (work == false) {
            try {
                String ngru2p = entrada.nextLine();
                int ngrup = Integer.valueOf(ngru2p);
                for (int cont = 0; cont < ngrup; cont++) {
                    int fcel = (int) (Math.random() * nfil);
                    int ccel = (int) (Math.random() * ncol);
                    if ((fcel > 1 && fcel < nfil - 1) && (ccel > 1 && ccel < ncol - 1)) {
                        mapNow[fcel][ccel] = 'O';
                        mapNow[fcel - 1][ccel - 1] = 'O';
                        mapNow[fcel - 1][ccel] = 'O';
                        mapNow[fcel][ccel + 1] = 'O';
                        mapNow[fcel + 1][ccel] = 'O';
                        print(mapNow, pob, gen);
                    } else
                        cont--;
                }
                work = true;
            } catch (Exception e) {
                System.out.println("No se ha podido realizar la operación, vuelve a introducir los datos.");
            }
        }
    }
}