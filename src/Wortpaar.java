public class Wortpaar {
    private String upper;
    private String lower;

    public Wortpaar(String upper, String lower) {
        this.upper = upper;
        this.lower = lower;
    }

    public String getUpper() {
        return upper;
    }

    public String getLower() {
        return lower;
    }

    public static Wortpaar parseWortpaar(String parseable) {
        String[] parts = parseable.substring(1, parseable.length() - 1).split(",");
        if (parts.length != 2) {
            return null;
        }
        return new Wortpaar(parts[0], parts[1]);
    }

    @Override
    public String toString() {
        return "(" + upper + "," + lower + ")";
    }
}
