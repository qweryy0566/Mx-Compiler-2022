package IR;

import IR.entity.*;
import IR.type.*;
import IR.inst.*;
import utils.BuiltinElements;

import java.util.ArrayList;
import java.util.HashMap;

public class IRProgram implements BuiltinElements {
  public ArrayList<IRFunction> funcList = new ArrayList<IRFunction>();
  public ArrayList<IRGlobalVar> globalVarList = new ArrayList<IRGlobalVar>();
  public ArrayList<IRStructType> structTypeList = new ArrayList<IRStructType>();

  public HashMap<String, IRStringConst> stringConst = new HashMap<>();

  public IRFunction initFunc = new IRFunction("__mx_global_var_init", irVoidType), mainFunc;
  public IRBasicBlock initBlock = new IRBasicBlock(initFunc, "entry_");

  public IRProgram() {
    initFunc.appendBlock(initBlock);
    initFunc.exitBlock = new IRBasicBlock(initFunc, "return_");
    initBlock.terminalInst = new IRJumpInst(initBlock, initFunc.exitBlock);
    initFunc.exitBlock.terminalInst = new IRRetInst(initFunc.exitBlock, irVoidConst);
  }

  public IRStringConst addStringConst(String str) {
    // transfer escape characters
    String val = "";
    for (int i = 0; i < str.length(); ++i) {
      char c = str.charAt(i);
      if (c == '\\') {
        ++i;
        switch (str.charAt(i)) {
          case 'n': val += '\n'; break;
          case '\"': val += '\"'; break;
          default: val += '\\';
        }
      } else val += c;
    }
    if (!stringConst.containsKey(val))
      stringConst.put(val, new IRStringConst(val));
    return stringConst.get(val);
  }

  @Override
  public String toString() {
    String ret = "";
    ret += "target datalayout = \"e-m:e-p:32:32-p270:32:32-p271:32:32-p272:64:64-f64:32:64-f80:32-n8:16:32-S128\"\n";
    ret += "target triple = \"i386-pc-linux-gnu\"\n\n";
    for (IRStructType structType : structTypeList) {
      ret += structType + " = type {";
      for (int i = 0; i < structType.memberType.size(); ++i) {
        ret += structType.memberType.get(i);
        if (i != structType.memberType.size() - 1)
          ret += ", ";
      }
      ret += "}\n";
    }
    for (IRStringConst str : stringConst.values())
      ret += "@str." + String.valueOf(str.id) + " = private unnamed_addr constant ["
          + String.valueOf(str.val.length() + 1) + " x i8] c\"" + str.printStr() + "\"\n";
    for (IRGlobalVar globalVar : globalVarList)
      ret += globalVar + " = dso_local global " + ((IRPtrType) globalVar.type).pointToType() + " " + globalVar.initVal + "\n";
    
    ret += "\ndeclare dso_local i8* @malloc(i32)\n";
    ret += "declare dso_local i8* @strcpy(i8*, i8*)\n";
    ret += "declare dso_local i8* @strcat(i8*, i8*)\n";
    ret += "declare dso_local i32 @strlen(i8*)\n";
    ret += "declare void @print(i8*)\n";
    ret += "declare void @println(i8*)\n";
    ret += "declare void @printInt(i32)\n";
    ret += "declare void @printlnInt(i32)\n";
    ret += "declare i8* @getString()\n";
    ret += "declare i32 @getInt()\n";
    ret += "declare i8* @toString(i32)\n";
    ret += "declare i8* @__mx_substring(i8*, i32, i32)\n";
    ret += "declare i32 @__mx_parseInt(i8*)\n";
    ret += "declare i32 @__mx_ord(i8*, i32)\n";
    ret += "declare i8 @__mx_strlt(i8*, i8*)\n";
    ret += "declare i8 @__mx_strle(i8*, i8*)\n";
    ret += "declare i8 @__mx_strgt(i8*, i8*)\n";
    ret += "declare i8 @__mx_strge(i8*, i8*)\n";
    ret += "declare i8 @__mx_streq(i8*, i8*)\n";
    ret += "declare i8 @__mx_strneq(i8*, i8*)\n\n";

    if (initFunc != null)
      ret += initFunc + "\n";
    for (IRFunction func : funcList)
      ret += func + "\n";
    return ret;
  }
}