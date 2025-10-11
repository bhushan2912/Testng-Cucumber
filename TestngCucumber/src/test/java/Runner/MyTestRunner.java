package Runner;

import Base.CustomizeTestNGCucumberRunner;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/org/features/dmart.feature",
        glue = {"stepDef", "Hooks"},
        monochrome = true,
        dryRun = false,
//      tags = "@Smoke", // optional
        plugin = {
                "pretty",                                   // prints readable console logs
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" // Allure reporting plugin
        }
)
public class MyTestRunner extends CustomizeTestNGCucumberRunner {

}
