package es.codeurjc.mastercloudapps.your_race;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

public class ArchTest {
    private final JavaClasses importedClasses = new ClassFileImporter().importPackages("es.codeurjc.mastercloudapps.your_race");
    @Test
    public void service_architecture_rule() {
        ArchRule rule = classes().that().areAnnotatedWith(Service.class)
                        .or().haveNameMatching(".*Service")
                        .should().resideInAPackage("..service..");

        rule.check(importedClasses);
    }

    @Test
    public void layeredArchitecture_rule() {
        layeredArchitecture()
                .consideringAllDependencies()
                .layer("RestController").definedBy("..rest..")
                .layer("Component").definedBy("..component..")
                .layer("Service").definedBy("..service..")
                .layer("Repository").definedBy("..repos..")

                .whereLayer("RestController").mayNotBeAccessedByAnyLayer()
                .whereLayer("Service").mayOnlyBeAccessedByLayers("RestController","Component")
                .whereLayer("Repository").mayOnlyBeAccessedByLayers("Service")
                .check(importedClasses);
    }

    @Test
    public void beFreeOfCycles_architecture_rule() {
        slices().matching("es.codeurjc.mastercloudapps.your_race.(*)..").should().beFreeOfCycles();
    }
}