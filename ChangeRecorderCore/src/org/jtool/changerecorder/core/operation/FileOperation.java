/*
 *  Copyright 2014
 *  Software Science and Technology Lab.
 *  Department of Computer Science, Ritsumeikan University
 */

package org.jtool.changerecorder.core.operation;

import org.jtool.changerecorder.core.util.StringComparator;
import org.jtool.changerecorder.core.util.Time;

/**
 * Stores information on a file operation.
 * @author Katsuhisa Maruyama
 */
public class FileOperation extends AbstractOperation {
    
    /**
     * Defines the type of a file action.
     */
    public enum Type {
        OPEN, CLOSE, SAVE, ACT, INSTANT, NONE;
        
        /**
         * Checks the type of the file action
         * @param str the string indicating the action 
         * @return the type of the file action, or <code>NONE</code> if none
         */
        public static Type parseType(String str) {
            if (StringComparator.isSameIgnoreCase(str, "open")) {
                return OPEN;
            } else if (StringComparator.isSameIgnoreCase(str, "close")) {
                return CLOSE;
            } else if (StringComparator.isSameIgnoreCase(str, "save")) {
                return SAVE;
            } else if (StringComparator.isSameIgnoreCase(str, "act")) {
                return ACT;
            } else if (StringComparator.isSameIgnoreCase(str, "instant")) {
                return INSTANT;
            }
            return NONE;
        }
    }
    
    /**
     * The sort of the file action for this operation
     */
    protected Type actionType;
    
    /**
     * The contents of the source code when this file operation was performed
     */
    protected String code;
    
    /**
     * Creates an instance storing information on this file operation.
     * @param time the time when this operation was performed
     * @param path the path name of the file on which this operation was performed
     * @param author the author's name
     * @param atype the type of the operation
     * @param code the contents of the source code when this file operation was performed
     */
    public FileOperation(long time, String path, String author, Type atype, String code) {
        super(time, path, author);
        this.actionType = atype;
        this.code = code;
    }
    
    /**
     * Creates an instance storing information on this file operation.
     * @param time the time when this operation was performed
     * @param path the path name of the file on which this operation was performed
     * @param atype the type of the operation
     * @param code the contents of the source code when this file operation was performed
     */
    public FileOperation(long time, String path, Type atype, String code) {
        this(time, path, AbstractOperation.getUserName(), atype, code);
    }
    
    /**
     * Returns the sort of the file action for this operation.
     * @return the sort of the file action for this operation
     */
    public Type getActionType() {
        return actionType;
    }
    
    /**
     * Returns the contents of the source code when this file operation was performed
     * @return the contents of the source code
     */
    public String getCode() {
        return code;
    }
    
    /**
     * Sets the contents of the source code when this file operation was performed
     * @param code the contents of the source code
     */
    public void setCode(String code) {
        this.code = code;
    }
    
    /**
     * Returns the sort of this operation.
     * @return the string indicating the operation sort
     */
    @Override
    public OperationType getOperationType() {
        return OperationType.FILE_OPERATION;
    }
    
    /**
     * Tests if this operation is the same as a given one.
     * @param op the given operation
     * @return <code>true</code> if the two operations are the same, otherwise <code>false</code>
     */
    @Override
    public boolean equals(IOperation op) {
        if (!(op instanceof FileOperation)) {
            return false;
        }
        
        FileOperation fop = (FileOperation)op;
        return super.equals(fop) &&
               actionType == fop.getActionType() && StringComparator.isSame(code, fop.getCode());
    }
    
    /**
     * Returns the string for printing, which does not contain a new line character at its end.
     * @return the string for printing
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(Time.toUsefulFormat(time));
        buf.append(" FILE ");
        buf.append("act:[" + actionType.toString() + "] ");
        buf.append("aut:[" + author + "] ");
        buf.append("path:[" + path + "] ");
        if (code != null) {
            buf.append(" [" + code.substring(0, Math.min(12, code.length())) + "...]");
        }
        
        return buf.toString();
    }
}
