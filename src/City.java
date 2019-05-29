import java.util.ArrayList;

public class City {

    int index;
    double x;
    double y;
    ArrayList<Item> items;

    public City(int index, double x, double y){
        this.index = index;
        this.x = x;
        this.y = y;
        items = new ArrayList<>();
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public int getIndex(){
        return index;
    }

    public ArrayList<Item> getItems(){
        return items;
    }

    public void addItem(Item item){
        items.add(item);
    }

    public String toString(){
        return index + " ( " + x + " " + y+" )";
    }

    public void printItems(){
        for (Item i: items) {
            System.out.println(i);
        }
    }

}
