package eu.union.dev.utils;

import org.bukkit.Material;
import org.bukkit.util.Vector;

import java.util.Random;

public class RandomUtils {

    public static final Random random = new Random(System.nanoTime());

    private RandomUtils() {
        // No instance allowed
    }

    /**
     * Get a random X,Y,Z vector
     * @return
     */
    public static Vector getRandomVector() {
        double x, y, z;
        x = random.nextDouble() * 2 - 1;
        y = random.nextDouble() * 2 - 1;
        z = random.nextDouble() * 2 - 1;

        return new Vector(x, y, z).normalize();
    }

    /**
     * Get a random X,Z circular vector
     * @return
     */
    public static Vector getRandomCircleVector() {
        double rnd, x, z;
        rnd = random.nextDouble() * 2 * Math.PI;
        x = Math.cos(rnd);
        z = Math.sin(rnd);

        return new Vector(x, 0, z);
    }

    /**
     * A easy way to return a chance in 100%
     * @param chance
     * @return
     */
    public static boolean chance(int chance){
        if(random.nextInt(100) <= chance){
            return true;
        }else{
            return false;
        }
    }

    public static Material getRandomMaterial(Material[] materials) {
        return materials[random.nextInt(materials.length)];
    }

    public static double getRandomAngle() {
        return random.nextDouble() * 2 * Math.PI;
    }
}
