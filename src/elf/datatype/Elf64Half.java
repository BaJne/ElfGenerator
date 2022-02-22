package elf.datatype;

/**
 * Unsigned medium integer
 */
public class Elf64Half  extends ElfDataType<Integer> {
    public Elf64Half(int data) { super(data); }

    @Override public ElfDataType.Size getSize() { return Size._2_BYTES; }
    @Override public boolean isUnsigned() { return true; }
}
