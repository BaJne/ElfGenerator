package elf;

import elf.datatype.*;
import elf.header.ElfHeader;
import elf.programinfo.ProgramHeaderTable;
import elf.section.Section;
import elf.section.SectionHeaderTable;
import elf.segment.Segment;

import java.math.BigInteger;
import java.util.ArrayList;

// TODO Static constants could be defined in order to perform different code generation process
public class Elf {
    private final ElfHeader header;
    private final ProgramHeaderTable programHeaderTable;
    private final ArrayList<Segment> segments;                  // For executable elf file we use segments
    private final ArrayList<Section> sections;
    // ...
    private final SectionHeaderTable sectionHeaderTable;

    private Elf64Address programCounter;


    public Elf(ElfHeader header){
        this.header = header;
        programHeaderTable = new ProgramHeaderTable();
        segments = new ArrayList<>();
        sections = new ArrayList<Section>();
        sectionHeaderTable = new SectionHeaderTable();

        programCounter = new Elf64Address(   // Program counter should start from the end of header
                BigInteger.valueOf(header.elfHeaderSize.value()));
    }

    public void writeToFile(){

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(header).append('\n');
        sb.append(sectionHeaderTable).append('\n');
        return sb.toString();
    }

    public static void main(String[] args) {
        ElfHeader header = new ElfHeader();
        Elf file = new Elf(header);

        // Looking at most simple assembly code create elf file.


        System.out.println(file);
    }
}