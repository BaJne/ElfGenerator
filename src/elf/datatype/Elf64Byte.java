package elf.datatype;

/**
 * Unsigned small integer
 */
public class Elf64Byte extends ElfDataType<Short> {
    public Elf64Byte(short data) { super(data); }

    @Override public ElfDataType.Size getSize() { return Size._1_BYTE; }
    @Override public boolean isUnsigned() { return true; }
}
