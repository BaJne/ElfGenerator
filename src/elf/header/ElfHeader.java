package elf.header;

import elf.datatype.*;
import elf.section.SectionHeaderEntry;
import elf.util.Util.Const;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Formatter;

public class ElfHeader {
    /**
     * Identify the file as an ELF object file, and provide information
     * about the data representation of the object file structures.
     */
    public Elf64Byte[] elfIdentifier;
    public Elf64Half objectFileType;
    public Elf64Half machineType;
    public Elf64Word objectFileVersion;

    /**
     * This member gives the virtual address to which the system first transfers control, thus
     * starting the process. If the file has no associated entry point, this member holds zero.
     */
    public Elf64Address entryPointAddress;
    public Elf64Offset programHeaderOffset;
    public Elf64Offset sectionHeaderOffset;

    /**
     * This member holds processor-specific flags associated with the file. Flag names take
     * the form EF_machine_flag. See ‘‘Machine Information’’ for flag definitions.
     */
    public Elf64Word processorSpecificFlag;
    public Elf64Half elfHeaderSize;
    public Elf64Half programHeaderEntrySize;
    public Elf64Half numOfProgramHeaderEntries;
    public Elf64Half sectionHeaderEntrySize;
    public Elf64Half numOfSectionHeaderEntries;

    /**
     * This member holds the section header table index of the entry associated with the section
     * name string table. If the file has no section name string table, this member holds
     * the value SHN_UNDEF.
     */
    public Elf64Half sectionStringTableIndex;

    public ElfHeader() {
        // TODO: After making of most simple elf file is done, start adding methods that will
        // TODO: allow us to customize process of making different elf file.
        elfIdentifier = new Elf64Byte[16];

        elfIdentifier[0] = new Elf64Byte((short)127);  // \x7f
        elfIdentifier[1] = new Elf64Byte((short)'E');
        elfIdentifier[2] = new Elf64Byte((short)'L');
        elfIdentifier[3] = new Elf64Byte((short)'F');

        elfIdentifier[4] = new Elf64Byte(FileClass.ELF_CLASS_64.value);
        elfIdentifier[5] = new Elf64Byte(DataEncoding.ELF_DATA2LSB.value);
        elfIdentifier[6] = new Elf64Byte(FileVersion.EV_CURRENT.value);
        elfIdentifier[7] = new Elf64Byte(ApplicationBinaryInterface.ELF_OS_ABI_SYSV.value);

        // ABI version
        // This field is used to distinguish among incompatible versions of an ABI
        elfIdentifier[8] = new Elf64Byte((short)0);

        // Start of padding bytes
        for(int i = 9; i < 16; i++){
            elfIdentifier[i] = new Elf64Byte((short)0);
        }
        // Size of elfIdentifier
        // elfIdentifier[15] = new Elf64Byte((short)16);

        objectFileType = new Elf64Half(FileType.ET_DYN.value);
        machineType = new Elf64Half(ProcessorArchitecture.EM_X86_64.value);
        objectFileVersion = new Elf64Word(FileVersion.EV_CURRENT.value);

        entryPointAddress = new Elf64Address(BigInteger.ZERO);  // TODO still to set
        programHeaderOffset = new Elf64Offset(BigInteger.ZERO);
        sectionHeaderOffset = new Elf64Offset(BigInteger.ZERO);

        processorSpecificFlag = new Elf64Word(0);
        elfHeaderSize = new Elf64Half((short)0x0040);

        programHeaderEntrySize = new Elf64Half(0);        // TODO still to set
        numOfProgramHeaderEntries = new Elf64Half(0);

        sectionHeaderEntrySize = new Elf64Half(SectionHeaderEntry.SIZE_IN_BYTES);
        numOfSectionHeaderEntries = new Elf64Half(0);

        sectionStringTableIndex = new Elf64Half(0);
    }

    public enum FileClass{
        ELF_CLASS_NONE((short)0),
        ELF_CLASS_32((short)1),
        ELF_CLASS_64((short)2);

        private final short value;
        FileClass(short value) { this.value = value; }
    }

    public enum DataEncoding{
        ELF_DATA_NONE((short)0),  /** Invalid encoding */
        ELF_DATA2LSB((short)1),   /** Little-Endian    */
        ELF_DATA2MSB((short)2);   /** Big-Endian       */

        private final short value;
        DataEncoding(short value) { this.value = value; }
    }

    public enum ApplicationBinaryInterface{
        ELF_OS_ABI_SYSV((short)0),           /** System V ABI                      */
        ELF_OS_ABI_HPUX((short)1),           /** HP-UX operating system            */
        ELF_OS_ABI_STANDALONE((short)255);   /** Standalone (embedded) application */

        private final short value;
        ApplicationBinaryInterface(short value) { this.value = value; }
    }

    public enum FileType{
        ET_NONE(0),        /** No file type       */
        ET_REL(1),         /** Relocatable file   */
        ET_EXEC(2),        /** Executable file    */
        ET_DYN(3),         /** Shared object file */
        ET_CORE(4),        /** Core file          */
        ET_LOPROC(0xff00), /** Processor-specific */
        ET_HIPROC(0xffff); /** Processor-specific */

        private final int value;
        FileType(int value) { this.value = value; }
    }

    public enum ProcessorArchitecture {
        EM_NONE(0),           /** No Machine */
        EM_M32(1),            /** AT&T WE 32100 */
        EM_SPARC(2),          /** SPARC */
        EM_386(3),            /** Intel 80386 */
        EM_68K(4),            /** Motorola 68000 */
        EM_88K(5),            /** Motorola 88000 */
        EM_860(7),            /** Intel 80860 */
        EM_MIPS(8),           /** MIPS RS3000 */
        // ...
        EM_X86_64(0x003E);    /** AMD x86-64 */

        private final int value;
        ProcessorArchitecture(int value) { this.value = value; }
    }

    public enum FileVersion {
        EV_NONE((short)0),
        EV_CURRENT((short)1);

        private final short value;
        FileVersion(short value) { this.value = value; }
    }

    public byte[] toBytes(){
        ByteBuffer buffer = ByteBuffer.allocate(64);
        for(int i = 0; i < 16; ++i){
            buffer.put(elfIdentifier[i].toBytes());
        }

        buffer.put(objectFileType.toBytes());
        buffer.put(machineType.toBytes());
        buffer.put(objectFileVersion.toBytes());
        buffer.put(entryPointAddress.toBytes());
        buffer.put(programHeaderOffset.toBytes());
        buffer.put(sectionHeaderOffset.toBytes());

        buffer.put(processorSpecificFlag.toBytes());
        buffer.put(elfHeaderSize.toBytes());

        buffer.put(programHeaderEntrySize.toBytes());
        buffer.put(numOfProgramHeaderEntries.toBytes());
        buffer.put(sectionHeaderEntrySize.toBytes());
        buffer.put(numOfSectionHeaderEntries.toBytes());
        buffer.put(sectionStringTableIndex.toBytes());

        return buffer.array();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);

        formatter.format("ELF header\n").format("============\n\n\n");

        Elf64Address offset = new Elf64Address(BigInteger.valueOf(0));

        formatter.format(Const.dbgFormat, "Name", "Offset", "NumValue", "Value");

        formatter.format(Const.dbgFormat, "File identification", offset, "7f 45 4c 46", "ELF");
        offset.incrementBy(ElfDataType.Size._4_BYTES.numOfBytes);

        formatter.format(Const.dbgFormat, "File class", offset, elfIdentifier[4], "");
        offset.incrementBy(elfIdentifier[4].getSize().numOfBytes);

        formatter.format(Const.dbgFormat, "Data encoding", offset, elfIdentifier[5], "");
        offset.incrementBy(elfIdentifier[5].getSize().numOfBytes);

        formatter.format(Const.dbgFormat, "File version", offset, elfIdentifier[6], "");
        offset.incrementBy(elfIdentifier[6].getSize().numOfBytes);

        formatter.format(Const.dbgFormat, "Application Binary Interface", offset, elfIdentifier[7], "");
        offset.incrementBy(elfIdentifier[7].getSize().numOfBytes);

        formatter.format(Const.dbgFormat, "ABI version", offset, elfIdentifier[8], "");
        offset.incrementBy(ElfDataType.Size._1_BYTE.numOfBytes * 7);

        formatter.format(Const.dbgFormat, "File identification size", offset, elfIdentifier[15], "");
        offset.incrementBy(ElfDataType.Size._1_BYTE.numOfBytes);

        formatter.format(Const.dbgFormat, "File type", offset, objectFileType, "");
        offset.incrementBy(objectFileType.getSize().numOfBytes);

        formatter.format(Const.dbgFormat, "Machine type", offset, machineType, "");
        offset.incrementBy(machineType.getSize().numOfBytes);

        formatter.format(Const.dbgFormat, "Object File version", offset, objectFileVersion, "");
        offset.incrementBy(objectFileVersion.getSize().numOfBytes);

        formatter.format(Const.dbgFormat, "Entry point address", offset, entryPointAddress, "");
        offset.incrementBy(entryPointAddress.getSize().numOfBytes);

        formatter.format(Const.dbgFormat, "Program header offset", offset, programHeaderOffset, "");
        offset.incrementBy(programHeaderOffset.getSize().numOfBytes);

        formatter.format(Const.dbgFormat, "Section header offset", offset, sectionHeaderOffset, "");
        offset.incrementBy(sectionHeaderOffset.getSize().numOfBytes);

        formatter.format(Const.dbgFormat, "Process-specific flags", offset, processorSpecificFlag, "");
        offset.incrementBy(processorSpecificFlag.getSize().numOfBytes);

        formatter.format(Const.dbgFormat, "Elf header size", offset, elfHeaderSize, "");
        offset.incrementBy(elfHeaderSize.getSize().numOfBytes);

        formatter.format(Const.dbgFormat, "Program header entry size", offset, programHeaderEntrySize, "");
        offset.incrementBy(programHeaderEntrySize.getSize().numOfBytes);

        formatter.format(Const.dbgFormat, "Num of program header entries", offset, numOfProgramHeaderEntries, "");
        offset.incrementBy(numOfProgramHeaderEntries.getSize().numOfBytes);

        formatter.format(Const.dbgFormat, "Section header entry size", offset, sectionHeaderEntrySize, "");
        offset.incrementBy(sectionHeaderEntrySize.getSize().numOfBytes);

        formatter.format(Const.dbgFormat, "Num of section header entries", offset, numOfSectionHeaderEntries, "");
        offset.incrementBy(numOfSectionHeaderEntries.getSize().numOfBytes);

        formatter.format(Const.dbgFormat, "String table index", offset, sectionStringTableIndex, "");
        offset.incrementBy(sectionStringTableIndex.getSize().numOfBytes);

        sb.append('\n');

        return sb.toString();
    }
}
