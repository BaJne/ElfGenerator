package elf.section;

/**
 * Each section occupies one contiguous (possibly empty) sequence of bytes within a file.
 * Sections in a file may not overlap. No byte in a file resides in more than one section.
 * An object file may have inactive space. The various headers and the sections might not ‘‘cover’’
 * every byte in an object file. The contents of the inactive data are unspecified.
 */
public abstract class Section {
    // TODO:
    // 1. Make API:
    //      * Fill compiled code
    //      * Make relocation data and insert inside rel section.
    //      * Find way to update multiple dependent sections - probably from most top level.
    // 2. Every Section has it's entry inside section header table. (need to link this properly, and to fast change
    //    corresponding entries
    // 3. -

    // For debug purpose
    private String name;

    public Section(String name) {
        this.name = name;
    }

    public String getSectionName() {
        return name;
    }
}
