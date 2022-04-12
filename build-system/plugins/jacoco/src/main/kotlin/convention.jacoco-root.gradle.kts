import com.orcchg.stockshock.infra.plugins.utility.withVersionCatalogs
import com.vanniktech.android.junit.jacoco.JunitJacocoExtension

plugins {
    id("com.vanniktech.android.junit.jacoco")
}

description = "Plugin, which configures JaCoCo for the root project"

withVersionCatalogs {
    configure<JunitJacocoExtension> {
        jacocoVersion = versions.jacoco.get()
        xml.enabled = true
        includeNoLocationClasses = true
        includeInstrumentationCoverageInMergedReport = true
        excludes.add("jdk.internal.*")
    }
}
