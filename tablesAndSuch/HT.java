package tablesAndSuch;
import java.io.*;

public class HT implements java.io.Serializable {
    public static final class Node {
	Object key;
	Node next;
	double count;
	// Object value;
	Node(Object k, double c, Node n) { key = k; next = n; count = c; }
    }

    Node[] table = new Node[8]; // always a power of 2
    int size = 0;
    public boolean contains(Object key) {
	int h = key.hashCode();
	int i = h & (table.length - 1);
	for (Node e = table[i]; e != null; e = e.next) {
	    if (key.equals(e.key))
		return true;
	}
	return false;
    }

	public double getOrDefault(Object key, int value){
		Node v;
		return(((v = get(key)) != null) || contains(key))
			? v.count
			: value;
	}


	public Node get(Object key) {
	int h = key.hashCode();
	int i = h & (table.length - 1);
	for (Node e = table[i]; e != null; e = e.next) {
	    if (key.equals(e.key))
		return e;
	}
	return null;
    }

	public Double getValue(Object key) {
	int h = key.hashCode();
	int i = h & (table.length - 1);
	for (Node e = table[i]; e != null; e = e.next) {
	    if (key.equals(e.key))
		return e.count;
	}
	return null;
    }


    public void add(Object key, double count) {
	int h = key.hashCode();
	int i = h & (table.length - 1);
	for (Node e = table[i]; e != null; e = e.next) {
	    if (key.equals(e.key))
		return;
	}
	table[i] = new Node(key, count, table[i]);
	++size;
	if ((float)size/table.length >= 0.75f)
	    resize();
    }

    void resize() {
	Node[] oldTable = table;
	int oldCapacity = oldTable.length;
	int newCapacity = oldCapacity << 1;
	Node[] newTable = new Node[newCapacity];
	for (int i = 0; i < oldCapacity; ++i) {
	    for (Node e = oldTable[i]; e != null; e = e.next) {
		int h = e.key.hashCode();
		int j = h & (newTable.length - 1);
		newTable[j] = new Node(e.key, e.count, newTable[j]);
	    }
	}
	table = newTable;
    }

    void resizeV2() { // avoids unnecessary creation
	Node[] oldTable = table;
	int oldCapacity = oldTable.length;
	int newCapacity = oldCapacity << 1;
	Node[] newTable = new Node[newCapacity];
	for (int i = 0; i < oldCapacity; ++i) {
            Node e = oldTable[i];
            while (e != null) {
                Node next = e.next;
		int h = e.key.hashCode();
		int j = h & (newTable.length - 1);
                e.next = newTable[j];
		newTable[j] = e;
                e = next;
	    }
	}
	table = newTable;
    }

    public void remove(Object key) {
	int h = key.hashCode();
	int i = h & (table.length - 1);
	Node e = table[i], p = null;
	while (e != null) {
	    if (key.equals(e.key)) {
		if (p == null)
		    table[i] = e.next;
		else
		    p.next = e.next;
		break;
	    }
	    p = e;
	    e = e.next;
	}
    }

    public void printAll() {
        for (int i = 0; i < table.length; ++i)
            for (Node e = table[i]; e != null; e = e.next)
                System.out.println(e.key + " : " + e.count);
    }
	
    private void writeObject(ObjectOutputStream s) throws Exception {
	s.defaultWriteObject();
	s.writeInt(size);
	for (int i = 0; i < table.length; ++i) {
	    for (Node e = table[i]; e != null; e = e.next) {
		s.writeObject(e.key);
	    }
	}
    }

    private void readObject(ObjectInputStream s) throws Exception {
	s.defaultReadObject();
	int n = s.readInt();
	for (int i = 0; i < n; ++i)
	    add(s.readObject(), n);
    }


}