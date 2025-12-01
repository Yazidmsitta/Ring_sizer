// Top-level build file
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.20" apply false
    id("com.google.devtools.ksp") version "1.9.20-1.0.14" apply false
}


// Repositories are now managed in settings.gradle.kts
// Removed flatDir as we don't have local AAR files

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
