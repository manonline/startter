package com.manonline.examples.threading;

public class ValueAndType {

    public ValueAndType() {
    }

    public static void main(String[] args) {
        Changer c = new Changer();

        String stra = " Mighty ";
        String strb = " Mouse ";
        c.swap(stra, strb);
        System.out.println(stra + "   " + strb);

        String[] strArr = new String[2];
        strArr[0] = stra;
        strArr[1] = strb;
        c.swap(strArr);
        System.out.println(strArr[0] + "   " + strArr[1]);
    }

    static class Changer {
        public <T> void swap(T a, T b) {
            T temp = a;
            a = b;
            b = temp;
        }

        public <T> void swap(T[] t) {
            if (t.length < 2) {
                System.out.println(" error! ");
                return;
            }

            T temp = t[0];
            t[0] = t[1];
            t[1] = temp;
        }
    }
}
