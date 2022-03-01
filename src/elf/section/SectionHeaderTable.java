package elf.section;

import elf.datatype.Elf64Word;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * An object file’s section header table lets one locate all the file’s sections.
 */
public class SectionHeaderTable {
    private final LinkedHashMap<Elf64Word, SectionHeaderEntry> table;

    public SectionHeaderTable(){
        table = new LinkedHashMap<>();
        initReservedSectionEntry();
    }

    private void initReservedSectionEntry() {
        SectionHeaderEntry zeroEntry = new SectionHeaderEntry();
        zeroEntry.setSectionType(SectionHeaderEntry.SectionType.SHT_NULL);
        addSectionEntry(zeroEntry.getSectionName(), zeroEntry);

        SectionHeaderEntry relocationSection = new SectionHeaderEntry();
        relocationSection.setSectionName(0x0000007AL);
        relocationSection.setSectionType(SectionHeaderEntry.SectionType.SHT_RELA);
        relocationSection.setSectionAttributes(SectionHeaderEntry.SectionFlag.SHF_ALLOC);
        addSectionEntry(relocationSection.getSectionName(), relocationSection);
    }

    public void addSectionEntry(Elf64Word sectionName, SectionHeaderEntry entry){
        table.put(sectionName, entry);
    }

    public String getHexContent(){
        // TODO: implement this

        return "";
    }

    private enum ReservedEntries {
        /** This value marks an undefined, missing, irrelevant, or otherwise meaningless section reference */
        SHN_UNDEF(0),

        /** This value specifies the lower bound of the range of reserved indexes.*/
        SHN_LORESERVE(0xff00),

        /**
         *  SHN_LOPROC through SHN_HIPROC
         *          Values in this inclusive range are reserved for processor-specific semantics.
         */
        SHN_LOPROC(0xff00),
        SHN_HIPROC(0xff1f),

        /**
         * This value specifies absolute values for the corresponding reference. For example,
         * symbols defined relative to section number SHN_ABS have absolute values and are
         * not affected by relocation.
         */
        SHN_ABS(0xfff1),

        /**
         * Symbols defined relative to this section are common symbols, such as FORTRAN
         * COMMON or unallocated C external variables.
         */
        SHN_COMMON(0xfff2),

        /**
         * This value specifies the upper bound of the range of reserved indexes. The system
         * reserves indexes between SHN_LORESERVE and SHN_HIRESERVE, inclusive; the
         * values do not reference the section header table. That is, the section header table
         * does not contain entries for the reserved indexes.
         */
        SHN_HIRESERVE(0xffff);

        private final long value;

        ReservedEntries(int value) {
            this.value = value;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Section table\n");
        sb.append("====================\n\n");
        Set<Map.Entry<Elf64Word, SectionHeaderEntry>> set = table.entrySet();
        int order = 0;
        for(Map.Entry<Elf64Word, SectionHeaderEntry> e : set){
            sb.append("Section header table: ").append(order++).append('\n');
            sb.append("--------------------\n");
            sb.append(e.getValue().toString()).append('\n');
        }

        return sb.toString();
    }
}