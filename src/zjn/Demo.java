package zjn;

import java.io.*;
import java.util.*;


public class Demo {

    private static String keyWords[] = {"abstract ", "boolean ", "break ", "byte ", "case"
            , "catch ", "char ", "class ", "continue ", "default ", "do ",
            "double ", "else ", "extends ", "final ", "finally ", "float ", "for ",
            "if ", "implements ", "import ", "instanceof ", "int ", "interface ",
            "long ", "native ", "new ", "package ", "private ", "protected ",
            "public ", "return ", "short ", "static ", "super ", "switch ",
            "synchronized ", "this ", "throw ", "throws ", "transient ", "try ",
            "void ", "volatile ", "while ", "strictfp ", "enum ", "goto ", "const ", "assert "
    }; // 关键字数组

    private static String operators[] = {"+", "-", "*", "/", "=", ">", "<", "&", "|", "!"};

    private static String separators[] = {",", ";", "{", "}", "(", ")", "[", "]", "_",
            ":", ".", "\""}; // 界符数组

    public StringBuilder readfile(String src) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new FileReader(new File(src))
            );
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.indexOf("//") != -1) {
                    line = line.substring(0, line.indexOf("//"));
                } else if (line.indexOf("/*") != -1) {
                    line = line.substring(0, line.indexOf("/*"));
                }
                stringBuilder.append(line);
            }
            return stringBuilder;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void ycl() {
        File file = new File("src/outfiles/keywords.txt");
        if (file.exists()) {
            file.delete();
        }
        File file1 = new File("src/outfiles/operators.txt");
        if (file1.exists()) {
            file1.delete();
        }
        File file2 = new File("src/outfiles/others.txt");
        if (file2.exists()) {
            file2.delete();
        }
        File file3 = new File("src/outfiles/separators.txt");
        if (file3.exists()) {
            file3.delete();
        }
    }

    public void analyse() {
        StringBuilder stringBuilder = readfile("src/infiles/input.txt");
        String s = stringBuilder.toString();

        for (int i = 0; i < keyWords.length; i++) {
            s = s.replace(keyWords[i], " $" + i + " ");
        }
        for (int i = 0; i < operators.length; i++) {
            s = s.replace(operators[i], " @" + (keyWords.length + i) + " ");
        }
        for (int i = 0; i < separators.length; i++) {
            s = s.replace(separators[i], " #" + separators[i] + " ");
        }
        String[] list = s.split(" ");
        List<String> ks = new ArrayList<>();
        List<String> os = new ArrayList<>();
        List<String> ss = new ArrayList<>();
        List<String> others = new ArrayList<>();

        for (String a : list) {
            if (a.trim().equals(""))
                continue;
            if (a.startsWith("$")) {
                ks.add(a);
            } else if (a.startsWith("@")) {
                os.add(a);
            } else if (a.startsWith("#")) {
                ss.add(a);
            } else {
                others.add(a);
            }
        }
        try {
            FileWriter fileWriter1 = new FileWriter(new File("src/outfiles/keyWords.txt"));
            for (String a : ks) {
                String temp = a.substring(1);
                fileWriter1.write(temp + "," + keyWords[Integer.valueOf(temp)] + "\r\n");
            }
            fileWriter1.flush();
            fileWriter1.close();
            int len = keyWords.length;
            FileWriter fileWriter2 = new FileWriter(new File("src/outfiles/operators.txt"));
            for (String a : os) {
                String temp = a.substring(1);
                fileWriter2.write(temp + "," + operators[Integer.valueOf(temp) - len] + "\r\n");
            }
            fileWriter2.flush();
            fileWriter2.close();
            len = len + operators.length;
            FileWriter fileWriter3 = new FileWriter(new File("src/outfiles/separators.txt"));
            for (String a : ss) {
                String temp = a.substring(1);
                fileWriter3.write(len + "," + temp + "\r\n");
            }
            fileWriter3.flush();
            fileWriter3.close();
            FileWriter fileWriter4 = new FileWriter(new File("src/outfiles/others.txt"));
            for (String a : others) {
                fileWriter4.write(a + "\r\n");
            }
            fileWriter4.flush();
            fileWriter4.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("OK");
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        demo.ycl();
        demo.analyse();
    }
}
