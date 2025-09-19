//version = System.getenv("VERSION") ?: "LOCAL"

plugins {
    id("java-conventions")
    id("testing-conventions")
    id("publishing-conventions")
    id("com.github.johnrengelman.shadow") version "8.1.1" // no need to specify version again
}

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

dependencies {
    implementation("com.drewnoakes:metadata-extractor:2.19.0")
    implementation("commons-io:commons-io:2.19.0")
    implementation("ar.com.hjg:pngj:2.1.0")
}
version = System.getenv("VERSION") ?: "LOCAL"

tasks.withType<ShadowJar>().configureEach {
    archiveClassifier.set("")

    // Package relocation
    relocate("com.drewnoakes", "shadow.com.drewnoakes")
    relocate("org.apache.commons.io", "shadow.org.apache.commons.io")
    relocate("ar.com.hjg", "shadow.ar.com.hjg")

    // Minimization: removes unused classes from dependencies
    minimize()

    // Optional but useful for service loading
    mergeServiceFiles()
}

// Optional: disable default jar task
tasks.named<Jar>("jar") {
    enabled = false
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}
