/**
 *
 */
package org.ldaniels528.javapc.ibmpc.devices.cpu.x86.opcodes.flags;

import org.ldaniels528.javapc.ibmpc.devices.cpu.I8086;
import org.ldaniels528.javapc.ibmpc.devices.cpu.x86.opcodes.AbstractOpCode;
import org.ldaniels528.javapc.ibmpc.system.IbmPcSystem;

/**
 * <pre>
 * Clear Carry Flag
 *
 * Usage:  CLC
 * Modifies flags: CF
 *
 * Sets the Carry Flag to 0.
 * </pre>
 *
 * @author lawrence.daniels@gmail.com
 */
public class CLC extends AbstractFlagUpdateOpCode {
    private static final CLC instance = new CLC();

    /**
     * Private constructor
     */
    private CLC() {
        super();
    }

    /**
     * @return the singleton instance of this class
     */
    public static CLC getInstance() {
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(IbmPcSystem system, final I8086 cpu) {
        cpu.FLAGS.setCF(false);
    }

}