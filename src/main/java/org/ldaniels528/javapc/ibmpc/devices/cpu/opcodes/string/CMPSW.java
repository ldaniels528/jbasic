package org.ldaniels528.javapc.ibmpc.devices.cpu.opcodes.string;

import org.ldaniels528.javapc.ibmpc.devices.cpu.I8086;
import org.ldaniels528.javapc.ibmpc.devices.cpu.opcodes.AbstractOpCode;
import org.ldaniels528.javapc.ibmpc.devices.cpu.opcodes.FlagsAffected;
import org.ldaniels528.javapc.ibmpc.devices.cpu.opcodes.RegistersAffected;
import org.ldaniels528.javapc.ibmpc.devices.cpu.operands.Operand;
import org.ldaniels528.javapc.ibmpc.devices.memory.IbmPcRandomAccessMemory;
import org.ldaniels528.javapc.ibmpc.system.IbmPcSystem;

/**
 * Compare String Word
 * <pre>
 * Usage:  CMPS    dest,src
 *         CMPSB
 *         CMPSW
 *         CMPSD   (386+)
 * Modifies flags: AF CF OF PF SF ZF
 *
 * Subtracts destination value from source without saving results.
 * Updates flags based on the subtraction and  the index registers
 * (E)SI and (E)DI are incremented or decremented depending on the
 * state of the Direction Flag.  CMPSB inc/decrements the index
 * registers by 1, CMPSW inc/decrements by 2, while CMPSD increments
 * or decrements by 4.  The REP prefixes can be used to process
 * entire data items.
 *
 *                          Clocks                 Size
 * Operands         808x  286   386   486          Bytes
 *
 * dest,src          22    8     10    8             1  (W88=30)
 * </pre>
 *
 * @author lawrence.daniels@gmail.com
 * @see CMPSB
 * @see REPZ
 * @see REPNZ
 */
@FlagsAffected({"AF", "CF", "OF", "PF", "SF", "ZF"})
@RegistersAffected({"DS", "SI", "ES", "DI"})
public class CMPSW extends AbstractOpCode {
    private static CMPSW instance = new CMPSW();

    /**
     * Private constructor
     */
    private CMPSW() {
        super();
    }

    /**
     * @return the singleton instance of this class
     */
    public static CMPSW getInstance() {
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(IbmPcSystem system, final I8086 cpu) {
        // get the RAM instance
        final IbmPcRandomAccessMemory memory = cpu.getRandomAccessMemory();

        // compare byte from DS:[SI] to ES:[DI]
        final Operand src = memory.getWord(cpu.DS, cpu.SI);
        final Operand dst = memory.getWord(cpu.ES, cpu.DI);

        // perform the comparison (update flags)
        cpu.FLAGS.updateAND(dst, src);

        // setup increment/decrement value
        final int delta = cpu.FLAGS.isDF() ? -2 : 2;

        // increment/decrement SI
        cpu.SI.add(delta);
        cpu.DI.add(delta);
    }

}