package elf.symbol;

import elf.datatype.*;

import java.math.BigInteger;

public class Symbol {
    /**
     * Contains the offset, in bytes, to the symbol name, relative to the
     * start of the symbol string table. If this field contains zero, the symbol has
     * no name.
     */
    private Elf64Word name;
    /**
     * Contains the symbol type and its binding attributes (that is, its
     * scope). The binding attributes are contained in the high-order four bits of
     * the eight-bit byte, and the symbol type is contained in the low-order four
     * bits.
     * Example: 0x03 : 0 - Local scope | 3 - Section associated symbol
     */
    private Elf64Byte info;
    /** Reserved for future use, must be zero */
    private Elf64Byte other;
    /**
     * contains the section index of the section in which the symbol is
     * “defined.” For undefined symbols, this field contains SHN_UNDEF; for
     * absolute symbols, it contains SHN_ABS; and for common symbols, it
     * contains SHN_COMMON.
     */ // TODO: Resolve what are absolute and common symbols
    private Elf64Half sectionTableIndex;
    /**
     * Contains the value of the symbol.
     * This may be an absolute value or a relocatable address.
     * In relocatable files, this field contains the alignment constraint for
     * common symbols, and a section-relative offset for defined relocatable
     * symbols.
     * In executable and shared object files, this field contains a virtual address
     * for defined relocatable symbols.
     */
    private Elf64Address symbolValue;
    /**
     * contains the size associated with the symbol. If a symbol does not
     * have an associated size, or the size is unknown, this field contains zero.
     */
    private Elf64XWord objectSize;

    public Symbol(){
        name = new Elf64Word(0);
        other = new Elf64Byte((short)0);
        info = new Elf64Byte((short)0);
        sectionTableIndex = new Elf64Half(0);
        symbolValue = new Elf64Address(BigInteger.ZERO);
        objectSize = new Elf64XWord(BigInteger.ZERO);
    }

    public Elf64Word getName() { return name; }

    public void setName(Elf64Word name) { this.name = name; }

    public Elf64Byte getInfo() { return info; }

    public void setSymbolBindings(SymbolBindings bind) {
        short newValue = (short)(this.info.value() & 0x0F);
        newValue += bind.value << 4;
        this.info.setValue(newValue);
    }

    public void setSymbolType(SymbolType type){
        short newValue = (short)(this.info.value() & 0xF0);
        newValue += type.value;
        this.info.setValue(newValue);
    }

    public Elf64Half getSectionTableIndex() {
        return sectionTableIndex;
    }

    public void setSectionTableIndex(Elf64Half sectionTableIndex) {
        this.sectionTableIndex = sectionTableIndex;
    }

    public Elf64Address getSymbolValue() {
        return symbolValue;
    }

    public void setSymbolValue(Elf64Address symbolValue) {
        this.symbolValue = symbolValue;
    }

    public Elf64XWord getObjectSize() {
        return objectSize;
    }

    public void setObjectSize(Elf64XWord objectSize) {
        this.objectSize = objectSize;
    }

    public enum SymbolBindings{
        /** Not visible outside the object file */
        STB_LOCAL((short)0),
        /** Global symbol, visible to all object files */
        STB_GLOBAL((short)1),
        /** Global scope, but with lower precedence than global symbols */
        STB_WEAK((short)2),
        /** Environment-specific use */
        STB_LOOS((short)10),
        STB_HIOS((short)12),
        /** Processor-specific use */
        STB_LOPROC((short)13),
        STB_HIPROC((short)15);

        private final short value;
        private SymbolBindings(short size){ value = size; }
    }

    public enum SymbolType{
        /** Not visible outside the object file */
        STT_NOTYPE((short)0),
        /** Data object */
        STT_OBJECT((short)1),
        /** Function entry point */
        STT_FUNC((short)2),
        /** Symbol is associated with section */
        STT_SECTION((short)3),
        /** Source file associated with the object file */
        STT_FILE((short)4),
        /** Environment-specific use */
        STT_LOOS((short)10),
        STT_HIOS((short)12),
        /** Processor-specific use */
        STT_LOPROC((short)13),
        STT_HIPROC((short)15);

        private final short value;
        private SymbolType(short size){ value = size; }
    }

}
