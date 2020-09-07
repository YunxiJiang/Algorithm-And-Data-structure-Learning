import java.lang.reflect.Array;
import java.util.Arrays;

public class MergeSort {
    private MergeSort() {}

    public static <E extends Comparable<E>> void sort(E[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    private static <E extends Comparable<E>> void sort(E[] arr, int l, int r) {

        if (l >= r) {
            return;
        }

        // 如果数据规模小于16的话，那么使用InsertionSort进行排序
        // 虽然InsertionSort是O(n2)的复杂度，但常数项很小，对于小规模数据，反而会更快
        // 但对于一些高级语言而言，这样做却很有可能起反作用
        // 但奇怪的是，在这里这层优化起了反作用
//        if (r - l >= 15) {
//            InsertionSort.sort(arr, l, r);
//            return;
//        }

        int mid = l + (r - l) / 2;  // 本来可以写成(l + r) / 2 , 但(l + r)可能造成整型溢出
        sort(arr, l, mid);
        sort(arr, mid + 1, r);

        // 优化merge的执行次数
        if (arr[mid].compareTo(arr[mid + 1]) > 0) {
            merge(arr, l, mid, r);
        }
    }

    // 合并两个有序的区间 arr[l, mid] 和 arr[mid + 1, r]
    private static <E extends Comparable<E>> void merge(E[] arr, int l, int mid, int r) {
        E[] temp = Arrays.copyOfRange(arr, l, r + 1); // copyOfRange是左闭右开的

        int i = l, j = mid + 1;

        for (int k = l; k <= r; k++) {
            if (i > mid) {
                arr[k] = temp[j - l];
                j++;
            } else if (j > r) {
                arr[k] = temp[i - l];
                i++;
            } else if (temp[i - l].compareTo(temp[j - l]) <= 0) {
                arr[k] = temp[i - l];
                i++;
            } else {
                arr[k] = temp[j - l];
                j++;
            }
        }
    }

    // 对内存进行优化
    public static <E extends Comparable<E>> void sort2(E[] arr) {
        E[] temp = Arrays.copyOf(arr, arr.length);
        sort2(arr, 0, arr.length - 1, temp);
    }

    private static <E extends Comparable<E>> void sort2(E[] arr, int l, int r, E[] temp) {

        if (l >= r) {
            return;
        }


        int mid = l + (r - l) / 2;  // 本来可以写成(l + r) / 2 , 但(l + r)可能造成整型溢出
        sort2(arr, l, mid, temp);
        sort2(arr, mid + 1, r, temp);

        // 优化merge的执行次数
        if (arr[mid].compareTo(arr[mid + 1]) > 0) {
            merge2(arr, l, mid, r, temp);
        }
    }

    // 合并两个有序的区间 arr[l, mid] 和 arr[mid + 1, r]
    private static <E extends Comparable<E>> void merge2(E[] arr, int l, int mid, int r,
                                                         E[] temp) {

        System.arraycopy(arr, l, temp, l, r - l + 1);

        int i = l, j = mid + 1;

        for (int k = l; k <= r; k++) {
            if (i > mid) {
                arr[k] = temp[j];
                j++;
            } else if (j > r) {
                arr[k] = temp[i];
                i++;
            } else if (temp[i].compareTo(temp[j]) <= 0) {
                arr[k] = temp[i];
                i++;
            } else {
                arr[k] = temp[j];
                j++;
            }
        }
    }
    public static void main(String[] args){

        int n = 6000000;

        System.out.println("Random Array : ");
        Integer[] arr = ArrayGenerator.generateRandomArray(n, n);
        Integer[] arr2 = Arrays.copyOf(arr, arr.length);

        SortingHelper.sortTest("MergeSort", arr);
        SortingHelper.sortTest("MergeSort2", arr2);


//        system.out.println("\nordered array : ");
//        arr = arraygenerator.generateorderedarray(n);
//        arr2 = arrays.copyof(arr, arr.length);

//        SortingHelper.sortTest("MergeSort", arr);
//        SortingHelper.sortTest("MergeSort2", arr2);
    }
}
