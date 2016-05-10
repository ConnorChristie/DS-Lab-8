package christieck;

import java.util.*;

/**
 * A lookup table implementation that holds key/value pairs.
 * Each entry is sorted based on the key and access to entries
 * in the lookup table are O(log n) (using a binary search).
 *
 * @param <K> The key type
 * @param <V> The value type
 * @author Connor Christie
 */
public class LookupTable<K extends Comparable<? super K>, V> implements Map<K, V>
{
    /**
     * Inner Entry class that represents one key/value pair to
     * be stored in the lookup table.
     * <p>
     * If this entry class was used to represent a dictionary, you
     * can think of the key as the word and the value as the
     * definition for the word.  Words are sorted in a dictionary
     * based on the key, and the definition just tags along.
     *
     * @author Connor Christie
     */
    private class Entry implements Comparable<Entry>
    {
        /**
         * The key with which the specified value is to be associated
         */
        private K key;

        /**
         * The value that tags along with the key.
         */
        private V value;

        /**
         * Constructor
         *
         * @param key   Key with which the specified value is to be associated
         * @param value The value that should tag along with the key
         * @throws NullPointerException if the key is null
         */
        public Entry(K key, V value)
        {
            if (key == null)
            {
                throw new NullPointerException();
            }

            this.key = key;
            this.value = value;
        }

        /**
         * Override of the compareTo method which uses the key to
         * determine the relative order of the two Entry objects
         * based on the key in each object.
         * The value in each Entry object is ignored by this method.
         */
        @Override
        public int compareTo(Entry target)
        {
            return key.compareTo(target.getKey());
        }

        /**
         * Override of the equals method which uses the key to
         * determine the equality of the two Entry objects.
         * The value in each Entry object is ignored by this method.
         */
        @Override
        public boolean equals(Object target)
        {
            return (target instanceof LookupTable<?, ?>.Entry)
                    && key.equals(((LookupTable<?, ?>.Entry) target).getKey());
        }

        /**
         * Simple getter
         *
         * @return The key with which the specified value is to be associated
         */
        public K getKey()
        {
            return key;
        }

        /**
         * Simple getter
         *
         * @return The value associated with the key
         */
        public V getValue()
        {
            return value;
        }

        /**
         * Simple setter
         *
         * @param value The replacement value.
         */
        public void setValue(V value)
        {
            this.value = value;
        }
    }

    /**
     * The table that stores all of the entries in the lookup table.
     */
    private List<Entry> table;

    /**
     * Constructor
     */
    public LookupTable()
    {
        table = new ArrayList<>();
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old value
     * is replaced by the specified value.
     * <p>
     * Implementation note: You must use the Collections.binarySearch()
     * method to determine where to find or place the key/value pair.
     *
     * @param key   Key with which the specified value is to be associated
     * @param value Value to be associated with the specified key
     * @return the previous value associated with key, or null if there
     * was no mapping for key.
     * @throws NullPointerException if the key is null
     */
    public V put(K key, V value)
    {
        if (key == null)
        {
            throw new NullPointerException();
        }

        Entry entry = new Entry(key, value);

        int index = Collections.binarySearch(table, entry);

        if (containsKey(key))
        {
            return table.set(index, entry).getValue();
        }

        table.add((index + 1) * -1, entry);

        return null;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if
     * this lookup table contains no mapping for the key.
     * <p>
     * Note: a return value of null does not necessarily indicate that the
     * lookup table contains no mapping for the key; it's also possible that
     * the lookup table explicitly maps the key to null. The containsKey
     * operation may be used to distinguish these two cases.
     * <p>
     * Implementation note: You must use the Collections.binarySearch()
     * method to determine where to find the key/value pair.
     *
     * @param key Key with which the specified value is to be associated
     * @return the value to which the specified key is mapped, or null if
     * this lookup table contains no mapping for the key.
     * @throws NullPointerException if the key is null
     */
    public V get(Object key)
    {
        if (key == null)
        {
            throw new NullPointerException();
        }

        if (containsKey(key))
        {
            int index = Collections.binarySearch(table, new Entry((K) key, null));

            return table.get(index).getValue();
        }

        return null;
    }

    /**
     * Returns the number of key-value mappings in this lookup table.
     *
     * @return the number of key-value mappings in this lookup table.
     */
    public int size()
    {
        return table.size();
    }

    /**
     * Removes all of the mappings from this lookup table. The lookup
     * table will be empty after this call returns.
     */
    public void clear()
    {
        table.clear();
    }

    /**
     * Returns true if this lookup table contains a mapping for the
     * specified key.
     * <p>
     * Implementation note: You must use the Collections.binarySearch()
     * method to determine if the key is contained in the lookup table.
     *
     * @param key Key whose presence in this map is to be tested.
     * @return true if this map contains a mapping for the specified key
     * @throws NullPointerException if the key is null
     */
    public boolean containsKey(Object key)
    {
        if (key == null)
        {
            throw new NullPointerException();
        }

        int index = Collections.binarySearch(table, new Entry((K) key, null));

        return index >= 0;
    }

    /**
     * Returns true if this lookup table contains no key-value mappings.
     *
     * @return true if this lookup table contains no key-value mappings.
     */
    public boolean isEmpty()
    {
        return table.isEmpty();
    }

    /**
     * Removes the mapping for a key from this map if it is present.
     * <p>
     * Returns the value to which this map previously associated the key,
     * or null if the map contained no mapping for the key.
     * <p>
     * Note: a return value of null does not necessarily indicate that the
     * lookup table contained no mapping for the key; it's also possible
     * that the lookup table explicitly mapped the key to null.
     * <p>
     * Implementation note: You must use the Collections.binarySearch()
     * method to determine where to find the key/value pair.
     *
     * @param key Key whose mapping is to be removed from the lookup table.
     * @return the previous value associated with key, or null if there was
     * no mapping for key.
     * @throws NullPointerException if the key is null
     */
    public V remove(Object key)
    {
        if (key == null)
        {
            throw new NullPointerException();
        }

        if (containsKey(key))
        {
            int index = Collections.binarySearch(table, new Entry((K) key, null));

            if (index >= 0)
            {
                return table.remove(index).getValue();
            }
        }

        return null;
    }

    @Override
    public Set<K> keySet()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<V> values()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsValue(Object value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m)
    {
        throw new UnsupportedOperationException();
    }
}