package elf.datatype;

import java.math.BigInteger;

public abstract class ElfDataType<T extends Number> {
    public enum Size{
        _8_BYTES((short)8),
        _4_BYTES((short)4),
        _2_BYTES((short)2),
        _1_BYTE((short)1);

        public final short numOfBytes;
        private Size(short size){ numOfBytes = size; }
    }

    protected String hex;
    protected T data;

    public abstract Size getSize();
    public abstract boolean isUnsigned();

    public ElfDataType(T data){ this.data = data; }

    public T value(){ return data; }

    public void setValue(T data) {
        this.data = data;
        hex = null;
    }

    @Override
    public boolean equals(Object obj) {
        BigInteger num1, num2;
        if(obj instanceof ElfDataType){
            Number n2 = ((ElfDataType<?>) obj).data;

            if(n2 instanceof BigInteger){
                num2 = (BigInteger) n2;
            }
            else{
                num2 = new BigInteger(String.valueOf(n2.longValue()));
            }

            if(data instanceof BigInteger){
                num1 = (BigInteger) data;
            }
            else {
                num1 = new BigInteger(String.valueOf(data.longValue()));
            }

            return num1.equals(num2);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    public byte[] toBytes(){
        byte[] result;

        if(data instanceof BigInteger) {
            byte[] temp = ((BigInteger)data).toByteArray();
            int numOfZerosToInsert = getSize().numOfBytes - temp.length;
            result = new byte[getSize().numOfBytes];
            for(int i = 0; i < numOfZerosToInsert; ++i) {
                result[i] = 0;
            }
            for(int i = 0; i < temp.length; ++i) {
                result[i + numOfZerosToInsert] = temp[i];
            }
        }
        else {
            result = new byte[getSize().numOfBytes];
            long tempData = data.longValue();

            for(int i = 0; i < result.length; i++){
                result[i] = (byte)(0xFF & tempData);
                tempData >>= 8;
            }
        }

        return result;
    }

    private String getHex(){
        StringBuilder sb = new StringBuilder();

        if(data instanceof BigInteger) { // If BigDecimal is used for storing data.
            String hex = ((BigInteger)data).toString(16);
            sb.append(hex);

            int length = hex.length();
            if(length % 2 != 0){  // If hex length is odd add zero in front
                sb.insert(0, '0');
                length++;
            }

            int numOfZerosToInsert = getSize().numOfBytes - length/2;
            boolean isOdd = numOfZerosToInsert % 2 == 0;
            for(int i = 0; i < numOfZerosToInsert; i++){
                sb.insert(0, "00");
            }
        }
        else {
            long tempData = data.longValue();

            for(int i = 0; i < getSize().numOfBytes; i++){
                String hexSegment =  Long.toHexString(0xFF & tempData);
                sb.insert(0, hexSegment);
                if(hexSegment.length() < 2){
                    sb.insert(0, '0');
                }
                tempData >>= 8;
            }
        }

        hex = sb.toString();
        return hex;
    }

    @Override
    public String toString() {
        if(hex == null){
            getHex();
        }

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < hex.length() ; i+=2 ) {
            sb.append(hex.substring(i, i+2));
            sb.append(' ');
        }

        return sb.toString();
    }
}
