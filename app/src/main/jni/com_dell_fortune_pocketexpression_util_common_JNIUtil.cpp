#include "com_dell_fortune_pocketexpression_util_common_JNIUtil.h"

JNIEXPORT jstring JNICALL Java_com_dell_fortune_pocketexpression_util_common_JNIUtil_getSignature
  (JNIEnv * env, jobject obj){
    return env -> NewStringUTF("Hello World");
  }

