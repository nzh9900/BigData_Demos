//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.ni.flink.api.pojo;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.IntStream;

public class MemorySize implements Serializable, Comparable<MemorySize> {
    private static final long serialVersionUID = 1L;
    public static final MemorySize ZERO = new MemorySize(0L);
    public static final MemorySize MAX_VALUE = new MemorySize(Long.MAX_VALUE);
    private static final List<MemoryUnit> ORDERED_UNITS;
    private final long bytes;
    private transient String stringified;
    private transient String humanReadableStr;

    public MemorySize(long bytes) {
        if (bytes < 0L) {
            throw new IllegalArgumentException("bytes must be >= 0");
        }
        this.bytes = bytes;
    }

    public static MemorySize ofMebiBytes(long mebiBytes) {
        return new MemorySize(mebiBytes << 20);
    }

    public long getBytes() {
        return this.bytes;
    }

    public long getKibiBytes() {
        return this.bytes >> 10;
    }

    public int getMebiBytes() {
        return (int) (this.bytes >> 20);
    }

    public long getGibiBytes() {
        return this.bytes >> 30;
    }

    public long getTebiBytes() {
        return this.bytes >> 40;
    }

    public int hashCode() {
        return (int) (this.bytes ^ this.bytes >>> 32);
    }

    public boolean equals(Object obj) {
        return obj == this || obj != null && obj.getClass() == this.getClass() && ((MemorySize) obj).bytes == this.bytes;
    }

    public String toString() {
        if (this.stringified == null) {
            this.stringified = this.formatToString();
        }

        return this.stringified;
    }

    private String formatToString() {
        MemoryUnit highestIntegerUnit = (MemoryUnit) IntStream.range(0, ORDERED_UNITS.size()).sequential().filter((idx) -> {
            return this.bytes % ((MemoryUnit) ORDERED_UNITS.get(idx)).getMultiplier() != 0L;
        }).boxed().findFirst().map((idx) -> {
            return idx == 0 ? (MemoryUnit) ORDERED_UNITS.get(0) : (MemoryUnit) ORDERED_UNITS.get(idx - 1);
        }).orElse(MemorySize.MemoryUnit.BYTES);
        return String.format("%d %s", this.bytes / highestIntegerUnit.getMultiplier(), highestIntegerUnit.getUnits()[1]);
    }

    public String toHumanReadableString() {
        if (this.humanReadableStr == null) {
            this.humanReadableStr = this.formatToHumanReadableString();
        }

        return this.humanReadableStr;
    }

    private String formatToHumanReadableString() {
        Optional<Integer> var10000 = IntStream.range(0, ORDERED_UNITS.size()).sequential().filter((idx) -> {
            return this.bytes > ((MemoryUnit) ORDERED_UNITS.get(idx)).getMultiplier();
        }).boxed().max(Comparator.naturalOrder());
        List<MemoryUnit> var10001 = ORDERED_UNITS;
        var10001.getClass();
        MemoryUnit highestUnit = (MemoryUnit) var10000.map(var10001::get).orElse(MemorySize.MemoryUnit.BYTES);
        if (highestUnit == MemorySize.MemoryUnit.BYTES) {
            return String.format("%d %s", this.bytes, MemorySize.MemoryUnit.BYTES.getUnits()[1]);
        } else {
            double approximate = 1.0 * (double) this.bytes / (double) highestUnit.getMultiplier();
            return String.format(Locale.ROOT, "%.3f%s (%d bytes)", approximate, highestUnit.getUnits()[1], this.bytes);
        }
    }

    public int compareTo(MemorySize that) {
        return Long.compare(this.bytes, that.bytes);
    }

    public MemorySize add(MemorySize that) {
        return new MemorySize(Math.addExact(this.bytes, that.bytes));
    }

    public MemorySize subtract(MemorySize that) {
        return new MemorySize(Math.subtractExact(this.bytes, that.bytes));
    }

    public MemorySize multiply(double multiplier) {
        if (multiplier < 0.0) {
            throw new IllegalArgumentException("multiplier must be >= 0");
        }
        BigDecimal product = BigDecimal.valueOf(this.bytes).multiply(BigDecimal.valueOf(multiplier));
        if (product.compareTo(BigDecimal.valueOf(Long.MAX_VALUE)) > 0) {
            throw new ArithmeticException("long overflow");
        } else {
            return new MemorySize(product.longValue());
        }
    }

    public MemorySize divide(long by) {
        if (by < 0L) {
            throw new IllegalArgumentException("divisor must be >= 0");
        }
        return new MemorySize(this.bytes / by);
    }

    public static MemorySize parse(String text) throws IllegalArgumentException {
        return new MemorySize(parseBytes(text));
    }

    public static MemorySize parse(String text, MemoryUnit defaultUnit) throws IllegalArgumentException {
        return !MemorySize.MemoryUnit.hasUnit(text) ? parse(text + defaultUnit.getUnits()[0]) : parse(text);
    }

    public static long parseBytes(String text) throws IllegalArgumentException {
        if (text == null) {
            throw new IllegalArgumentException("text can not be null");
        }
        String trimmed = text.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("argument is an empty- or whitespace-only string");
        }
        int len = trimmed.length();

        int pos;
        char current;
        for (pos = 0; pos < len && (current = trimmed.charAt(pos)) >= '0' && current <= '9'; ++pos) {
        }

        String number = trimmed.substring(0, pos);
        String unit = trimmed.substring(pos).trim().toLowerCase(Locale.US);
        if (number.isEmpty()) {
            throw new NumberFormatException("text does not start with a number");
        } else {
            long value;
            try {
                value = Long.parseLong(number);
            } catch (NumberFormatException var13) {
                throw new IllegalArgumentException("The value '" + number + "' cannot be re represented as 64bit number (numeric overflow).");
            }

            long multiplier = (Long) parseUnit(unit).map(MemoryUnit::getMultiplier).orElse(1L);
            long result = value * multiplier;
            if (result / multiplier != value) {
                throw new IllegalArgumentException("The value '" + text + "' cannot be re represented as 64bit number of bytes (numeric overflow).");
            } else {
                return result;
            }
        }
    }

    private static Optional<MemoryUnit> parseUnit(String unit) {
        if (matchesAny(unit, MemorySize.MemoryUnit.BYTES)) {
            return Optional.of(MemorySize.MemoryUnit.BYTES);
        } else if (matchesAny(unit, MemorySize.MemoryUnit.KILO_BYTES)) {
            return Optional.of(MemorySize.MemoryUnit.KILO_BYTES);
        } else if (matchesAny(unit, MemorySize.MemoryUnit.MEGA_BYTES)) {
            return Optional.of(MemorySize.MemoryUnit.MEGA_BYTES);
        } else if (matchesAny(unit, MemorySize.MemoryUnit.GIGA_BYTES)) {
            return Optional.of(MemorySize.MemoryUnit.GIGA_BYTES);
        } else if (matchesAny(unit, MemorySize.MemoryUnit.TERA_BYTES)) {
            return Optional.of(MemorySize.MemoryUnit.TERA_BYTES);
        } else if (!unit.isEmpty()) {
            throw new IllegalArgumentException("Memory size unit '" + unit + "' does not match any of the recognized units: " + MemorySize.MemoryUnit.getAllUnits());
        } else {
            return Optional.empty();
        }
    }

    private static boolean matchesAny(String str, MemoryUnit unit) {
        String[] var2 = unit.getUnits();
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            String s = var2[var4];
            if (s.equals(str)) {
                return true;
            }
        }

        return false;
    }

    static {
        ORDERED_UNITS = Arrays.asList(MemorySize.MemoryUnit.BYTES, MemorySize.MemoryUnit.KILO_BYTES, MemorySize.MemoryUnit.MEGA_BYTES, MemorySize.MemoryUnit.GIGA_BYTES, MemorySize.MemoryUnit.TERA_BYTES);
    }

    public static enum MemoryUnit {
        BYTES(new String[]{"b", "bytes"}, 1L),
        KILO_BYTES(new String[]{"k", "kb", "kibibytes"}, 1024L),
        MEGA_BYTES(new String[]{"m", "mb", "mebibytes"}, 1048576L),
        GIGA_BYTES(new String[]{"g", "gb", "gibibytes"}, 1073741824L),
        TERA_BYTES(new String[]{"t", "tb", "tebibytes"}, 1099511627776L);

        private final String[] units;
        private final long multiplier;

        private MemoryUnit(String[] units, long multiplier) {
            this.units = units;
            this.multiplier = multiplier;
        }

        public String[] getUnits() {
            return this.units;
        }

        public long getMultiplier() {
            return this.multiplier;
        }

        public static String getAllUnits() {
            return concatenateUnits(BYTES.getUnits(), KILO_BYTES.getUnits(), MEGA_BYTES.getUnits(), GIGA_BYTES.getUnits(), TERA_BYTES.getUnits());
        }

        public static boolean hasUnit(String text) {
            if (text == null) {
                throw new IllegalArgumentException("text can not be null");
            }
            String trimmed = text.trim();
            if (trimmed.isEmpty()) {
                throw new IllegalArgumentException("argument is an empty- or whitespace-only string");
            }
            int len = trimmed.length();

            int pos;
            char current;
            for (pos = 0; pos < len && (current = trimmed.charAt(pos)) >= '0' && current <= '9'; ++pos) {
            }

            String unit = trimmed.substring(pos).trim().toLowerCase(Locale.US);
            return unit.length() > 0;
        }

        private static String concatenateUnits(String[]... allUnits) {
            StringBuilder builder = new StringBuilder(128);
            String[][] var2 = allUnits;
            int var3 = allUnits.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                String[] units = var2[var4];
                builder.append('(');
                String[] var6 = units;
                int var7 = units.length;

                for (int var8 = 0; var8 < var7; ++var8) {
                    String unit = var6[var8];
                    builder.append(unit);
                    builder.append(" | ");
                }

                builder.setLength(builder.length() - 3);
                builder.append(") / ");
            }

            builder.setLength(builder.length() - 3);
            return builder.toString();
        }
    }
}
