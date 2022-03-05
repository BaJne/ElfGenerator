package elf.util;

public class Util {
    public static class Const{
        public static final String dbgFormat = "%-30s%-30s%-30s%-32s\n";
        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_YELLOW = "\u001B[33m";
    }

    public static char[] toCharArray(String s){
        int len = s.length();
        char[] result = new char[len + 1];
        for(int i = 0; i < len; ++i){
            result[i] = s.charAt(i);
        }
        result[len] = '\0';
        return result;
    }
}
