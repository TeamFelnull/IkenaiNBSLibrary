package red.fellnull.ikenainbstest;

import red.felnull.ikenainbs.Layer;
import red.felnull.ikenainbs.NBS;

import java.io.FileInputStream;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("start");
        NBS nbs = new NBS(new FileInputStream("D:\\pcdatas\\music\\nbs\\mgs2main.nbs"));
        //  NBS nbs = new NBS(new FileInputStream("D:\\pcdatas\\music\\nbs\\test.nbs"));
        //   NBS nbs = new NBS(new FileInputStream("D:\\pcdatas\\music\\nbs\\sandstormnew.nbs"));
        //   NBS nbs = new NBS(new FileInputStream("D:\\pcdatas\\music\\nbs\\sandstorm.nbs"));
        for (Layer value : nbs.getLayers().values()) {
            value.getNoteData().forEach((n, m) -> {
                System.out.println(n+":"+ m.getPitch());
            });
        }
    }
}
