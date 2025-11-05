package HashTable;

/**
 * Hashing with wrong hashCode() or equals(). Suppose that you implement a data type
 * OlympicAthleteO for use in java.util.hasMap
 * 1. Describe what happens when you override hashCOde() but not equals()
 * 2. Describe what happens when you override equals but not hashCode()
 * 3. Describe what happens when you override hashCode() but implement
 * public boolean equals(OlympicAthleteO that) instead of
 * public boolean equals(Object that) instead of
 */
public class InterviewQuestion2 {
    /**
     * In Java, for objects used as keys in hash-based collections (HashMap, HashSet, Hashtable):
     * If two objects are equal according to equals(), they must have the same hashCode().
     * If this contract is broken, the collection behaves incorrectly — keys might not be found, duplicates may appear, etc.
     */

    /**
     * 1. You have customized hash codes, so two different instances with same data may land in the same bucket.
     * But since equals() is still from Object, it checks memory address equality, not content.
     * So, even if two athletes have identical name/country, the HashMap treats them as different keys.
     *
     * RESULT: map.containsKey(new OlympicAthleteO("Usain", 1)) will return false even if an identical key was inserted.
     * Logical duplicates allowed in HashSet.
     */
    class OlympicAthleteO {
        String name;
        int countryCode;

        public OlympicAthleteO(String name, int countryCode) {
            this.name = name;
            this.countryCode = countryCode;
        }

        @Override
        public int hashCode() {
            return name.hashCode() + countryCode;
        }
        // equals() is NOT overridden — uses Object’s default (identity check)
    }

    /**
     * 2. equals() says two athletes with same data are equal
     * But since hashCode() is not overridden, their hash codes differ (each object’s memory address hash).
     * So they go into different buckets in HashMap.
     *
     * Result:
     * map.containsKey(new OlympicAthleteO("Usain", 1)) → returns false
     * because lookup happens in a different bucket.
     * The contract is violated — map/search behaves inconsistently.
     */
    private class OlympicAthleteO1{
        String name;
        int countryCode;

        public OlympicAthleteO1(String name, int countryCode) {
            this.name = name;
            this.countryCode = countryCode;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof OlympicAthleteO)) return false;
            OlympicAthleteO that = (OlympicAthleteO) obj;
            return this.countryCode == that.countryCode && this.name.equals(that.name);
        }
        // hashCode() NOT overridden — uses Object’s identity-based hashCode
    }

    /**
     * 3. The equals() method above does NOT override Object.equals(Object) — it just overloads it.
     * Java’s collections (HashMap, HashSet) call equals(Object) internally.
     * Since Object.equals() (identity check) is still used, it behaves as if equals() was never overridden.
     *
     * Result:
     * Even though you wrote an equals() that looks correct, it’s ignored.
     * So behavior is same as case 1: duplicates exist, lookups fail.
     */
    class OlympicAthlete3 {
        String name;
        int countryCode;

        public OlympicAthlete3(String name, int countryCode) {
            this.name = name;
            this.countryCode = countryCode;
        }

        @Override
        public int hashCode() {
            return name.hashCode() + countryCode;
        }

        // WRONG SIGNATURE (overloads, doesn’t override)
        public boolean equals(OlympicAthleteO that) {
            return this.countryCode == that.countryCode && this.name.equals(that.name);
        }
    }

    class OlympicAthleteCorrectImplementation {
        String name;
        int countryCode;

        public OlympicAthleteCorrectImplementation(String name, int countryCode) {
            this.name = name;
            this.countryCode = countryCode;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof OlympicAthleteO)) return false;
            OlympicAthleteO that = (OlympicAthleteO) obj;
            return this.countryCode == that.countryCode && this.name.equals(that.name);
        }

        @Override
        public int hashCode() {
            return 31 * name.hashCode() + countryCode;
        }
    }
}
