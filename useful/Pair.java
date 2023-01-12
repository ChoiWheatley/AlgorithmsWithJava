package useful;

import java.util.Objects;

public class Pair<A extends Comparable<A>, B extends Comparable<B>> implements Comparable<Pair<A, B>> {
    public final A first;
    public final B second;

    public Pair(A a, B b) {
        first = a;
        second = b;
    }

    @Override
    public int compareTo(Pair<A, B> o) {
        int ret = this.first.compareTo(o.first);
        if (ret == 0) {
            ret = this.second.compareTo(o.second);
        }
        return ret;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pair<?, ?>) {
            Pair<? extends A, ? extends B> o = (Pair<? extends A, ? extends B>) obj;
            return this.first.compareTo(o.first) == 0 && this.second.compareTo(o.second) == 0;
        }
        return false;
    }

    public static <A extends Comparable<A>, B extends Comparable<B>> Pair<A, B> of(A first, B second) {
        return new Pair<>(first, second);
    }
}