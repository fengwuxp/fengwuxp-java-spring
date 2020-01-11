package com.wuxp.api.interceptor;

import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;


/**
 * Cache specific evaluation context that adds a method parameters as SpEL
 * variables, in a lazy manner. The lazy nature eliminates unneeded
 * parsing of classes byte code for parameter discovery.
 *
 * <p>Also define a set of "unavailable variables" (i.e. variables that should
 * lead to an exception right the way when they are accessed). This can be useful
 * to verify a condition does not match even when not all potential variables
 * are present.
 *
 * <p>To limit the creation of objects, an ugly constructor is used
 * (rather then a dedicated 'closure'-like class for deferred execution).
 */
public class ApiEvaluationContext extends MethodBasedEvaluationContext {

    /**
     * Indicate that there is no result variable.
     */
    public static final Object NO_RESULT = new Object();

    /**
     * Indicate that the result variable cannot be used at all.
     */
    public static final Object RESULT_UNAVAILABLE = new Object();

    /**
     * The name of the variable holding the result object.
     */
    public static final String RESULT_VARIABLE = "result";

    public static final String REQUEST_IP_VARIABLE = "ip";

    public static final String REQUEST_OBJECT_VARIABLE = "_this";

    public static final String CURRENT_VALUE_VARIABLE = "_val";

    private final Set<String> unavailableVariables = new HashSet<>(1);

    public ApiEvaluationContext(Object rootObject, Method method, Object[] arguments, ParameterNameDiscoverer parameterNameDiscoverer) {
        super(rootObject, method, arguments, parameterNameDiscoverer);
    }

    /**
     * Add the specified variable name as unavailable for that context.
     * Any expression trying to access this variable should lead to an exception.
     * <p>This permits the validation of expressions that could potentially a
     * variable even when such variable isn't available yet. Any expression
     * trying to use that variable should therefore fail to evaluate.
     */
    public void addUnavailableVariable(String name) {
        this.unavailableVariables.add(name);
    }


    /**
     * Load the param information only when needed.
     */
    @Override
    @Nullable
    public Object lookupVariable(String name) {
        if (this.unavailableVariables.contains(name)) {
            throw new RuntimeException(MessageFormat.format("variable not available{0}", name));
        }
        return super.lookupVariable(name);
    }

    /**
     * @param result the return value (can be {@code null}) or
     *               {@link #NO_RESULT} if there is no return at this time
     */
    public void setEvaluationContextMethodResult(Object result) {
        if (result == RESULT_UNAVAILABLE) {
            this.addUnavailableVariable(RESULT_VARIABLE);
        } else if (result != NO_RESULT) {
            this.setVariable(RESULT_VARIABLE, result);
        }
    }
}
