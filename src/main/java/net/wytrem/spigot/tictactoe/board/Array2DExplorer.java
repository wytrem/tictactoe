package net.wytrem.spigot.tictactoe.board;

import com.google.common.base.Preconditions;

import java.util.Iterator;

public class Array2DExplorer {

    public static <T> int consecutives(Iterable<T> iterable, T find) {
        int best = 0;
        int current = 0;

        for (T object : iterable) {
            if (object == null) {
                current = 0;
            }
            else if (object.equals(find)) {
                current++;
                if (current > best) {
                    best = current;
                }
            }
        }

        return best;
    }

    public static <T> Iterable<T> column(T[][] array, int column) {
        Preconditions.checkNotNull(array);
        Preconditions.checkElementIndex(column, array.length);
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    private int index = 0;

                    @Override
                    public boolean hasNext() {
                        return this.index < array[column].length;
                    }

                    @Override
                    public T next() {
                        return array[column][this.index++];
                    }
                };
            }
        };
    }


    public static <T> Iterable<T> forwardsDiagonal(T[][] array, int x, int y) {
        Preconditions.checkArgument(array.length > 0);
        Preconditions.checkArgument(array[0].length > 0);
        Preconditions.checkElementIndex(x, array.length);
        Preconditions.checkElementIndex(y, array[0].length);
        int length = Math.min(array.length - x, array[0].length -y);

        return () -> new Iterator<T>() {

            private int index;

            @Override
            public boolean hasNext() {
                return this.index < length;
            }

            @Override
            public T next() {
                T result = array[x + this.index][y + this.index];

                this.index ++;
                return result;
            }
        };
    }

    public static <T> Iterable<T> backwardsDiagonal(T[][] array, int x, int y) {
        Preconditions.checkArgument(array.length > 0);
        Preconditions.checkArgument(array[0].length > 0);
        Preconditions.checkElementIndex(x, array.length);
        Preconditions.checkElementIndex(y, array[0].length);
        int length = Math.min(x + 1, array[0].length - y);

        return () -> new Iterator<T>() {

            private int index;

            @Override
            public boolean hasNext() {
                return this.index < length;
            }

            @Override
            public T next() {
                T result = array[x - this.index][y + this.index];

                this.index ++;
                return result;
            }
        };
    }

    public static <T> Iterable<T> line(T[][] array, int line) {
        Preconditions.checkNotNull(array);
        Preconditions.checkArgument(array.length > 0);
        Preconditions.checkNotNull(array[0]);
        Preconditions.checkElementIndex(line, array[0].length);
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    private int index = 0;

                    @Override
                    public boolean hasNext() {
                        return this.index < array.length;
                    }

                    @Override
                    public T next() {
                        return array[this.index++][line];
                    }
                };
            }
        };
    }
}
