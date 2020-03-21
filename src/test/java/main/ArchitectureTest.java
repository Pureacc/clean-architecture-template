package main;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;

public class ArchitectureTest {
	private static final String ROOT = "org.pureacc.app";
	private static final String MAIN = "org.pureacc.app.main..";
	private static final String VOCABULARY = "org.pureacc.app.vocabulary..";
	private static final String DOMAIN = "org.pureacc.app.domain..";

	private static final String APPLICATION_API = "org.pureacc.app.application.api..";
	private static final String APPLICATION_COMMAND = "org.pureacc.app.application.command..";
	private static final String APPLICATION_QUERY = "org.pureacc.app.application.query..";

	private static final String INFRA = "org.pureacc.app.infra..";
	private static final String INFRA_REST = "org.pureacc.app.infra.rest..";
	private static final String INFRA_JPA = "org.pureacc.app.infra.jpa..";
	private static final String INFRA_TIME = "org.pureacc.app.infra.time..";
	private static final String INFRA_EVENTS = "org.pureacc.app.infra.events..";
	private static final String INFRA_SECURITY = "org.pureacc.app.infra.security..";
	private static final String INFRA_EXCEPTION_HANDLING = "org.pureacc.app.infra.exceptionhandling..";
	private static final String INFRA_VALIDATION = "org.pureacc.app.infra.validation..";
	private static final String INFRA_AUDIT = "org.pureacc.app.infra.audit..";
	private static final String[] CALLING_INFRA = new String[] { INFRA_REST, INFRA_EVENTS };
	private static final String[] IMPLEMENTING_INFRA = new String[] { INFRA_JPA,
																	  INFRA_EVENTS,
																	  INFRA_TIME,
																	  INFRA_SECURITY,
																	  INFRA_EXCEPTION_HANDLING,
																	  INFRA_VALIDATION,
																	  INFRA_AUDIT };

	private static final String WHITELIST = "java..";

	private JavaClasses classes;

	@Before
	public void setup() {
		classes = new ClassFileImporter().withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
				.importPackages(ROOT);
	}

	@Test
	public void properPackageStructure() {
		classes().should()
				.resideInAnyPackage(MAIN, VOCABULARY, DOMAIN, APPLICATION_API, APPLICATION_COMMAND, APPLICATION_QUERY,
						INFRA)
				.check(classes);
	}

	@Test
	public void vocabulary() {
		classes().that()
				.resideInAPackage(VOCABULARY)
				.should()
				.onlyAccessClassesThat()
				.resideInAnyPackage(VOCABULARY, WHITELIST)
				.check(classes);
	}

	@Test
	public void domain() {
		classes().that()
				.resideInAPackage(DOMAIN)
				.should()
				.onlyAccessClassesThat()
				.resideInAnyPackage(DOMAIN, VOCABULARY, WHITELIST)
				.check(classes);
	}

	@Test
	public void applicationApi() {
		classes().that()
				.resideInAPackage(APPLICATION_API)
				.should()
				.onlyAccessClassesThat()
				.resideInAnyPackage(APPLICATION_API, VOCABULARY, WHITELIST)
				.check(classes);
	}

	@Test
	public void applicationCommand() {
		classes().that()
				.resideInAPackage(APPLICATION_COMMAND)
				.should()
				.onlyAccessClassesThat()
				.resideInAnyPackage(APPLICATION_COMMAND, APPLICATION_API, DOMAIN, VOCABULARY, WHITELIST)
				.check(classes);
	}

	@Test
	public void applicationQuery() {
		classes().that()
				.resideInAPackage(APPLICATION_QUERY)
				.should()
				.onlyAccessClassesThat()
				.resideInAnyPackage(APPLICATION_QUERY, APPLICATION_API, DOMAIN, VOCABULARY, WHITELIST)
				.check(classes);
	}

	@Test
	public void callingInfra() {
		for (String infra : CALLING_INFRA) {
			classes().that()
					.resideInAnyPackage(infra)
					.should()
					.onlyAccessClassesThat()
					.resideOutsideOfPackages(APPLICATION_QUERY, APPLICATION_COMMAND, DOMAIN)
					.andShould()
					.onlyAccessClassesThat()
					.resideOutsideOfPackages(allInfraExcept(infra))
					.check(classes);
		}
	}

	@Test
	public void implementingInfra() {
		for (String infra : IMPLEMENTING_INFRA) {
			classes().that()
					.resideInAPackage(infra)
					.should()
					.onlyAccessClassesThat()
					.resideOutsideOfPackages(APPLICATION_QUERY, APPLICATION_COMMAND)
					.andShould()
					.onlyAccessClassesThat()
					.resideOutsideOfPackages(allInfraExcept(infra))
					.check(classes);
		}
	}

	private String[] allInfraExcept(String infra) {
		return Stream.concat(Arrays.stream(CALLING_INFRA), Arrays.stream(IMPLEMENTING_INFRA))
				.filter(s -> !s.equals(infra))
				.toArray(String[]::new);
	}
}
