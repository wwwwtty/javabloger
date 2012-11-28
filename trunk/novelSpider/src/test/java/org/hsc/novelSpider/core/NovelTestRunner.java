package org.hsc.novelSpider.core;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

public class NovelTestRunner extends BlockJUnit4ClassRunner {

	public NovelTestRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	/**
	 * Wraps the {@link Statement} returned by the parent implementation with a
	 * {@link RunBeforeTestClassCallbacks} statement, thus preserving the
	 * default functionality but adding support for the Spring TestContext
	 * Framework.
	 * @see RunBeforeTestClassCallbacks
	 */
	@Override
	protected Statement withBeforeClasses(Statement statement) {
		Statement junitBeforeClasses = super.withBeforeClasses(statement);
		return new RunBeforeTestClassCallbacks(junitBeforeClasses, getTestContextManager());
	}

	/**
	 * Wraps the {@link Statement} returned by the parent implementation with a
	 * {@link RunAfterTestClassCallbacks} statement, thus preserving the default
	 * functionality but adding support for the Spring TestContext Framework.
	 * @see RunAfterTestClassCallbacks
	 */
	@Override
	protected Statement withAfterClasses(Statement statement) {
		Statement junitAfterClasses = super.withAfterClasses(statement);
		return new RunAfterTestClassCallbacks(junitAfterClasses, getTestContextManager());
	}
}
