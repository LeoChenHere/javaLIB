package javalib.utils;

import javalib.tools.PrintTool;
import java.util.ArrayList;

/**
 *
 * @author leo
 */
public class ALHM extends java.util.ArrayList implements java.io.Serializable {
    
    public ALHM(){
        super();
    }
    public ALHM(ALHM alhm){
        this.addAll(alhm);
    }
    public ALHM(ArrayList al){
        this.addAll(al);
    }
    public ALHM(HM hm){
        this.add(hm);
    }

    
    public void printAll(){
        PrintTool.printALHM(this);
    }
    public void print(int i){
        PrintTool.printHM((HM)this.get(i));
    }
}
