package ru.nchernetsov;

public class HW2 {

    public static void main(String[] args) {
        HW2 hw2 = new HW2();

        // hw2.bArrayExample();
        // hw2.listExample();
        hw2.iArray2Example();
    }

    private void bArrayExample() {
        BArray<Integer> a = new BArray<>(5, 5);

        for (int i = 0; i < 10; i++)
            a.add(i, i * i);

        for (int i = 0; i < 10; i++)
            System.out.println(a.get(i));
    }

    private void listExample() {
        OList<Integer> list = new OList<>();
        for (int i = 0; i < 10; i++) {
            ListItem<Integer> listItem = new ListItem<>(i * i);
            list.add(listItem);
        }

        ListItem<Integer> head = list.head();
        while (head != null) {
            System.out.println(head.getItem());
            head = head.getNext();
        }
    }

    private void iArray2Example() {
        OList<BArray<Integer>> list = new OList<>();

        BArray<Integer> bArray1 = new BArray<>(9, 0);
        BArray<Integer> bArray2 = new BArray<>(9, 0);
        BArray<Integer> bArray3 = new BArray<>(9, 0);

        bArray1.add(0, 1);
        bArray1.add(1, 2);
        bArray1.add(2, 3);
        bArray1.add(3, 4);
        bArray1.add(4, 5);

        bArray2.add(0, 6);
        bArray2.add(1, 7);
        bArray2.add(2, 8);
        bArray2.add(3, 9);
        bArray2.add(4, 10);
        bArray2.add(5, 11);
        bArray2.add(6, 12);

        bArray3.add(0, 13);
        bArray3.add(1, 14);
        bArray3.add(2, 15);
        bArray3.add(3, 16);

        ListItem<BArray<Integer>> listItem1 = new ListItem<>(bArray1);
        ListItem<BArray<Integer>> listItem2 = new ListItem<>(bArray2);
        ListItem<BArray<Integer>> listItem3 = new ListItem<>(bArray3);

        list.add(listItem1);
        list.add(listItem2);
        list.add(listItem3);

        IArray2<Integer> iArray2 = new IArray2<>(list);

        // должно быть равно 10, т.к. отсчёт от нуля
        System.out.println(iArray2.get(9));
    }
}
