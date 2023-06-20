#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_allinonewhatstools_test_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_allinonewhatstools_test_ApiManage_APIClient_APIUrl(JNIEnv *env, jclass clazz) {
    std::string hello = "http://159.89.162.204/Test/";
    return env->NewStringUTF(hello.c_str());
}