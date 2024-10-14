package io.tiklab.kaelthas;
import java.io.File;

public class TestScan {
    public static int classSum = 0;
    public static void main(String[] args) {

//        File file = new File("D:\\code\\tiklab-matflow");
        File file = new File("D:\\code\\companycode\\xmonitor\\tiklab-xmonitor");

        doFile(file);

        System.out.println(classSum);

    }

    public static void doFile(File file) {

        if (file.isDirectory()){
            File[] files = file.listFiles();
            if (files != null) {
                for (File fil : files) {
                    doFile(fil);
                }
            }
        }else {
            if (file.isFile()) {
                if ("java".equals(file.getName().substring(file.getName().indexOf(".") + 1))) {
                    classSum += 1;
                }
            }
        }
    }


}
