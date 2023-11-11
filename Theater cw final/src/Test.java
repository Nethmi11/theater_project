import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        ArrayList<String> names = new ArrayList<String>();
        names.add("Nethmi");
        names.add("Wajira");
        names.add("Nilmini");
        names.add("Thinuri");
        names.add("Niketh");

//        Listiterator<String> iterator

        for(int i=0;i<names.size();i++){
            if((names.get(i)).equals("Thinuri")){
                System.out.println(names.get(i));
            }
        }

    }

}
