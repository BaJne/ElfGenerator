package elf.section.symbol;

import elf.datatype.Elf64Word;
import elf.section.Section;
import elf.section.SectionHeaderEntry;
import elf.util.Util;

import java.util.Formatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class SymbolTable extends Section {
    private final LinkedHashMap<String, Symbol> table;

    public SymbolTable(){
        super(".symtab");
        table = new LinkedHashMap<>();

        // The first symbol table entry is reserved and must be all zeroes.
        Symbol undSymbol = new Symbol();
        table.put(" ", undSymbol);
    }

    public void addSymbol(String name, Symbol sym){
        table.put(name, sym);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);

        sb.append("SYMBOL TABLE").append('\n');
        sb.append("============").append('\n').append('\n');

        sb.append(Util.Const.ANSI_YELLOW);
        formatter.format(Util.Const.tableDbgFormat, "Value", "Size", "Info (Binding|Type)", "Other", "Sect. Tab. Index", "Name");
        sb.append(Util.Const.ANSI_RESET);

        Set<Map.Entry<String, Symbol>> set = table.entrySet();
        for(Map.Entry<String, Symbol> e : set){
            Symbol sym = e.getValue();
            formatter.format(Util.Const.tableDbgFormat, sym.getSymbolValue(),
                    sym.getObjectSize(), sym.getInfo(), sym.getOther(), sym.getSectionTableIndex(), e.getKey());
        }
        sb.append('\n');

        return sb.toString();
    }
}
