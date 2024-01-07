package revisionDS_POO1_23_24;


public interface Result<T, V extends Enum> extends Comparable<Result<T, V>>{
    //Value of the result
    T getValue();
    //Status of the result
    V getStatus();

    /**
     * Returns the code associated to this result.
     * @return the code associated to this result.
     * The code is an integer value that can be used to compare results and to signal if a result is dangerous.
     */
    int getCode();

    /**
     * The possible values for the code associated to a result explaining this result is dangerous.
     */
    int DANGER = 3;

}