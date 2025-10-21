package PerfectBalanceRedBlackTree.LineSegmentIntersectin;

public class Event implements Comparable<Event> {
    public enum Type {
        LEFT, RIGHT
    }

    private final int x;
    private final int y;
    private final Line segment;
    private final Type type;
    public Event(int x, int y, Line segment, Type type) {
        this.x = x;
        this.y = y;
        this.segment = segment;
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Line getSegment() {
        return segment;
    }

    public Type getType() {
        return type;
    }

    @Override
    public int compareTo(Event that) {
        // Sort by x first, then y
        if (this.x != that.x) return Integer.compare(this.x, that.x);
        if (this.y != that.y) return Integer.compare(this.y, that.y);

        // Break ties: LEFT events before RIGHT events (important for sweep line)
        if (this.type != that.type) {
            return this.type == Type.LEFT ? -1 : 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return String.format("Event(%d, %d, %s, %s)", x, y, segment, type);
    }

}
