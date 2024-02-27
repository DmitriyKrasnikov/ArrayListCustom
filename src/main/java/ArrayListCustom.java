import java.util.*;

public class ArrayListCustom<E> {
    private Object[] elements;
    private int size;

    public ArrayListCustom() {
        elements = new Object[10];
        size = 0;
    }

    public void add(int index, E element) {
        if (element == null) {
            throw new NullPointerException("Element cannot be null");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, size * 2);
        }
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    public boolean addAll(Collection<? extends E> c) {
        if (c == null) {
            throw new NullPointerException("Collection cannot be null");
        }
        for (E element : c) {
            add(size, element);
        }
        return true;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return (E) elements[index];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        E oldElement = (E) elements[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elements, index + 1, elements, index, numMoved);
        }
        elements[--size] = null;
        return oldElement;
    }

    public boolean remove(Object o) {
        if (o == null) {
            throw new NullPointerException("Object cannot be null");
        }
        for (int index = 0; index < size; index++) {
            if (o.equals(elements[index])) {
                remove(index);
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super E> c) {
        if (c == null) {
            throw new NullPointerException("Comparator cannot be null");
        }
        E[] a = (E[]) elements;
        mergeSort(a, 0, size - 1, c);
    }

    private void mergeSort(E[] a, int left, int right, Comparator<? super E> c) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(a, left, mid, c);
            mergeSort(a, mid + 1, right, c);
            merge(a, left, mid, right, c);
        }
    }

    @SuppressWarnings("unchecked")
    private void merge(E[] a, int left, int mid, int right, Comparator<? super E> c) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        E[] L = (E[]) new Object[n1];
        E[] R = (E[]) new Object[n2];
        System.arraycopy(a, left, L, 0, n1);
        System.arraycopy(a, mid + 1, R, 0, n2);
        int i = 0, j = 0;
        for (int k = left; k <= right; k++) {
            if ((j >= n2) || (i < n1 && c.compare(L[i], R[j]) <= 0)) {
                a[k] = L[i++];
            } else {
                a[k] = R[j++];
            }
        }
    }

    public static void main(String[] args) {
        ArrayListCustom<Integer> list = new ArrayListCustom<>();

        // Проверка метода add
        list.add(0, 1);
        list.add(1, 2);
        list.add(2, 3);
        System.out.println("После добавления элементов: " + Arrays.toString(list.elements));

        // Проверка метода addAll
        list.addAll(Arrays.asList(4, 5, 6));
        System.out.println("После добавления коллекции: " + Arrays.toString(list.elements));

        // Проверка метода get
        System.out.println("Элемент на позиции 1: " + list.get(1));

        // Проверка метода remove (по индексу)
        list.remove(1);
        System.out.println("После удаления элемента на позиции 1: " + Arrays.toString(list.elements));

        // Проверка метода remove (по объекту)
        list.remove(Integer.valueOf(3));
        System.out.println("После удаления элемента 3: " + Arrays.toString(list.elements));

        // Проверка метода sort
        list.sort(Comparator.reverseOrder());
        System.out.println("После сортировки в обратном порядке: " + Arrays.toString(list.elements));

        // Проверка метода isEmpty
        System.out.println("Список пуст? " + list.isEmpty());

        // Проверка метода clear
        list.clear();
        System.out.println("После очистки: " + Arrays.toString(list.elements));
        System.out.println("Список пуст? " + list.isEmpty());
    }
}