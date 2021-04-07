###Jackson Custom Naming Strategy

```java
		ObjectMapper mapper = new ObjectMapper();
		mapper.setPropertyNamingStrategy(new PropertyNamingStrategy() {

			@Override
			public String nameForField(MapperConfig<?> config, AnnotatedField field, String defaultName) {
				return field.getName();
			}

			@Override
			public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
				return convert(method, defaultName);
			}

			@Override
			public String nameForSetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
				return convert(method, defaultName);
			}

			/**
			 * get the class from getter/setter methods
			 * 
			 * @param method
			 * @param defaultName
			 *            - jackson generated name
			 * @return the correct property name
			 */
			private String convert(AnnotatedMethod method, String defaultName) {
				Class<?> clazz = method.getDeclaringClass();
				List<Field> flds = getAllFields(clazz);
				for (Field fld : flds) {
					if (fld.getName().equalsIgnoreCase(defaultName)) {
						return fld.getName();
					}
				}

				return defaultName;
			}

			/**
			 * get all fields from class and its parent classes
			 * 
			 * @param currentClass
			 *            - should not be null
			 * @return fields from the currentClass and its superclass
			 */
			private List<Field> getAllFields(Class<?> currentClass) {
				List<Field> flds = new ArrayList<>();
				while (currentClass != null) {
					Field[] fields = currentClass.getDeclaredFields();
					Collections.addAll(flds, fields);
					if (currentClass.getSuperclass() == null)
						break;
					currentClass = currentClass.getSuperclass();
				}
				return flds;
			}
		});
```
