package asm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

/*
 * 
 * �������ʹ��ASM

����ASM����еĺ����������¼�����

������  ClassReader:�������������������class�ֽ����ļ���

������  ClassWriter:�����������¹����������࣬����˵�޸������������Լ��������������������µ�����ֽ����ļ���

������  ClassAdapter:����Ҳʵ����ClassVisitor�ӿڣ����������ķ�������ί�и���һ��ClassVisitor����

����ʾ��1.ͨ��asm��������ֽ���
 * 
 * 
 */


public class GeneratorClass {

	public GeneratorClass() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) throws IOException {
        //����һ����ֻ��ҪClassWriter�������
        ClassWriter cw = new ClassWriter(0);
        //ͨ��visit����ȷ�����ͷ����Ϣ
        cw.visit(Opcodes.V1_5, Opcodes.ACC_PUBLIC+Opcodes.ACC_ABSTRACT+Opcodes.ACC_INTERFACE,
                "com/asm3/Comparable", null, "java/lang/Object", new String[]{"com/asm3/Mesurable"});
        //�����������
        cw.visitField(Opcodes.ACC_PUBLIC+Opcodes.ACC_FINAL+Opcodes.ACC_STATIC,
                "LESS", "I", null, new Integer(-1)).visitEnd();
        cw.visitField(Opcodes.ACC_PUBLIC+Opcodes.ACC_FINAL+Opcodes.ACC_STATIC,
                "EQUAL", "I", null, new Integer(0)).visitEnd();
        cw.visitField(Opcodes.ACC_PUBLIC+Opcodes.ACC_FINAL+Opcodes.ACC_STATIC,
                "GREATER", "I", null, new Integer(1)).visitEnd();
        //������ķ���
        cw.visitMethod(Opcodes.ACC_PUBLIC+Opcodes.ACC_ABSTRACT, "compareTo",
                "(Ljava/lang/Object;)I", null, null).visitEnd();
        cw.visitEnd(); //ʹcw���Ѿ����
        //��cwת�����ֽ�����д���ļ�����ȥ
        byte[] data = cw.toByteArray();
        File file = new File("D://Comparable.class");
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(data);
        fout.close();
    }
}

