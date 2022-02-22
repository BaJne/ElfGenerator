package elf.datatype;

/**
 * Signed integer
 */
public class Elf64SWord extends ElfDataType<Integer> {
    public Elf64SWord(int data) { super(data); }

    @Override public ElfDataType.Size getSize() { return Size._4_BYTES; }
    @Override public boolean isUnsigned() { return false; }
}
