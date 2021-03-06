package org.ldaniels528.javapc.ibmpc.devices.cpu.opcodes.addressing;

import org.ldaniels528.javapc.ibmpc.devices.cpu.I8086;
import org.ldaniels528.javapc.ibmpc.devices.cpu.opcodes.AbstractDualOperandOpCode;
import org.ldaniels528.javapc.ibmpc.devices.cpu.opcodes.RegistersAffected;
import org.ldaniels528.javapc.ibmpc.devices.cpu.operands.OperandHelper;
import org.ldaniels528.javapc.ibmpc.devices.cpu.operands.memory.MemoryReference;
import org.ldaniels528.javapc.ibmpc.devices.cpu.registers.X86Register;
import org.ldaniels528.javapc.ibmpc.devices.memory.IbmPcRandomAccessMemory;
import org.ldaniels528.javapc.ibmpc.system.IbmPcSystem;

/**
 * <pre>
 * Load Pointer Using ES (LES)
 *
 * Usage: LES dest,src
 *
 * Modifies Flags: None
 *
 * Loads 32-bit pointer from memory source to destination
 * register and ES. The offset is placed in the destination
 * register and the segment is placed in ES. To use this
 * instruction the word at the lower memory address must
 * contain the offset and the word at the higher address
 * must contain the segment. This simplifies the loading
 * of far pointers from the stack and the interrupt vector table.
 * </pre>
 *
 * @author lawrence.daniels@gmail.com
 */
@RegistersAffected({"ES"})
public class LES extends AbstractDualOperandOpCode {

    /**
     * LES dst, src (e.g. 'LES AX,[BX+SI]')
     *
     * @param dest the given {@link X86Register destination}
     * @param src  the given {@link MemoryReference source}
     */
    public LES(final X86Register dest, final MemoryReference src) {
        super("LES", dest, OperandHelper.getMemoryPointer(src, dest.size()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final IbmPcSystem system, final I8086 cpu) {
        // get the random access memory (RAM) instance
        final IbmPcRandomAccessMemory memory = cpu.getRandomAccessMemory();

        // cache the segment register (ES)
        final X86Register segReg = cpu.ES;

        // get segment and offset
        final int base = src.get();
        final int offset = memory.getWord(segReg.get(), base);
        final int segment = memory.getWord(segReg.get(), base + 2);

        // load the segment and offset into DS:dst
        segReg.set(segment);
        dest.set(offset);
    }

}
