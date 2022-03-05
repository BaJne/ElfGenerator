package elf.section.symbol;

import elf.datatype.Elf64Word;
import elf.section.Section;

import java.util.LinkedHashMap;

public class SymbolTable extends Section {
    private final LinkedHashMap<Elf64Word, Symbol> table;

    public SymbolTable(){
        super(".symtab");
        table = new LinkedHashMap<>();
    }


}
