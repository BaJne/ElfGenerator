package elf.section.string;

import elf.datatype.Elf64Word;
import elf.section.Section;
import elf.util.Util;

import java.util.HashMap;

public class StringTable extends Section {

    HashMap<Elf64Word, char[]> table;

    public StringTable(String name) {
        super(".strtab");
        table = new HashMap<>();
    }

    public void addString(Elf64Word offset, String newString){
        table.put(offset, Util.toCharArray(newString));
    }



}
