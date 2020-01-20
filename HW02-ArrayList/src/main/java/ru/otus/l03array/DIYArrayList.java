package ru.otus.l03array;

import java.util.*;

public class DIYArrayList<T> implements List<T>{
    private static final int BEGIN_LENGTH = 16;

    private T[] m = (T[]) new Object[BEGIN_LENGTH];

    private int size = 0;

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean contains(final Object o) {
        for(int i = 0; i < this.size(); i += 1){
            if(m[i].equals(o)) return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new ElementsIterator(0);
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(this.m, this.size());
    }

    @Override
    public <T1> T1[] toArray(final T1[] a) {
        if(a.length < this.size()) {
            System.arraycopy(this.m, 0, a, 0, a.length);
        } else {
            System.arraycopy(this.m,0, a,0, this.size());
        }
        return a;
    }

    @Override
    public boolean add(final T t) {
        if(this.m.length == this.size()) {
            final T[] oldM = m;
            this.m = (T[]) new Object[this.size() * 2];
            System.arraycopy(oldM,0, m,0, oldM.length);
        }
        this.m[this.size] = t;
        this.size += 1;
        return  true;
    }

    @Override
    public boolean remove(final Object o) {
        for(int i = 0; i < this.size(); i += 1) {
            if (this.m[i].equals(o)) {
                this.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        for(final Object item: c) {
            if(!this.contains(item)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(final Collection<? extends T> c) {
        for(final T item: c) {
            this.add(item);
        }
        return true;
    }

    @Override
    public boolean addAll (final int index, final Collection<? extends T> c) {
        if(index > this.size() || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        int newIndex = index;
        for(final T item: c) {
            this.add(newIndex, item);
            newIndex += 1;
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean removed = false;
        for(final Object item: c) {
            if(this.remove(item)) {
                removed = true;
            }
        }
        return removed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        this.m = (T[]) new Object[BEGIN_LENGTH];
        this.size = 0;
    }

    @Override
    public T get(int index) {
        if(index > this.size() - 1 || index < 0){
            throw new IndexOutOfBoundsException();
        }
        return this.m[index];
    }

    @Override
    public T set(int index, T element) {
        if(index > this.size() - 1 || index < 0){
            throw new IndexOutOfBoundsException();
        }
        final T oldElement = this.m[index];
        this.m[index] = element;
        return oldElement;
    }

    @Override
    public void add(final int index, final T element) {
        if(index > this.size() || index < 0) {
            throw new  IndexOutOfBoundsException();
        }
        if(index == this.size()) {
            add(element);
        } else {
            final T[] oldM = m;
            if(this.m.length == this.size()) {
                this.m = (T[]) new Object[this.size() * 2];
            }
            System.arraycopy(oldM, 0, m, 0, index);
            System.arraycopy(oldM, index, m, index + 1, this.size() - index);
            this.set(index, element);
            this.size += 1;
        }
    }

    @Override
    public T remove(final int index) {
        if (index > this.size() - 1 || index < 0) throw new IndexOutOfBoundsException();
        final T element = m[index];
        if (index != this.size()) {
            System.arraycopy(m, index + 1, m, index, this.size() - index - 1);
        }
        this.size -= 1;
        return element;
    }

    @Override
    public int indexOf(final Object o) {
        for(int i = 0; i < this.size(); i += 1){
            if(this.m[i].equals(o)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(final Object o) {
        int lastIndex = -1;
        for(int i = 0; i < this.size(); i += 1){
            if(this.m[i].equals(o)){
                lastIndex = i;
            }
        }
        return lastIndex;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ElementsIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new ElementsIterator(index);
    }

    @Override
    public List<T> subList(final int fromIndex, final int toIndex) {
        if(fromIndex < 0 || toIndex > this.size() - 1 || fromIndex > toIndex){
            throw new IndexOutOfBoundsException();
        }
        if(fromIndex == toIndex) {
            return new DIYArrayList<T>();
        }
        final List<T> newArrayList = new DIYArrayList<T>();
        final ListIterator<T> iterator = this.listIterator(fromIndex);
        while (iterator.hasNext()) {
            newArrayList.add(iterator.next());
        }
        return newArrayList;
    }

    private class ElementsIterator implements ListIterator<T> {
        private static final int LAST_IS_NOT_SET = -1;

        /**
         * Index of element to be returned by subsequent call to next.
         */
        private int index;

        /**
         * Index of element returned by most recent call to next or
         * previous.  Reset to -1 if this element is deleted by a call
         * to remove.
         */
        private int lastIndex = LAST_IS_NOT_SET;

        public ElementsIterator() {
            this(0);
        }

        public ElementsIterator(final int index) {
            this.index = index;
        }

        @Override
        public boolean hasNext() {
            return DIYArrayList.this.size() > index;
        }

        @Override
        public T next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            this.lastIndex = this.index;
            this.index += 1;
            return DIYArrayList.this.m[this.lastIndex];
        }

        @Override
        public boolean hasPrevious() {
            return this.index != 0;
        }

        @Override
        public T previous() {
            try {
                this.index -= 1;
                this.lastIndex = this.index;
                T elementPrevios = DIYArrayList.this.m[index];
                return elementPrevios;
            } catch (IndexOutOfBoundsException e) {
                throw new NoSuchElementException();
            }
        }

        @Override
        public int nextIndex() {
            return this.index;
        }

        @Override
        public int previousIndex() {
            return this.index - 1;
        }

        @Override
        public void remove() {
            if(lastIndex == LAST_IS_NOT_SET) {
                throw new IllegalStateException();
            }
            DIYArrayList.this.remove(this.lastIndex);
            this.index -= 1;
            this.lastIndex = LAST_IS_NOT_SET;
        }

        @Override
        public void set(final T t) {
            DIYArrayList.this.m[this.lastIndex] = t;
        }

        @Override
        public void add(final T t) {
            DIYArrayList.this.add(this.index, t);
        }
    }
}
