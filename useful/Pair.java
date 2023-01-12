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
            return equals((Pair<?, ?>) obj);
        }
        return false;
    }

    public <A extends Comparable<A>, B extends Comparable<B>> boolean equals(Pair<A, B> o) {
        return this.first == o.first && this.second == o.second;
    }

    public static <A extends Comparable<A>, B extends Comparable<B>> Pair<A, B> of(A first, B second) {
        return new Pair<>(first, second);
    }
}