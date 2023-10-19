package fr.epu.tracking;


import java.util.Objects;

/**
 * TP java
 *
 * @author frédéric rallo - frederic.rallo@univ-cotedazur.fr
 * @author Mireille Blay-Fornarino
 */
public class Position {
    private double x;
    private double y;

    private static final double EPSILON = 0.001;

    // -------------------------- accesseurs ------------

    //We prohibit changing a position externally
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    //-------------- constructeurs ----------------

    /**
     * constructeur normal
     * @param ix l'abscisse du point
     * @param iy l'ordonnée du point
     */
    public Position(double ix, double iy) {
        x=ix;
        y=iy;
    }

    /**
     * default constructor
     */
    public Position(){
        this(0,0);
    }

    // ------------------------ public methods
    /**
     * calculate the distance to another position
     * @param p another position
     * @return a norm distance of the vector (this,p)
     */
    public double distance(Position p) {
        return Math.sqrt((p.y - y) * (p.y - y) + (p.x - x) * (p.x - x));
    }


    public boolean isEquivalent(Position p){
        return (Math.abs(x-p.x)<EPSILON  && Math.abs(y-p.y)<EPSILON );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Double.compare(x, position.x) == 0 && Double.compare(y, position.y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

// ---- Non demandées dans le TD
    /**
     * calcule le point projeté de ce point sur l'axe des abscisses
     * @return un nouveau Point
     */
    public Position projX() {
        return new Position(x,0);
    }

    /**
     * calcule le point projeté de ce point sur l'axe des ordonnées
     * @return un nouveau Point
     */
    public Position projY() {
        return new Position(0,y);
    }
    //------------------------------------


    @Override
    public String toString() {
        return "("+x+" ; "+y+")";
    }


}
