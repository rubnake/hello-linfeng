package asm.api;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class ClassVisitorTest extends ClassVisitor {


    public ClassVisitorTest() {
        super(Opcodes.ASM4);
    }


    @Override
    public void visit(
            final int version,
            final int access,
            final String name,
            final String signature,
            final String superName,
            final String[] interfaces) {

//        this.currentCls = tClassService.saveClass(name);
    	
    	System.out.println(name);
    	System.out.println(access);
    	System.out.println((access & Opcodes.ACC_INTERFACE));
    	System.out.println(superName);
    	
    	


    }


}
