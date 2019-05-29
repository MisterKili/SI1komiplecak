public class Segment {

    int distance;
    double speed;
    double time;
    City cityA;
    City cityB;
    int knapsackWeight;
    Data data;

    public Segment(City a, City b, int knapsackWeight, Data d){
        cityA = a;
        cityB = b;
        data = d;
        this.knapsackWeight = knapsackWeight;
        distance = data.getDistance(a.getIndex(), b.getIndex());
        speed = calculateSpeed();
        time = calculateTime();
    }

    public double calculateSpeed(){
        return data.getMaxSpeed() - knapsackWeight*(data.getMaxSpeed() - data.getMinSpeed())/data.getCapacityOfKnapsack();
    }

    public double calculateTime(){
        return (double)distance/speed;
    }

    public double getTime() {
        return time;
    }
}
