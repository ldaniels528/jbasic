package org.ldaniels528.javapc.ibmpc.devices.cpu.operands.memory;

import org.ldaniels528.javapc.ibmpc.devices.cpu.I8086;
import org.ldaniels528.javapc.ibmpc.devices.memory.IbmPcRandomAccessMemory;

/**
 * Represents a NEAR pointer to a 16-bit (word) memory address
 * @author lawrence.daniels@gmail.com
 */
public class MemoryAddressNEAR16 implements OperandAddress {
	private final IbmPcRandomAccessMemory memory;
	private final I8086 cpu;
	private final int offset;
	
	/**
	 * Creates a NEAR pointer to a 16-bit memory address
	 * @param cpu the given {@link org.ldaniels528.javapc.ibmpc.devices.cpu.I8086 CPU} instance
	 * @param offset the offset portion of the address
	 */
	public MemoryAddressNEAR16( final I8086 cpu, final int offset ) {
		this.cpu	= cpu;
		this.memory	= cpu.getRandomAccessMemory();
		this.offset = offset;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int get() {
		return memory.getWord( cpu.DS.get(), offset );
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void set( final int value ) {
		memory.setWord( cpu.DS.get(), offset, value );
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		return SIZE_16BIT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return String.format( "[%04X]", offset );
	}
	
}
