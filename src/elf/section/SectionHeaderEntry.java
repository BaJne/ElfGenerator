package elf.section;

import elf.datatype.Elf64Address;
import elf.datatype.Elf64Offset;
import elf.datatype.Elf64Word;
import elf.datatype.Elf64XWord;
import elf.util.Const;

import java.math.BigInteger;
import java.util.Formatter;

public class SectionHeaderEntry {
    /**
     * This member specifies the name of the section. Its value is an index into the section
     * header string table section [see ‘‘String Table’’ below], giving the location of a null-terminated
     * string.
     */
    private Elf64Word sectionName;
    private Elf64Word sectionType;

    /**
     * Sections support 1-bit flags that describe miscellaneous attributes. Flag definitions
     * appear below.
     */
    private Elf64XWord sectionAttributes;
    private Elf64Address virtualMemoryAddress;
    private Elf64Offset offsetInFile;
    private Elf64XWord sectionSize;

    /**
     * This member holds a section header table index link, whose interpretation depends
     * on the section type.
     */
    /**
     * SHT_DYNAMIC    - String table used by entries in this section
     * SHT_HASH       - Symbol table to which the hash table applies
     * SHT_REL        - Symbol table referenced by relocations
     * SHT_RELA
     * SHT_SYMTAB     - String table used by entries in this section
     * SHT_DYNSYM
     * Other          - SHN_UNDEF
     * */
    private Elf64Word linkToOtherSection;

    /**
     * This member holds extra information, whose interpretation depends on the section
     * type. A table below describes the values.
     */
    /**
     * SHT_REL     - Section index of section to which the relocations apply.
     * SHT_RELA
     * SHT_SYMTAB  - Index of first non-local symbol (i.e., number of local symbols)
     * SHT_DYNSYM
     * Other       - 0
     * */
    private Elf64Word sectionInfo;              // Miscellaneous information
    private Elf64XWord addressAlignment;        // Address alignment boundary
    private Elf64XWord entriesSize;             // Size of entries if section has table

    public SectionHeaderEntry(){
        sectionName = new Elf64Word(0);
        sectionType = new Elf64Word(0);
        sectionAttributes = new Elf64XWord(new BigInteger("0"));
        virtualMemoryAddress = new Elf64Address(new BigInteger("0"));
        offsetInFile = new Elf64Offset(BigInteger.valueOf(0));
        sectionSize = new Elf64XWord(BigInteger.valueOf(0));
        linkToOtherSection = new Elf64Word(0);
        sectionInfo = new Elf64Word(0);
        addressAlignment = new Elf64XWord(BigInteger.valueOf(0));
        entriesSize = new Elf64XWord(BigInteger.valueOf(0));
    }

    public SectionHeaderEntry(
        Elf64Word sectionName,
        Elf64Word sectionType,
        Elf64XWord sectionAttributes,
        Elf64Address virtualMemoryAddress,
        Elf64Offset offsetInFile,
        Elf64XWord sectionSize,
        Elf64Word linkToOtherSection,
        Elf64Word sectionInfo,
        Elf64XWord addressAlignment,
        Elf64XWord entriesSize
    ) {
        this.sectionName = sectionName;
        this.sectionType = sectionType;
        this.sectionAttributes = sectionAttributes;
        this.virtualMemoryAddress = virtualMemoryAddress;
        this.offsetInFile = offsetInFile;
        this.sectionSize = sectionSize;
        this.linkToOtherSection = linkToOtherSection;
        this.sectionInfo = sectionInfo;
        this.addressAlignment = addressAlignment;
        this.entriesSize = entriesSize;
    }

    public void setSectionName(Long sectionName) {
        this.sectionName.setValue(sectionName);
    }

    public void setSectionType(SectionType sectionType) {
        this.sectionType.setValue(sectionType.value);
    }

    public void setSectionAttributes(SectionFlag flag) {
        this.sectionAttributes.setValue(new BigInteger(flag.value, 16));
    }

    public void setVirtualMemoryAddress(BigInteger memAdr) {
        this.virtualMemoryAddress.setValue(memAdr);
    }

    public void setOffsetInFile(BigInteger offset) {
        this.offsetInFile.setValue(offset);
    }

    public void setSectionSize(BigInteger size) {
        this.sectionSize.setValue(size);
    }

    public void setLinkToOtherSection(Long link) {
        this.linkToOtherSection.setValue(link);
    }

    public void setSectionInfo(Long info) {
        this.sectionInfo.setValue(info);
    }

    public void setAddressAlignment(String align) {
        this.addressAlignment.setValue(new BigInteger(align));
    }

    public void setEntriesSize(String entriesSize) {
        this.entriesSize.setValue(new BigInteger(entriesSize));
    }

    public Elf64Word getSectionName() {
        return sectionName;
    }

    public void getHexContent() {
        // TODO get hexa content
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);

        Elf64Address offset = new Elf64Address(BigInteger.valueOf(0));

        formatter.format(Const.dbgFormat, "Section Name:", offset, sectionName, "");
        formatter.format(Const.dbgFormat, "Section Type:", offset, sectionType, "");
        formatter.format(Const.dbgFormat, "Section Attributes:", offset, sectionAttributes, "");
        formatter.format(Const.dbgFormat, "Virtual Memory Address:", offset, virtualMemoryAddress, "");
        formatter.format(Const.dbgFormat, "Offset In File:", offset, offsetInFile, "");
        formatter.format(Const.dbgFormat, "Section Size:", offset, sectionSize, "");
        formatter.format(Const.dbgFormat, "Link To Other Section:", offset, linkToOtherSection, "");
        formatter.format(Const.dbgFormat, "Section Info:", offset, sectionInfo, "");
        formatter.format(Const.dbgFormat, "Address Alignment:", offset, addressAlignment, "");
        formatter.format(Const.dbgFormat, "Entries Size:", offset, entriesSize, "");

        return sb.toString();
    }

    public enum SectionType{
        SHT_NULL(0),            /** Marks an unused section header               */
        SHT_PROGBITS(1),        /** Contains information defined by the program  */
        SHT_SYMTAB(2),          /** Contains a linker symbol table               */
        SHT_STRTAB(3),          /** Contains a string table                      */
        SHT_RELA(4),            /** Contains "Rela" type relocation entries      */
        SHT_HASH(5),            /** Contains a symbol hash table                 */
        SHT_DYNAMIC(6),
        SHT_NOTE(7),
        SHT_NOBITS(8),
        SHT_REL(9),
        SHT_SHLIB(10),
        SHT_DYNSYM(11),
        SHT_LOOS(0x60000000),
        SHT_HIOS(0x6FFFFFFF),
        SHT_LOPROC(0x70000000),
        SHT_HIPROC(0x7FFFFFFF);

        private final long value;
        private SectionType(long size){ value = size; }
    }

    public enum SectionFlag{
        SHF_WRITE("1"),              /** Section contains writable data                   */
        SHF_ALLOC("2"),              /** Section is allocated in memory image of program  */
        SHF_EXECINSTR("4"),          /** Section contains executable instructions         */
        SHF_MASKOS("0F000000"),      /** Environment-specific use                         */
        SHF_MASKPROC("F0000000");    /** Processor-specific use                           */

        private final String value;
        private SectionFlag(String size){ value = size; }
    }
}