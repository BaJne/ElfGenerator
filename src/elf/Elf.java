package elf;

import elf.datatype.*;
import elf.header.ElfHeader;
import elf.programinfo.ProgramHeaderTable;
import elf.section.Section;
import elf.section.SectionHeaderEntry;
import elf.section.SectionHeaderTable;
import elf.section.string.StringTable;
import elf.section.symbol.Symbol;
import elf.section.symbol.SymbolTable;
import elf.segment.Segment;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;

// TODO Static constants could be defined in order to perform different code generation process
public class Elf {
    private final ElfHeader header;
    private final ProgramHeaderTable programHeaderTable;
    private final ArrayList<Segment> segments;                  // For executable elf file we use segments
    private final ArrayList<Section> sections;
    // ...
    private final StringTable stringTable;
    private final SymbolTable symbolTable;
    private final SectionHeaderTable sectionHeaderTable;

    private Elf64Address programCounter;


    public Elf(ElfHeader header){
        this.header = header;
        programHeaderTable = new ProgramHeaderTable();
        segments = new ArrayList<>();

        sections = new ArrayList<Section>();
        sectionHeaderTable = new SectionHeaderTable();

        // Make predefined sections
        stringTable = new StringTable();
        symbolTable = new SymbolTable();

        // TODO Make helper function to create section and also add it to section table, update symbols, strings table, etc.
        // Add symbol table section entry
        Elf64Word offset = stringTable.addString(symbolTable.getSectionName());
        SectionHeaderEntry entry = new SectionHeaderEntry();
        entry.setSectionName(offset);
        entry.setSectionType(SectionHeaderEntry.SectionType.SHT_SYMTAB);
        entry.linkSectionName(symbolTable.getSectionName());
        entry.setEntriesSize(String.valueOf(Symbol.SIZE_IN_BYTES));
        sectionHeaderTable.addSectionEntry(symbolTable.getSectionName(), entry); // TODO: This could be postponed for later

        // Add string table section entry
        offset = stringTable.addString(stringTable.getSectionName());
        entry = new SectionHeaderEntry();
        entry.setSectionName(offset);
        entry.setSectionType(SectionHeaderEntry.SectionType.SHT_STRTAB);
        entry.linkSectionName(stringTable.getSectionName());
        sectionHeaderTable.addSectionEntry(stringTable.getSectionName(), entry);

        // Update information about section table.
        header.sectionStringTableIndex = new Elf64Half(sectionHeaderTable.getEntryCount()-1);


        programCounter = new Elf64Address(   // Program counter should start from the end of header
                BigInteger.valueOf(header.elfHeaderSize.value()));
    }

    public void finalizeElfStructure(){     // TODO if we need to change this name...
        header.numOfSectionHeaderEntries = new Elf64Half(sectionHeaderTable.getEntryCount());
    }

    public void writeToFile(String fileName){
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fileName))) {
            outputStream.write(header.toBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(header).append('\n');
        sb.append(sectionHeaderTable).append('\n');
        sb.append(symbolTable).append('\n');
        sb.append(stringTable).append('\n');
        return sb.toString();
    }

    // TODO list:
    //  1.  Make different elf files depending if file is shared, exec., etc.
    public static void main(String[] args) {
        // Example how we initialize and create Elf file
        ElfHeader header = new ElfHeader();
        Elf elf = new Elf(header);

        // Making changes to ELF: adding sections, segments, etc. ...
        // ...

        elf.finalizeElfStructure();

        elf.writeToFile("./test/output/elf");

        System.out.println(elf);
    }
}
