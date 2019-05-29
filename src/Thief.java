import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Thief {

    ArrayList<Item> knapsack = new ArrayList<>();
    ArrayList<City> route;
    ArrayList<Segment> segments = new ArrayList<>();
    int capacityLeft = 0;
    int knapsackWeight = 0;
    Data data;

    public Thief(int capacityLeft){
        this.capacityLeft = capacityLeft;
    }

    public Thief(City firstCity, Data data){
        this.data = data;
        this.route = greedy(firstCity);
        capacityLeft = data.capacityOfKnapsack;
    }

    public Thief(Data data, ArrayList<City> route){
        this.data = data;
        this.route = route;
        capacityLeft = data.capacityOfKnapsack;
    }

    public Thief(Thief toCopy){
        this.data = toCopy.data;
        this.capacityLeft = data.capacityOfKnapsack;
        this.route = (ArrayList<City>) toCopy.route.clone();
    }

    public Thief(Data data){
        route = (ArrayList<City>) data.cities.clone();
        capacityLeft = data.capacityOfKnapsack;
        Collections.shuffle(route);
        this.data = data;
        route.add(route.get(0));
    }


    public ArrayList<City> greedy(City firstCity){
        ArrayList<City> newRoute = new ArrayList<>();
        newRoute.add(firstCity);
        for(int i = 0; i<data.cities.size()-1; i++){
            newRoute.add(data.getClosestNewCity(newRoute.get(newRoute.size()-1), newRoute));
        }
        newRoute.add(firstCity);
        return newRoute;
    }

    public void selectItem(ArrayList<Item> list){
        boolean isSelected = false;
        int i = 0;
        int n = list.size();
        while (i<n && isSelected == false){
            if(list.get(i).getWeight() <= capacityLeft){
                knapsack.add(list.get(i));
                capacityLeft -= list.get(i).getWeight();
                knapsackWeight += list.get(i).getWeight();
                isSelected = true;
            }
            i++;
        }
    }

    public int calculateRouteDistance(){
        int distanceSum = 0;
        int distanceAB;

        for(int i = 1; i<route.size(); i++){
            distanceAB = data.getDistance(route.get(i-1).getIndex()-1, route.get(i).getIndex()-1);
            distanceSum += distanceAB;
        }
        return distanceSum;
    }

    public String getRoute(){
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<route.size(); i++){
            sb.append(route.get(i).getIndex()+" ");
        }
        return sb.toString();
    }

    public int getTotalProfit(){
        int totalProfit = 0;
        for (int i = 0; i<knapsack.size(); i++){
            totalProfit += knapsack.get(i).getProfit();
        }
        return totalProfit;
    }

    public int getTotalTime(){
        double totalTime = 0;
        for (Segment s: segments) {
            totalTime += s.getTime();
        }
        return (int) Math.ceil(totalTime);
    }

    public Data getData(){
        return data;
    }

    public int getKnapsackWeight(){
        return knapsackWeight;
    }

    public void go(){
        Segment currentSegment;
        City nextCity;
        City previousCity;
        for(int i = 1; i<route.size(); i++){
            nextCity = route.get(i);
            previousCity = route.get(i-1);
            currentSegment = new Segment(previousCity, nextCity, knapsackWeight, data);
            segments.add(currentSegment);
            selectItem(nextCity.getItems());
//            System.out.println(previousCity.getIndex()+" -> "+nextCity.getIndex()+"     sack weight: "+currentSegment.knapsackWeight+
//                    "       speed: "+currentSegment.speed);
        }
    }

    public void printItemsInfo(){
        System.out.println("Total profit: "+ getTotalProfit());
        System.out.println("Total weight: "+ getKnapsackWeight());
        printItems();
    }

    public void printItems(){
        for (int i =0 ; i<knapsack.size(); i++){
            System.out.println(knapsack.get(i));
        }
    }

    public void printRoute(){
        System.out.println(getRoute());
    }

    public int getRouteSize(){
        return route.size();
    }

    public int functionG(){
        return getTotalProfit() - getTotalTime();
    }

    public void mutate(){
        //todo
    }

    public void swapMutate(){
        Random random = new Random();
        int cityIndex1 = random.nextInt(route.size() - 2) + 1;  //+1 zeby nie zamieniac pierwszego miasta
        int cityIndex2 = random.nextInt(route.size() - 2) + 1;  //-2 zeby nie zmieniac ostatniego miasta
        Collections.swap(route, cityIndex1, cityIndex2);
    }


    public void inverse(){
        //maybe later
    }
}
