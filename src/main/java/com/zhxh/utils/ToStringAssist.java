package com.zhxh.utils;

/**
 * <p>Assists in implementing {@link Object#toString()} methods.</p>
 * <p>
 * <p>This class enables a good and consistent <code>toString()</code> to be built for any
 * class or object. This class aims to simplify the process by:</p>
 * <ul>
 * <li>allowing field names</li>
 * <li>handling all types consistently</li>
 * <li>handling nulls consistently</li>
 * <li>outputting arrays and multi-dimensional arrays</li>
 * <li>enabling the detail level to be controlled for Objects and Collections</li>
 * <li>handling class hierarchies</li>
 * </ul>
 * <p>
 * <p>To use this class write code as follows:</p>
 * <p>
 * <pre>
 * public class Person {
 *   String name;
 *   int age;
 *   boolean smoker;
 *
 *   ...
 *
 *   public String toString() {
 *     return new ToStringAssist(this).
 *       append("name", name).
 *       append("age", age).
 *       append("smoker", smoker).
 *       toString();
 *   }
 * }
 * </pre>
 * <p>
 * <p>This will produce a toString of the format:
 * <code>Person@7f54[name=Stephen,age=29,smoker=false]</code></p>
 * <p>
 * <p>To add the superclass <code>toString</code>, use {@link #appendSuper}.
 * To append the <code>toString</code> from an object that is delegated
 * to (or any other object), use {@link #appendToString}.</p>
 * <p>
 * <p>Alternatively, there is a method that uses reflection to determine
 * the fields to test. Because these fields are usually private, the method,
 * <code>reflectionToString</code>, uses <code>AccessibleObject.setAccessible</code> to
 * change the visibility of the fields. This will fail under a security manager,
 * unless the appropriate permissions are set up correctly. It is also
 * slower than testing explicitly.</p>
 * <p>
 * <p>A typical invocation for this method would look like:</p>
 * <p>
 * <pre>
 * public String toString() {
 *   return ToStringAssist.reflectionToString(this);
 * }
 * </pre>
 * <p>
 * <p>You can also use the builder to debug 3rd party objects:</p>
 * <p>
 * <pre>
 * System.out.println("An object: " + ToStringAssist.reflectionToString(anObject));
 * </pre>
 * <p>
 * <p>The exact format of the <code>toString</code> is determined by
 * the {@link ToStringStyle} passed into the constructor.</p>
 *
 * @author Stephen Colebourne
 * @author Gary Gregory
 * @author Pete Gieser
 * @since 1.0
 */
public class ToStringAssist {

    /**
     * The default style of output to use.
     */
    private static ToStringStyle defaultStyle = ToStringStyle.DEFAULT_STYLE;

    //----------------------------------------------------------------------------

    /**
     * <p>Gets the default <code>ToStringStyle</code> to use.</p>
     * <p>
     * <p>This could allow the <code>ToStringStyle</code> to be
     * controlled for an entire application with one call.</p>
     * <p>
     * <p>This might be used to have a verbose
     * <code>ToStringStyle</code> during development and a compact
     * <code>ToStringStyle</code> in production.</p>
     *
     * @return the default <code>ToStringStyle</code>
     */
    public static ToStringStyle getDefaultStyle() {
        return defaultStyle;
    }

    /**
     * <p>Sets the default <code>ToStringStyle</code> to use.</p>
     *
     * @param style the default <code>ToStringStyle</code>
     * @throws IllegalArgumentException if the style is <code>null</code>
     */
    public static void setDefaultStyle(ToStringStyle style) {
        if (style == null) {
            throw new IllegalArgumentException("The style must not be null");
        }
        defaultStyle = style;
    }

    /**
     * Current toString buffer.
     */
    private final StringBuilder buffer;

    /**
     * The object being output.
     */
    private final Object object;

    /**
     * The style of output to use.
     */
    private final ToStringStyle style;

    /**
     * <p>Constructor for <code>ToStringAssist</code>.</p>
     * <p>
     * <p>This constructor outputs using the default style set with
     * <code>setDefaultStyle</code>.</p>
     *
     * @param object the Object to build a <code>toString</code> for
     * @throws IllegalArgumentException if the Object passed in is
     *                                  <code>null</code>
     */
    public ToStringAssist(Object object) {
        this(object, getDefaultStyle(), null);
    }

    /**
     * <p>Constructor for <code>ToStringAssist</code> specifying the
     * output style.</p>
     * <p>
     * <p>If the style is <code>null</code>, the default style is used.</p>
     *
     * @param object the Object to build a <code>toString</code> for
     * @param style  the style of the <code>toString</code> to create,
     *               may be <code>null</code>
     * @throws IllegalArgumentException if the Object passed in is
     *                                  <code>null</code>
     */
    public ToStringAssist(Object object, ToStringStyle style) {
        this(object, style, null);
    }

    /**
     * <p>Constructor for <code>ToStringAssist</code>.</p>
     * <p>
     * <p>If the style is <code>null</code>, the default style is used.</p>
     * <p>
     * <p>If the buffer is <code>null</code>, a new one is created.</p>
     *
     * @param object the Object to build a <code>toString</code> for
     * @param style  the style of the <code>toString</code> to create,
     *               may be <code>null</code>
     * @param buffer the <code>StringBuilder</code> to populate, may be
     *               <code>null</code>
     */
    public ToStringAssist(Object object, ToStringStyle style, StringBuilder buffer) {
        if (style == null) {
            style = getDefaultStyle();
        }
        if (buffer == null) {
            buffer = new StringBuilder(512);
        }
        this.buffer = buffer;
        this.style = style;
        this.object = object;

        style.appendStart(buffer, object);
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> a <code>boolean</code>
     * value.</p>
     *
     * @param value the value to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(boolean value) {
        style.append(buffer, null, value);
        return this;
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> a <code>boolean</code>
     * array.</p>
     *
     * @param array the array to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(boolean[] array) {
        style.append(buffer, null, array, null);
        return this;
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> a <code>byte</code>
     * value.</p>
     *
     * @param value the value to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(byte value) {
        style.append(buffer, null, value);
        return this;
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> a <code>byte</code>
     * array.</p>
     *
     * @param array the array to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(byte[] array) {
        style.append(buffer, null, array, null);
        return this;
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> a <code>char</code>
     * value.</p>
     *
     * @param value the value to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(char value) {
        style.append(buffer, null, value);
        return this;
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> a <code>char</code>
     * array.</p>
     *
     * @param array the array to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(char[] array) {
        style.append(buffer, null, array, null);
        return this;
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> a <code>double</code>
     * value.</p>
     *
     * @param value the value to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(double value) {
        style.append(buffer, null, value);
        return this;
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> a <code>double</code>
     * array.</p>
     *
     * @param array the array to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(double[] array) {
        style.append(buffer, null, array, null);
        return this;
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> a <code>float</code>
     * value.</p>
     *
     * @param value the value to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(float value) {
        style.append(buffer, null, value);
        return this;
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> a <code>float</code>
     * array.</p>
     *
     * @param array the array to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(float[] array) {
        style.append(buffer, null, array, null);
        return this;
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> an <code>int</code>
     * value.</p>
     *
     * @param value the value to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(int value) {
        style.append(buffer, null, value);
        return this;
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> an <code>int</code>
     * array.</p>
     *
     * @param array the array to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(int[] array) {
        style.append(buffer, null, array, null);
        return this;
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> a <code>long</code>
     * value.</p>
     *
     * @param value the value to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(long value) {
        style.append(buffer, null, value);
        return this;
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> a <code>long</code>
     * array.</p>
     *
     * @param array the array to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(long[] array) {
        style.append(buffer, null, array, null);
        return this;
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> an <code>Object</code>
     * value.</p>
     *
     * @param obj the value to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(Object obj) {
        style.append(buffer, null, obj, null);
        return this;
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> an <code>Object</code>
     * array.</p>
     *
     * @param array the array to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(Object[] array) {
        style.append(buffer, null, array, null);
        return this;
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> a <code>short</code>
     * value.</p>
     *
     * @param value the value to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(short value) {
        style.append(buffer, null, value);
        return this;
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append to the <code>toString</code> a <code>short</code>
     * array.</p>
     *
     * @param array the array to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(short[] array) {
        style.append(buffer, null, array, null);
        return this;
    }

    /**
     * <p>Append to the <code>toString</code> a <code>boolean</code>
     * value.</p>
     *
     * @param fieldName the field name
     * @param value     the value to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(String fieldName, boolean value) {
        style.append(buffer, fieldName, value);
        return this;
    }

    /**
     * <p>Append to the <code>toString</code> a <code>boolean</code>
     * array.</p>
     *
     * @param fieldName the field name
     * @param array     the array to add to the <code>hashCode</code>
     * @return this
     */
    public ToStringAssist append(String fieldName, boolean[] array) {
        style.append(buffer, fieldName, array, null);
        return this;
    }

    /**
     * <p>Append to the <code>toString</code> a <code>boolean</code>
     * array.</p>
     * <p>
     * <p>A boolean parameter controls the level of detail to show.
     * Setting <code>true</code> will output the array in full. Setting
     * <code>false</code> will output a summary, typically the size of
     * the array.</p>
     *
     * @param fieldName  the field name
     * @param array      the array to add to the <code>toString</code>
     * @param fullDetail <code>true</code> for detail, <code>false</code>
     *                   for summary info
     * @return this
     */
    public ToStringAssist append(String fieldName, boolean[] array, boolean fullDetail) {
        style.append(buffer, fieldName, array, fullDetail);
        return this;
    }

    /**
     * <p>Append to the <code>toString</code> an <code>byte</code>
     * value.</p>
     *
     * @param fieldName the field name
     * @param value     the value to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(String fieldName, byte value) {
        style.append(buffer, fieldName, value);
        return this;
    }

    /**
     * <p>Append to the <code>toString</code> a <code>byte</code> array.</p>
     *
     * @param fieldName the field name
     * @param array     the array to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(String fieldName, byte[] array) {
        style.append(buffer, fieldName, array, null);
        return this;
    }

    /**
     * <p>Append to the <code>toString</code> a <code>byte</code>
     * array.</p>
     * <p>
     * <p>A boolean parameter controls the level of detail to show.
     * Setting <code>true</code> will output the array in full. Setting
     * <code>false</code> will output a summary, typically the size of
     * the array.
     *
     * @param fieldName  the field name
     * @param array      the array to add to the <code>toString</code>
     * @param fullDetail <code>true</code> for detail, <code>false</code>
     *                   for summary info
     * @return this
     */
    public ToStringAssist append(String fieldName, byte[] array, boolean fullDetail) {
        style.append(buffer, fieldName, array, fullDetail);
        return this;
    }

    /**
     * <p>Append to the <code>toString</code> a <code>char</code>
     * value.</p>
     *
     * @param fieldName the field name
     * @param value     the value to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(String fieldName, char value) {
        style.append(buffer, fieldName, value);
        return this;
    }

    /**
     * <p>Append to the <code>toString</code> a <code>char</code>
     * array.</p>
     *
     * @param fieldName the field name
     * @param array     the array to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(String fieldName, char[] array) {
        style.append(buffer, fieldName, array, null);
        return this;
    }

    /**
     * <p>Append to the <code>toString</code> a <code>char</code>
     * array.</p>
     * <p>
     * <p>A boolean parameter controls the level of detail to show.
     * Setting <code>true</code> will output the array in full. Setting
     * <code>false</code> will output a summary, typically the size of
     * the array.</p>
     *
     * @param fieldName  the field name
     * @param array      the array to add to the <code>toString</code>
     * @param fullDetail <code>true</code> for detail, <code>false</code>
     *                   for summary info
     * @return this
     */
    public ToStringAssist append(String fieldName, char[] array, boolean fullDetail) {
        style.append(buffer, fieldName, array, fullDetail);
        return this;
    }

    /**
     * <p>Append to the <code>toString</code> a <code>double</code>
     * value.</p>
     *
     * @param fieldName the field name
     * @param value     the value to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(String fieldName, double value) {
        style.append(buffer, fieldName, value);
        return this;
    }

    /**
     * <p>Append to the <code>toString</code> a <code>double</code>
     * array.</p>
     *
     * @param fieldName the field name
     * @param array     the array to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(String fieldName, double[] array) {
        style.append(buffer, fieldName, array, null);
        return this;
    }

    /**
     * <p>Append to the <code>toString</code> a <code>double</code>
     * array.</p>
     * <p>
     * <p>A boolean parameter controls the level of detail to show.
     * Setting <code>true</code> will output the array in full. Setting
     * <code>false</code> will output a summary, typically the size of
     * the array.</p>
     *
     * @param fieldName  the field name
     * @param array      the array to add to the <code>toString</code>
     * @param fullDetail <code>true</code> for detail, <code>false</code>
     *                   for summary info
     * @return this
     */
    public ToStringAssist append(String fieldName, double[] array, boolean fullDetail) {
        style.append(buffer, fieldName, array, fullDetail);
        return this;
    }

    /**
     * <p>Append to the <code>toString</code> an <code>float</code>
     * value.</p>
     *
     * @param fieldName the field name
     * @param value     the value to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(String fieldName, float value) {
        style.append(buffer, fieldName, value);
        return this;
    }

    /**
     * <p>Append to the <code>toString</code> a <code>float</code>
     * array.</p>
     *
     * @param fieldName the field name
     * @param array     the array to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(String fieldName, float[] array) {
        style.append(buffer, fieldName, array, null);
        return this;
    }

    /**
     * <p>Append to the <code>toString</code> a <code>float</code>
     * array.</p>
     * <p>
     * <p>A boolean parameter controls the level of detail to show.
     * Setting <code>true</code> will output the array in full. Setting
     * <code>false</code> will output a summary, typically the size of
     * the array.</p>
     *
     * @param fieldName  the field name
     * @param array      the array to add to the <code>toString</code>
     * @param fullDetail <code>true</code> for detail, <code>false</code>
     *                   for summary info
     * @return this
     */
    public ToStringAssist append(String fieldName, float[] array, boolean fullDetail) {
        style.append(buffer, fieldName, array, fullDetail);
        return this;
    }

    /**
     * <p>Append to the <code>toString</code> an <code>int</code>
     * value.</p>
     *
     * @param fieldName the field name
     * @param value     the value to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(String fieldName, int value) {
        style.append(buffer, fieldName, value);
        return this;
    }

    /**
     * <p>Append to the <code>toString</code> an <code>int</code>
     * array.</p>
     *
     * @param fieldName the field name
     * @param array     the array to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(String fieldName, int[] array) {
        style.append(buffer, fieldName, array, null);
        return this;
    }

    /**
     * <p>Append to the <code>toString</code> an <code>int</code>
     * array.</p>
     * <p>
     * <p>A boolean parameter controls the level of detail to show.
     * Setting <code>true</code> will output the array in full. Setting
     * <code>false</code> will output a summary, typically the size of
     * the array.</p>
     *
     * @param fieldName  the field name
     * @param array      the array to add to the <code>toString</code>
     * @param fullDetail <code>true</code> for detail, <code>false</code>
     *                   for summary info
     * @return this
     */
    public ToStringAssist append(String fieldName, int[] array, boolean fullDetail) {
        style.append(buffer, fieldName, array, fullDetail);
        return this;
    }

    /**
     * <p>Append to the <code>toString</code> a <code>long</code>
     * value.</p>
     *
     * @param fieldName the field name
     * @param value     the value to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(String fieldName, long value) {
        style.append(buffer, fieldName, value);
        return this;
    }

    /**
     * <p>Append to the <code>toString</code> a <code>long</code>
     * array.</p>
     *
     * @param fieldName the field name
     * @param array     the array to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(String fieldName, long[] array) {
        style.append(buffer, fieldName, array, null);
        return this;
    }

    /**
     * <p>Append to the <code>toString</code> a <code>long</code>
     * array.</p>
     * <p>
     * <p>A boolean parameter controls the level of detail to show.
     * Setting <code>true</code> will output the array in full. Setting
     * <code>false</code> will output a summary, typically the size of
     * the array.</p>
     *
     * @param fieldName  the field name
     * @param array      the array to add to the <code>toString</code>
     * @param fullDetail <code>true</code> for detail, <code>false</code>
     *                   for summary info
     * @return this
     */
    public ToStringAssist append(String fieldName, long[] array, boolean fullDetail) {
        style.append(buffer, fieldName, array, fullDetail);
        return this;
    }

    /**
     * <p>Append to the <code>toString</code> an <code>Object</code>
     * value.</p>
     *
     * @param fieldName the field name
     * @param obj       the value to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(String fieldName, Object obj) {
        style.append(buffer, fieldName, obj, null);
        return this;
    }

    /**
     * <p>Append to the <code>toString</code> an <code>Object</code>
     * value.</p>
     *
     * @param fieldName  the field name
     * @param obj        the value to add to the <code>toString</code>
     * @param fullDetail <code>true</code> for detail,
     *                   <code>false</code> for summary info
     * @return this
     */
    public ToStringAssist append(String fieldName, Object obj, boolean fullDetail) {
        style.append(buffer, fieldName, obj, fullDetail);
        return this;
    }

    /**
     * <p>Append to the <code>toString</code> an <code>Object</code>
     * array.</p>
     *
     * @param fieldName the field name
     * @param array     the array to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(String fieldName, Object[] array) {
        style.append(buffer, fieldName, array, null);
        return this;
    }

    /**
     * <p>Append to the <code>toString</code> an <code>Object</code>
     * array.</p>
     * <p>
     * <p>A boolean parameter controls the level of detail to show.
     * Setting <code>true</code> will output the array in full. Setting
     * <code>false</code> will output a summary, typically the size of
     * the array.</p>
     *
     * @param fieldName  the field name
     * @param array      the array to add to the <code>toString</code>
     * @param fullDetail <code>true</code> for detail, <code>false</code>
     *                   for summary info
     * @return this
     */
    public ToStringAssist append(String fieldName, Object[] array, boolean fullDetail) {
        style.append(buffer, fieldName, array, fullDetail);
        return this;
    }

    /**
     * <p>Append to the <code>toString</code> an <code>short</code>
     * value.</p>
     *
     * @param fieldName the field name
     * @param value     the value to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(String fieldName, short value) {
        style.append(buffer, fieldName, value);
        return this;
    }

    /**
     * <p>Append to the <code>toString</code> a <code>short</code>
     * array.</p>
     *
     * @param fieldName the field name
     * @param array     the array to add to the <code>toString</code>
     * @return this
     */
    public ToStringAssist append(String fieldName, short[] array) {
        style.append(buffer, fieldName, array, null);
        return this;
    }

    /**
     * <p>Append to the <code>toString</code> a <code>short</code>
     * array.</p>
     * <p>
     * <p>A boolean parameter controls the level of detail to show.
     * Setting <code>true</code> will output the array in full. Setting
     * <code>false</code> will output a summary, typically the size of
     * the array.
     *
     * @param fieldName  the field name
     * @param array      the array to add to the <code>toString</code>
     * @param fullDetail <code>true</code> for detail, <code>false</code>
     *                   for summary info
     * @return this
     */
    public ToStringAssist append(String fieldName, short[] array, boolean fullDetail) {
        style.append(buffer, fieldName, array, fullDetail);
        return this;
    }

    /**
     * <p>Appends with the same format as the default <code>Object toString()
     * </code> method. Appends the class name followed by
     * {@link System#identityHashCode(Object)}.</p>
     *
     * @param object the <code>Object</code> whose class name and id to output
     * @return this
     * @since 2.0
     */
    public ToStringAssist appendAsObjectToString(Object object) {
        if (object == null) {
            throw new NullPointerException("Cannot get the toString of a null identity");
        }
        buffer.append(object.getClass().getName())
                .append('@')
                .append(Integer.toHexString(System.identityHashCode(object)));

        return this;
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Append the <code>toString</code> from the superclass.</p>
     * <p>
     * <p>This method assumes that the superclass uses the same <code>ToStringStyle</code>
     * as this one.</p>
     * <p>
     * <p>If <code>superToString</code> is <code>null</code>, no change is made.</p>
     *
     * @param superToString the result of <code>super.toString()</code>
     * @return this
     * @since 2.0
     */
    public ToStringAssist appendSuper(String superToString) {
        if (superToString != null) {
            style.appendSuper(buffer, superToString);
        }
        return this;
    }

    /**
     * <p>Append the <code>toString</code> from another object.</p>
     * <p>
     * <p>This method is useful where a class delegates most of the implementation of
     * its properties to another class. You can then call <code>toString()</code> on
     * the other class and pass the result into this method.</p>
     * <p>
     * <pre>
     *   private AnotherObject delegate;
     *   private String fieldInThisClass;
     *
     *   public String toString() {
     *     return new ToStringAssist(this).
     *       appendToString(delegate.toString()).
     *       append(fieldInThisClass).
     *       toString();
     *   }</pre>
     * <p>
     * <p>This method assumes that the other object uses the same <code>ToStringStyle</code>
     * as this one.</p>
     * <p>
     * <p>If the <code>toString</code> is <code>null</code>, no change is made.</p>
     *
     * @param toString the result of <code>toString()</code> on another object
     * @return this
     * @since 2.0
     */
    public ToStringAssist appendToString(String toString) {
        if (toString != null) {
            style.appendToString(buffer, toString);
        }
        return this;
    }

    /**
     * <p>Returns the <code>Object</code> being output.</p>
     *
     * @return The object being output.
     * @since 2.0
     */
    public Object getObject() {
        return object;
    }

    /**
     * <p>Gets the <code>StringBuilder</code> being populated.</p>
     *
     * @return the <code>StringBuilder</code> being populated
     */
    public StringBuilder getStringBuilder() {
        return buffer;
    }

    //----------------------------------------------------------------------------

    /**
     * <p>Gets the <code>ToStringStyle</code> being used.</p>
     *
     * @return the <code>ToStringStyle</code> being used
     * @since 2.0
     */
    public ToStringStyle getStyle() {
        return style;
    }

    /**
     * <p>Returns the built <code>toString</code>.</p>
     * <p>
     * <p>This method appends the end of data indicator, and can only be called once.
     * Use {@link #getStringBuilder} to get the current string state.</p>
     * <p>
     * <p>If the object is <code>null</code>, return the style's <code>nullText</code></p>
     *
     * @return the String <code>toString</code>
     */
    public String toString() {
        if (this.getObject() == null) {
            this.getStringBuilder().append(this.getStyle().getNullText());
        } else {
            style.appendEnd(this.getStringBuilder(), this.getObject());
        }
        return this.getStringBuilder().toString();
    }

}
