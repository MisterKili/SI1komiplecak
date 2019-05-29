import java.util.Comparator;

public class ItemComparator implements Comparator<Item> {

    @Override
    public int compare(Item i1, Item i2) {
        if(i1.getValue() < i2.getValue())
            return 1;
        else
            return -1;
    }
}
