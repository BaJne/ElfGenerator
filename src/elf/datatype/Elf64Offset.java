package elf.datatype;

import java.math.BigInteger;

/**
 * Unsigned file offset
 */
public class Elf64Offset extends ElfDataType<BigInteger> {
    public Elf64Offset(BigInteger data) { super(data); }

    @Override public ElfDataType.Size getSize() { return Size._8_BYTES; }
    @Override public boolean isUnsigned() { return true; }
}