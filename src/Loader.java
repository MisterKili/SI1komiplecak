import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Loader {

    Data data;

    public void read(String fileName){
        String fullPath = "C:\\Users\\wkili\\IdeaProjects\\SI1komiplecak\\student"+fileName;
        File file = new File(fullPath);
        data = new Data();
        try {
            String currLine;
            Scanner scanner = new Scanner(file);
            String[] splitted;
            while (scanner.hasNextLine()){
                currLine = scanner.nextLine();
                splitted = currLine.split("\\s+");
                switch (splitted[0]){
                    case "DIMENSION:":
                        data.setDimensions(Integer.parseInt(splitted[1]));
                        break;
                    case "NUMBER":
                        data.setNumberOfItems(Integer.parseInt(splitted[3]));
                        break;
                    case "CAPACITY":
                        data.setCapacityOfKnapsack(Integer.parseInt(splitted[3]));
                        break;
                    case "MIN":
                        data.setMinSpeed(Double.parseDouble(splitted[2]));
                        break;
                    case "MAX":
                        data.setMaxSpeed(Double.parseDouble(splitted[2]));
                        break;
                    case "RENTING":
                        data.setRentingRatio(Double.parseDouble(splitted[2]));
                        break;
                    case "NODE_COORD_SECTION":
                        readCities(scanner);
                        break;
                    case "ITEMS":
                        readItems(scanner);
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void readCities(Scanner scanner){
        String currLine;
        String[] splitted;
        int cityIndex;
        double cityX;
        double cityY;
        City city;

        for(int i = 0; i<data.getDimensions(); i++){
            currLine = scanner.nextLine();
            splitted = currLine.split("\\s+");
            cityIndex = Integer.parseInt(splitted[0]);
            cityX = Double.parseDouble(splitted[1]);
            cityY = Double.parseDouble(splitted[2]);

            city = new City(cityIndex, cityX, cityY);
            data.addCity(city);
        }
        data.calculateDistances();
    }

    public void readItems(Scanner scanner){
        String currLine;
        String[] splitted;
        int itemIndex;
        int profit;
        int weight;
        int cityIndex;
        Item item;

        for(int i = 0; i<data.getNumberOfItems(); i++) {
            currLine = scanner.nextLine();
            splitted = currLine.split("\\s+");
            itemIndex = Integer.parseInt(splitted[0]);
            profit = Integer.parseInt(splitted[1]);
            weight = Integer.parseInt(splitted[2]);
            cityIndex = Integer.parseInt(splitted[3]);

            item = new Item(itemIndex, profit, weight);
            data.addItemToCity(item, cityIndex);
        }

        data.sortItemsInCities();
    }

    public Data getData(){
        return data;
    }
}
