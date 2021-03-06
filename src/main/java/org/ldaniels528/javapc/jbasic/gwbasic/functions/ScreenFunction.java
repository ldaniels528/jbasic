package org.ldaniels528.javapc.jbasic.gwbasic.functions;

import org.ldaniels528.javapc.jbasic.common.tokenizer.TokenIterator;

import org.ldaniels528.javapc.ibmpc.devices.display.IbmPcDisplay;
import org.ldaniels528.javapc.ibmpc.devices.display.modes.IbmPcDisplayMode;
import org.ldaniels528.javapc.ibmpc.devices.memory.IbmPcRandomAccessMemory;
import org.ldaniels528.javapc.ibmpc.devices.memory.MemoryObject;
import org.ldaniels528.javapc.jbasic.common.JBasicCompiledCodeReference;
import org.ldaniels528.javapc.jbasic.common.exceptions.JBasicException;
import org.ldaniels528.javapc.jbasic.common.functions.JBasicFunction;
import org.ldaniels528.javapc.jbasic.common.values.types.impl.JBasicTempNumber;

/**
 * SCREEN Function
 * <br>Purpose: To return the ASCII code (0-255) for the character at 
 * the specified row (line) and column on the screen.
 * <br>Syntax: x=SCREEN(<i>row</i>,<i>col</i>[,<i>type</i>])
 * @author lawrence.daniels@gmail.com
 */
public class ScreenFunction extends JBasicFunction {
	private static final int MIN_PARAMS = 2;
	private static final int[] PARAM_TYPES = {
		TYPE_NUMERIC, TYPE_NUMERIC, TYPE_NUMERIC
	};	

    /**
     * Creates an instance of this function
     * @param it the given {@link TokenIterator iterator}
     * @throws JBasicException
     */
    public ScreenFunction( String name, TokenIterator it ) 
    throws JBasicException {
    		super( name, it, PARAM_TYPES, MIN_PARAMS );
    }

    /* 
     * (non-Javadoc)
     * @see org.ldaniels528.javapc.jbasic.values.Value#getValue(org.ldaniels528.javapc.jbasic.environment.JBasicEnvironment)
     */
    public MemoryObject getValue( final JBasicCompiledCodeReference program ) {
		// get the parameter values
		final MemoryObject[] objects = getParameterValues( program );
		
		// identifier the parameters we need
		final int row 	= objects[0].toInteger();
		final int col 	= objects[1].toInteger();
		final int type	= ( objects.length > 2 ) ? objects[2].toInteger() : 0;
		
		// get the display instance
		final IbmPcDisplay display = program.getSystem().getDisplay();
		
		// get the current display mode
		final IbmPcDisplayMode mode = display.getDisplayMode();
		
    		// determine the ascii code or attribute @ (col,row)
		final int segment = mode.getMemorySegment();
		final int offset = display.getVideoOffset( col, row ) + 
							( ( type != 0 ) && !mode.isGraphical() ? 1 : 0 );
    		
		// get the ascii code or attribute @ (col,row)
		final IbmPcRandomAccessMemory memory = program.getSystem().getRandomAccessMemory();
		final int ascii = memory.getByte( segment, offset );
  
	  	// return the position of the cursor
	  	return new JBasicTempNumber( ascii );
    }

}
