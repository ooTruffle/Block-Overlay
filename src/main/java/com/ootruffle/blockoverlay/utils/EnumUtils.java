package com.ootruffle.blockoverlay.utils;

import java.util.Arrays;

public class EnumUtils {
   public EnumUtils() {}

   // Get the ordinal value of an enum by name
   public static <T extends Enum<T>> int getOrdinal(Class<T> clazz, String name) {
      T enumValue = fromName(clazz, name);
      return (enumValue != null) ? enumValue.ordinal() : -1;
   }

   // Get the next enum value in a circular manner
   public static <T extends Enum<T>> T getNext(Class<T> clazz, String name) {
      T[] enums = clazz.getEnumConstants();
      T enumValue = fromName(clazz, name);
      if (enumValue == null) return enums[0]; // Default to the first enum if not found
      return enums[(enumValue.ordinal() + 1) % enums.length];
   }

   // Find an enum by name
   public static <T extends Enum<T>> T fromName(Class<T> clazz, String name) {
      try {
         return Enum.valueOf(clazz, name);
      } catch (IllegalArgumentException e) {
         return null; // Return null if the name isn't valid
      }
   }

   // Get all enum names
   public static String[] getNames(Class<? extends Enum<?>> clazz) {
      return Arrays.stream(clazz.getEnumConstants()).map(Enum::name).toArray(String[]::new);
   }
}