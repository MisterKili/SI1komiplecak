import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Tour {

    ArrayList<Thief> thieves;
    int tourID;
    int pop;

    public Tour(int id, int pop){
        tourID = id;
        this.pop = pop;
        thieves = new ArrayList<>(pop);
    }

    public void addThief(Thief t){
        thieves.add(t);
    }

    public void generateRandomThieves(Data data){
        for (int i = 0; i<pop; i++){
            thieves.add(new Thief(data));
        }
    }

    public void printBestThief(){
        sortThieves();
        System.out.println("Funkcja G: "+thieves.get(0).functionG());
        System.out.println("Trasa: "+thieves.get(0).getRoute());
        System.out.println("Profit: "+thieves.get(0).getTotalProfit());
        System.out.println();
    }

    public void printThieves(){
        for(Thief t: thieves){
            System.out.print(t.functionG()+" ");
        }
    }

    public String getRes(){
        String res = tourID+", "+getBestG()+", "+(int)getAverageG()+", "+getWorstG()+"\n";
        return res;
    }

    public void goThieves(){
        for (Thief t: thieves) {
            t.go();
        }
    }

    public void sortThieves(){
        thieves.sort(new ThiefComparator());
    }

    public int getBestG(){
        return thieves.get(0).functionG();
    }

    public int getWorstG(){
        return thieves.get(thieves.size()-1).functionG();
    }

    public double getAverageG(){
        int n = thieves.size();
        int summ = 0;
        for (Thief t: thieves) {
            summ += t.functionG();
        }
        double avg = summ/n;
        return avg;
    }

    public Thief crossingOverOX(Thief t1, Thief t2) {
        int numberOfCities = t1.getRouteSize();
        Random random = new Random();
        int begin = random.nextInt(numberOfCities - 2) + 1;
        int end = random.nextInt(numberOfCities - 2) + 1;
        if (begin == end) {
            return t1;
        }
        if (begin > end) {
            int temp = end;
            end = begin;
            begin = temp;
        }
        ArrayList<City> routeTemp = new ArrayList<>();              //miasta ktore z t1 przechodza do t2
        City[] crossed = new City[numberOfCities];                  //koncowa trasa
        ArrayList<City> t2FromEnd = new ArrayList<>(numberOfCities);//z trasy t2 zaczynajac od end

        //wypelnienie routeTemp i fragmentu crossed
        for (int i = begin; i < end; i++) {
            routeTemp.add(t1.route.get(i));
            crossed[i] = t1.route.get(i);
        }

        //z trasy t2 jesli nie ma miasta w routeTemp koncowka trasy
        for (int i = end; i < numberOfCities - 1; i++) {
            if (!routeTemp.contains(t2.route.get(i)))
                t2FromEnd.add(t2.route.get(i));
        }

        //z trasy t2 jesli nie ma miasta w routeTemp poczatek trasy
        for (int i = 0; i < end; i++) {
            if (!routeTemp.contains(t2.route.get(i)))
                t2FromEnd.add(t2.route.get(i));
        }

        //wypelnianie koncowki crossed
        int j = 0;
        for (int i = end; i < crossed.length - 1; i++) {
            crossed[i] = t2FromEnd.get(j);
            j++;
        }

        //wypelnienie poczatku crossed
        for (int i = 0; i < begin; i++) {
            crossed[i] = t2FromEnd.get(j);
            j++;
        }

        //dodanie na koniec pierwszego miasta
        crossed[crossed.length - 1] = crossed[0];

        //zamiana crossed na arrayliste
        ArrayList<City> childsRoute = new ArrayList<>(Arrays.asList(crossed));

        Thief child = new Thief(t1.getData(), childsRoute);

        return child;
    }

    public Tour newTour(double withoutChange, double Pm, double Px){
        Thief t1;
        Thief t2;
        Thief child;
        Random random = new Random();
        double fateMutation;
        double fateCrossing;

        Tour newTour = new Tour(this.tourID + 1, this.pop);

        int howManyWithoutChange = 0;

        if(withoutChange != 0)
            howManyWithoutChange = (int) (pop*withoutChange);

        for(int i = 0; i<howManyWithoutChange; i++){
            newTour.addThief(new Thief(thieves.get(i)));
        }

        for(int i = 1; i<pop; i++){
            t1 = this.thieves.get(i-1);
            t2 = this.thieves.get(i);
            fateMutation = random.nextDouble();
            fateCrossing = random.nextDouble();
            if(fateCrossing>Px) {
                child = crossingOverOX(t1, t2);     //elitaryzm
            }else {
                child = new Thief(t1.data);
            }
            if(fateMutation>Pm){
                child.swapMutate();
            }
            newTour.addThief(child);
        }

        return newTour;
    }

    public Tour newTourRoulette(double withoutChange, double Pm, double Px){
        Thief t1;
        Thief t2;
        Thief child;
        Random random = new Random();
        double fateMutation;
        double fateCrossing;
        int roul1;
        int roul2;

        Tour newTour = new Tour(this.tourID + 1, this.pop);

        ArrayList<Thief> roulette = new ArrayList<>();

        int howManyWithoutChange = 0;

        if(withoutChange != 0)
            howManyWithoutChange = (int) (pop*withoutChange);

        for(int i = 0; i<howManyWithoutChange; i++){
            newTour.addThief(new Thief(thieves.get(i)));
        }

        for(int i = 0; i<pop; i++){
            for(int j = 0; j<(pop-i)/10; j++){
                roulette.add(thieves.get(i));
            }
        }

        for(int i = 1; i<pop-howManyWithoutChange; i++){
            roul1 = random.nextInt(roulette.size());
            roul2 = random.nextInt(roulette.size());
            t1 = new Thief(roulette.get(roul1));
            t2 = new Thief(roulette.get(roul2));
            fateMutation = random.nextDouble();
            fateCrossing = random.nextDouble();
            if(fateCrossing>Px) {
                child = crossingOverOX(t1, t2);     //elitaryzm
            }else {
                child = new Thief(t1.data);
            }
            if(fateMutation>Pm){
                child.swapMutate();
            }
            newTour.addThief(child);
        }

        return newTour;
    }

}
