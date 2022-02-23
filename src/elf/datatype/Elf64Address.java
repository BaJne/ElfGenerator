package elf.datatype;

import java.math.BigInteger;

/**
 * Unsigned program address
 */
public class Elf64Address extends ElfDataType<BigInteger> {
    public Elf64Address(BigInteger data) { super(data); }

    @Override public ElfDataType.Size getSize() { return Size._8_BYTES; }
    @Override public boolean isUnsigned() { return true; }
    public void incrementBy(int size){
        setValue(data.add(BigInteger.valueOf(size)));
    }
}
