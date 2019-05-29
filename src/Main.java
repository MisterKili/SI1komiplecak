import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[]args) {

        final int POP_SIZE = 200;
        final int GEN = 300;
        double Px = 0.25;
        double Pm = 0.5;
        final double WITHOUT_CHANGE = 0.1;
        String FILE_NAME = "hard_0_ruletka4.csv";
        String SOURCE_NAME = "hard_2.ttp";


        for(int l = 0; l<3; l++) {

            SOURCE_NAME = "hard_"+l+".ttp";
            FILE_NAME = "skrajne_hard_"+l+"_Px025_Pm05.csv";


            Loader loader = new Loader();
            loader.read(SOURCE_NAME);

            Data data = loader.getData();

//                // losowy
//                Thief tRandom = new Thief(data);
//                tRandom.go();
//                System.out.println("Random:");
//                tRandom.printRoute();
//                System.out.println(tRandom.getTotalProfit());
//                System.out.println("G: " + tRandom.functionG());
//
//                System.out.println();
//
//                //greedy
//                System.out.println("Greedy:");
//                Thief tGreedy = new Thief(data.cities.get(0), data);
//                tGreedy.go();
//                tGreedy.printRoute();
//                System.out.println(tGreedy.getTotalProfit());
//                System.out.println("G: " + tGreedy.functionG());

            File file = new File(FILE_NAME);
            FileWriter fr;
            try {
                fr = new FileWriter(file, true);

                Tour tour = new Tour(1, POP_SIZE);
                tour.generateRandomThieves(data);
                tour.goThieves();
                tour.sortThieves();
                fr.write(tour.getRes());

                for (int i = 1; i <= GEN - 1; i++) {
                    tour = tour.newTour(WITHOUT_CHANGE, Pm, Px);
                    tour.goThieves();
                    tour.sortThieves();
                    fr.write(tour.getRes());
                    tour.printThieves();
                    System.out.println();
                }
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            }
        }
}
