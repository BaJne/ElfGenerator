package elf.datatype;

/**
 * Signed long integer
 */
public class Elf64SXWord extends ElfDataType<Long> {
    public Elf64SXWord(long data) { super(data); }

    @Override public ElfDataType.Size getSize() { return Size._8_BYTES; }
    @Override public boolean isUnsigned() { return false; }
}
