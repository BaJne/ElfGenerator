package elf.datatype;

public class Elf64Word extends ElfDataType<Long> {
    public Elf64Word(long data) { super(data); }

    @Override public ElfDataType.Size getSize() { return Size._4_BYTES; }
    @Override public boolean isUnsigned() { return true; }
}
