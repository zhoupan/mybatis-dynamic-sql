package org.mybatis.qbe.condition;

public class IsNotNullCondition<T> implements NoValueCondition<T> {

    private IsNotNullCondition() {
        super();
    }
    
    @Override
    public String apply() {
        return "is not null"; //$NON-NLS-1$
    }
    
    public static <T> IsNotNullCondition<T> newInstance() {
        return new IsNotNullCondition<>();
    }
}