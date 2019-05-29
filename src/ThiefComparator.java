import java.util.Comparator;

public class ThiefComparator implements Comparator<Thief> {
    @Override
    public int compare(Thief o1, Thief o2) {
        if(o1.functionG() < o2.functionG())
            return 1;
        else
            return -1;
    }
}
