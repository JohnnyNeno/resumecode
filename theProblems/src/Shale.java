import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Shale {

    public static void main(String[] args) {
        new Shale();
    }

    public Shale() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Unesite duzinu niza");
        int a = sc.nextInt();
        int[] array1 = new int[a * 2];
        Random rand = new Random();
        int k = array1.length;
        for (int i = 0; i < k; i++) {
            array1[i] = rand.nextInt(100);
            System.out.println(array1[i]);
            Arrays.sort(array1);
        }
        
        

        int prvihN[] = Arrays.copyOfRange((array1), 0, k/2);
        int drugihN[] = Arrays.copyOfRange((array1), k/2, k);
        System.out.println("Prvi niz: " + Arrays.toString(prvihN));
        System.out.println("Drugi niz: " + Arrays.toString(drugihN));

    }
}