package org.ldaniels528.javapc.ibmpc.app;

import org.ldaniels528.javapc.JavaPCConstants;
import org.ldaniels528.javapc.ibmpc.devices.cpu.Intel8086;
import org.ldaniels528.javapc.ibmpc.devices.cpu.ProgramArguments;
import org.ldaniels528.javapc.ibmpc.devices.cpu.ProgramContext;
import org.ldaniels528.javapc.ibmpc.devices.display.IbmPcDisplay;
import org.ldaniels528.javapc.ibmpc.devices.display.IbmPcDisplayFrame;
import org.ldaniels528.javapc.ibmpc.devices.keyboard.IbmPcKeyboard;
import org.ldaniels528.javapc.ibmpc.devices.memory.IbmPcRandomAccessMemory;
import org.ldaniels528.javapc.ibmpc.devices.memory.X86MemoryProxy;
import org.ldaniels528.javapc.ibmpc.exceptions.X86AssemblyException;
import org.ldaniels528.javapc.ibmpc.system.IbmPcSystemXT;

import java.io.File;
import java.io.IOException;

import static java.lang.String.format;
import static org.ldaniels528.javapc.util.ResourceHelper.getBinaryContents;

/**
 * IBM PC/MS-DOS Emulator
 *
 * @author lawrence.daniels@gmail.com
 */
public class IbmPcEmulator implements JavaPCConstants {
    private final IbmPcDisplayFrame frame;
    private final IbmPcSystemXT system;
    private final IbmPcRandomAccessMemory memory;
    private final X86MemoryProxy proxy;
    private final IbmPcDisplay display;
    private final IbmPcKeyboard keyboard;
    private final Intel8086 cpu;

    /**
     * Default constructor
     */
    public IbmPcEmulator() {
        this.frame = new IbmPcDisplayFrame(String.format("JavaPC - IBM PC Emulation Mode v%s", VERSION));
        this.system = new IbmPcSystemXT(frame);

        // get references to all devices
        this.cpu = system.getCPU();
        this.memory = system.getRandomAccessMemory();
        this.display = system.getDisplay();
        this.keyboard = system.getKeyboard();

        // create debug helper objects
        this.proxy = new X86MemoryProxy(memory, 0x13F0, 0x0100);
    }

    /**
     * IBM PC Emulator application
     *
     * @param args the given command line arguments
     * @throws Throwable
     */
    public static void main(final String[] args) throws Throwable {
        // check the command line arguments
        if (args.length < 1) {
            throw new IllegalArgumentException(format("%s <binary.com>", IbmPcEmulator.class.getName()));
        }

        // load the .COM executable
        final IbmPcEmulator ibmPC = new IbmPcEmulator();
        ibmPC.execute(new File(args[0]));
    }

    /**
     * Executes the given .COM file
     *
     * @param binaryFile the given MS-DOS .COM binary file
     */
    public void execute(final File binaryFile) throws IOException, X86AssemblyException {
        // load the MS-DOS .COM binary file from disk
        final byte[] code = getBinaryContents(binaryFile);

        // update the display
        display.update();

        // initialize the CPU
        final ProgramContext pc = new ProgramContext(proxy.getSegment(), proxy.getSegment(), proxy.getOffset(), new ProgramArguments[0]);
        memory.setBytes(proxy.getSegment(), proxy.getOffset(), code, code.length);
        cpu.execute(system, pc);
    }

}