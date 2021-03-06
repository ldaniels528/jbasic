package org.ldaniels528.javapc.ibmpc.devices.cpu.opcodes.system;

import org.ldaniels528.javapc.ibmpc.devices.cpu.I8086;
import org.ldaniels528.javapc.ibmpc.devices.cpu.opcodes.AbstractOpCode;
import org.ldaniels528.javapc.ibmpc.system.IbmPcSystem;

/**
 * <pre>
 * Usage:   HLT
 * Modifies flags: None
 *
 * Halts CPU until RESET line is activated, NMI or maskable interrupt
 * received.  The CPU becomes dormant but retains the current CS:IP
 * for later restart.
 * </pre>
 *
 * @author lawrence.daniels@gmail.com
 */
public class HLT extends AbstractOpCode {
    private static HLT instance = new HLT();

    /**
     * Private constructor
     */
    private HLT() {
        super();
    }

    /**
     * @return the singleton instance of this class
     */
    public static HLT getInstance() {
        return instance;
    }

    /* (non-Javadoc)
     * @see org.ldaniels528.javapc.ibmpc.devices.cpu.OpCode#execute(org.ldaniels528.javapc.ibmpc.devices.cpu.VirtualCPU)
     */
    public void execute(IbmPcSystem system, final I8086 cpu) {
        cpu.halt();
    }

}
