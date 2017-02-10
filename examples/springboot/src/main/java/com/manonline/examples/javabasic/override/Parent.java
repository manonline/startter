package com.manonline.examples.javabasic.override;

/**
 * Created by davidqi on 2/9/17.
 */
class Parent {

    public String publicValue = "Parent Public Value";
    public final String publicFinalValue = "Parent Public Final Value";
    public static String publicStaticValue = "Parent Public Static Value";
    public static final String publicStaticFinalValue = "Parent Public Static Final Value";

    private String privateValue = "Parent Public Value";
    private final String privateFinalValue = "Private Final Value";
    private static String privateStaticValue = "Private Static Value";
    private static final String privateStaticFinalValue = "Private Static Final Value";

    public String publicMethod() {
        return "Parent Public Method ...";
    }
    public final String publicFinalMethod() {
        return "Parent Public Final Method ...";
    }
    public static String publicStaticMethod() {
        return "Parent Public Static Method ...";
    }
    public static final String publicStaticFinalMethod() {
        return "Parent Public Static Final Method ...";
    }

    private String privateMethod() {
        return "Parent Private Method ...";
    }
    private final String privateFinalMethod() {
        return "Parent Private Final Method ...";
    }
    private static String privateStaticMethod() {
        return "Parent Private Static Method ...";
    }
    private static final String privateStaticFinalMethod() {
        return "Parent Private Static Final Method ...";
    }

    protected String protectedMethod(int m, int n) {
        return "Parent Protected Method ...";
    }

}
