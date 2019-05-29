public class Item {

    int index;
    int profit;
    int weight;
    double value;

    public Item(int index, int profit, int weight){
        this.index = index;
        this.profit = profit;
        this.weight = weight;
        this.value = (double)profit/weight;
    }

    @Override
    public String toString() {
        return index+", profit:  "+profit+", weight: "+weight+" value: "+value;
    }

    public int getIndex() {
        return index;
    }

    public int getProfit() {
        return profit;
    }

    public int getWeight() {
        return weight;
    }

    public double getValue() {
        return value;
    }
}
