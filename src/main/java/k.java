import hibernate.Connector;
import model.*;
import view.MusicPlayer;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class k {
    public static void main(String[] args) {
//        List<A> list=new ArrayList<>();
//        list.add(new A(4));
//        list.add(new A(2));
//        list.add(new A(5));
//        list.sort(A::compareTo);
//        System.out.println(new A(2).compareTo(new A(1)));
//        System.out.println(new A(2).compareTo(new A(2)));
//        System.out.println(list);
MusicPlayer.getInstance().play(MusicPlayer.row);
        for (int i = 0; i < 9e8; i++) {

        }
        MusicPlayer.getInstance().play(MusicPlayer.row);
        for (int i = 0; i < 9e8; i++) {

        }
    }
}

//class A implements Comparable<A>{
//    int i;
//    A(int f){
//        i=f;
//    }
//
//
//    @Override
//    public int compareTo(A o) {
//        return Integer.compare(o.i,this.i);
//    }
//
//    @Override
//    public String toString() {
//        return i+"";
//    }
//}
