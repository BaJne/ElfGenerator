package elf.datatype;

import java.math.BigInteger;

/**
 * Unsigned long integer
 */
public class Elf64XWord extends ElfDataType<BigInteger> {
    public Elf64XWord(BigInteger data) { super(data); }

    @Override public ElfDataType.Size getSize() { return Size._8_BYTES; }
    @Override public boolean isUnsigned() { return true; }
}
