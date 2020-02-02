package everreal;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "de.monochromata.cucumber.report.PrettyReports:target/cucumber"}, tags = {"@smoke"})
public class RunCucumberTest {
}
