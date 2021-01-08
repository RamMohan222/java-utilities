### Custom Annotation for to prevent the particular properties from serialization with Gson.
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GsonIgnore {
}

private static ExclusionStrategy strategy = new ExclusionStrategy() {
		@Override
		public boolean shouldSkipClass(Class<?> clazz) {
			return false;
		}

		@Override
		public boolean shouldSkipField(FieldAttributes field) {
			return field.getAnnotation(GsonIgnore.class) != null;
		}
	};

private static Gson gson = new GsonBuilder().setExclusionStrategies(strategy).setPrettyPrinting().create();
```
