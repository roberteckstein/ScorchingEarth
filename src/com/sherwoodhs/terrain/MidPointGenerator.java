package com.sherwoodhs.terrain;
import java.util.Random;


public class MidPointGenerator {

    public int[] map;
    public int width;
    public int averageHeight;
    private double multiplier;  //  .8 to 1.2 generates different levels of slopes (flat to mountainous)

    // use random.nextInt() in the code for random numbers
    Random random = new Random();

    public MidPointGenerator(int width, int startingHeight, double multiplier) {

        this.width = width;
        this.averageHeight = startingHeight;
        this.multiplier = multiplier;

        map = new int[width];

        map[0] = averageHeight;
        map[width-1] = averageHeight;

        // Start the recursive midpoint algorithm
        midpoint(0, width-1);

    }

    // This is the actual midpoint displacement algorithm. This is a common algorithm in graphics,
    // electronics, etc. For a good explanation (albeit in Python), see the following website:
    // https://bitesofcode.wordpress.com/2016/12/23/landscape-generation-using-midpoint-displacement/

    public boolean midpoint(int x1, int x2)
    {
        //  We've subdivided down to just one pixel, so we can exit because
        //  it doesn't need to do anything
        if(x2-x1<2) return false;

        // Find distances between the two points to use when generating a random number.
        int distance=x2-x1;
        int halfDistance=distance/2;

        // Find the middle point
        int midpoint=(x1+x2)/2;

        //  Find the values at the two endpoints
        int endpointValue1=map[x1];
        int endpointValue2=map[x2];

        // Here's the magic formula. Compute a new value for only the middle point, taking the two
        // values at the endpoints, adding a random number, and subtracting half the distance.
        map[midpoint] = ((endpointValue1+endpointValue2+(int)(random.nextInt(distance)*multiplier)-halfDistance) / 2);

        // Now subdivide into two halves, and recursively call it again for each side
        midpoint(midpoint,x2);
        midpoint(x1, midpoint);

        return true;
    }

}