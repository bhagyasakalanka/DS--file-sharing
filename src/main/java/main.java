import java.util.*;

// for testing purposes
public class main {


    private static List<String> createFileList() {
        ArrayList<String> fileList = new ArrayList<>();
        fileList.add("Adventures of Tintin");
        fileList.add("Jack and Jill");
        fileList.add("Glee");
        fileList.add("The Vampire Diarie");
        fileList.add("King Arthur");
        fileList.add("Windows XP");
        fileList.add("Harry Potter");
        fileList.add("Kung Fu Panda");
        fileList.add("Lady Gaga");
        fileList.add("Twilight");
        fileList.add("Windows 8");
        fileList.add("Mission Impossible");
        fileList.add("Turn Up The Music");
        fileList.add("Super Mario");
        fileList.add("American Pickers");
        fileList.add("Microsoft Office 2010");
        fileList.add("Happy Feet");
        fileList.add("Modern Family");
        fileList.add("American Idol");
        fileList.add("Hacking for Dummies");
        Collections.shuffle(fileList);
        List<String> subFileList = fileList.subList(0, 5);
//        System.out.println("File List : " + Arrays.toString(subFileList.toArray()) + "\n");
        return subFileList;
    }
}
