// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    /* Plugin KSP */
    alias(libs.plugins.google.devtools.ksp) apply false

    /* Plugin Hilt */
    alias(libs.plugins.dagger.hilt.android) apply false

    /* Plugin Serialization*/
    alias(libs.plugins.jetbrains.serialization) apply false

}

buildscript{
    dependencies {
        classpath(libs.secrets.gradle.plugin)
    }
}