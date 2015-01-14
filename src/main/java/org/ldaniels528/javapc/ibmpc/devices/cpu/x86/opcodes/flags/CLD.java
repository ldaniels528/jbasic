package org.ldaniels528.javapc.ibmpc.devices.cpu.x86.opcodes.flags;

import org.ldaniels528.javapc.ibmpc.devices.cpu.I8086;
import org.ldaniels528.javapc.ibmpc.devices.cpu.x86.opcodes.AbstractOpCode;
import org.ldaniels528.javapc.ibmpc.system.IbmPcSystem;

/**
 * <pre>
 * Clear Direction Flag
 *
 * Usage:  CLD
 * Modifies flags: DF
 *
 * Sets the Direction Flag to 0.
 * </pre>
 *
 * @author lawrence.daniels@gmail.com
 */
public class CLD extends AbstractFlagUpdateOpCode {
    private static final CLD instance = new CLD();

    /**
     * Private constructor
     */
    private CLD() {
        super();
    }

    /**
     * @return the singleton instance of this class
     */
    public static CLD getInstance() {
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(IbmPcSystem system, final I8086 cpu) {
        cpu.FLAGS.setDF(false);
    }

}
