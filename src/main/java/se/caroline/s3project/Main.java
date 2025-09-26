package se.caroline.s3project;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        S3Service s3Service = new S3Service(); // korrekt instans

        while (true) {
            System.out.println("\n=== S3 Meny ===");
            System.out.println("1. Lista filer i bucket");
            System.out.println("2. Ladda upp fil");
            System.out.println("3. Ladda ner fil");
            System.out.println("0. Avsluta");
            System.out.print("Välj ett alternativ: ");

            String val = scanner.nextLine();

            try {
                switch (val) {
                    case "1":
                        s3Service.listaFiler();
                        break;
                    case "2":
                        System.out.print("Ange lokal filsökväg att ladda upp: ");
                        String filSokvag = scanner.nextLine();
                        s3Service.laddaUppFil(filSokvag);
                        break;
                    case "3":
                        System.out.print("Ange filnamnet som ska laddas ner (nyckel i bucket): ");
                        String nyckel = scanner.nextLine();
                        System.out.print("Ange målsökväg: ");
                        String malSokvag = scanner.nextLine();
                        s3Service.laddaNerFil(nyckel, malSokvag);
                        break;
                    case "0":
                        System.out.println("Programmet avslutas...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Ogiltigt val. Försök igen.");
                }
            } catch (Exception e) {
                System.err.println("Fel: " + e.getMessage());
            }
        }
    }
}


