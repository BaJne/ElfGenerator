package elf.section.string;

import elf.datatype.Elf64Word;
import elf.section.Section;
import elf.util.Util;
import elf.util.Util.Const;

import java.util.ArrayList;
import java.util.Formatter;

/**
 * String table sections contain strings used for section names and symbol
 * names. A string table is just an array of bytes containing null-terminated
 * strings.
 *
 * Section header table entries, and symbol table entries refer to strings
 * in a string table with an index relative to the beginning of the string table.
 *
 * The first byte in a string table is defined to be null, so that the index 0 always refers
 * to a null or non-existent name.
 */
public class StringTable extends Section {

    private final ArrayList<char[]> table;
    private long offset;        // Offset from String table section where we put str.

    public StringTable() {
        super(".strtab");
        table = new ArrayList<>();

        // Initialize zero index
        table.add(new char[]{'\0'});
        offset = 1;
    }

    public Elf64Word addString(String newString){
        table.add(Util.toCharArray(newString));
        Elf64Word temp = new Elf64Word(offset);
        offset += (newString.length() + 1);
        return temp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);

        sb.append("STRING TABLE").append('\n');
        sb.append("============").append('\n').append('\n');

        String format = "%-30s%-60s\n";

        sb.append(Const.ANSI_YELLOW);
        formatter.format(format, "Offset", "String value");
        sb.append(Const.ANSI_RESET);

        long offs = 0;
        for (char[] chars : table) {
            String str = String.valueOf(chars);
            formatter.format(format, new Elf64Word(offs), str);
            offs += str.length();
        }

        return sb.toString();
    }
}
