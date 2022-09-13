package me.banfan;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.scopedpool.ScopedClassPoolRepositoryImpl;

import java.io.*;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;


public class PreMainTraceAgent {


    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("agentArgs : " + agentArgs);
        inst.addTransformer(new DefineTransformer(), true);
    }

    static class DefineTransformer implements ClassFileTransformer {

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

            byte[] byteCode = classfileBuffer;

           boolean x = className!=null && className.startsWith("com/example/parserstructuregraphaop/utils") && !className.contains("CGLIB$");

            if (x){
                System.out.println("premain load Class:" + className);
                try {

                    ClassPool classPool = ClassPool.getDefault();
                    CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(byteCode));
                    CtMethod[] methods = ctClass.getDeclaredMethods();
                    for (CtMethod method : methods) {
                        String to = FileUtils.getFileContent("E:\\Project\\JavaProject\\parser-agent\\src\\main\\resources\\to.txt");
                        String back = FileUtils.getFileContent("E:\\Project\\JavaProject\\parser-agent\\src\\main\\resources\\back.txt");
                        method.insertBefore(to);
                        method.insertAfter(back);
                    }
                    byteCode = ctClass.toBytecode();
                    ctClass.detach();
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }


            }

            return byteCode;
        }

        private void useMethod(CtMethod method) throws CannotCompileException {
            String format = String.format("System.out.println(\"%s\");", method.getName());
            method.insertAfter(format);
        }
    }
}
