package org.ldaniels528.javapc.ibmpc.compiler.element.registers;

/**
 * Represents a 16-bit 8086 (General Purpose or Segment) Register Descriptor
 * @author lawrence.daniels@gmail.com
 */
public abstract class X86RegisterRef16 extends X86RegisterRef {

	/**
	 * Creates a new 16-bit 8086 (general purpose or segment) register descriptor
	 * @param name the name of the register
	 * @param id the register's ID
	 * @param index the given index
	 * @param groupIndex the given group index
	 */
	protected X86RegisterRef16( final String name, final int id, final int index, final int groupIndex ) {
		super( name, id, index, groupIndex );
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean is16BitRegister() {
		return true;
	}
	
}
