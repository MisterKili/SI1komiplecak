import java.util.ArrayList;
import java.util.Collections;

public class Data {

    int dimensions;
    int numberOfItems;
    int capacityOfKnapsack;
    double minSpeed;
    double maxSpeed;
    double rentingRatio = 1;
    ArrayList<City> cities = new ArrayList<>();
    int[][] distances;

    public void setDimensions(int dimensions) {
        this.dimensions = dimensions;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public void setCapacityOfKnapsack(int capacityOfKnapsack) {
        this.capacityOfKnapsack = capacityOfKnapsack;
    }

    public void setMinSpeed(double minSpeed) {
        this.minSpeed = minSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setRentingRatio(double rentingRatio) {
        this.rentingRatio = rentingRatio;
    }

    public void addItemToCity(Item item, int cityIndex){
        cities.get(cityIndex-1).addItem(item);
    }

    public int getDimensions() {
        return dimensions;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public int getCapacityOfKnapsack() {
        return capacityOfKnapsack;
    }

    public double getMinSpeed() {
        return minSpeed;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public double getRentingRatio() {
        return rentingRatio;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public int[][] getDistancesTable(){
        return distances;
    }

    public void addCity(City city){
        cities.add(city);
    }

    public void printData(){
        System.out.println("Dimensions: "+dimensions);
        System.out.println("Number of items: "+numberOfItems);
        System.out.println("Capacity: "+capacityOfKnapsack);
        System.out.println("Min speed: "+minSpeed);
        System.out.println("Max speed: "+maxSpeed);
        printCities();
    }

    public void printCities(){
        for (City c: cities) {
            System.out.println(c.toString());
            c.printItems();
        }
    }

    public void calculateDistances(){
        int numberOfCities = cities.size();
        distances = new int[numberOfCities][numberOfCities];
        for (int i = 0; i<numberOfCities; i++){
            for (int j = 0; j<numberOfCities; j++){
                distances[i][j] = (int)Math.ceil(Math.sqrt(Math.pow(cities.get(i).getX() - cities.get(j).getX(), 2) +
                        Math.pow(cities.get(i).getY() - cities.get(j).getY(), 2)));
            }
        }
    }

    public int getDistance(int city1, int city2){
        return distances[city1-1][city2-1];
    }

    public void printDistances(){
        for(int i = 0; i<dimensions; i++){
            for(int j = 0; j<dimensions; j++){
                System.out.print((int)distances[j][i]+" ");
            }
            System.out.println();
        }
    }

    public void sortItemsInCities(){
        for(City c: cities){
            Collections.sort(c.items, new ItemComparator());
        }
    }

    public City getClosestNewCity(City c, ArrayList<City> reachedCities){
        ArrayList<City> closestCities =(ArrayList<City>) cities.clone();

        int cityID = c.getIndex();
        int[] distancesFromCity = distances[cityID - 1].clone();
        City closest = c;
        int n = cities.size();

        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (distancesFromCity[j] > distancesFromCity[j+1])
                {
                    // swap arr[j+1] and arr[i]
                    int temp = distancesFromCity[j];
                    distancesFromCity[j] = distancesFromCity[j+1];
                    distancesFromCity[j+1] = temp;

                    Collections.swap(closestCities, j+1, j);
                }

        for (City ci: closestCities){
//            System.out.println(ci.getIndex()+" ");
            if(!reachedCities.contains(ci))
                return ci;
        }

        return closest;
    }
}
